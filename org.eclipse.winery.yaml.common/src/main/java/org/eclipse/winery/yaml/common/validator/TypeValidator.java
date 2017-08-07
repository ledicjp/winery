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

import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TCapabilityType;
import org.eclipse.winery.model.tosca.yaml.TDataType;
import org.eclipse.winery.model.tosca.yaml.TEntrySchema;
import org.eclipse.winery.model.tosca.yaml.TGroupType;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TNodeTemplate;
import org.eclipse.winery.model.tosca.yaml.TNodeType;
import org.eclipse.winery.model.tosca.yaml.TPolicyType;
import org.eclipse.winery.model.tosca.yaml.TPropertyDefinition;
import org.eclipse.winery.model.tosca.yaml.TRelationshipType;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractVisitor;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.yaml.common.Defaults;
import org.eclipse.winery.yaml.common.Exception.InvalidNativeTypeExtend;
import org.eclipse.winery.yaml.common.Exception.InvalidParentType;
import org.eclipse.winery.yaml.common.Exception.MissingNodeType;
import org.eclipse.winery.yaml.common.Exception.UnknownCapabilitySourceType;
import org.eclipse.winery.yaml.common.Exception.UnknownDataType;
import org.eclipse.winery.yaml.common.Namespaces;
import org.eclipse.winery.yaml.common.validator.support.Parameter;
import org.eclipse.winery.yaml.common.validator.support.Result;

public class TypeValidator extends AbstractVisitor<Result, Parameter> {
	private TypeVisitor typeVisitor;

	public TypeValidator(String path, String namespace) {
		this.typeVisitor = new TypeVisitor(namespace, path);
		this.typeVisitor.addDataTypes(Defaults.YAML_TYPES, Namespaces.YAML_NS);
		this.typeVisitor.addDataTypes(Defaults.TOSCA_TYPES, Namespaces.TOSCA_NS);
	}

	public void validate(TServiceTemplate serviceTemplate) throws IException {
		this.typeVisitor.visit(serviceTemplate, new Parameter());

		this.visit(serviceTemplate, new Parameter());
	}

	/**
	 * Validates that a map of lists contains a list mapped to the namespace uri of a QName
	 * and the list contains the local name of the QName
	 */
	private Boolean validateTypeIsDefined(QName type, Map<String, List<String>> map) {
		return map.containsKey(type.getNamespaceURI()) && map.get(type.getNamespaceURI()).contains(type.getLocalPart());
	}

	@Override
	public Result visit(TArtifactType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null && !validateTypeIsDefined(node.getDerived_from(), typeVisitor.getArtifactTypes())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TCapabilityType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null && !validateTypeIsDefined(node.getDerived_from(), typeVisitor.getCapabilityTypes())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}

		if (node.getValid_source_types() != null) {
			for (QName source : node.getValid_source_types()) {
				if (!validateTypeIsDefined(source, typeVisitor.getNodeTypes())) {
					String msg = "The valid source type \"" + source + "\" for the capability type \""
							+ parameter.getKey() + "\" is undefined. \n" + print(parameter.getContext());
					throw new UnknownCapabilitySourceType(msg);
				}
			}
		}

		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TDataType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null && !validateTypeIsDefined(node.getDerived_from(), typeVisitor.getDataTypes())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}

		// Extend a native DataType should fail
		if (node.getDerived_from() != null &&
				(Defaults.YAML_TYPES.contains(node.getDerived_from().getLocalPart()) ||
						Defaults.TOSCA_TYPES.contains(node.getDerived_from().getLocalPart())
				) &&
				node.getProperties() != null && node.getProperties().size() != 0) {
			String msg = "The native data type \"" + parameter.getKey() + "\" cannot be extended with properties! \n" + print(parameter.getContext());
			throw new InvalidNativeTypeExtend(msg);
		}

		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TGroupType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null && !validateTypeIsDefined(node.getDerived_from(), typeVisitor.getGroupTypes())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TInterfaceType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null && !validateTypeIsDefined(node.getDerived_from(), typeVisitor.getInterfaceTypes())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TRelationshipType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null && !validateTypeIsDefined(node.getDerived_from(), typeVisitor.getRelationshipTypes())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TNodeType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null && !validateTypeIsDefined(node.getDerived_from(), typeVisitor.getNodeTypes())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TPolicyType node, Parameter parameter) throws IException {
		if (node.getDerived_from() != null && !validateTypeIsDefined(node.getDerived_from(), typeVisitor.getPolicyTypes())) {
			String msg = "The parent type \"" + node.getDerived_from() + "\" is undefined! \n" + print(parameter.getContext());
			throw new InvalidParentType(msg);
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TNodeTemplate node, Parameter parameter) throws IException {
		if (node.getType() != null && !validateTypeIsDefined(node.getType(), typeVisitor.getNodeTypes())) {
			// TODO add parameter.getContext to exception
			throw new MissingNodeType(node.getType().toString());
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public Result visit(TPropertyDefinition node, Parameter parameter) throws IException {
		if (node.getType() != null && !validateTypeIsDefined(node.getType(), typeVisitor.getDataTypes())) {
			String msg = parameter.getKey() + ":type \"" + node.getType() + "\" is undefined!\n" + print(parameter.getContext());
			throw new UnknownDataType(msg);
		}

		if (node.getType() != null && (node.getType().getLocalPart().equals("list") || node.getType().getLocalPart().equals("map"))) {
			if (node.getEntry_schema() != null) {
				TEntrySchema entrySchema = node.getEntry_schema();
				if (entrySchema.getType() != null && !validateTypeIsDefined(entrySchema.getType(), typeVisitor.getDataTypes())) {
					String msg = parameter.getKey() + "entry_schema:type \"" + entrySchema.getType() + "\" is undefined!" + print(parameter.getContext());
					throw new UnknownDataType(msg);
				}
			}
		}

		super.visit(node, parameter);
		return null;
	}

	private String print(List<String> list) {
		return "Context::INLINE = " + String.join(":", list);
	}
}