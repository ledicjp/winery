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
package org.eclipse.winery.yaml.common.reader.YAML;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
import org.eclipse.winery.yaml.common.Exception.UnrecognizedFieldException;
import org.eclipse.winery.yaml.common.Exception.YAMLParserException;
import org.eclipse.winery.yaml.common.Namespaces;
import org.eclipse.winery.yaml.common.validator.FieldValidator;

import org.eclipse.jdt.annotation.Nullable;

public class Builder {
    private final String namespace;
    private List<String> exceptionMessages;
    private Map<String, String> prefix2Namespace;
    private FieldValidator validator;

    public Builder(String namespace) {
        this.namespace = namespace;
        this.validator = new FieldValidator();
        this.exceptionMessages = new ArrayList<>();
    }

    private void initPrefix2Namespace(Object object) {
        if (!Objects.nonNull(object)) {
            return;
        }

        this.prefix2Namespace = new LinkedHashMap<>();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) object;

        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (!(entry.getValue() instanceof String)) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> importDefinition = (Map<String, Object>) entry.getValue();
                    if (importDefinition != null) {
                        String namespacePrefix = (String) importDefinition.get("namespace_prefix");
                        String namespaceUri = (String) importDefinition.get("namespace_uri");
                        if (namespacePrefix != null && namespaceUri != null) {
                            this.prefix2Namespace.put(namespacePrefix, namespaceUri);
                        }
                    }
                }
            }
        }
    }

    private <T> boolean validate(Class<T> clazz, Object object) {
        if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> fields = (Map<String, Object>) object;
            if (!clazz.equals(TInterfaceType.class)) {
                this.exceptionMessages.addAll(validator.validate(clazz, fields));
            }

            return true;
        } else {
            // TODO exception msg: Because instance (=object) is nonNull and not a map
            return false;
        }
    }

    @Nullable
    public TServiceTemplate buildServiceTemplate(Object object) throws YAMLParserException {
        if (!Objects.nonNull(object) || !validate(TServiceTemplate.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        // build map between prefix and namespaces
        initPrefix2Namespace(map.get("imports"));

        String toscaDefinitionsVersion = (String) map.get("tosca_definitions_version");

        TServiceTemplate.Builder builder = new TServiceTemplate.Builder(toscaDefinitionsVersion);

        builder.setMetadata(buildMetadata(map.get("metadata")));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setDslDefinitions(buildDslDefinitions(map.get("dsl_definitions")));
        builder.setRepositories(buildRepositories(map.get("repositories")));
        builder.setImports(buildImports(map.get("imports")));
        builder.setArtifactTypes(buildArtifactTypes(map.get("artifact_types")));
        builder.setDataTypes(buildDataTypes(map.get("data_types")));
        builder.setCapabilityTypes(buildCapabilityTypes(map.get("capability_types")));
        builder.setInterfaceTypes(buildInterfaceTypes(map.get("interface_types")));
        builder.setRelationshipTypes(buildRelationshipTypes(map.get("relationship_types")));
        builder.setNodeTypes(buildNodeTypes(map.get("node_types")));
        builder.setGroupTypes(buildGroupTypes(map.get("group_types")));
        builder.setPolicyTypes(buildPolicyTypes(map.get("policy_types")));
        builder.setTopologyTemplate(buildTopologyTemplate(map.get("topology_template")));

        if (!this.exceptionMessages.isEmpty()) {
            throw new UnrecognizedFieldException(this.exceptionMessages);
        }
        return builder.build();
    }

    @Nullable
    public TTopologyTemplateDefinition buildTopologyTemplate(Object object) {
        if (!Objects.nonNull(object) || !validate(TTopologyTemplateDefinition.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TTopologyTemplateDefinition.Builder builder = new TTopologyTemplateDefinition.Builder();
        builder.setDescription(buildDescription(map.get("description")));
        builder.setInputs(buildMapParameterDefinition(map.get("inputs")));
        builder.setNodeTemplates((buildNodeTemplates(map.get("node_templates"))));
        builder.setRelationshipTemplates(buildRelationshipTemplates(map.get("relationship_templates")));
        builder.setGroups(buildGroupDefinitions(map.get("groups")));
        builder.setPolicies(buildMapPolicyDefinition(map.get("policies")));
        builder.setOutputs(buildMapParameterDefinition(map.get("outputs")));
        builder.setSubstitutionMappings(buildSubstitutionMappings(map.get("substitution_mappings")));

        return builder.build();
    }

    @Nullable
    public Metadata buildMetadata(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> tmp = (Map<String, Object>) object;

        Metadata metadata = new Metadata();
        tmp.entrySet().stream()
            .filter(entry -> Objects.nonNull(entry.getValue()))
            .forEach(entry -> {
                metadata.put(entry.getKey(), entry.getValue().toString());
                if (entry.getValue() instanceof Date) {
                    metadata.put(entry.getKey(), new SimpleDateFormat("yyyy-MM-dd").format(entry.getValue()));
                }
            });

        return metadata;
    }

    @Nullable
    public String buildDescription(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        return (String) object;
    }

    @Nullable
    public Map<String, Object> buildDslDefinitions(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, Object> dslDefinitions = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            dslDefinitions.put(entry.getKey(), entry.getValue());
        }

        return dslDefinitions;
    }

    @Nullable
    public Map<String, TRepositoryDefinition> buildRepositories(Object object) {
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            return new TRepositoryDefinition.Builder((String) object).build();
        }

        if (!validate(TRepositoryDefinition.class, object)) {
            return null;
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
        if (!Objects.nonNull(object) || !validate(Credential.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;
        @SuppressWarnings("unchecked")
        Map<String, String> keys = (Map<String, String>) map.get("keys");

        Credential credential = new Credential();
        credential.setProtocol((String) map.get("protocol"));
        credential.setTokenType((String) map.get("token_type"));
        credential.setToken((String) map.get("token"));
        credential.setKeys(keys);

        return credential;
    }

    @Nullable
    public List<TMapImportDefinition> buildImports(Object object) {
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        TMapImportDefinition mapImportDefinition = new TMapImportDefinition();
        mapImportDefinition.put(key, buildImportDefinition(object));

        return mapImportDefinition;
    }

    @Nullable
    public TImportDefinition buildImportDefinition(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            return new TImportDefinition.Builder((String) object).build();
        }

        if (!validate(TImportDefinition.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;
        String url = (String) map.get("file");

        TImportDefinition.Builder builder = new TImportDefinition.Builder(url);
        builder.setRepository(buildQName((String) map.get("repository")));
        builder.setNamespaceUri((String) map.get("namespace_uri"));
        builder.setNamespacePrefix((String) map.get("namespace_prefix"));

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
    public Map<String, TArtifactType> buildArtifactTypes(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TArtifactType> artifactTypes = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            artifactTypes.put(entry.getKey(), buildArtifactType(entry.getValue()));
        }

        return artifactTypes;
    }

    @Nullable
    public TArtifactType buildArtifactType(Object object) {
        if (!Objects.nonNull(object) || !validate(TArtifactType.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TArtifactType.Builder builder = new TArtifactType.Builder(buildEntityType(object, TArtifactType.class));
        builder.setMimeType((String) map.get("mime_type"));
        builder.setFileExt(buildListString(map.get("file_ext")));

        return builder.build();
    }

    @Nullable
    public TEntityType buildEntityType(Object object, Class child) {
        if (!Objects.nonNull(object) || !validate(child, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TEntityType.Builder builder = new TEntityType.Builder();
        builder.setDescription(buildDescription(map.get("description")));
        builder.setVersion(buildVersion(map.get("version")));
        builder.setDerivedFrom(buildQName((String) map.get("derived_from")));
        builder.setProperties(buildProperties(map.get("properties")));
        builder.setAttributes(buildAttributes(map.get("attributes")));
        builder.setMetadata(buildMetadata(map.get("metadata")));

        return builder.build();
    }

    @Nullable
    public TVersion buildVersion(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        return new TVersion((String) object);
    }

    @Nullable
    public Map<String, TPropertyDefinition> buildProperties(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TPropertyDefinition> properties = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            properties.put(entry.getKey(), buildPropertyDefinition(entry.getValue(), TPropertyDefinition.class));
        }

        return properties;
    }

    @Nullable
    public TPropertyDefinition buildPropertyDefinition(Object object, Class child) {
        if (!Objects.nonNull(object) || !validate(child, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;
        QName type = buildQName((String) map.get("type"));

        TPropertyDefinition.Builder builder = new TPropertyDefinition.Builder(type);
        builder.setDescription(buildDescription(map.get("description")));
        builder.setRequired(buildRequired(map.get("required")));
        builder.setDefault(map.get("default"));
        builder.setStatus(buildStatus(map.get("status")));
        builder.setConstraints(buildConstraints(map.get("constraints")));
        builder.setEntrySchema(buildEntrySchema(map.get("entry_schema")));

        return builder.build();
    }

    @Nullable
    public Boolean buildRequired(Object object) {
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        TConstraintClause constraintClause = new TConstraintClause();

        switch (key) {
            case "equal":
                constraintClause.setEqual(object);
                break;
            case "greater_than":
                constraintClause.setGreaterOrEqual(object);
                break;
            case "greater_or_equal":
                constraintClause.setGreaterOrEqual(object);
                break;
            case "less_than":
                constraintClause.setLessThan(object);
                break;
            case "less_or_equal":
                constraintClause.setLessOrEqual(object);
                break;
            case "in_range":
                constraintClause.setInRange(buildListObject(object));
                break;
            case "valid_values":
                constraintClause.setValidValues(buildListObject(object));
                break;
            case "length":
                constraintClause.setLength(object);
                break;
            case "min_length":
                constraintClause.setMinLength(object);
                break;
            case "max_length":
                constraintClause.setMaxLength(object);
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Object> result = (List<Object>) object;
        return result;
    }

    @Nullable
    public TEntrySchema buildEntrySchema(Object object) {
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object) || !validate(TAttributeDefinition.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        QName type = buildQName((String) map.get("type"));
        TAttributeDefinition.Builder builder = new TAttributeDefinition.Builder(type);
        builder.setDescription(buildDescription(map.get("description")));
        builder.setDefault(map.get("default"));
        builder.setStatus(buildStatus(map.get("status")));
        builder.setEntrySchema(buildEntrySchema(map.get("entry_schema")));

        return builder.build();
    }

    @Nullable
    public List<String> buildListString(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<String> tmp = (List<String>) object;

        return tmp;
    }

    @Nullable
    public Map<String, TDataType> buildDataTypes(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TDataType> dataTypes = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            dataTypes.put(entry.getKey(), buildDataType(entry.getValue()));
        }

        return dataTypes;
    }

    @Nullable
    public TDataType buildDataType(Object object) {
        if (!Objects.nonNull(object) || !validate(TDataType.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TDataType.Builder builder = new TDataType.Builder(buildEntityType(object, TDataType.class));
        builder.setConstraints(buildConstraints(map.get("constraints")));

        return builder.build();
    }

    @Nullable
    public Map<String, TCapabilityType> buildCapabilityTypes(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TCapabilityType> capabilityTypes = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            capabilityTypes.put(entry.getKey(), buildCapabilityType(entry.getValue()));
        }

        return capabilityTypes;
    }

    @Nullable
    public TCapabilityType buildCapabilityType(Object object) {
        if (!Objects.nonNull(object) || !validate(TCapabilityType.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TCapabilityType.Builder builder = new TCapabilityType.Builder(buildEntityType(object, TCapabilityType.class));
        builder.setValidSourceTypes(buildListQName(buildListString(map.get("valid_source_types"))));
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
    public Map<String, TInterfaceType> buildInterfaceTypes(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TInterfaceType> interfaceTypes = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            interfaceTypes.put(entry.getKey(), buildInterfaceType(entry.getValue()));
        }

        return interfaceTypes;
    }

    @Nullable
    public TInterfaceType buildInterfaceType(Object object) {
        if (!Objects.nonNull(object) || !validate(TInterfaceType.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        List<String> INTERFACE_KEYS = new ArrayList<>(Arrays.asList("inputs", "description", "version", "derived_from", "properties", "attributes", "metadata"));

        TInterfaceType.Builder builder = new TInterfaceType.Builder(buildEntityType(object, TInterfaceType.class));
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
        if (!Objects.nonNull(object) || !validate(TOperationDefinition.class, object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        Map<String, TPropertyAssignmentOrDefinition> result = new LinkedHashMap<>();
        if (context.equals("TNodeType") ||
            context.equals("TRelationshipType") ||
            context.equals("TGroupType") ||
            context.equals("TInterfaceType")) {
            Map<String, TPropertyDefinition> propertyDefinitionMap = buildProperties(object);
            for (Map.Entry<String, TPropertyDefinition> entry : propertyDefinitionMap.entrySet()) {
                put(result, entry.getKey(), entry.getValue());
            }
            return result;
        } else {
            Map<String, TPropertyAssignment> propertyAssignmentMap = buildMapPropertyAssignment(object);
            for (Map.Entry<String, TPropertyAssignment> entry : propertyAssignmentMap.entrySet()) {
                put(result, entry.getKey(), entry.getValue());
            }
            return result;
        }
    }

    @Nullable
    public Map<String, TPropertyAssignment> buildMapPropertyAssignment(Object object) {
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        TPropertyAssignment.Builder builder = new TPropertyAssignment.Builder();
        builder.setValue(object);

        return builder.build();
    }

    @Nullable
    public TImplementation buildImplementation(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            return new TImplementation(buildQName((String) object));
        }

        if (!validate(TImplementation.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String primary = (String) map.get("primary");
        TImplementation.Builder builder = new TImplementation.Builder(buildQName(primary));
        builder.setDependencies(buildListQName(buildListString(map.get("dependencies"))));

        return builder.build();
    }

    @Nullable
    public Map<String, TRelationshipType> buildRelationshipTypes(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TRelationshipType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildRelationshipType(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TRelationshipType buildRelationshipType(Object object) {
        if (!Objects.nonNull(object) || !validate(TRelationshipType.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TRelationshipType.Builder builder = new TRelationshipType.Builder(buildEntityType(object, TRelationshipType.class));
        builder.setValidTargetTypes(buildListQName(buildListString(map.get("valid_target_types"))));
        builder.setAttributes(buildAttributes(map.get("attributes")));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TRelationshipType"));

        return builder.build();
    }

    @Nullable
    public Map<String, TInterfaceDefinition> buildMapInterfaceDefinition(Object object, String context) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TInterfaceDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildInterfaceDefinition(entry.getValue(), context));
        }

        return result;
    }

    @Nullable
    public TInterfaceDefinition buildInterfaceDefinition(Object object, String context) {
        if (!Objects.nonNull(object) || !validate(TInterfaceType.class, object)) {
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
    public Map<String, TNodeType> buildNodeTypes(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TNodeType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildNodeType(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TNodeType buildNodeType(Object object) {
        if (!Objects.nonNull(object) || !validate(TNodeType.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TNodeType.Builder builder = new TNodeType.Builder(buildEntityType(object, TNodeType.class));
        builder.setAttributes(buildAttributes(map.get("attributes")));
        builder.setRequirements(buildListMapRequirementDefinition(map.get("requirements")));
        builder.setCapabilities(buildMapCapabilityDefinition(map.get("capabilities")));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TNodeType"));
        builder.setArtifacts(buildMapArtifactDefinition(map.get("artifacts")));

        return builder.build();
    }

    @Nullable
    public List<TMapRequirementDefinition> buildListMapRequirementDefinition(Object object) {
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        TMapRequirementDefinition result = new TMapRequirementDefinition();
        put(result, key, buildRequirementDefinition(object));

        return result;
    }

    @Nullable
    public TRequirementDefinition buildRequirementDefinition(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            return new TRequirementDefinition.Builder(buildQName((String) object)).build();
        }

        if (!validate(TRequirementDefinition.class, object)) {
            return null;
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            return new TRelationshipDefinition.Builder(buildQName((String) object)).build();
        }

        if (!validate(TRequirementDefinition.class, object)) {
            return null;
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TCapabilityDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildCapabilityDefinition(entry.getValue()));
        }

        return result;
    }

    private <T> void put(Map<String, T> map, String key, T value) {
        if (Objects.nonNull(map) && Objects.nonNull(key) && Objects.nonNull(value)) {
            map.put(key, value);
        }
    }

    @Nullable
    public TCapabilityDefinition buildCapabilityDefinition(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            return new TCapabilityDefinition.Builder(buildQName((String) object)).build();
        }

        if (!validate(TCapabilityDefinition.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        String type = (String) map.get("type");
        TCapabilityDefinition.Builder builder = new TCapabilityDefinition.Builder(buildQName(type));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setOccurrences(buildListString(map.get("occurrences")));
        builder.setValidSourceTypes(buildListQName(buildListString(map.get("valid_source_types"))));
        builder.setProperties(buildProperties(map.get("properties")));
        builder.setAttributes(buildAttributes(map.get("attributes")));

        return builder.build();
    }

    @Nullable
    public Map<String, TArtifactDefinition> buildMapArtifactDefinition(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TArtifactDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildArtifactDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TArtifactDefinition buildArtifactDefinition(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            String file = (String) object;
            // TODO infer artifact type and mime type from file URI
            String type = file.substring(file.lastIndexOf("."), file.length());
            return new TArtifactDefinition.Builder(buildQName(type), new ArrayList<>(Collections.singleton(file))).build();
        }

        if (!validate(TArtifactDefinition.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        QName type = buildQName((String) map.get("type"));
        List<String> files;
        if (map.get("file") instanceof String) {
            files = new ArrayList<>(Collections.singleton((String) map.get("file")));
        } else if (map.get("files") instanceof List) {
            files = buildListString(map.get("files"));
        } else {
            files = null;
            assert false;
        }
        TArtifactDefinition.Builder builder = new TArtifactDefinition.Builder(type, files);
        builder.setRepository((String) map.get("repository"));
        builder.setDescription(buildDescription(map.get("description")));
        builder.setDeployPath((String) map.get("deploy_path"));
        builder.setProperties(buildMapPropertyAssignment(map.get("properties")));

        return builder.build();
    }

    @Nullable
    public Map<String, TGroupType> buildGroupTypes(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TGroupType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildGroupType(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TGroupType buildGroupType(Object object) {
        if (!Objects.nonNull(object) || !validate(TGroupType.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TGroupType.Builder builder = new TGroupType.Builder(buildEntityType(object, TGroupType.class));
        builder.setMembers(buildListQName(buildListString(map.get("members"))));
        builder.setRequirements(buildListMapRequirementDefinition(map.get("requirements")));
        builder.setCapabilities(buildMapCapabilityDefinition(map.get("capabilities")));
        builder.setInterfaces(buildMapInterfaceDefinition(map.get("interfaces"), "TGroupType"));

        return builder.build();
    }

    @Nullable
    public Map<String, TPolicyType> buildPolicyTypes(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TPolicyType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildPolicyType(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TPolicyType buildPolicyType(Object object) {
        if (!Objects.nonNull(object) || !validate(TPolicyType.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TPolicyType.Builder builder = new TPolicyType.Builder(buildEntityType(object, TPolicyType.class));
        builder.setTargets(buildListQName(buildListString(map.get("targets"))));
        builder.setTriggers(map.get("triggers"));

        return builder.build();
    }

    @Nullable
    public Map<String, TParameterDefinition> buildMapParameterDefinition(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TParameterDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildParameterDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TParameterDefinition buildParameterDefinition(Object object) {
        if (!Objects.nonNull(object) || !validate(TParameterDefinition.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TParameterDefinition.Builder builder = new TParameterDefinition.Builder(buildPropertyDefinition(object, TParameterDefinition.class));
        builder.setValue(map.get("value"));

        return builder.build();
    }

    @Nullable
    public Map<String, TNodeTemplate> buildNodeTemplates(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TNodeTemplate> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildNodeTemplate(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TNodeTemplate buildNodeTemplate(Object object) {
        if (!Objects.nonNull(object) || !validate(TNodeTemplate.class, object)) {
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
        builder.setNodeFilter(buildNodeFilterDefinition(map.get("node_filter")));
        builder.setCopy(buildQName((String) map.get("copy")));

        return builder.build();
    }

    @Nullable
    public Map<String, TAttributeAssignment> buildMapAttributeAssignment(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TAttributeAssignment> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildAttributeAssignment(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TAttributeAssignment buildAttributeAssignment(Object object) {
        if (!Objects.nonNull(object) || !validate(TAttributeAssignment.class, object)) {
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
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        TMapRequirementAssignment result = new TMapRequirementAssignment();
        put(result, key, buildRequirementAssignment(object));

        return result;
    }

    @Nullable
    public TRequirementAssignment buildRequirementAssignment(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            return new TRequirementAssignment(buildQName((String) object));
        }

        if (!validate(TRequirementAssignment.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TRequirementAssignment.Builder builder = new TRequirementAssignment.Builder();
        builder.setCapability(buildQName((String) map.get("capability")));
        builder.setNode(buildQName((String) map.get("node")));
        builder.setRelationship(buildRelationshipAssignment(map.get("relationship")));
        builder.setNodeFilter(buildNodeFilterDefinition(map.get("node_filter")));
        builder.setOccurrences(buildListString(map.get("occurrences")));

        return builder.build();
    }

    @Nullable
    public TRelationshipAssignment buildRelationshipAssignment(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        if (object instanceof String) {
            return new TRelationshipAssignment.Builder(buildQName((String) object)).build();
        }

        if (!validate(TRelationshipAssignment.class, object)) {
            return null;
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TInterfaceAssignment> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildInterfaceAssignment(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TInterfaceAssignment buildInterfaceAssignment(Object object) {
        if (!Objects.nonNull(object) || !validate(TInterfaceAssignment.class, object)) {
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
        if (!Objects.nonNull(object) || !validate(TNodeFilterDefinition.class, object)) {
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
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        TMapPropertyFilterDefinition result = new TMapPropertyFilterDefinition();
        put(result, key, buildPropertyFilterDefinition(object));

        return result;
    }

    @Nullable
    public TPropertyFilterDefinition buildPropertyFilterDefinition(Object object) {
        if (!Objects.nonNull(object) || !validate(TPropertyFilterDefinition.class, object)) {
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
        if (!Objects.nonNull(object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        TMapObject result = new TMapObject();
        put(result, key, object);

        return result;
    }

    @Nullable
    public Map<String, TCapabilityAssignment> buildMapCapabilityAssignment(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TCapabilityAssignment> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildCapabilityAssignment(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TCapabilityAssignment buildCapabilityAssignment(Object object) {
        if (!Objects.nonNull(object) || !validate(TCapabilityAssignment.class, object)) {
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
    public Map<String, TRelationshipTemplate> buildRelationshipTemplates(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TRelationshipTemplate> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildRelationshipTemplate(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TRelationshipTemplate buildRelationshipTemplate(Object object) {
        if (!Objects.nonNull(object) || !validate(TRelationshipTemplate.class, object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TGroupDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildGroupDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TGroupDefinition buildGroupDefinition(Object object) {
        if (!Objects.nonNull(object) || !validate(TGroupDefinition.class, object)) {
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
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TPolicyDefinition> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildPolicyDefinition(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TPolicyDefinition buildPolicyDefinition(Object object) {
        if (!Objects.nonNull(object) || !validate(TPolicyDefinition.class, object)) {
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
        if (!Objects.nonNull(object) || !validate(TSubstitutionMappings.class, object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        TSubstitutionMappings.Builder builder = new TSubstitutionMappings.Builder();
        builder.setNodeType(buildQName((String) map.get("node_type")));
        builder.setCapabilities(buildMapStringList(map.get("capabilities")));
        builder.setRequirements(buildMapStringList(map.get("requirements")));

        return builder.build();
    }

    @Nullable
    public Map<String, TListString> buildMapStringList(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) object;

        Map<String, TListString> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            put(result, entry.getKey(), buildStringList(entry.getValue()));
        }

        return result;
    }

    @Nullable
    public TListString buildStringList(Object object) {
        if (!Objects.nonNull(object)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<String> tmp = (List<String>) object;

        TListString stringList = new TListString();
        stringList.addAll(tmp);

        return stringList;
    }
}

