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
package org.eclipse.winery.model.tosca.yaml.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.winery.model.tosca.yaml.TArtifactDefinition;
import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TAttributeAssignment;
import org.eclipse.winery.model.tosca.yaml.TAttributeDefinition;
import org.eclipse.winery.model.tosca.yaml.TCapabilityAssignment;
import org.eclipse.winery.model.tosca.yaml.TCapabilityDefinition;
import org.eclipse.winery.model.tosca.yaml.TCapabilityType;
import org.eclipse.winery.model.tosca.yaml.TConstraintClause;
import org.eclipse.winery.model.tosca.yaml.TDataType;
import org.eclipse.winery.model.tosca.yaml.TEntityType;
import org.eclipse.winery.model.tosca.yaml.TEntrySchema;
import org.eclipse.winery.model.tosca.yaml.TGroupDefinition;
import org.eclipse.winery.model.tosca.yaml.TGroupType;
import org.eclipse.winery.model.tosca.yaml.TImplementation;
import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TInterfaceAssignment;
import org.eclipse.winery.model.tosca.yaml.TInterfaceDefinition;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TNodeFilterDefinition;
import org.eclipse.winery.model.tosca.yaml.TNodeTemplate;
import org.eclipse.winery.model.tosca.yaml.TNodeType;
import org.eclipse.winery.model.tosca.yaml.TOperationDefinition;
import org.eclipse.winery.model.tosca.yaml.TParameterDefinition;
import org.eclipse.winery.model.tosca.yaml.TPolicyDefinition;
import org.eclipse.winery.model.tosca.yaml.TPolicyType;
import org.eclipse.winery.model.tosca.yaml.TPropertyAssignment;
import org.eclipse.winery.model.tosca.yaml.TPropertyAssignmentOrDefinition;
import org.eclipse.winery.model.tosca.yaml.TPropertyDefinition;
import org.eclipse.winery.model.tosca.yaml.TPropertyFilterDefinition;
import org.eclipse.winery.model.tosca.yaml.TRelationshipAssignment;
import org.eclipse.winery.model.tosca.yaml.TRelationshipDefinition;
import org.eclipse.winery.model.tosca.yaml.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.yaml.TRelationshipType;
import org.eclipse.winery.model.tosca.yaml.TRepositoryDefinition;
import org.eclipse.winery.model.tosca.yaml.TRequirementAssignment;
import org.eclipse.winery.model.tosca.yaml.TRequirementDefinition;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.TSubstitutionMappings;
import org.eclipse.winery.model.tosca.yaml.TTopologyTemplateDefinition;
import org.eclipse.winery.model.tosca.yaml.TVersion;
import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.support.TMapImportDefinition;
import org.eclipse.winery.model.tosca.yaml.support.TMapPropertyFilterDefinition;
import org.eclipse.winery.model.tosca.yaml.support.TMapRequirementAssignment;
import org.eclipse.winery.model.tosca.yaml.support.TMapRequirementDefinition;

public abstract class AbstractVisitor implements IVisitor {

	@Override
	public IResult visit(TArtifactDefinition node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TArtifactType node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TAttributeAssignment node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TAttributeDefinition node, IParameter parameter) throws IException {
		if (node.getEntry_schema() != null) {
			node.getEntry_schema().accept(this, new Parameter(parameter, "entry_schema"));
		}
		return null;
	}

	@Override
	public IResult visit(TCapabilityAssignment node, IParameter parameter) throws IException {
		if (node.getProperties() != null) {
			for (Map.Entry<String, TPropertyAssignment> entry : node.getProperties().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
				}
			}
		}
		if (node.getAttributes() != null) {
			for (Map.Entry<String, TAttributeAssignment> entry : node.getAttributes().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "attributes", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TCapabilityDefinition node, IParameter parameter) throws IException {
		if (node.getProperties() != null) {
			for (Map.Entry<String, TPropertyDefinition> entry : node.getProperties().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
				}
			}
		}
		if (node.getAttributes() != null) {
			for (Map.Entry<String, TAttributeDefinition> entry : node.getAttributes().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "attributes", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TCapabilityType node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TConstraintClause node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TDataType node, IParameter parameter) throws IException {
		if (node.getConstraints() != null) {
			for (TConstraintClause entry : node.getConstraints()) {
				entry.accept(this, new Parameter(parameter, "constraints"));
			}
		}
		return null;
	}

	@Override
	public IResult visit(TEntityType node, IParameter parameter) throws IException {
		if (node.getVersion() != null) {
			node.getVersion().accept(this, new Parameter(parameter, "version"));
		}
		if (node.getProperties() != null) {
			for (Map.Entry<String, TPropertyDefinition> entry : node.getProperties().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
				}
			}
		}
		if (node.getAttributes() != null) {
			for (Map.Entry<String, TAttributeDefinition> entry : node.getAttributes().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "attributes", entry.getKey()));
				}
			}
		}
		if (node.getMetadata() != null) {
			node.getMetadata().accept(this, new Parameter(parameter, "metadata"));
		}
		return null;
	}

	@Override
	public IResult visit(TEntrySchema node, IParameter parameter) throws IException {
		if (node.getConstraints() != null) {
			for (TConstraintClause entry : node.getConstraints()) {
				entry.accept(this, new Parameter(parameter, "constraints"));
			}
		}
		return null;
	}

	@Override
	public IResult visit(TGroupDefinition node, IParameter parameter) throws IException {
		if (node.getMetadata() != null) {
			node.getMetadata().accept(this, new Parameter(parameter, "metadata"));
		}
		if (node.getProperties() != null) {
			for (Map.Entry<String, TPropertyAssignment> entry : node.getProperties().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
				}
			}
		}
		if (node.getInterfaces() != null) {
			for (Map.Entry<String, TInterfaceDefinition> entry : node.getInterfaces().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interfaces", entry.getKey()));
				}
			}
		}

		return null;
	}

	@Override
	public IResult visit(TGroupType node, IParameter parameter) throws IException {
		if (node.getRequirements() != null) {
			for (TMapRequirementDefinition map : node.getRequirements()) {
				for (Map.Entry<String, TRequirementDefinition> entry : map.entrySet()) {
					if (entry.getValue() != null) {
						entry.getValue().accept(this, new Parameter(parameter, "requirements", entry.getKey()));
					}
				}
			}
		}
		if (node.getCapabilities() != null) {
			for (Map.Entry<String, TCapabilityDefinition> entry : node.getCapabilities().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "capabilities", entry.getKey()));
				}
			}
		}
		if (node.getInterfaces() != null) {
			for (Map.Entry<String, TInterfaceDefinition> entry : node.getInterfaces().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interfaces", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TImplementation node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TImportDefinition node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TInterfaceAssignment node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TInterfaceDefinition node, IParameter parameter) throws IException {
		if (node.getInputs() != null) {
			for (Map.Entry<String, TPropertyAssignmentOrDefinition> entry : node.getInputs().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "inputs", entry.getKey()));
				}
			}
		}
		if (node.getOperations() != null) {
			for (Map.Entry<String, TOperationDefinition> entry : node.getOperations().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "operations", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TInterfaceType node, IParameter parameter) throws IException {
		if (node.getInputs() != null) {
			for (Map.Entry<String, TPropertyDefinition> entry : node.getInputs().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "inputs", entry.getKey()));
				}
			}
		}
		if (node.getOperations() != null) {
			for (Map.Entry<String, TOperationDefinition> entry : node.getOperations().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "operations", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TNodeFilterDefinition node, IParameter parameter) throws IException {
		if (node.getProperties() != null) {
			for (TMapPropertyFilterDefinition map : node.getProperties()) {
				for (Map.Entry<String, TPropertyFilterDefinition> entry : map.entrySet()) {
					if (entry.getValue() != null) {
						entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
					}
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TNodeTemplate node, IParameter parameter) throws IException {
		if (node.getMetadata() != null) {
			node.getMetadata().accept(this, new Parameter(parameter, "metadata"));
		}
		if (node.getProperties() != null) {
			for (Map.Entry<String, TPropertyAssignment> entry : node.getProperties().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
				}
			}
		}
		if (node.getAttributes() != null) {
			for (Map.Entry<String, TAttributeAssignment> entry : node.getAttributes().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "attributes", entry.getKey()));
				}
			}
		}
		if (node.getRequirements() != null) {
			for (TMapRequirementAssignment map : node.getRequirements()) {
				for (Map.Entry<String, TRequirementAssignment> entry : map.entrySet()) {
					if (entry.getValue() != null) {
						entry.getValue().accept(this, new Parameter(parameter, "requirements", entry.getKey()));
					}
				}
			}
		}
		if (node.getCapabilities() != null) {
			for (Map.Entry<String, TCapabilityAssignment> entry : node.getCapabilities().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "capabilities", entry.getKey()));
				}
			}
		}
		if (node.getInterfaces() != null) {
			for (Map.Entry<String, TInterfaceDefinition> entry : node.getInterfaces().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interfaces", entry.getKey()));
				}
			}
		}
		if (node.getArtifacts() != null) {
			for (Map.Entry<String, TArtifactDefinition> entry : node.getArtifacts().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "artifacts", entry.getKey()));
				}
			}
		}
		if (node.getNode_filter() != null) {
			node.getNode_filter().accept(this, new Parameter(parameter, "node_filter"));
		}
		return null;
	}

	@Override
	public IResult visit(TNodeType node, IParameter parameter) throws IException {
		if (node.getAttributes() != null) {
			for (Map.Entry<String, TAttributeDefinition> entry : node.getAttributes().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "attributes", entry.getKey()));
				}
			}
		}
		if (node.getRequirements() != null) {
			for (TMapRequirementDefinition map : node.getRequirements()) {
				for (Map.Entry<String, TRequirementDefinition> entry : map.entrySet()) {
					if (entry.getValue() != null) {
						entry.getValue().accept(this, new Parameter(parameter, "requirements", entry.getKey()));
					}
				}
			}
		}
		if (node.getCapabilities() != null) {
			for (Map.Entry<String, TCapabilityDefinition> entry : node.getCapabilities().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "capabilities", entry.getKey()));
				}
			}
		}
		if (node.getInterfaces() != null) {
			for (Map.Entry<String, TInterfaceDefinition> entry : node.getInterfaces().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interfaces", entry.getKey()));
				}
			}
		}
		if (node.getArtifacts() != null) {
			for (Map.Entry<String, TArtifactDefinition> entry : node.getArtifacts().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "artifacts", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TOperationDefinition node, IParameter parameter) throws IException {
		if (node.getInputs() != null) {
			for (Map.Entry<String, TPropertyAssignmentOrDefinition> entry : node.getInputs().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "inputs", entry.getKey()));
				}
			}
		}
		if (node.getImplementation() != null) {
			node.getImplementation().accept(this, new Parameter(parameter, "implementation"));
		}
		return null;
	}

	@Override
	public IResult visit(TParameterDefinition node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TPolicyDefinition node, IParameter parameter) throws IException {
		if (node.getMetadata() != null) {
			node.getMetadata().accept(this, new Parameter(parameter, "metadata"));
		}
		if (node.getProperties() != null) {
			for (Map.Entry<String, TPropertyAssignment> entry : node.getProperties().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TPolicyType node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TPropertyAssignment node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TPropertyDefinition node, IParameter parameter) throws IException {
		if (node.getConstraints() != null) {
			for (TConstraintClause entry : node.getConstraints()) {
				entry.accept(this, new Parameter(parameter, "constraints"));
			}
		}
		if (node.getEntry_schema() != null) {
			node.getEntry_schema().accept(this, new Parameter(parameter, "entry_schema"));
		}
		return null;
	}

	@Override
	public IResult visit(TPropertyFilterDefinition node, IParameter parameter) throws IException {
		if (node.getConstraints() != null) {
			for (TConstraintClause entry : node.getConstraints()) {
				entry.accept(this, new Parameter(parameter, "constraints"));
			}
		}
		return null;
	}

	@Override
	public IResult visit(TRelationshipAssignment node, IParameter parameter) throws IException {
		if (node.getProperties() != null) {
			for (Map.Entry<String, TPropertyAssignment> entry : node.getProperties().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
				}
			}
		}
		if (node.getInterfaces() != null) {
			for (Map.Entry<String, TInterfaceAssignment> entry : node.getInterfaces().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interfaces", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TRelationshipDefinition node, IParameter parameter) throws IException {
		if (node.getInterfaces() != null) {
			for (Map.Entry<String, TInterfaceDefinition> entry : node.getInterfaces().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interfaces", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TRelationshipTemplate node, IParameter parameter) throws IException {
		if (node.getMetadata() != null) {
			node.getMetadata().accept(this, new Parameter(parameter, "metadata"));
		}
		if (node.getProperties() != null) {
			for (Map.Entry<String, TPropertyAssignment> entry : node.getProperties().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "properties", entry.getKey()));
				}
			}
		}
		if (node.getAttributes() != null) {
			for (Map.Entry<String, TAttributeAssignment> entry : node.getAttributes().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "attributes", entry.getKey()));
				}
			}
		}
		if (node.getInterfaces() != null) {
			for (Map.Entry<String, TInterfaceDefinition> entry : node.getInterfaces().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interfaces", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TRelationshipType node, IParameter parameter) throws IException {
		if (node.getInterfaces() != null) {
			for (Map.Entry<String, TInterfaceDefinition> entry : node.getInterfaces().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interfaces", entry.getKey()));
				}
			}
		}
		return null;
	}

	@Override
	public IResult visit(TRepositoryDefinition node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TRequirementAssignment node, IParameter parameter) throws IException {
		if (node.getRelationship() != null) {
			node.getRelationship().accept(this, new Parameter(parameter, "relationship"));
		}
		if (node.getNode_filter() != null) {
			node.getNode_filter().accept(this, new Parameter(parameter, "node_filter"));
		}
		return null;
	}

	@Override
	public IResult visit(TRequirementDefinition node, IParameter parameter) throws IException {
		if (node.getRelationship() != null) {
			node.getRelationship().accept(this, new Parameter(parameter, "relationship"));
		}
		return null;
	}

	@Override
	public IResult visit(TServiceTemplate node, IParameter parameter) throws IException {
		if (node.getMetadata() != null) {
			node.getMetadata().accept(this, new Parameter(parameter, "metadata"));
		}
		if (node.getRepositories() != null) {
			for (Map.Entry<String, TRepositoryDefinition> entry : node.getRepositories().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "repositories", entry.getKey()));
				}
			}
		}
		if (node.getImports() != null) {
			for (TMapImportDefinition map : node.getImports()) {
				for (Map.Entry<String, TImportDefinition> entry : map.entrySet()) {
					if (entry.getValue() != null) {
						entry.getValue().accept(this, new Parameter(parameter, "imports", entry.getKey()));
					}
				}
			}
		}
		if (node.getArtifact_types() != null) {
			for (Map.Entry<String, TArtifactType> entry : node.getArtifact_types().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "artifact_types", entry.getKey()));
				}
			}
		}
		if (node.getData_types() != null) {
			for (Map.Entry<String, TDataType> entry : node.getData_types().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "data_types", entry.getKey()));
				}
			}
		}
		if (node.getCapability_types() != null) {
			for (Map.Entry<String, TCapabilityType> entry : node.getCapability_types().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "capability_types", entry.getKey()));
				}
			}
		}
		if (node.getInterface_types() != null) {
			for (Map.Entry<String, TInterfaceType> entry : node.getInterface_types().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "interface_types", entry.getKey()));
				}
			}
		}
		if (node.getRelationship_types() != null) {
			for (Map.Entry<String, TRelationshipType> entry : node.getRelationship_types().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "relationship_types", entry.getKey()));
				}
			}
		}
		if (node.getNode_types() != null) {
			for (Map.Entry<String, TNodeType> entry : node.getNode_types().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "node_types", entry.getKey()));
				}
			}
		}
		if (node.getGroup_types() != null) {
			for (Map.Entry<String, TGroupType> entry : node.getGroup_types().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "group_types", entry.getKey()));
				}
			}
		}
		if (node.getPolicy_types() != null) {
			for (Map.Entry<String, TPolicyType> entry : node.getPolicy_types().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "policy_types", entry.getKey()));
				}
			}
		}
		if (node.getTopology_template() != null) {
			node.getTopology_template().accept(this, new Parameter(parameter, "topology_template"));
		}
		return null;
	}

	@Override
	public IResult visit(TSubstitutionMappings node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(TTopologyTemplateDefinition node, IParameter parameter) throws IException {
		if (node.getInputs() != null) {
			for (Map.Entry<String, TParameterDefinition> entry : node.getInputs().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "inputs", entry.getKey()));
				}
			}
		}
		if (node.getNode_templates() != null) {
			for (Map.Entry<String, TNodeTemplate> entry : node.getNode_templates().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "node_templates", entry.getKey()));
				}
			}
		}
		if (node.getRelationship_templates() != null) {
			for (Map.Entry<String, TRelationshipTemplate> entry : node.getRelationship_templates().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "relationship_templates", entry.getKey()));
				}
			}
		}
		if (node.getGroups() != null) {
			for (Map.Entry<String, TGroupDefinition> entry : node.getGroups().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "groups", entry.getKey()));
				}
			}
		}
		if (node.getPolicies() != null) {
			for (Map.Entry<String, TPolicyDefinition> entry : node.getPolicies().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "policies", entry.getKey()));
				}
			}
		}
		if (node.getOutputs() != null) {
			for (Map.Entry<String, TParameterDefinition> entry : node.getOutputs().entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().accept(this, new Parameter(parameter, "outputs", entry.getKey()));
				}
			}
		}
		if (node.getSubstitution_mappings() != null) {
			node.getSubstitution_mappings().accept(this, new Parameter(parameter, "substitution_mappings"));
		}
		return null;
	}

	@Override
	public IResult visit(TVersion node, IParameter parameter) throws IException {
		return null;
	}

	@Override
	public IResult visit(Metadata node, IParameter parameter) throws IException {
		return null;
	}

	public static class Parameter implements IParameter {
		private List<String> context;

		public Parameter() {
			this.context = new ArrayList<>();
		}

		public Parameter(IParameter parameter, String contextEnd) {
			this.context = new ArrayList<>();
			this.context.addAll(parameter.getContext());
			this.context.add(contextEnd);
		}

		public Parameter(IParameter parameter, String contextPart, String contextEnd) {
			this.context = new ArrayList<>();
			this.context.addAll(parameter.getContext());
			this.context.add(contextPart);
			this.context.add(contextEnd);
		}

		@Override
		public String getKey() {
			if (this.context.size() > 0) {
				return this.context.get(this.context.size() - 1);
			} else return "";
		}

		@Override
		public List<String> getContext() {
			return this.context;
		}
	}
}
