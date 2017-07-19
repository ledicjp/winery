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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TCapabilityType;
import org.eclipse.winery.model.tosca.yaml.TDataType;
import org.eclipse.winery.model.tosca.yaml.TEntrySchema;
import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TNodeTemplate;
import org.eclipse.winery.model.tosca.yaml.TNodeType;
import org.eclipse.winery.model.tosca.yaml.TOperationDefinition;
import org.eclipse.winery.model.tosca.yaml.TPropertyDefinition;
import org.eclipse.winery.model.tosca.yaml.TRepositoryDefinition;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.TTopologyTemplateDefinition;
import org.eclipse.winery.model.tosca.yaml.tosca.datatypes.Credential;
import org.eclipse.winery.yaml.common.Exception.ImplementationArtifactInvalidOnInterfaceType;
import org.eclipse.winery.yaml.common.Exception.InvalidNativeTypeExtend;
import org.eclipse.winery.yaml.common.Exception.InvalidParentType;
import org.eclipse.winery.yaml.common.Exception.InvalidTOSCAVersion;
import org.eclipse.winery.yaml.common.Exception.MissingArtifactType;
import org.eclipse.winery.yaml.common.Exception.MissingNodeType;
import org.eclipse.winery.yaml.common.Exception.MissingRepositoryDefinition;
import org.eclipse.winery.yaml.common.Exception.MissingRequiredKeyname;
import org.eclipse.winery.yaml.common.Exception.MissingTOSCAVersion;
import org.eclipse.winery.yaml.common.Exception.UnknownCapabilitySourceType;
import org.eclipse.winery.yaml.common.Exception.UnknownDataType;
import org.eclipse.winery.yaml.common.Exception.ValueTypeMismatch;
import org.eclipse.winery.yaml.common.Exception.YAMLParserException;
import org.eclipse.winery.yaml.common.reader.Reader;

public class Validator {
	public static final String normativeTypesLocation = "src/main/resources/tosca_simple_yaml_1_1.yml";
	public static final List<String> YAML_TYPES = new ArrayList<>(Arrays.asList("string", "integer", "float", "boolean", "timestamp", "null"));
	public static final List<String> TOSCA_TYPES = new ArrayList<>(Arrays.asList("list", "map"));

	public void validate(TServiceTemplate serviceTemplate) throws YAMLParserException {
		validateTOSCADefinitionVersion(serviceTemplate.getTosca_definitions_version());
		validateTOSCARepositories(serviceTemplate.getRepositories());
		validateTOSCAVersionMetadata(serviceTemplate.getMetadata());

		if (serviceTemplate.getInterface_types() != null) {
			for (Map.Entry<String, TInterfaceType> entry : serviceTemplate.getInterface_types().entrySet()) {
				validate(entry.getKey(), entry.getValue());
			}
		}
	}

	public void validate(String name, TInterfaceType interfaceType) throws YAMLParserException {
		if (interfaceType == null) {
			return;
		}

		// Interface Types MUST NOT include any implementations for defined operations
		if (interfaceType.getOperations() != null) {
			for (Map.Entry<String, TOperationDefinition> entry : interfaceType.getOperations().entrySet()) {
				if (entry.getValue().getImplementation() != null) {
					throw new ImplementationArtifactInvalidOnInterfaceType("The InterfaceType \"" + name + "\" MUST NOT include any implementations for defined operations");
				}
			}
		}
	}

	public void validate(Map<String, TRepositoryDefinition> repositories, String name) throws YAMLParserException {
		if (repositories == null || !repositories.containsKey(name)) {
			String msg = "The repository \"" + name + "\" has no RepositoryDefinition";
			throw new MissingRepositoryDefinition(msg);
		}
	}

	public void validate(TImportDefinition importDefinition, String name) throws YAMLParserException {
		if (importDefinition.getFile() == null || importDefinition.getFile().equals("")) {
			String context = "Import Definition \"" + name + "\"";
			String keyname = "file";
			throw new MissingRequiredKeyname(keyname, context);
		}
	}

	public void validateTOSCADefinitionVersion(String tosca_definition_version) throws YAMLParserException {
		if (tosca_definition_version == null) {
			String msg = "tosca_definition_version is missing";
			throw new MissingTOSCAVersion(msg);
		}

		String pattern = "tosca_simple_yaml_\\d_\\d|http://docs\\.oasis-open\\.org/tosca/ns/simple/yaml/\\d\\.\\d";
		if (!tosca_definition_version.matches(pattern)) {
			String msg = "\"" + tosca_definition_version + "\" is an invalid tosca_definition_version, which does not follow the pattern: \"" + pattern + "\"";
			throw new InvalidTOSCAVersion(msg);
		}
	}

	public void validateTOSCARepositories(Map<String, TRepositoryDefinition> repositories) throws YAMLParserException {
		if (repositories == null) {
			return;
		}
		String context_prefix = "Repository definition \"";
		String context_postfix = "\"";
		for (Map.Entry<String, TRepositoryDefinition> entry : repositories.entrySet()) {
			TRepositoryDefinition repository = entry.getValue();
			if (repository.getUrl() == null || repository.getUrl().equals("")) {
				String keyname = "url";
				throw new MissingRequiredKeyname(keyname, context_prefix + entry.getKey() + context_postfix);
			}
			if (repository.getCredential() != null) {
				Credential credential = repository.getCredential();
				if (credential.getToken() == null || credential.getToken() == "") {
					String keyname = "credential.token";
					throw new MissingRequiredKeyname(keyname, context_prefix + entry.getKey() + context_postfix);
				}
				if (credential.getToken_type() == null || credential.getToken_type() == "") {
					String keyname = "credential.token_type";
					throw new MissingRequiredKeyname(keyname, context_prefix + entry.getKey() + context_postfix);
				}
			}
		}
	}

	public void validateTOSCAVersionMetadata(Map<String, String> metadata) throws YAMLParserException {
		if (metadata == null) {
			return;
		}

		if (metadata.containsKey("template_version")) {
			String template_version = metadata.get("template_version");
			String template_version_match = "\\d+\\.\\d+(\\.\\d+(\\.\\w+(-\\d+)?)?)?";

			if (!template_version.matches(template_version_match)) {
				String msg = "The value of the metadata field template_version is invalid";
				throw new ValueTypeMismatch(msg);
			}
		}
	}

	public void validateTypeRefs(TServiceTemplate serviceTemplate, Map<String, TServiceTemplate> imports) throws YAMLParserException {
		if (serviceTemplate == null) {
			return;
		}

		Map<String, TServiceTemplate> sts = new LinkedHashMap<>();
		sts.putAll(imports);
		sts.put("_local", serviceTemplate);
		sts.put("tosca", Reader.INSTANCE.parseSkipTest(normativeTypesLocation));

		validateTypeRefs(serviceTemplate.getTopology_template(), sts);

		List<String> artifactTypes = buildArtifactTypes(sts);

		if (serviceTemplate.getArtifact_types() != null) {
			for (Map.Entry<String, TArtifactType> artifactType : serviceTemplate.getArtifact_types().entrySet()) {
				validateTypeRefs(artifactType.getKey(), artifactType.getValue(), artifactTypes);
			}
		}

		List<String> dataTypes = buildDataTypes(sts);
		if (serviceTemplate.getData_types() != null) {
			for (Map.Entry<String, TDataType> entry : serviceTemplate.getData_types().entrySet()) {
				validateTypeRefs(entry.getKey(), entry.getValue(), dataTypes);
			}
		}

		List<String> capabilityTypes = buildCapabilityTypes(sts);
		List<String> nodeTypes = buildNodeTypes(sts);
		if (serviceTemplate.getCapability_types() != null) {
			for (Map.Entry<String, TCapabilityType> entry : serviceTemplate.getCapability_types().entrySet()) {
				validateTypeRefs(entry.getKey(), entry.getValue(), capabilityTypes, nodeTypes);
			}
		}
	}

	public void validateTypeRefs(String name, TCapabilityType capabilityType, List<String> capabilityTypes, List<String> nodeTypes) throws YAMLParserException {
		if (capabilityType == null) {
			return;
		}

		if (capabilityType.getDerived_from() != null && !capabilityTypes.contains(capabilityType.getDerived_from())) {
			String msg = "The parent type \"" + capabilityType.getDerived_from() + "\" of \"" + name + "\" is undefined";
			throw new InvalidParentType(msg);
		}

		if (capabilityType.getValid_source_types() != null) {
			for (String source : capabilityType.getValid_source_types()) {
				if (!nodeTypes.contains(source)) {
					String msg = "The valid source type \"" + source + "\" for the capability type \"" + name + "\" is undefined.";
					throw new UnknownCapabilitySourceType(msg);
				}
			}
		}
	}

	public void validateTypeRefs(String name, TDataType dataType, List<String> dataTypes) throws YAMLParserException {
		if (dataType == null) {
			return;
		}

		if (dataType.getDerived_from() != null && !dataTypes.contains(dataType.getDerived_from())) {
			String msg = "The parent type \"" + dataType.getDerived_from() + "\" of \"" + name + "\" is undefined.";
			throw new InvalidParentType(msg);
		}

		if (dataType.getProperties() != null) {
			for (Map.Entry<String, TPropertyDefinition> entry : dataType.getProperties().entrySet()) {
				validateTypeRefs("<TDataType \"" + name + "\">:<TPropertyDefinition \"" + entry.getKey() + "\">", entry.getValue(), dataTypes);
			}
		}

		// Extend a native DataType should fail
		if ((YAML_TYPES.contains(dataType.getDerived_from()) || TOSCA_TYPES.contains(dataType.getDerived_from())) && dataType.getProperties() != null && dataType.getProperties().size() != 0) {
			String msg = "The native data type \"" + name + "\" cannot be extended with properties";
			throw new InvalidNativeTypeExtend(msg);
		}
	}

	public void validateTypeRefs(String name, TPropertyDefinition propertyDefinition, List<String> dataTypes) throws YAMLParserException {
		if (propertyDefinition == null) {
			return;
		}

		if (!dataTypes.contains(propertyDefinition.getType())) {
			String msg = name + ":type \"" + propertyDefinition.getType() + "\" is undefined.";
			throw new UnknownDataType(msg);
		}

		if (propertyDefinition.getType().equals("list") || propertyDefinition.getType().equals("map")) {
			if (propertyDefinition.getEntry_schema() != null) {
				TEntrySchema entrySchema = propertyDefinition.getEntry_schema();
				if (!dataTypes.contains(entrySchema.getType())) {
					String msg = name + "entry_schema:type \"" + entrySchema.getType() + "\" is undefined.";
					throw new UnknownDataType(msg);
				}
			}
		}
	}

	public void validateTypeRefs(String name, TArtifactType artifactType, List<String> sts) throws YAMLParserException {
		if (artifactType == null) {
			return;
		}

		if (artifactType.getDerived_from() != null && !sts.contains(artifactType.getDerived_from())) {
			String msg = "The Artifact Type \"" + artifactType.getDerived_from() + "\" is not defined.";
			throw new MissingArtifactType(msg);
		}
	}

	public void validateTypeRefs(TTopologyTemplateDefinition topologyTemplate, Map<String, TServiceTemplate> imports) throws YAMLParserException {
		if (topologyTemplate == null) {
			return;
		}

		// Node Templates
		for (Map.Entry<String, TNodeTemplate> entry : topologyTemplate.getNode_templates().entrySet()) {
			validateTypeRefs(entry.getValue(), imports);
		}
	}

	public void validateTypeRefs(TNodeTemplate nodeTemplate, Map<String, TServiceTemplate> imports) throws YAMLParserException {
		if (nodeTemplate == null) {
			return;
		}

		// Build list of NodeTypes
		List<String> nodeTypes = buildNodeTypes(imports);

		if (!nodeTypes.contains(nodeTemplate.getType())) {
			throw new MissingNodeType(nodeTemplate.getType());
		}
	}

	private List<String> buildNodeTypes(Map<String, TServiceTemplate> imports) {
		List<String> nodeTypes = new ArrayList<>();
		for (Map.Entry<String, TServiceTemplate> entry : imports.entrySet()) {
			List<String> prefixes = getPrefix(entry.getKey());
			if (entry.getValue() != null && entry.getValue().getNode_types() != null) {
				for (Map.Entry<String, TNodeType> nodeTypeEntry : entry.getValue().getNode_types().entrySet()) {
					for (String prefix : prefixes) {
						nodeTypes.add(prefix + nodeTypeEntry.getKey());
					}
				}
			}
		}
		return nodeTypes;
	}

	private List<String> buildDataTypes(Map<String, TServiceTemplate> imports) {
		List<String> dataTypes = new ArrayList<>();
		for (Map.Entry<String, TServiceTemplate> entry : imports.entrySet()) {
			List<String> prefixes = getPrefix(entry.getKey());
			if (entry.getValue() != null && entry.getValue().getData_types() != null) {
				for (Map.Entry<String, TDataType> dataTypeEntry : entry.getValue().getData_types().entrySet()) {
					for (String prefix : prefixes) {
						dataTypes.add(prefix + dataTypeEntry.getKey());
					}
				}
			}
		}

		dataTypes.addAll(YAML_TYPES);
		dataTypes.addAll(TOSCA_TYPES);
		return dataTypes;
	}

	private List<String> buildArtifactTypes(Map<String, TServiceTemplate> imports) {
		List<String> artifactTypes = new ArrayList<>();
		for (Map.Entry<String, TServiceTemplate> entry : imports.entrySet()) {
			List<String> prefixes = getPrefix(entry.getKey());
			if (entry.getValue() != null && entry.getValue().getArtifact_types() != null) {
				for (Map.Entry<String, TArtifactType> artifactTypeEntry : entry.getValue().getArtifact_types().entrySet()) {
					for (String prefix : prefixes) {
						artifactTypes.add(prefix + artifactTypeEntry.getKey());
					}
				}
			}
		}
		return artifactTypes;
	}

	private List<String> buildCapabilityTypes(Map<String, TServiceTemplate> imports) {
		List<String> capabilityTypes = new ArrayList<>();
		for (Map.Entry<String, TServiceTemplate> entry : imports.entrySet()) {
			List<String> prefixes = getPrefix(entry.getKey());
			if (entry.getValue() != null && entry.getValue().getCapability_types() != null) {
				for (Map.Entry<String, TCapabilityType> capabilityTypeEntry : entry.getValue().getCapability_types().entrySet()) {
					for (String prefix : prefixes) {
						capabilityTypes.add(prefix + capabilityTypeEntry.getKey());
					}
				}
			}
		}
		return capabilityTypes;
	}

	private List<String> getPrefix(String prefix) {
		List<String> prefixes = new ArrayList<>();
		if (prefix.equals("_local") || prefix.equals("_remote")) {
			prefixes.add("");
		} else if (prefix.equals("tosca")) {
			prefixes.add("");
			prefixes.add("tosca:");
		} else {
			prefixes.add(prefix + ":");
		}
		return prefixes;
	}
}
