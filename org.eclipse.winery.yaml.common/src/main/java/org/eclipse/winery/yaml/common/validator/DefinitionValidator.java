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
package org.eclipse.winery.yaml.common.validator;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractVisitor;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.yaml.common.Exception.MissingRepositoryDefinition;
import org.eclipse.winery.yaml.common.Namespaces;

public class DefinitionValidator extends AbstractVisitor {
	private DefinitionsVisitor definitionsVisitor;

	public DefinitionValidator(String path) {
		definitionsVisitor = new DefinitionsVisitor(Namespaces.DEFAULT_NS, path);
	}

	public void validate(TServiceTemplate serviceTemplate) throws IException {
		definitionsVisitor.visit(serviceTemplate, new Parameter());
	}

	@Override
	public IResult visit(TImportDefinition node, IParameter parameter) throws IException {
		if (!isDefined(node.getRepository(), definitionsVisitor.getRepositoryDefinitions())) {
			String msg = "No Repository definition for property repository \"" +
					node.getRepository() + "\" found! \n" + print(parameter.getContext());
			throw new MissingRepositoryDefinition(msg);
		}
		return super.visit(node, parameter);
	}

	private Boolean isDefined(QName name, Map<String, List<String>> map) {
		return name == null || map.containsKey(name.getNamespaceURI()) && map.get(name.getNamespaceURI()).contains(name.getLocalPart());
	}

	private String print(List<String> list) {
		return "Context::INLINE = " + String.join(":", list);
	}
}
