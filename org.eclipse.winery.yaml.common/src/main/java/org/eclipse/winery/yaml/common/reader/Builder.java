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
package org.eclipse.winery.yaml.common.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.namespace.QName;

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
import org.eclipse.winery.model.tosca.yaml.TStatusValue;
import org.eclipse.winery.model.tosca.yaml.TSubstitutionMappings;
import org.eclipse.winery.model.tosca.yaml.TTopologyTemplateDefinition;
import org.eclipse.winery.model.tosca.yaml.TVersion;
import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.support.TListString;
import org.eclipse.winery.model.tosca.yaml.support.TMapImportDefinition;
import org.eclipse.winery.model.tosca.yaml.support.TMapObject;
import org.eclipse.winery.model.tosca.yaml.support.TMapPropertyFilterDefinition;
import org.eclipse.winery.model.tosca.yaml.support.TMapRequirementAssignment;
import org.eclipse.winery.model.tosca.yaml.support.TMapRequirementDefinition;
import org.eclipse.winery.model.tosca.yaml.tosca.datatypes.Credential;
import org.eclipse.winery.yaml.common.Defaults;
import org.eclipse.winery.yaml.common.Namespaces;

import org.eclipse.jdt.annotation.Nullable;

public class Builder {
    private final String namespace;

    private Map<String, String> prefix2Namespace;

    public Builder(String namespace) {
        this.namespace = namespace;
    }

    private void initPrefix2Namespace(Object object) {
        if (object == null) {
            return;
        }

        this.prefix2Namespace = new LinkedHashMap<>();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) object;

        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (!(entry.getValue() instanceof String)) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> _import = (Map<String, Object>) entry.getValue();
                    if (_import != null) {
                        String namespace_prefix = (String) _import.get("namespace_prefix");
                        String namespace_uri = (String) _import.get("namespace_uri");
                        if (namespace_prefix != null && namespace_uri != null) {
                            this.prefix2Namespace.put(namespace_prefix, namespace_uri);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public TServiceTemplate buildServiceTemplate(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        // build map between prefix and namespaces
        initPrefix2Namespace(map.get("imports"));

        String tosca_definitions_version = (String) map.get("tosca_definitions_version");

        TServiceTemplate.Builder builder = new TServiceTemplate.Builder(tosca_definitions_version);

        builder.setMetadata(buildMetadata(map.get("metadata")));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setDsl_definitions(buildDsl_definitions(map.get("dsl_definitions")));
        builder.setRepositories(buildRepositories(map.get("repositories")));
        builder.setImports(buildImports(map.get("imports")));
        builder.setArtifact_types(buildArtifact_types(map.get("artifact_types")));
        builder.setData_types(buildData_types(map.get("data_types")));
        builder.setCapability_types(buildCapability_types(map.get("capability_types")));
        builder.setInterface_types(buildInterface_types(map.get("interface_types")));
        builder.setRelationship_types(buildRelationship_types(map.get("relationships")));
        builder.setNode_types(buildNode_types(map.get("node_types")));
        builder.setGroup_types(buildGroup_types(map.get("group_types")));
        builder.setPolicy_types(buildPolicy_types(map.get("policy_types")));
        builder.setTopology_template(buildTopology_template(map.get("topology_template")));

        return builder.build();
    }

    @Nullable
    public TTopologyTemplateDefinition buildTopology_template(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TTopologyTemplateDefinition.Builder builder = new TTopologyTemplateDefinition.Builder();
        builder.setDescription(buildDescription(map.get("description")));
        builder.setInputs(buildMapParameterDefinition(map.get("inputs")));
        builder.setNode_templates((buildNode_templates(map.get("node_templates"))));
        builder.setRelationship_templates(buildRelationship_templates(map.get("relationship_templates")));
        builder.setGroups(buildGroupDefinitions(map.get("groups")));
        builder.setPolicies(buildMapPolicyDefinition(map.get("policies")));
        builder.setOutputs(buildMapParameterDefinition(map.get("outputs")));
        builder.setSubstitution_mappings(buildSubstitutionMappings(map.get("substitution_mappings")));

        return builder.build();
    }

    @Nullable
    public Metadata buildMetadata(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, String> tmp = (Map<String, String>) object;

        Metadata metadata = new Metadata();
        metadata.putAll(tmp);

        return metadata;
    }

    @Nullable
    public String buildDescription(Object object) {
        if (object == null) {
            return null;
        }

        return (String) object;
    }

    @Nullable
    public Map<String, Object> buildDsl_definitions(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, Object> dsl_definitions = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            dsl_definitions.put(entry.getKey(), entry.getValue());
        }

        return dsl_definitions;
    }

    @Nullable
    public Map<String, TRepositoryDefinition> buildRepositories(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TRepositoryDefinition> repositories = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            repositories.put(entry.getKey(), buildRepositoryDefinition(entry.getValue()));
        }

        return repositories;
    }

    @Nullable
    public TRepositoryDefinition buildRepositoryDefinition(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return new TRepositoryDefinition.Builder((String) object).build();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;
        String url = (String) map.get("url");

        TRepositoryDefinition.Builder builder = new TRepositoryDefinition.Builder(url);
        builder.setDescription(buildDescription(map.get("description")));
        builder.setCredential(buildCredential(map.get("credential")));

        return builder.build();
    }

    @Nullable
    public Credential buildCredential(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;
        @SuppressWarnings("unchecked")
        Map<String, String> keys = (Map<String, String>) map.get("keys");

        Credential credential = new Credential();
        credential.setProtocol((String) map.get("protocol"));
        credential.setToken_type((String) map.get("token_type"));
        credential.setToken((String) map.get("token"));
        credential.setKeys(keys);

        return credential;
    }

    @Nullable
    public List<TMapImportDefinition> buildImports(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) object;

        List<TMapImportDefinition> imports = new ArrayList<>();
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                imports.add(buildMapImportDefinition(entry.getKey(), entry.getValue()));
            }
        }

        return imports;
    }

    @Nullable
    public TMapImportDefinition buildMapImportDefinition(String key, Object object) {
        if (object == null) {
            return null;
        }

        TMapImportDefinition mapImportDefinition = new TMapImportDefinition();
        mapImportDefinition.put(key, buildImportDefinition(object));

        return mapImportDefinition;
    }

    @Nullable
    public TImportDefinition buildImportDefinition(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return new TImportDefinition.Builder((String) object).build();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;
        String url = (String) map.get("file");

        TImportDefinition.Builder builder = new TImportDefinition.Builder(url);
        builder.setRepository(buildQName((String) map.get("repository")));
        builder.setNamespace_uri((String) map.get("namespace_uri"));
        builder.setNamespace_prefix((String) map.get("namespace_prefix"));

        return builder.build();
    }

    @Nullable
    private QName buildQName(String name) {
        if (name == null) {
            return null;
        }

        if (name.contains(":")) {
            Integer pos = name.indexOf(":");
            String prefix = name.substring(0, pos);
            name = name.substring(pos + 1, name.length());
            return new QName(prefix2Namespace.get(prefix), name, prefix);
        } else if (Defaults.TOSCA_NORMATIVE_NAMES.contains(name)) {
            return new QName(Namespaces.TOSCA_NS, name, "tosca");
        } else if (Defaults.YAML_TYPES.contains(name)) {
            return new QName(Namespaces.YAML_NS, name, "yaml");
        } else {
            return new QName(namespace, name, "");
        }
    }

    @Nullable
    public Map<String, TArtifactType> buildArtifact_types(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TArtifactType> artifact_types = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            artifact_types.put(entry.getKey(), buildArtifactType(entry.getValue()));
        }

        return artifact_types;
    }

    @Nullable
    public TArtifactType buildArtifactType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TArtifactType.Builder builder = new TArtifactType.Builder(buildEntityType(object));
        builder.setMime_type((String) map.get("mime_type"));
        builder.setFile_ext(buildListString(map.get("file_ext")));

        return builder.build();
    }

    @Nullable
    public TEntityType buildEntityType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TEntityType.Builder builder = new TEntityType.Builder();
        builder.setDescription(buildDescription(map.get("description")));
        builder.setVersion(buildVersion(map.get("version")));
        builder.setDerived_from(buildQName((String) map.get("derived_from")));
        builder.setProperties(buildProperties(map.get("properties")));
        builder.setAttributes(buildAttributes(map.get("attributes")));
        builder.setMetadata(buildMetadata(map.get("metadata")));

        return builder.build();
    }

    @Nullable
    public TVersion buildVersion(Object object) {
        if (object == null) {
            return null;
        }

        return new TVersion((String) object);
    }

    @Nullable
    public Map<String, TPropertyDefinition> buildProperties(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TPropertyDefinition> properties = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            properties.put(entry.getKey(), buildPropertyDefinition(entry.getValue()));
        }

        return properties;
    }

    @Nullable
    public TPropertyDefinition buildPropertyDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;
        QName type = buildQName((String) map.get("type"));

        TPropertyDefinition.Builder builder = new TPropertyDefinition.Builder(type);
        builder.setDescription(buildDescription(map.get("description")));
        builder.setRequired(buildRequired(map.get("required")));
        builder.set_default(map.get("default"));
        builder.setStatus(buildStatus(map.get("status")));
        builder.setConstraints(buildConstraints(map.get("constraints")));
        builder.setEntry_schema(buildEntrySchema(map.get("entry_schema")));

        return builder.build();
    }

    @Nullable
    public Boolean buildRequired(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return object.equals("true") ? Boolean.TRUE : Boolean.FALSE;
        }

        if (object instanceof Boolean) {
            return (Boolean) object;
        }

        return Boolean.FALSE;
    }

    @Nullable
    public TStatusValue buildStatus(Object object) {
        if (object == null) {
            return null;
        }

        String status = (String) object;

        switch (status) {
            case "supported":
                return TStatusValue.supported;
            case "unsupported":
                return TStatusValue.unsupported;
            case "experimental":
                return TStatusValue.experimental;
            case "deprecated":
                return TStatusValue.deprecated;
            default:
                assert (status.equals("supported") ||
                    status.equals("unsupported") ||
                    status.equals("experimental") ||
                    status.equals("deprecated"));
                return null;
        }
    }

    @Nullable
    public List<TConstraintClause> buildConstraints(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) object;

        List<TConstraintClause> constraints = new ArrayList<>();
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                constraints.add(buildConstraintClause(entry.getKey(), entry.getValue()));
            }
        }

        return constraints;
    }

    @Nullable
    public TConstraintClause buildConstraintClause(String key, Object object) {
        if (object == null) {
            return null;
        }

        TConstraintClause constraintClause = new TConstraintClause();

        switch (key) {
            case "equal":
                constraintClause.setEqual(object);
                break;
            case "greater_than":
                constraintClause.setGreater_or_equal(object);
                break;
            case "greater_or_equal":
                constraintClause.setGreater_or_equal(object);
                break;
            case "less_than":
                constraintClause.setLess_than(object);
                break;
            case "less_or_equal":
                constraintClause.setLess_or_equal(object);
                break;
            case "in_range":
                constraintClause.setIn_range(buildListObject(object));
                break;
            case "valid_values":
                constraintClause.setValid_values(buildListObject(object));
                break;
            case "length":
                constraintClause.setLength(object);
                break;
            case "min_length":
                constraintClause.setMin_length(object);
                break;
            case "max_length":
                constraintClause.setMax_length(object);
                break;
            case "pattern":
                constraintClause.setPattern(object);
                break;
            default:
        }

        return constraintClause;
    }

    @Nullable
    public List<Object> buildListObject(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Object> result = (List<Object>) object;
        return result;
    }

    @Nullable
    public TEntrySchema buildEntrySchema(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TEntrySchema.Builder builder = new TEntrySchema.Builder();
        builder.setType(buildQName((String) map.get("type")));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setConstraints(buildConstraints(map.get("constraints")));

        return builder.build();
    }

    @Nullable
    public Map<String, TAttributeDefinition> buildAttributes(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TAttributeDefinition> attributes = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            attributes.put(entry.getKey(), buildAttributeDefinition(entry.getValue()));
        }

        return attributes;
    }

    @Nullable
    public TAttributeDefinition buildAttributeDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        QName type = buildQName((String) map.get("type"));
        TAttributeDefinition.Builder builder = new TAttributeDefinition.Builder(type);
        builder.setDescription(buildDescription(map.get("description")));
        builder.set_default(map.get("default"));
        builder.setStatus(buildStatus(map.get("status")));
        builder.setEntry_schema(buildEntrySchema(map.get("entry_schema")));

        return builder.build();
    }

    @Nullable
    public List<String> buildListString(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<String> tmp = (List<String>) object;

        return tmp;
    }

    @Nullable
    public Map<String, TDataType> buildData_types(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TDataType> data_types = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            data_types.put(entry.getKey(), buildDataType(entry.getValue()));
        }

        return data_types;
    }

    @Nullable
    public TDataType buildDataType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TDataType.Builder builder = new TDataType.Builder(buildEntityType(object));
        builder.setConstraints(buildConstraints(map.get("constraints")));

        return builder.build();
    }

    @Nullable
    public Map<String, TCapabilityType> buildCapability_types(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TCapabilityType> capability_types = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            capability_types.put(entry.getKey(), buildCapabilityType(entry.getValue()));
        }

        return capability_types;
    }

    @Nullable
    public TCapabilityType buildCapabilityType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TCapabilityType.Builder builder = new TCapabilityType.Builder(buildEntityType(object));
        builder.setValid_source_types(buildListQName(buildListString(map.get("valid_source_types"))));
        builder.setAttributes(buildAttributes(map.get("attributes")));

        return builder.build();
    }

    @Nullable
    public List<QName> buildListQName(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.stream().map(this::buildQName).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Nullable
    public Map<String, TInterfaceType> buildInterface_types(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TInterfaceType> interface_types = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            interface_types.put(entry.getKey(), buildInterfaceType(entry.getValue()));
        }

        return interface_types;
    }

    @Nullable
    public TInterfaceType buildInterfaceType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        List<String> INTERFACE_KEYS = new ArrayList<>(Arrays.asList("inputs", "description", "version", "derived_from", "properties", "attributes", "metadata"));

        TInterfaceType.Builder builder = new TInterfaceType.Builder(buildEntityType(object));
        builder.setInputs(buildProperties(map.get("inputs")));

        Map<String, TOperationDefinition> operations = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!INTERFACE_KEYS.contains(entry.getKey())) {
                operations.put(entry.getKey(), buildOperationDefinition(entry.getValue(), "TInterfaceType"));
            }
        }
        if (operations.size() > 0) {
            builder.setOperations(operations);
        }

        return builder.build();
    }

    @Nullable
    public TOperationDefinition buildOperationDefinition(Object object, String context) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TOperationDefinition.Builder builder = new TOperationDefinition.Builder();
        builder.setDescription(buildDescription(map.get("description")));
        builder.setInputs(buildPropertyAssignmentOrDefinition(map.get("inputs"), context));
        builder.setOutputs(buildPropertyAssignmentOrDefinition(map.get("outputs"), context));
        builder.setImplementation(buildImplementation(map.get("implementation")));

        return builder.build();
    }

    @Nullable
    public Map<String, TPropertyAssignmentOrDefinition> buildPropertyAssignmentOrDefinition(Object object, String context) {
        if (object == null) {
            return null;
        }

        Map<String, TPropertyAssignmentOrDefinition> result = new LinkedHashMap<>();
        if (context.equals("TNodeType") ||
            context.equals("TRelationshipType") ||
            context.equals("TGroupType") ||
            context.equals("TInterfaceType")) {
            Map<String, TPropertyDefinition> propertyDefinitionMap = buildProperties(object);
            for (Map.Entry<String, TPropertyDefinition> entry : propertyDefinitionMap.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        } else {
            Map<String, TPropertyAssignment> propertyAssignmentMap = buildMapPropertyAssignment(object);
            for (Map.Entry<String, TPropertyAssignment> entry : propertyAssignmentMap.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }
    }

    @Nullable
    public Map<String, TPropertyAssignment> buildMapPropertyAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TPropertyAssignment> propertyAssignmentMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            propertyAssignmentMap.put(entry.getKey(), buildPropertyAssignment(entry.getValue()));
        }

        return propertyAssignmentMap;
    }

    @Nullable
    public TPropertyAssignment buildPropertyAssignment(Object object) {
        if (object == null) {
            return null;
        }

        TPropertyAssignment.Builder builder = new TPropertyAssignment.Builder();
        builder.setValue(object);

        return builder.build();
    }

    @Nullable
    public TImplementation buildImplementation(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return new TImplementation(buildQName((String) object));
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String primary = (String) map.get("primary");
        TImplementation.Builder builder = new TImplementation.Builder(buildQName(primary));
        builder.setDependencies(buildListQName(buildListString(map.get("dependencies"))));

        return builder.build();
    }

    @Nullable
    public Map<String, TRelationshipType> buildRelationship_types(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TRelationshipType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildRelationshipType(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TRelationshipType buildRelationshipType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TRelationshipType.Builder builder = new TRelationshipType.Builder(buildEntityType(object));
        builder.setValid_target_types(buildListQName(buildListString(map.get("valid_target_types"))));
        builder.setAttributes(buildAttributes(map.get("attributes")));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TRelationshipType"));

        return builder.build();
    }

    @Nullable
    public Map<String, TInterfaceDefinition> buildMapInterfaceDefinition(Object object, String context) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TInterfaceDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildInterfaceDefinition(entry.getValue(), context));
        }

        return result;
    }

    @Nullable
    public TInterfaceDefinition buildInterfaceDefinition(Object object, String context) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TInterfaceDefinition.Builder builder = new TInterfaceDefinition.Builder();
        builder.setType(buildQName((String) map.get("type")));
        builder.setInputs(buildPropertyAssignmentOrDefinition(map.get("inputs"), context));

        List<String> INTERFACE_DEFINITION_KEYS = new ArrayList<>(Arrays.asList("inputs", "type"));

        Map<String, TOperationDefinition> operations = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!INTERFACE_DEFINITION_KEYS.contains(entry.getKey())) {
                operations.put(entry.getKey(), buildOperationDefinition(entry.getValue(), context));
            }
        }
        if (operations.size() > 0) {
            builder.setOperations(operations);
        }

        return builder.build();
    }

    @Nullable
    public Map<String, TNodeType> buildNode_types(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TNodeType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildNodeType(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TNodeType buildNodeType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TNodeType.Builder builder = new TNodeType.Builder(buildEntityType(object));
        builder.setAttributes(buildAttributes(map.get("attributes")));
        builder.setRequirements(buildListMapRequirementDefinition(map.get("requirements")));
        builder.setCapabilities(buildMapCapabilityDefinition(map.get("capabilities")));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TNodeType"));
        builder.setArtifacts(buildMapArtifactDefinition(map.get("artifacts")));

        return builder.build();
    }

    @Nullable
    public List<TMapRequirementDefinition> buildListMapRequirementDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) object;

        List<TMapRequirementDefinition> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                result.add(buildMapRequirementDefinition(entry.getKey(), entry.getValue()));
            }
        }

        return result;
    }

    @Nullable
    public TMapRequirementDefinition buildMapRequirementDefinition(String key, Object object) {
        if (object == null) {
            return null;
        }

        TMapRequirementDefinition result = new TMapRequirementDefinition();
        result.put(key, buildRequirementDefinition(object));

        return result;
    }

    @Nullable
    public TRequirementDefinition buildRequirementDefinition(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return new TRequirementDefinition.Builder(buildQName((String) object)).build();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        QName capability = buildQName((String) map.get("capability"));
        TRequirementDefinition.Builder builder = new TRequirementDefinition.Builder(capability);
        builder.setNode(buildQName((String) map.get("node")));
        builder.setRelationship(buildRelationshipDefinition(map.get("relationship")));
        builder.setOccurrences(buildListString(map.get("occurrences")));

        return builder.build();
    }

    @Nullable
    public TRelationshipDefinition buildRelationshipDefinition(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return new TRelationshipDefinition.Builder(buildQName((String) object)).build();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String type = (String) map.get("type");
        TRelationshipDefinition.Builder builder = new TRelationshipDefinition.Builder(buildQName(type));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TRelationshipDefinition"));

        return builder.build();
    }

    @Nullable
    public Map<String, TCapabilityDefinition> buildMapCapabilityDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TCapabilityDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildCapabilityDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TCapabilityDefinition buildCapabilityDefinition(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return new TCapabilityDefinition.Builder(buildQName((String) object)).build();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String type = (String) map.get("type");
        TCapabilityDefinition.Builder builder = new TCapabilityDefinition.Builder(buildQName(type));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setOccurrences(buildListString(map.get("occurrences")));
        builder.setValid_source_types(buildListQName(buildListString(map.get("valid_source_types"))));
        builder.setProperties(buildProperties(map.get("properties")));
        builder.setAttributes(buildAttributes(map.get("attributes")));

        return builder.build();
    }

    @Nullable
    public Map<String, TArtifactDefinition> buildMapArtifactDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TArtifactDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildArtifactDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TArtifactDefinition buildArtifactDefinition(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            String file = (String) object;
            // TODO infer artifact type and mime type from file URI
            String type = file.substring(file.lastIndexOf("."), file.length());
            return new TArtifactDefinition.Builder(buildQName(type), new ArrayList<>(Collections.singleton(file))).build();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        QName type = buildQName((String) map.get("type"));
        List<String> files;
        if (map.get("file") instanceof String) {
            files = new ArrayList<>(Collections.singleton((String) map.get("file")));
        } else {
            files = buildListString(map.get("file"));
        }
        TArtifactDefinition.Builder builder = new TArtifactDefinition.Builder(type, files);
        builder.setRepository((String) map.get("repository"));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setDeploy_path((String) map.get("deploy_path"));
        builder.setProperties(buildMapPropertyAssignment(map.get("properties")));

        return builder.build();
    }

    @Nullable
    public Map<String, TGroupType> buildGroup_types(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TGroupType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildGroupType(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TGroupType buildGroupType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TGroupType.Builder builder = new TGroupType.Builder(buildEntityType(object));
        builder.setMembers(buildListQName(buildListString(map.get("members"))));
        builder.setRequirements(buildListMapRequirementDefinition(map.get("requirements")));
        builder.setCapabilities(buildMapCapabilityDefinition(map.get("capabilities")));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TGroupType"));

        return builder.build();
    }

    @Nullable
    public Map<String, TPolicyType> buildPolicy_types(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TPolicyType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildPolicyType(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TPolicyType buildPolicyType(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TPolicyType.Builder builder = new TPolicyType.Builder(buildEntityType(object));
        builder.setTargets(buildListQName(buildListString(map.get("targets"))));
        builder.setTriggers(map.get("triggers"));

        return builder.build();
    }

    @Nullable
    public Map<String, TParameterDefinition> buildMapParameterDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TParameterDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildParameterDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TParameterDefinition buildParameterDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TParameterDefinition.Builder builder = new TParameterDefinition.Builder(buildPropertyDefinition(object));
        builder.setValue(map.get("value"));

        return builder.build();
    }

    @Nullable
    public Map<String, TNodeTemplate> buildNode_templates(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TNodeTemplate> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildNodeTemplate(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TNodeTemplate buildNodeTemplate(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        QName type = buildQName((String) map.get("type"));
        TNodeTemplate.Builder builder = new TNodeTemplate.Builder(type);
        builder.setDescription(buildDescription(map.get("description")));
        builder.setMetadata(buildMetadata(map.get("metadata")));
        builder.setDirectives(buildListString(map.get("directives")));
        builder.setProperties(buildMapPropertyAssignment(map.get("properties")));
        builder.setAttributes(buildMapAttributeAssignment(map.get("attributes")));
        builder.setRequirements(buildListMapRequirementAssignment(map.get("requirements")));
        builder.setCapabilities(buildMapCapabilityAssignment(map.get("capabilities")));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TNodeTemplate"));
        builder.setArtifacts(buildMapArtifactDefinition(map.get("artifacts")));
        builder.setNode_filter(buildNodeFilterDefinition(map.get("node_filter")));
        builder.setCopy(buildQName((String) map.get("copy")));

        return builder.build();
    }

    @Nullable
    public Map<String, TAttributeAssignment> buildMapAttributeAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TAttributeAssignment> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildAttributeAssignment(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TAttributeAssignment buildAttributeAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TAttributeAssignment.Builder builder = new TAttributeAssignment.Builder();
        builder.setDescription(buildDescription(map.get("description")));
        builder.setValue(map.get("value"));

        return builder.build();
    }

    @Nullable
    public List<TMapRequirementAssignment> buildListMapRequirementAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) object;

        List<TMapRequirementAssignment> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                result.add(buildMapRequirementAssignment(entry.getKey(), entry.getValue()));
            }
        }

        return result;
    }

    @Nullable
    public TMapRequirementAssignment buildMapRequirementAssignment(String key, Object object) {
        if (object == null) {
            return null;
        }

        TMapRequirementAssignment result = new TMapRequirementAssignment();
        result.put(key, buildRequirementAssignment(object));

        return result;
    }

    @Nullable
    public TRequirementAssignment buildRequirementAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TRequirementAssignment.Builder builder = new TRequirementAssignment.Builder();
        builder.setCapability(buildQName((String) map.get("capability")));
        builder.setNode(buildQName((String) map.get("node")));
        builder.setRelationship(buildRelationshipAssignment(map.get("relationship")));
        builder.setNode_filter(buildNodeFilterDefinition(map.get("node_filter")));
        builder.setOccurrences(buildListString(map.get("occurrences")));

        return builder.build();
    }

    @Nullable
    public TRelationshipAssignment buildRelationshipAssignment(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return new TRelationshipAssignment.Builder(buildQName((String) object)).build();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String type = (String) map.get("type");
        TRelationshipAssignment.Builder builder = new TRelationshipAssignment.Builder(buildQName(type));
        builder.setProperties(buildMapPropertyAssignment(map.get("properties")));
        builder.setInterfaces(buildMapInterfaceAssignment(map.get("interfaces")));

        return builder.build();
    }

    @Nullable
    public Map<String, TInterfaceAssignment> buildMapInterfaceAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TInterfaceAssignment> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildInterfaceAssignment(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TInterfaceAssignment buildInterfaceAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TInterfaceAssignment.Builder builder = new TInterfaceAssignment.Builder();
        builder.setType(buildQName((String) map.get("type")));
        builder.setInputs(buildPropertyAssignmentOrDefinition(map.get("inputs"), "TInterfaceAssignment"));

        List<String> INTERFACE_ASSIGNMENT_KEYS = new ArrayList<>(Arrays.asList("type", "inputs"));

        Map<String, TOperationDefinition> operations = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!INTERFACE_ASSIGNMENT_KEYS.contains(entry.getKey())) {
                operations.put(entry.getKey(), buildOperationDefinition(entry.getValue(), "TInterfaceAssignment"));
            }
        }
        if (operations.size() > 0) {
            builder.setOperations(operations);
        }

        return builder.build();
    }

    @Nullable
    public TNodeFilterDefinition buildNodeFilterDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TNodeFilterDefinition.Builder builder = new TNodeFilterDefinition.Builder();
        builder.setProperties(buildListMapPropertyFilterDefinition(map.get("properties")));
        builder.setCapabilities(buildListMapObjectValue(map.get("capabilities")));

        return builder.build();
    }

    @Nullable
    public List<TMapPropertyFilterDefinition> buildListMapPropertyFilterDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) object;

        List<TMapPropertyFilterDefinition> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                result.add(buildMapPropertyDefinition(entry.getKey(), entry.getValue()));
            }
        }

        return result;
    }

    @Nullable
    public TMapPropertyFilterDefinition buildMapPropertyDefinition(String key, Object object) {
        if (object == null) {
            return null;
        }

        TMapPropertyFilterDefinition result = new TMapPropertyFilterDefinition();
        result.put(key, buildPropertyFilterDefinition(object));

        return result;
    }

    @Nullable
    public TPropertyFilterDefinition buildPropertyFilterDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TPropertyFilterDefinition.Builder builder = new TPropertyFilterDefinition.Builder();
        builder.setConstraints(buildConstraints(map.get("constraints")));

        return builder.build();
    }

    @Nullable
    public List<TMapObject> buildListMapObjectValue(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) object;

        List<TMapObject> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                result.add(buildMapObjectValue(entry.getKey(), entry.getValue()));
            }
        }

        return result;
    }

    @Nullable
    public TMapObject buildMapObjectValue(String key, Object object) {
        if (object == null) {
            return null;
        }

        TMapObject result = new TMapObject();
        result.put(key, object);

        return result;
    }

    @Nullable
    public Map<String, TCapabilityAssignment> buildMapCapabilityAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TCapabilityAssignment> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildCapabilityAssignment(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TCapabilityAssignment buildCapabilityAssignment(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TCapabilityAssignment.Builder builder = new TCapabilityAssignment.Builder();
        builder.setProperties(buildMapPropertyAssignment(map.get("properties")));
        builder.setAttributes(buildMapAttributeAssignment(map.get("attributes")));

        return builder.build();
    }

    @Nullable
    public Map<String, TRelationshipTemplate> buildRelationship_templates(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TRelationshipTemplate> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildRelationshipTemplate(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TRelationshipTemplate buildRelationshipTemplate(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String type = (String) map.get("type");
        TRelationshipTemplate.Builder builder = new TRelationshipTemplate.Builder(buildQName(type));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setMetadata(buildMetadata(map.get("metadata")));
        builder.setProperties(buildMapPropertyAssignment(map.get("properties")));
        builder.setAttributes(buildMapAttributeAssignment(map.get("attributes")));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TRelationshipTemplate"));
        builder.setCopy(buildQName((String) map.get("copy")));

        return builder.build();
    }

    @Nullable
    public Map<String, TGroupDefinition> buildGroupDefinitions(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TGroupDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildGroupDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TGroupDefinition buildGroupDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String type = (String) map.get("type");
        TGroupDefinition.Builder builder = new TGroupDefinition.Builder(buildQName(type));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setMetadata(buildMetadata(map.get("metadata")));
        builder.setProperties(buildMapPropertyAssignment(map.get("properties")));
        builder.setMembers(buildListQName(buildListString(map.get("members"))));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TGroupDefinition"));

        return builder.build();
    }

    @Nullable
    public Map<String, TPolicyDefinition> buildMapPolicyDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TPolicyDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildPolicyDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TPolicyDefinition buildPolicyDefinition(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String type = (String) map.get("type");
        TPolicyDefinition.Builder builder = new TPolicyDefinition.Builder(buildQName(type));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setMetadata(buildMetadata(map.get("metadata")));
        builder.setProperties(buildMapPropertyAssignment(map.get("properties")));
        builder.setTargets(buildListQName(buildListString(map.get("targets"))));

        return builder.build();
    }

    @Nullable
    public TSubstitutionMappings buildSubstitutionMappings(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TSubstitutionMappings.Builder builder = new TSubstitutionMappings.Builder();
        builder.setNode_type(buildQName((String) map.get("node_type")));
        builder.setCapabilities(buildMapStringList(map.get("capabilities")));
        builder.setRequirements(buildMapStringList(map.get("requirements")));

        return builder.build();
    }

    @Nullable
    public Map<String, TListString> buildMapStringList(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TListString> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), buildStringList(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TListString buildStringList(Object object) {
        if (object == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<String> tmp = (List<String>) object;

        TListString stringList = new TListString();
        stringList.addAll(tmp);

        return stringList;
    }
}

