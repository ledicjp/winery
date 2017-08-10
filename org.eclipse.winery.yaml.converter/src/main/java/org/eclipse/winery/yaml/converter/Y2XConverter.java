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
package org.eclipse.winery.yaml.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.winery.common.Util;
import org.eclipse.winery.model.tosca.Definitions;
import org.eclipse.winery.model.tosca.TAppliesTo;
import org.eclipse.winery.model.tosca.TArtifactReference;
import org.eclipse.winery.model.tosca.TArtifactTemplate;
import org.eclipse.winery.model.tosca.TArtifactType;
import org.eclipse.winery.model.tosca.TBoundaryDefinitions;
import org.eclipse.winery.model.tosca.TCapabilityDefinition;
import org.eclipse.winery.model.tosca.TCapabilityType;
import org.eclipse.winery.model.tosca.TDefinitions;
import org.eclipse.winery.model.tosca.TDeploymentArtifact;
import org.eclipse.winery.model.tosca.TDeploymentArtifacts;
import org.eclipse.winery.model.tosca.TEntityTemplate;
import org.eclipse.winery.model.tosca.TEntityType;
import org.eclipse.winery.model.tosca.TImplementationArtifacts;
import org.eclipse.winery.model.tosca.TImport;
import org.eclipse.winery.model.tosca.TInterface;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TNodeType;
import org.eclipse.winery.model.tosca.TNodeTypeImplementation;
import org.eclipse.winery.model.tosca.TOperation;
import org.eclipse.winery.model.tosca.TParameter;
import org.eclipse.winery.model.tosca.TPolicy;
import org.eclipse.winery.model.tosca.TPolicyTemplate;
import org.eclipse.winery.model.tosca.TPolicyType;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TRelationshipType;
import org.eclipse.winery.model.tosca.TRequirement;
import org.eclipse.winery.model.tosca.TRequirementDefinition;
import org.eclipse.winery.model.tosca.TRequirementType;
import org.eclipse.winery.model.tosca.TServiceTemplate;
import org.eclipse.winery.model.tosca.TTag;
import org.eclipse.winery.model.tosca.TTags;
import org.eclipse.winery.model.tosca.TTopologyTemplate;
import org.eclipse.winery.model.tosca.yaml.TArtifactDefinition;
import org.eclipse.winery.model.tosca.yaml.TAttributeDefinition;
import org.eclipse.winery.model.tosca.yaml.TImplementation;
import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TInterfaceDefinition;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TOperationDefinition;
import org.eclipse.winery.model.tosca.yaml.TPolicyDefinition;
import org.eclipse.winery.model.tosca.yaml.TPropertyAssignment;
import org.eclipse.winery.model.tosca.yaml.TPropertyAssignmentOrDefinition;
import org.eclipse.winery.model.tosca.yaml.TPropertyDefinition;
import org.eclipse.winery.model.tosca.yaml.TRequirementAssignment;
import org.eclipse.winery.model.tosca.yaml.TTopologyTemplateDefinition;
import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.yaml.common.Defaults;
import org.eclipse.winery.yaml.common.Namespaces;
import org.eclipse.winery.yaml.common.reader.Reader;
import org.eclipse.winery.yaml.common.writer.XML.Writer;
import org.eclipse.winery.yaml.converter.Visitors.ReferenceVisitor;
import org.eclipse.winery.yaml.converter.Visitors.SchemaVisitor;
import org.eclipse.winery.yaml.converter.support.AssignmentBuilder;
import org.eclipse.winery.yaml.converter.support.extension.TImplementationArtifactDefinition;

import org.eclipse.jdt.annotation.NonNull;

public class Y2XConverter {
	private String namespace;
	private String path;

	private List<TNodeTypeImplementation> nodeTypeImplementations;
	private Map<String, TArtifactTemplate> artifactTemplates;
	private List<TPolicyTemplate> policyTemplates;
	private List<TRequirementType> requirementTypes;
	private List<TImport> imports;

	private Map<String, TInterfaceType> interfaceTypes;

	private Map<String, List<TPolicy>> policies;
	private Map<String, List<Map.Entry<String, QName>>> relationshipSTMap;
	private Map<String, TNodeTemplate> nodeTemplateMap;

	private AssignmentBuilder assignmentBuilder;

	private ReferenceVisitor referenceVisitor;

	private void reset() {
		this.nodeTypeImplementations = new ArrayList<>();
		this.artifactTemplates = new LinkedHashMap<>();
		this.policyTemplates = new ArrayList<>();
		this.requirementTypes = new ArrayList<>();
		this.imports = new ArrayList<>();

		this.policies = new LinkedHashMap<>();
		this.relationshipSTMap = new LinkedHashMap<>();
		this.nodeTemplateMap = new LinkedHashMap<>();

		this.interfaceTypes = new LinkedHashMap<>();
	}

	/**
	 * Processes knowledge from TServiceTemplate needed to construct XML result
	 */
	private void init(org.eclipse.winery.model.tosca.yaml.TServiceTemplate node) {
		// no interface type for xml -> interface type information inserted into interface definitions
		convert(node.getInterface_types());

		SchemaVisitor schemaVisitor = new SchemaVisitor();
		try {
			schemaVisitor.visit(node, path, namespace);
		} catch (IException e) {
			e.printStackTrace();
		}
		this.assignmentBuilder = new AssignmentBuilder(schemaVisitor.getPropertyDefinition());
	}

	/**
	 * Converts TOSCA YAML ServiceTemplates to TOSCA XML Definitions
	 *
	 * @return TOSCA XML Definitions
	 */
	public Definitions convert(org.eclipse.winery.model.tosca.yaml.TServiceTemplate node, String id, String target_namespace, String path) {
		if (node == null) {
			return new Definitions();
		}

		// Reset
		this.reset();
		this.referenceVisitor = new ReferenceVisitor(node, target_namespace, path);

		this.namespace = target_namespace;
		this.path = path;

		init(node);

		Definitions.Builder builder = new Definitions.Builder(id + "_Definitions", target_namespace);

		builder.setImport(convert(node.getImports()));
		builder.addTypes(convert(node.getData_types()));
		builder.addTypes(convert(node.getGroup_types()));
		builder.addServiceTemplates(convertServiceTemplate(node, id, target_namespace));
		builder.addNodeTypes(convert(node.getNode_types()));
		builder.addNodeTypeImplementations(this.nodeTypeImplementations);
		builder.addRelationshipTypes(convert(node.getRelationship_types()));
		builder.addRequirementTypes(this.requirementTypes);
		builder.addCapabilityTypes(convert(node.getCapability_types()));
		builder.addArtifactTypes(convert(node.getArtifact_types()));
		builder.addArtifactTemplates(this.artifactTemplates.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
		builder.addPolicyTypes(convert(node.getPolicy_types()));
		builder.addPolicyTemplates(this.policyTemplates);
		builder.setName(id);

		convert(node.getImports());
		builder.addImports(this.imports);
		return builder.build();
	}

	/**
	 * Converts TOSCA YAML ServiceTemplates to TOSCA XML ServiceTemplates
	 *
	 * @param node TOSCA YAML ServiceTemplate
	 * @return TOSCA XML ServiceTemplate
	 */
	private TServiceTemplate convertServiceTemplate(org.eclipse.winery.model.tosca.yaml.TServiceTemplate node, String id, String targetNamespace) {
		if (node == null || node.getTopology_template() == null) {
			return null;
		}

		TTopologyTemplate topologyTemplate = convert(node.getTopology_template());

		TServiceTemplate.Builder builder = new TServiceTemplate.Builder(id, topologyTemplate);
		builder.addDocumentation(node.getDescription());

		TBoundaryDefinitions.Builder boundaryBuilder = new TBoundaryDefinitions.Builder();
		boundaryBuilder.addPolicies(this.policies.get("boundary"));

		builder.setBoundaryDefinitions(boundaryBuilder.build());
		builder.setName(id);
		builder.setTargetNamespace(targetNamespace);

		return builder.build();
	}

	/**
	 * Converts TPropertyDefinition and TAttributeDefinition to an xml schema
	 *
	 * @return TOSCA XML PropertyDefinition with referencing the schema of the Properties
	 */
	private TEntityType.PropertiesDefinition convertPropertyDefinition(String name) {
		TImport.Builder builder = new TImport.Builder(Namespaces.XML_NS);
		builder.setNamespace(this.namespace);
		builder.setLocation("types/" + name + ".xsd");
		this.imports.add(builder.build());

		TEntityType.PropertiesDefinition propertiesDefinition = new TEntityType.PropertiesDefinition();
		propertiesDefinition.setElement(new QName(name));
		return propertiesDefinition;
	}

	/**
	 * Converts TOSCA YAML EntityTypes to TOSCA XML EntityTypes
	 *
	 * Additional element version added to tag.
	 * Missing elements abstract, final will not be set.
	 * Missing element targetNamespace is searched in metadata
	 *
	 * @param node TOSCA YAML EntityType
	 * @return TOSCA XML EntityType
	 */
	private TEntityType convert(org.eclipse.winery.model.tosca.yaml.TEntityType node, String id) {
		TEntityType.Builder builder = new TEntityType.Builder(id);

		builder.addDocumentation(node.getDescription());
		builder.setDerivedFrom(node.getDerived_from());
		builder.addTags(convertMetadata(node.getMetadata()));

		if (node.getMetadata() != null && node.getMetadata().containsKey("targetNamespace")) {
			builder.setTargetNamespace(node.getMetadata().get("targetNamespace"));
		}

		if (node.getVersion() != null) {
			TTag tag = new TTag();
			tag.setName("version");
			tag.setValue(node.getVersion().getVersion());
			builder.addTags(tag);
		}

		builder.setPropertiesDefinition(convertPropertyDefinition(id + "_Properties"));

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML metadata to TOSCA XML Tags
	 *
	 * @param metadata map of strings
	 * @return TOSCA XML Tags
	 */
	private TTags convertMetadata(Metadata metadata) {
		if (metadata == null) {
			return null;
		}

		TTags.Builder builder = new TTags.Builder();
		for (Map.Entry<String, String> entry : metadata.entrySet()) {
			builder.addTag(entry.getKey(), entry.getValue());
		}

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML ArtifactTypes to TOSCA XML ArtifactTypes.
	 * Both objects have a super type EntityType.
	 * Additional elements mime_type and file_ext from TOSCA YAML are moved to tags in TOSCA XML
	 *
	 * @param node the YAML ArtifactType
	 * @return TOSCA XML ArtifactType
	 */
	private TArtifactType convert(org.eclipse.winery.model.tosca.yaml.TArtifactType node, String id) {
		if (node == null) {
			return null;
		}

		TEntityType entityType = convert((org.eclipse.winery.model.tosca.yaml.TEntityType) node, id);

		TArtifactType.Builder builder = new TArtifactType.Builder(entityType);

		if (node.getFile_ext() != null) {
			builder.addTags("file_ext", "[" + node.getFile_ext().stream().map(Object::toString)
					.collect(Collectors.joining(",")) + "]");
		}

		if (node.getMime_type() != null) {
			builder.addTags("mime_type", node.getMime_type());
		}

		return builder.build();
	}

	/**
	 * Converts a TOSCA YAML ArtifactDefinition to a TOSCA XML ArtifactTemplate
	 * Additional elements repository and deploy_path are put into Properties
	 *
	 * @param node TOSCA YAML ArtifactDefinition
	 * @return TOSCA XML ArtifactTemplate
	 */
	@NonNull
	private TArtifactTemplate convert(org.eclipse.winery.model.tosca.yaml.TArtifactDefinition node, String id) {
		TArtifactTemplate.Builder builder = new TArtifactTemplate.Builder(id, node.getType());

		for (String file : node.getFiles()) {
			TArtifactReference artifactReference = new TArtifactReference();
			// TODO combine repository and file to a reference
			artifactReference.setReference(file);

			builder.addArtifactReferences(artifactReference);
		}
		builder.setProperties(convertPropertyAssignments(node.getProperties(), getPropertyTypeName(node.getType())));

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML ArtifactDefinitions to TOSCA XML DeploymentArtifacts
	 *
	 * @param artifactDefinitionMap map of TOSCA YAML ArtifactDefinitions
	 * @return TOSCA XML DeploymentArtifacts
	 */
	private TDeploymentArtifacts convertDeploymentArtifacts(Map<String, TArtifactDefinition> artifactDefinitionMap) {
		TDeploymentArtifacts deploymentArtifacts = new TDeploymentArtifacts();
		for (Map.Entry<String, org.eclipse.winery.model.tosca.yaml.TArtifactDefinition> entry : artifactDefinitionMap.entrySet()) {
			TDeploymentArtifact deploymentArtifact = new TDeploymentArtifact();
			deploymentArtifact.setName(entry.getKey());
			deploymentArtifact.setArtifactType(entry.getValue().getType());

			TArtifactTemplate artifactTemplate = convert(entry.getValue(), entry.getKey());
			deploymentArtifact.setArtifactRef(new QName(artifactTemplate.getId()));

			deploymentArtifacts.getDeploymentArtifact().add(deploymentArtifact);
			this.artifactTemplates.put(artifactTemplate.getId(), artifactTemplate);
		}

		if (deploymentArtifacts.getDeploymentArtifact().size() == 0) {
			return null;
		}

		return deploymentArtifacts;
	}

	/**
	 * Converts TOSCA YAML ArtifactDefinitions to TOSCA XML ImplementationArtifacts
	 *
	 * @param artifactDefinitionMap map of TOSCA YAML ArtifactDefinitions
	 * @return TOSCA XML ImplementationArtifacts
	 */
	private TImplementationArtifacts convertImplementationArtifact(Map<String, TArtifactDefinition> artifactDefinitionMap) {
		TImplementationArtifacts implementationArtifacts = new TImplementationArtifacts();
		for (Map.Entry<String, org.eclipse.winery.model.tosca.yaml.TArtifactDefinition> entry : artifactDefinitionMap.entrySet()) {
			TImplementationArtifacts.ImplementationArtifact implementationArtifact = new TImplementationArtifacts.ImplementationArtifact();
			implementationArtifact.setName(entry.getKey());
			implementationArtifact.setArtifactType(entry.getValue().getType());

			if (entry.getValue() instanceof TImplementationArtifactDefinition) {
				implementationArtifact.setInterfaceName(((TImplementationArtifactDefinition) entry.getValue()).getInterfaceName());
				implementationArtifact.setOperationName(((TImplementationArtifactDefinition) entry.getValue()).getOperationName());
			}

			TArtifactTemplate artifactTemplate = convert(entry.getValue(), entry.getKey());
			implementationArtifact.setArtifactRef(new QName(artifactTemplate.getId()));

			implementationArtifacts.getImplementationArtifact().add(implementationArtifact);
			this.artifactTemplates.put(artifactTemplate.getId(), artifactTemplate);
		}

		if (implementationArtifacts.getImplementationArtifact().size() == 0) {
			return null;
		}

		return implementationArtifacts;
	}

	/**
	 * Converts TOSCA YAML NodeTypes to TOSCA XML NodeTypes
	 *
	 * @param node TOSCA YAML NodeType
	 * @return TOSCA XML NodeType
	 */
	private TNodeType convert(org.eclipse.winery.model.tosca.yaml.TNodeType node, String id) {
		if (node == null) {
			return null;
		}

		TEntityType entityType = convert((org.eclipse.winery.model.tosca.yaml.TEntityType) node, id);
		TNodeType.Builder builder = new TNodeType.Builder(entityType);

		// Extract Outputs from Attributes and attach them to the Operations (if possible)
		// Template: attribute.default: { get_operation_output: [ SELF, interfaceName, operationName, propertyName ] }
		for (Map.Entry<String, TAttributeDefinition> entry : node.getAttributes().entrySet()) {
			TAttributeDefinition attr = entry.getValue();
			if (attr.getDefault() != null && attr.getDefault() != null && attr.getDefault() instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) attr.getDefault();
				if (map != null && map.containsKey("get_operation_output")) {
					@SuppressWarnings("unchecked")
					List<String> values = (List<String>) map.get("get_operation_output");
					if (values.size() == 4 &&
							values.get(0).equals("SELF") &&
							node.getInterfaces().containsKey(values.get(1)) &&
							node.getInterfaces().get(values.get(1)).getOperations().containsKey(values.get(2)) &&
							!node.getInterfaces().get(values.get(1)).getOperations().get(values.get(2)).getOutputs().containsKey(values.get(3))
							) {
						TPropertyDefinition.Builder pBuilder = new TPropertyDefinition.Builder(attr.getType());
						node.getInterfaces().get(values.get(1)).getOperations().get(values.get(2)).getOutputs().put(values.get(3), pBuilder.build());
					}
				}
			}
		}

		builder.addRequirementDefinitions(convert(node.getRequirements()));
		builder.addCapabilityDefinitions(convert(node.getCapabilities()));
		builder.addInterfaces(convert(node.getInterfaces()));

		Map<String, TArtifactDefinition> implArtifacts = new LinkedHashMap<>();
		Map<String, TArtifactDefinition> deplArtifacts = new LinkedHashMap<>();

		// Split ArtifactDefinition into deployment and implementation artifacts
		node.getArtifacts().forEach((key, value) -> {
			ReferenceVisitor.Result result = referenceVisitor.getTypes(value.getType(), "TArtifactType");
			if (result.getNames().contains(Defaults.IMPLEMENTATION_ARTIFACTS)) {
				implArtifacts.put(key, value);
			} else if (result.getNames().contains(Defaults.DEPLOYMENT_ARTIFACTS)) {
				deplArtifacts.put(key, value);
			} else {
				implArtifacts.put(key, value);
				deplArtifacts.put(key, value);
			}
		});

		// Convert Interface.Operations Artifacts to ArtifactDefinition
		for (Map.Entry<String, TInterfaceDefinition> entry : node.getInterfaces().entrySet()) {
			entry.getValue().getOperations()
					.entrySet().stream()
					.filter(operation -> operation.getValue() != null && operation.getValue().getImplementation() != null)
					.forEach(operation -> {
						String interfaceName = entry.getKey();
						String operationName = operation.getKey();
						TImplementation implementation = operation.getValue().getImplementation();
						List<QName> list = implementation.getDependencies();
						if (implementation.getPrimary() != null) {
							list.add(implementation.getPrimary());
						}
						for (QName artifactQName : list) {
							String artifactName = artifactQName.getLocalPart();
							if (implArtifacts.containsKey(artifactName)) {
								TImplementationArtifactDefinition.Builder iABuilder = new TImplementationArtifactDefinition.Builder(implArtifacts.get(artifactName));
								TArtifactDefinition old = implArtifacts.get(artifactName);
								// TODO write Test!!!! (see Restrictions section in Artifacts.md
								// Check if implementation artifact is already defined for other interfaces
								if (!(old instanceof TImplementationArtifactDefinition)
										|| ((TImplementationArtifactDefinition) old).getInterfaceName() == null
										|| ((TImplementationArtifactDefinition) old).getInterfaceName().equals(interfaceName)) {
									iABuilder.setInterfaceName(interfaceName);
									// Check if ArtifactDefinition is used in more than one operation implementation 
									if (old instanceof TImplementationArtifactDefinition
											&& ((TImplementationArtifactDefinition) old).getInterfaceName().equals(interfaceName)
											&& !(((TImplementationArtifactDefinition) old).getOperationName().equals(operationName))) {
										iABuilder.setOperationName(null);
									} else {
										iABuilder.setOperationName(operationName);
									}
								} else {
									// if interface is not ImplementationArtifactDefinition
									// or interface not set
									// or interface already defined
									iABuilder.setInterfaceName(null);
								}
								iABuilder.setOperationName(operationName);

								implArtifacts.put(artifactName, iABuilder.build());
							}
						}
					});
		}

		convertNodeTypeImplementation(implArtifacts, deplArtifacts, id);

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML NodeTemplates to TOSCA XML NodeTemplates
	 * Additional TOSCA YAML element metadata is put into TOSCA XML documentation element
	 * Additional TOSCA YAML elements directives and copy are not converted
	 *
	 * @param node TOSCA YAML NodeTemplate
	 * @return TOSCA XML NodeTemplate
	 */
	private TNodeTemplate convert(org.eclipse.winery.model.tosca.yaml.TNodeTemplate node, String id) {
		if (node == null) {
			return null;
		}

		TNodeTemplate.Builder builder = new TNodeTemplate.Builder(id, node.getType());
		builder.addDocumentation(node.getDescription());
		builder.addDocumentation(node.getMetadata());
		builder.setName(id);
		builder.setProperties(convertPropertyAssignments(node.getProperties(), getPropertyTypeName(node.getType())));
		builder.addRequirements(convert(node.getRequirements()));
		builder.addCapabilities(convert(node.getCapabilities()));
		builder.setDeploymentArtifacts(convertDeploymentArtifacts(node.getArtifacts()));

		TNodeTemplate nodeTemplate = builder.build();
		this.nodeTemplateMap.put(id, nodeTemplate);

		return nodeTemplate;
	}

	/**
	 * Constructs the the name of the PropertyType for a given type
	 */
	private QName getPropertyTypeName(QName type) {
		return new QName(type.getNamespaceURI(), type.getLocalPart() + "_Properties");
	}

	/**
	 * Converts TOSCA YAML RequirementDefinition to TOSCA XML RequirementDefinition
	 *
	 * @param node TOSCA YAML RequirementDefinition
	 * @return TOSCA XML RequirementDefinition
	 */
	private TRequirementDefinition convert(org.eclipse.winery.model.tosca.yaml.TRequirementDefinition node, String id) {
		if (node == null) {
			return null;
		}

		// TOSCA YAML does not have RequirementTypes:
		// * construct TOSCA XML RequirementType from TOSCA YAML Requirement Definition
		QName requirementType = convertRequirementDefinition(node, node.getNode() + "_" + id + "_Type");

		TRequirementDefinition.Builder builder = new TRequirementDefinition.Builder(id, requirementType);

		builder.setLowerBound(node.getLowerBound());
		builder.setUpperBound(node.getUpperBound());

		// TODO Construct a Relationship:
		// * if a Requirement is fulfilled than a Relationship between the NodeTemplate containing the requirement and
		//   the NodeTemplate fulfilling the Requirement is established

		return builder.build();
	}

	/**
	 * Convert TOSCA YAML RequirementDefinition to TOSCA XML RequirementType
	 *
	 * @param node TOSCA YAML RequirementDefinition
	 * @param id   with name of the TRequirementType
	 * @return QName of the TOSCA XML RequirementType
	 */
	private QName convertRequirementDefinition(org.eclipse.winery.model.tosca.yaml.TRequirementDefinition node, String id) {
		if (node == null)
			return null;

		TEntityType.Builder ebuilder = new TEntityType.Builder(id);

		TRequirementType.Builder builder = new TRequirementType.Builder(ebuilder.build());
		builder.setRequiredCapabilityType(node.getCapability());

		TRequirementType result = builder.build();
		requirementTypes.add(result);
		return new QName(result.getName());
	}

	/**
	 * Converts TOSCA YAML RequirementAssignments to TOSCA XML Requirements
	 * Additional TOSCA YAML elements node_filter and occurrences are not converted
	 *
	 * @param node TOSCA YAML RequirementAssignments
	 * @return return List of TOSCA XML Requirements
	 */
	private TRequirement convert(TRequirementAssignment node, String id) {
		if (node == null) {
			return null;
		}

		TRequirement.Builder builder = new TRequirement.Builder(id, new QName(id + "_type"));

		if (node.getRelationship() != null && node.getNode() != null) {
			if (!this.relationshipSTMap.containsKey(node.getRelationship().getType())) {
				this.relationshipSTMap.put(node.getRelationship().getType().toString(), new ArrayList<>());
			}

			this.relationshipSTMap.get(node.getRelationship().getType()).add(new LinkedHashMap.SimpleEntry<>(id, node.getNode()));
		}

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML CapabilityTypes to TOSCA XML CapabilityTypes
	 *
	 * @param node TOSCA YAML CapabilityType
	 * @return TOSCA XML CapabilityType
	 */
	private TCapabilityType convert(org.eclipse.winery.model.tosca.yaml.TCapabilityType node, String id) {
		if (node == null) {
			return null;
		}

		TEntityType entityType = convert((org.eclipse.winery.model.tosca.yaml.TEntityType) node, id);

		TCapabilityType.Builder builder = new TCapabilityType.Builder(entityType);
		if (!node.getValid_source_types().isEmpty()) {
			builder.addTags("valid_source_types", "[" + node.getValid_source_types().stream().map(QName::toString).collect(Collectors.joining(",")) + "]");
		}
		return builder.build();
	}

	/**
	 * Converts TOSCA YAML CapabilityDefinitions to TOSCA XML CapabilityDefinitions
	 * Additional TOSCA YAML elements properties, attributes and valid_source_types are not converted
	 *
	 * @param node TOSCA YAML CapabilityDefinition
	 * @return TOSCA XML CapabilityDefinition
	 */
	private TCapabilityDefinition convert(org.eclipse.winery.model.tosca.yaml.TCapabilityDefinition node, String id) {
		if (node == null) {
			return null;
		}

		TCapabilityDefinition.Builder builder = new TCapabilityDefinition.Builder(id, node.getType());
		builder.addDocumentation(node.getDescription());

		builder.setLowerBound(node.getLowerBound());
		builder.setUpperBound(node.getUpperBound());

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML InterfaceDefinitions to TOSCA XML Interface
	 * Additional TOSCA YAML element input with PropertyAssignment or PropertyDefinition is not converted
	 *
	 * @param node TOSCA YAML InterfaceDefinition
	 * @return TOSCA XML Interface
	 */
	private TInterface convert(TInterfaceDefinition node, String id) {
		List<TOperation> operation = new ArrayList<>();
		if (this.interfaceTypes.containsKey(node.getType())) {
			operation.addAll(convert(this.interfaceTypes.get(node.getType()).getOperations()));
		}

		operation.addAll(convert(node.getOperations()));

		TInterface.Builder builder = new TInterface.Builder(id, operation);

		return builder.build();
	}

	/**
	 * Convert TOSCA YAML TopologyTemplatesDefinition to TOSCA XML TopologyTemplates
	 * Additional TOSCA YAML elements inputs, outputs, groups, policies,
	 * substitution_mappings and workflows are not converted
	 *
	 * @param node TOSCA YAML TopologyTemplateDefinition
	 * @return TOSCA XML TopologyTemplate
	 */
	private TTopologyTemplate convert(TTopologyTemplateDefinition node) {
		if (node == null) {
			return null;
		}

		TTopologyTemplate.Builder builder = new TTopologyTemplate.Builder();
		builder.addDocumentation(node.getDescription());

		builder.setNodeTemplates(convert(node.getNode_templates()));
		builder.setRelationshipTemplates(convert(node.getRelationship_templates()));
		convert(node.getPolicies());

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML RelationshipTypes to TOSCA XML RelationshipTypes
	 * Additional element valid_target_types (specifying Capability Types) is not converted
	 *
	 * @param node TOSCA YAML RelationshipType
	 * @return TOSCA XML RelationshipType
	 */
	private TRelationshipType convert(org.eclipse.winery.model.tosca.yaml.TRelationshipType node, String id) {
		if (node == null) {
			return null;
		}

		TEntityType entityType = convert((org.eclipse.winery.model.tosca.yaml.TEntityType) node, id);
		TRelationshipType.Builder builder = new TRelationshipType.Builder(entityType);

		// TODO Indicator for if Interface is SourceInterface or TargetInterface
		builder.addSourceInterfaces(convert(node.getInterfaces()));

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML RelationshipTemplate to TOSCA XML RelationshipTemplate
	 * Additional TOSCA YAML element interfaces is not converted
	 *
	 * @param node TOSCA YAML RelationshipTemplate
	 * @return TOSCA XML RelationshipTemplate
	 */
	private List<TRelationshipTemplate> convert(org.eclipse.winery.model.tosca.yaml.TRelationshipTemplate node, String idName) {
		if (node == null) {
			return null;
		}
		List<TRelationshipTemplate> relationshipTemplates = new ArrayList<>();

		QName key = node.getType();
		// Lookup type or name in relationshipSTMap
		if (this.relationshipSTMap.containsKey(node.getType())) {
			key = node.getType();
		} else if (this.relationshipSTMap.containsKey(idName)) {
			key = new QName(idName);
		} else {
			return null;
		}

		int num = 0;
		for (Map.Entry<String, QName> entry : this.relationshipSTMap.get(key)) {
			String id = idName + num++;
			QName type = node.getType();

			if (this.nodeTemplateMap.containsKey(entry.getKey()) && this.nodeTemplateMap.containsKey(entry.getValue())) {
				TRelationshipTemplate.SourceOrTargetElement sourceElement = new TRelationshipTemplate.SourceOrTargetElement();
				sourceElement.setRef(this.nodeTemplateMap.get(entry.getKey()));
				TRelationshipTemplate.SourceOrTargetElement targetElement = new TRelationshipTemplate.SourceOrTargetElement();
				targetElement.setRef(this.nodeTemplateMap.get(entry.getValue()));

				TRelationshipTemplate.Builder
						builder = new TRelationshipTemplate.Builder(id, type, sourceElement, targetElement);

				builder.setName(idName);

				relationshipTemplates.add(builder.build());
			}
		}

		return relationshipTemplates;
	}

	/**
	 * Converts TOSCA YAML PolicyTypes to TOSCA XML  PolicyTypes
	 * Additional TOSCA YAML element triggers is not converted
	 *
	 * @param node TOSCA YAML PolicyType
	 * @return TOSCA XML PolicyType
	 */
	private TPolicyType convert(org.eclipse.winery.model.tosca.yaml.TPolicyType node, String id) {
		if (node == null) {
			return null;
		}

		TEntityType entityType = convert((org.eclipse.winery.model.tosca.yaml.TEntityType) node, id);
		TPolicyType.Builder builder = new TPolicyType.Builder(entityType);

		builder.setAppliesTo(convertTargets(node.getTargets()));

		return builder.build();
	}

	/**
	 * Converts TOSCA YAML PolicyDefinitions to TOSCA XML Policy and PolicyTemplate
	 * Additional TOSCA YAML element trigger is not converted
	 *
	 * @param node TOSCA YAML PolicyDefinition
	 */
	private void convert(TPolicyDefinition node, String id) {
		if (node == null) {
			return;
		}

		TPolicyTemplate.Builder builder = new TPolicyTemplate.Builder(id + "_templ", node.getType());
		builder.setName(id);
		builder.setProperties(convertPropertyAssignments(node.getProperties(), getPropertyTypeName(node.getType())));
		this.policyTemplates.add(builder.build());

		TPolicy.Builder pbuilder = new TPolicy.Builder(node.getType());
		pbuilder.setName(id);
		pbuilder.setPolicyRef(new QName(id + "_templ"));

		/* if PolicyDefinition has targets the resulting Policy is added to the target else its added to the
		 * BoundaryDefinition of the Service Template
		 */
		if (node.getTargets() == null || node.getTargets().size() == 0) {
			this.addPolicy("boundary", pbuilder.build());
		} else {
			for (QName target : node.getTargets()) {
				this.addPolicy(target.toString(), pbuilder.build());
			}
		}
	}

	/**
	 * Adds TOSCA XML Policy to Map<String, TPolicy> policies
	 *
	 * @param target Key of the map
	 */
	private void addPolicy(String target, TPolicy policy) {
		if (this.policies.containsKey(target)) {
			this.policies.get(target).add(policy);
		} else {
			List<TPolicy> policies = new ArrayList<>();
			policies.add(policy);
			this.policies.put(target, policies);
		}
	}

	/**
	 * Converts TOSCA YAML TImportDefinitions and returns list of TOSCA XML TImports
	 */
	private TImport convert(TImportDefinition node, String name) {
		Reader reader = new Reader();
		String namespace = node.getNamespace_uri() == null ? this.namespace : node.getNamespace_uri();
		try {
			org.eclipse.winery.model.tosca.yaml.TServiceTemplate serviceTemplate = reader.readImportDefinition(node, path, namespace);
			Converter converter = new Converter();
			TDefinitions definitions = converter.convertY2X(serviceTemplate, getFileNameFromFile(node.getFile()), namespace, path);
			Writer writer = new Writer();
			String location = path + "/" + Util.URLencode(namespace) + "/" + name + ".xsd";
			try {
				writer.writeXML(definitions, location);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			TImport.Builder builder = new TImport.Builder(Namespaces.XML_NS);
			builder.setLocation(location);
			builder.setNamespace(namespace);
			return builder.build();
		} catch (IException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getFileNameFromFile(String filename) {
		return filename.substring(filename.lastIndexOf(File.separator) + 1, filename.lastIndexOf("."));
	}

	/**
	 * Convert A list of TOSCA YAML PolicyType targets to TOSCA XML PolicyType AppliesTo
	 *
	 * @param targetList list of TOSCA YAML PolicyType targets
	 * @return TOSCA XML PolicyType AppliesTo
	 */
	private TAppliesTo convertTargets(List<QName> targetList) {
		if (targetList == null || targetList.size() == 0) {
			return null;
		}

		List<TAppliesTo.NodeTypeReference> references = new ArrayList<>();
		for (QName nodeRef : targetList) {
			TAppliesTo.NodeTypeReference ref = new TAppliesTo.NodeTypeReference();
			ref.setTypeRef(nodeRef);
			references.add(ref);
		}

		TAppliesTo appliesTo = new TAppliesTo();
		appliesTo.getNodeTypeReference().addAll(references);
		return appliesTo;
	}

	/**
	 * Converts a map of TOSCA YAML PropertyAssignment to TOSCA XML EntityTemplate.Properties
	 */
	private TEntityTemplate.Properties convertPropertyAssignments(Map<String, TPropertyAssignment> propertyMap, QName type) {
		if (propertyMap == null) {
			return null;
		}

		TEntityTemplate.Properties properties = new TEntityTemplate.Properties();
		properties.setAny(assignmentBuilder.getAssignment(propertyMap, type));
		return properties;
	}

	/**
	 * Converts TOSCA YAML ArtifactDefinitions to TOSCA XML NodeTypeImplementations and ArtifactTemplates
	 */
	private void convertNodeTypeImplementation(
			Map<String, org.eclipse.winery.model.tosca.yaml.TArtifactDefinition> implArtifacts,
			Map<String, org.eclipse.winery.model.tosca.yaml.TArtifactDefinition> deplArtifacts, String type) {
		TNodeTypeImplementation.Builder builder = new TNodeTypeImplementation.Builder(type + "_impl", new QName(type));

		builder.setDeploymentArtifacts(convertDeploymentArtifacts(deplArtifacts));
		builder.setImplementationArtifacts(convertImplementationArtifact(implArtifacts));

		this.nodeTypeImplementations.add(builder.build());
	}

	private TOperation convert(TOperationDefinition node, String id) {
		TOperation.Builder builder = new TOperation.Builder(id);
		builder.addDocumentation(node.getDescription());

		builder.addInputParameters(convertParameters(node.getInputs()));
		builder.addOutputParameters(convertParameters(node.getOutputs()));

		return builder.build();
	}

	private List<TParameter> convertParameters(Map<String, TPropertyAssignmentOrDefinition> node) {
		return node.entrySet().stream()
				.map(entry -> {
					if (entry.getValue() instanceof TPropertyDefinition) {
						return convertParameter((TPropertyDefinition) entry.getValue(), entry.getKey());
					} else {
						return null;
					}
				}).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private TParameter convertParameter(TPropertyDefinition node, String id) {
		String type = convertType(node.getType());
		Boolean required = node.getRequired();

		TParameter.Builder builder = new TParameter.Builder(id, type, required);

		return builder.build();
	}

	public void convert(org.eclipse.winery.model.tosca.yaml.TAttributeDefinition node, String id) {
		/*// TODO missing information for TAttributeDefinition
		node.getType();
		node.getDescription();
		node.getEntry_schema();
		node.getDefault();
		node.getStatus();*/
	}

	private Object convert(org.eclipse.winery.model.tosca.yaml.TGroupType node, String name) {
		// TODO 
		return null;
	}

	public Object convert(org.eclipse.winery.model.tosca.yaml.TDataType node, String name) {
		// TODO
		TImport.Builder builder = new TImport.Builder(Namespaces.XML_NS);
		builder.setLocation(Util.URLencode(this.namespace) + ".xsd");
		TImport _import = builder.build();
		if (!this.imports.contains(_import)) {
			this.imports.add(_import);
		}
		return null;
	}

	public String convertType(QName type) {
		// TODO complete default type conversion
		switch (type.getLocalPart()) {
			case "string":
				return "xs:string";
		}
		return type.toString();
	}

	@SuppressWarnings({"unchecked"})
	private <V, T> List<T> convert(List<? extends Map<String, V>> node) {
		return node.stream()
				.flatMap(map -> map.entrySet().stream())
				.map((Map.Entry<String, V> entry) -> {
					if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TImportDefinition) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TImportDefinition) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TRequirementDefinition) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TRequirementDefinition) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TRequirementAssignment) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TRequirementAssignment) entry.getValue(), entry.getKey());
					} else {
						V v = entry.getValue();
						assert (v instanceof org.eclipse.winery.model.tosca.yaml.TImportDefinition ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TRequirementDefinition ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TRequirementAssignment);
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	@SuppressWarnings({"unchecked"})
	private <V, T> List<T> convert(Map<String, V> map) {
		return map.entrySet().stream()
				.map((Map.Entry<String, V> entry) -> {
					if (entry.getValue() == null) {
						return null;
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TRelationshipType) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TRelationshipType) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TRelationshipTemplate) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TRelationshipTemplate) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TArtifactType) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TArtifactType) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TArtifactDefinition) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TArtifactDefinition) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TCapabilityType) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TCapabilityType) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TCapabilityDefinition) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TCapabilityDefinition) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TPolicyType) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TPolicyType) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TRequirementDefinition) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TRequirementDefinition) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TInterfaceType) {
						assert (!interfaceTypes.containsKey(entry.getKey()));
						this.interfaceTypes.put(entry.getKey(), (org.eclipse.winery.model.tosca.yaml.TInterfaceType) entry.getValue());
						return null;
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TInterfaceDefinition) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TInterfaceDefinition) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TOperationDefinition) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TOperationDefinition) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TNodeTemplate) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TNodeTemplate) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TDataType) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TDataType) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TGroupType) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TGroupType) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TNodeType) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TNodeType) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TImportDefinition) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TImportDefinition) entry.getValue(), entry.getKey());
					} else if (entry.getValue() instanceof org.eclipse.winery.model.tosca.yaml.TPolicyType) {
						return (T) convert((org.eclipse.winery.model.tosca.yaml.TPolicyType) entry.getValue(), entry.getKey());
					} else {
						V v = entry.getValue();
						System.err.println(v);
						assert (v instanceof org.eclipse.winery.model.tosca.yaml.TRelationshipType ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TRelationshipTemplate ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TArtifactType ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TArtifactDefinition ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TCapabilityType ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TCapabilityDefinition ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TPolicyType ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TRequirementDefinition ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TInterfaceType ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TInterfaceDefinition ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TOperationDefinition ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TNodeTemplate ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TDataType ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TGroupType ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TNodeType ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TImportDefinition ||
								v instanceof org.eclipse.winery.model.tosca.yaml.TPolicyDefinition
						);
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
