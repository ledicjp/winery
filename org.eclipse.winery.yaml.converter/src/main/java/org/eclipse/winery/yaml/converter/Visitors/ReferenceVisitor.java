/*******************************************************************************
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Christoph Kleine - initial API and implementation
 *******************************************************************************/
package org.eclipse.winery.yaml.converter.Visitors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TEntityType;
import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.support.TMapImportDefinition;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractVisitor;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.yaml.common.Namespaces;
import org.eclipse.winery.yaml.common.reader.Reader;

public class ReferenceVisitor extends AbstractVisitor<ReferenceVisitor.Result, ReferenceVisitor.Parameter> {
	private final TServiceTemplate serviceTemplate;
	private final String namespace;
	private final Reader reader;
	private final String PATH;

	private Map<TImportDefinition, ReferenceVisitor> visitors;
	private Map<TImportDefinition, TServiceTemplate> serviceTemplates;

	public ReferenceVisitor(TServiceTemplate serviceTemplate, String namespace, String PATH) {
		this.serviceTemplate = serviceTemplate;
		this.namespace = namespace;
		this.reader = new Reader();
		this.PATH = PATH;
		this.visitors = new LinkedHashMap<>();
		this.serviceTemplates = new LinkedHashMap<>();
	}

	public Result getTypes(QName reference, String entityClassName) {
		try {
			return visit(serviceTemplate, new Parameter(reference, entityClassName));
		} catch (IException e) {
			e.printStackTrace();
			return new Result(null);
		}
	}

	@Override
	public Result visit(TImportDefinition node, Parameter parameter) throws IException {
		if (node.getNamespace_uri() == null && !parameter.reference.getNamespaceURI().equals(Namespaces.DEFAULT_NS)) {
			return null;
		}

		String namespace = node.getNamespace_uri() == null ? Namespaces.DEFAULT_NS : node.getNamespace_uri();
		if (!this.visitors.containsKey(node)) {
			this.serviceTemplates.put(node, reader.readImportDefinition(node, PATH, namespace));
			this.visitors.put(node, new ReferenceVisitor(this.serviceTemplates.get(node), namespace, PATH));
		}

		return this.visitors.get(node).visit(this.serviceTemplates.get(node), parameter);
	}

	@Override
	public Result visit(TEntityType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null) {
			return serviceTemplate.accept(this, new Parameter(node.getDerived_from(), parameter.entityClass)).copy(node, node.getDerived_from());
		}
		return new Result(node);
	}

	@Override
	public Result visit(TServiceTemplate node, Parameter parameter) throws IException {
		Result result;
		if (parameter.reference.getNamespaceURI().equals(this.namespace)) {
			switch (parameter.entityClass) {
				case "TArtifactType":
					if (node.getArtifact_types().containsKey(parameter.reference.getLocalPart())) {
						return node.getArtifact_types().get(parameter.reference.getLocalPart()).accept(this, parameter.copy());
					}
			}
		}

		for (TMapImportDefinition map : node.getImports()) {
			for (Map.Entry<String, TImportDefinition> entry : map.entrySet()) {
				result = entry.getValue().accept(this, parameter);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

	@Override
	public Result visit(TArtifactType node, Parameter parameter) throws IException {
		return null;
	}

	public static class Result extends AbstractResult<Result> {
		private List<Object> objects;
		private List<QName> names;

		public Result(Object object) {
			this.objects = new ArrayList<>();
			this.names = new ArrayList<>();
			if (object != null) {
				this.objects.add(object);
			}
		}

		public List<Object> getObjects() {
			return objects;
		}

		public List<QName> getNames() {
			return names;
		}

		public Result copy(Object object, QName name) {
			this.objects.add(object);
			this.names.add(name);
			return this;
		}

		@Override
		public Result add(Result result) {
			// No collecting (deep search)
			return this;
		}
	}

	public static class Parameter extends AbstractParameter<Parameter> {
		private final QName reference;
		private final String entityClass;

		public Parameter(QName reference, String entityName) {
			this.reference = reference;
			this.entityClass = entityName;
		}

		@Override
		public Parameter copy() {
			Parameter parameter = new Parameter(this.reference, this.entityClass);
			parameter.getContext().addAll(this.getContext());
			return parameter;
		}

		@Override
		public Parameter self() {
			return this;
		}
	}
}
