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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TCapabilityType;
import org.eclipse.winery.model.tosca.yaml.TDataType;
import org.eclipse.winery.model.tosca.yaml.TGroupType;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TNodeType;
import org.eclipse.winery.model.tosca.yaml.TPolicyType;
import org.eclipse.winery.model.tosca.yaml.TRelationshipType;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractVisitor;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.yaml.common.Exception.InvalidParentType;

public class TypeValidator extends AbstractVisitor {
	private static final List<String> YAML_TYPES = new ArrayList<>(Arrays.asList("string", "integer", "float", "boolean", "timestamp", "null"));
	private static final List<String> TOSCA_TYPES = new ArrayList<>(Arrays.asList("list", "map"));
	private TypeVisitor typeVisitor;

	public TypeValidator(Map<String, TServiceTemplate> serviceTemplates) throws IException {
		this.typeVisitor = new TypeVisitor();
		this.typeVisitor.setDataTypes(YAML_TYPES);
		this.typeVisitor.setDataTypes(TOSCA_TYPES);

		for (Map.Entry<String, TServiceTemplate> entry : serviceTemplates.entrySet()) {
			switch (entry.getKey()) {
				case "tosca":
					this.typeVisitor.setNS("tosca");
					this.typeVisitor.visit(entry.getValue(), new Parameter());
					this.typeVisitor.setNS("");
					this.typeVisitor.visit(entry.getValue(), new Parameter());
					break;
				case "_local":
					this.typeVisitor.setNS("");
					this.typeVisitor.visit(entry.getValue(), new Parameter());
					break;
				default:
					this.typeVisitor.setNS(entry.getKey());
					this.typeVisitor.visit(entry.getValue(), new Parameter());
			}
		}
	}

	public void validate(TServiceTemplate serviceTemplate) throws IException {
		this.typeVisitor.setNS("");
		this.typeVisitor.visit(serviceTemplate, new Parameter());

		this.visit(serviceTemplate, new Parameter());
	}

	@Override
	public IResult visit(TArtifactType node, IParameter parameter) throws IException {
		if (node.getDerived_from() != null && !typeVisitor.getArtifactTypes().contains(node.getDerived_from())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TCapabilityType node, IParameter parameter) throws IException {
		if (node.getDerived_from() != null && !typeVisitor.getCapabilityTypes().contains(node.getDerived_from())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TDataType node, IParameter parameter) throws IException {
		if (node.getDerived_from() != null && !typeVisitor.getDataTypes().contains(node.getDerived_from())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TGroupType node, IParameter parameter) throws IException {
		if (node.getDerived_from() != null && !typeVisitor.getGroupTypes().contains(node.getDerived_from())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TInterfaceType node, IParameter parameter) throws IException {
		if (node.getDerived_from() != null && !typeVisitor.getInterfaceTypes().contains(node.getDerived_from())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TRelationshipType node, IParameter parameter) throws IException {
		if (node.getDerived_from() != null && !typeVisitor.getRelationshipTypes().contains(node.getDerived_from())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TNodeType node, IParameter parameter) throws IException {
		if (node.getDerived_from() != null && !typeVisitor.getNodeTypes().contains(node.getDerived_from())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TPolicyType node, IParameter parameter) throws IException {
		if (node.getDerived_from() != null && !typeVisitor.getPolicyTypes().contains(node.getDerived_from())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	private String print(List<String> list) {
		return "Context::INLINE = " + String.join(":", list);
	}
}
