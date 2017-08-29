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
package org.eclipse.winery.model.tosca.yaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.support.TMapImportDefinition;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tServiceTemplate", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
    "tosca_definitions_version",
    "metadata",
    "description",
    "dsl_definitions",
    "repositories",
    "imports",
    "artifact_types",
    "data_types",
    "capability_types",
    "interface_types",
    "relationship_types",
    "node_types",
    "group_types",
    "policy_types",
    "topology_template"
})
public class TServiceTemplate {
    @XmlAttribute(name = "tosca_definitions_version", required = true)
    private String tosca_definitions_version;
    private Metadata metadata;
    private String description;
    private Map<String, Object> dsl_definitions;
    private Map<String, TRepositoryDefinition> repositories;
    private List<TMapImportDefinition> imports;
    private Map<String, TArtifactType> artifact_types;
    private Map<String, TDataType> data_types;
    private Map<String, TCapabilityType> capability_types;
    private Map<String, TInterfaceType> interface_types;
    private Map<String, TRelationshipType> relationship_types;
    private Map<String, TNodeType> node_types;
    private Map<String, TGroupType> group_types;
    private Map<String, TPolicyType> policy_types;
    private TTopologyTemplateDefinition topology_template;

    public TServiceTemplate() {

    }

    public TServiceTemplate(Builder builder) {
        this.setTosca_definitions_version(builder.tosca_definitions_version);
        this.setMetadata(builder.metadata);
        this.setDescription(builder.description);
        this.setDsl_definitions(builder.dsl_definitions);
        this.setRepositories(builder.repositories);
        this.setImports(builder.imports);
        this.setArtifact_types(builder.artifact_types);
        this.setData_types(builder.data_types);
        this.setCapability_types(builder.capability_types);
        this.setInterface_types(builder.interface_types);
        this.setRelationship_types(builder.relationship_types);
        this.setNode_types(builder.node_types);
        this.setGroup_types(builder.group_types);
        this.setPolicy_types(builder.policy_types);
        this.setTopology_template(builder.topology_template);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TServiceTemplate)) return false;
        TServiceTemplate that = (TServiceTemplate) o;
        return Objects.equals(tosca_definitions_version, that.tosca_definitions_version) &&
            Objects.equals(metadata, that.metadata) &&
            Objects.equals(description, that.description) &&
            Objects.equals(dsl_definitions, that.dsl_definitions) &&
            Objects.equals(repositories, that.repositories) &&
            Objects.equals(imports, that.imports) &&
            Objects.equals(artifact_types, that.artifact_types) &&
            Objects.equals(data_types, that.data_types) &&
            Objects.equals(capability_types, that.capability_types) &&
            Objects.equals(interface_types, that.interface_types) &&
            Objects.equals(relationship_types, that.relationship_types) &&
            Objects.equals(node_types, that.node_types) &&
            Objects.equals(group_types, that.group_types) &&
            Objects.equals(policy_types, that.policy_types) &&
            Objects.equals(topology_template, that.topology_template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tosca_definitions_version, metadata, description, dsl_definitions, repositories, imports, artifact_types, data_types, capability_types, interface_types, relationship_types, node_types, group_types, policy_types, topology_template);
    }

    @NonNull
    public String getTosca_definitions_version() {
        return tosca_definitions_version;
    }

    public void setTosca_definitions_version(String tosca_definitions_version) {
        this.tosca_definitions_version = tosca_definitions_version;
    }

    @NonNull
    public Metadata getMetadata() {
        if (this.metadata == null) {
            this.metadata = new Metadata();
        }
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @NonNull
    public Map<String, Object> getDsl_definitions() {
        if (this.dsl_definitions == null) {
            this.dsl_definitions = new LinkedHashMap<>();
        }

        return dsl_definitions;
    }

    public void setDsl_definitions(Map<String, Object> dsl_definitions) {
        this.dsl_definitions = dsl_definitions;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public Map<String, TRepositoryDefinition> getRepositories() {
        if (this.repositories == null) {
            this.repositories = new LinkedHashMap<>();
        }

        return repositories;
    }

    public void setRepositories(Map<String, TRepositoryDefinition> repositories) {
        this.repositories = repositories;
    }

    @NonNull
    public List<TMapImportDefinition> getImports() {
        if (this.imports == null) {
            this.imports = new ArrayList<>();
        }

        return imports;
    }

    public void setImports(List<TMapImportDefinition> imports) {
        this.imports = imports;
    }

    @NonNull
    public Map<String, TArtifactType> getArtifact_types() {
        if (this.artifact_types == null) {
            this.artifact_types = new LinkedHashMap<>();
        }

        return artifact_types;
    }

    public void setArtifact_types(Map<String, TArtifactType> artifact_types) {
        this.artifact_types = artifact_types;
    }

    @NonNull
    public Map<String, TDataType> getData_types() {
        if (this.data_types == null) {
            this.data_types = new LinkedHashMap<>();
        }

        return data_types;
    }

    public void setData_types(Map<String, TDataType> data_types) {
        this.data_types = data_types;
    }

    @NonNull
    public Map<String, TCapabilityType> getCapability_types() {
        if (this.capability_types == null) {
            this.capability_types = new LinkedHashMap<>();
        }

        return capability_types;
    }

    public void setCapability_types(Map<String, TCapabilityType> capability_types) {
        this.capability_types = capability_types;
    }

    @NonNull
    public Map<String, TInterfaceType> getInterface_types() {
        if (this.interface_types == null) {
            this.interface_types = new LinkedHashMap<>();
        }

        return interface_types;
    }

    public void setInterface_types(Map<String, TInterfaceType> interface_types) {
        this.interface_types = interface_types;
    }

    @NonNull
    public Map<String, TRelationshipType> getRelationship_types() {
        if (this.relationship_types == null) {
            this.relationship_types = new LinkedHashMap<>();
        }

        return relationship_types;
    }

    public void setRelationship_types(Map<String, TRelationshipType> relationship_types) {
        this.relationship_types = relationship_types;
    }

    @NonNull
    public Map<String, TNodeType> getNode_types() {
        if (this.node_types == null) {
            this.node_types = new LinkedHashMap<>();
        }

        return node_types;
    }

    public void setNode_types(Map<String, TNodeType> node_types) {
        this.node_types = node_types;
    }

    @NonNull
    public Map<String, TGroupType> getGroup_types() {
        if (this.group_types == null) {
            this.group_types = new LinkedHashMap<>();
        }

        return group_types;
    }

    public void setGroup_types(Map<String, TGroupType> group_types) {
        this.group_types = group_types;
    }

    @NonNull
    public Map<String, TPolicyType> getPolicy_types() {
        if (this.policy_types == null) {
            this.policy_types = new LinkedHashMap<>();
        }

        return policy_types;
    }

    public void setPolicy_types(Map<String, TPolicyType> policy_types) {
        this.policy_types = policy_types;
    }

    @Nullable
    public TTopologyTemplateDefinition getTopology_template() {
        return topology_template;
    }

    public void setTopology_template(TTopologyTemplateDefinition topology_template) {
        this.topology_template = topology_template;
    }

    public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
        return visitor.visit(this, parameter);
    }

    public static class Builder {
        private final String tosca_definitions_version;
        private Metadata metadata;
        private String description;
        private Map<String, Object> dsl_definitions;
        private Map<String, TRepositoryDefinition> repositories;
        private List<TMapImportDefinition> imports;
        private Map<String, TArtifactType> artifact_types;
        private Map<String, TDataType> data_types;
        private Map<String, TCapabilityType> capability_types;
        private Map<String, TInterfaceType> interface_types;
        private Map<String, TRelationshipType> relationship_types;
        private Map<String, TNodeType> node_types;
        private Map<String, TGroupType> group_types;
        private Map<String, TPolicyType> policy_types;
        private TTopologyTemplateDefinition topology_template;

        public Builder(String tosca_definitions_version) {
            this.tosca_definitions_version = tosca_definitions_version;
        }

        public Builder setMetadata(Metadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDsl_definitions(Map<String, Object> dsl_definitions) {
            this.dsl_definitions = dsl_definitions;
            return this;
        }

        public Builder setRepositories(Map<String, TRepositoryDefinition> repositories) {
            this.repositories = repositories;
            return this;
        }

        public Builder setImports(List<TMapImportDefinition> imports) {
            this.imports = imports;
            return this;
        }

        public Builder setArtifact_types(Map<String, TArtifactType> artifact_types) {
            this.artifact_types = artifact_types;
            return this;
        }

        public Builder setData_types(Map<String, TDataType> data_types) {
            this.data_types = data_types;
            return this;
        }

        public Builder setCapability_types(Map<String, TCapabilityType> capability_types) {
            this.capability_types = capability_types;
            return this;
        }

        public Builder setInterface_types(Map<String, TInterfaceType> interface_types) {
            this.interface_types = interface_types;
            return this;
        }

        public Builder setRelationship_types(Map<String, TRelationshipType> relationship_types) {
            this.relationship_types = relationship_types;
            return this;
        }

        public Builder setNode_types(Map<String, TNodeType> node_types) {
            this.node_types = node_types;
            return this;
        }

        public Builder setNode_type(String key, TNodeType node_type) {
            if (this.node_types == null) {
                this.node_types = new LinkedHashMap<>();
            }
            this.node_types.put(key, node_type);
            return this;
        }

        public Builder setGroup_types(Map<String, TGroupType> group_types) {
            this.group_types = group_types;
            return this;
        }

        public Builder setPolicy_types(Map<String, TPolicyType> policy_types) {
            this.policy_types = policy_types;
            return this;
        }

        public Builder setTopology_template(TTopologyTemplateDefinition topology_template) {
            this.topology_template = topology_template;
            return this;
        }

        public Builder addDsl_definitions(Map<String, Object> dsl_definitions) {
            if (dsl_definitions == null || dsl_definitions.isEmpty()) {
                return this;
            }

            if (this.dsl_definitions == null) {
                this.dsl_definitions = dsl_definitions;
            } else {
                this.dsl_definitions.putAll(dsl_definitions);
            }

            return this;
        }

        public Builder addDsl_definitions(String name, Object dsl_definition) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addDsl_definitions(Collections.singletonMap(name, dsl_definition));
        }

        public Builder addRepositories(Map<String, TRepositoryDefinition> repositories) {
            if (repositories == null || repositories.isEmpty()) {
                return this;
            }

            if (this.repositories == null) {
                this.repositories = repositories;
            } else {
                this.repositories.putAll(repositories);
            }

            return this;
        }

        public Builder addRepositories(String name, TRepositoryDefinition repository) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addRepositories(Collections.singletonMap(name, repository));
        }

        public Builder addImports(List<TMapImportDefinition> imports) {
            if (imports == null || imports.isEmpty()) {
                return this;
            }

            if (this.imports == null) {
                this.imports = imports;
            } else {
                this.imports.addAll(imports);
            }

            return this;
        }

        public Builder addImports(TMapImportDefinition _import) {
            if (_import == null | _import.isEmpty()) {
                return this;
            }

            return addImports(Collections.singletonList(_import));
        }

        public Builder addImports(Map<String, TImportDefinition> imports) {
            if (imports == null || imports.isEmpty()) {
                return this;
            }

            imports.forEach((key, value) -> {
                TMapImportDefinition tmp = new TMapImportDefinition();
                tmp.put(key, value);
                addImports(tmp);
            });

            return this;
        }

        public Builder addImports(String name, TImportDefinition _import) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addImports(Collections.singletonMap(name, _import));
        }

        public Builder addArtifact_types(Map<String, TArtifactType> artifact_types) {
            if (artifact_types == null || artifact_types.isEmpty()) {
                return this;
            }

            if (this.artifact_types == null) {
                this.artifact_types = artifact_types;
            } else {
                this.artifact_types.putAll(artifact_types);
            }

            return this;
        }

        public Builder addArtifact_types(String name, TArtifactType artifact_type) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addArtifact_types(Collections.singletonMap(name, artifact_type));
        }

        public Builder addData_types(Map<String, TDataType> data_types) {
            if (data_types == null || data_types.isEmpty()) {
                return this;
            }

            if (this.data_types == null) {
                this.data_types = data_types;
            } else {
                this.data_types.putAll(data_types);
            }

            return this;
        }

        public Builder addData_types(String name, TDataType data_type) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addData_types(Collections.singletonMap(name, data_type));
        }

        public Builder addCapability_types(Map<String, TCapabilityType> capability_types) {
            if (capability_types == null || capability_types.isEmpty()) {
                return this;
            }

            if (this.capability_types == null) {
                this.capability_types = capability_types;
            } else {
                this.capability_types.putAll(capability_types);
            }

            return this;
        }

        public Builder addCapability_types(String name, TCapabilityType capability_type) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addCapability_types(Collections.singletonMap(name, capability_type));
        }

        public Builder addInterface_types(Map<String, TInterfaceType> interface_types) {
            if (interface_types == null || interface_types.isEmpty()) {
                return this;
            }

            if (this.interface_types == null) {
                this.interface_types = interface_types;
            } else {
                this.interface_types.putAll(interface_types);
            }

            return this;
        }

        public Builder addInterface_types(String name, TInterfaceType interface_type) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addInterface_types(Collections.singletonMap(name, interface_type));
        }

        public Builder addRelationship_types(Map<String, TRelationshipType> relationship_types) {
            if (relationship_types == null || relationship_types.isEmpty()) {
                return this;
            }

            if (this.relationship_types == null) {
                this.relationship_types = relationship_types;
            } else {
                this.relationship_types.putAll(relationship_types);
            }

            return this;
        }

        public Builder addRelationship_types(String name, TRelationshipType relationship_type) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addRelationship_types(Collections.singletonMap(name, relationship_type));
        }

        public Builder addNode_types(Map<String, TNodeType> node_types) {
            if (node_types == null || node_types.isEmpty()) {
                return this;
            }

            if (this.node_types == null) {
                this.node_types = node_types;
            } else {
                this.node_types.putAll(node_types);
            }

            return this;
        }

        public Builder addNode_types(String name, TNodeType node_type) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addNode_types(Collections.singletonMap(name, node_type));
        }

        public Builder addGroup_types(Map<String, TGroupType> group_types) {
            if (group_types == null || group_types.isEmpty()) {
                return this;
            }

            if (this.group_types == null) {
                this.group_types = group_types;
            } else {
                this.group_types.putAll(group_types);
            }

            return this;
        }

        public Builder addGroup_types(String name, TGroupType group_type) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addGroup_types(Collections.singletonMap(name, group_type));
        }

        public Builder addPolicy_types(Map<String, TPolicyType> policy_types) {
            if (policy_types == null || policy_types.isEmpty()) {
                return this;
            }

            if (this.policy_types == null) {
                this.policy_types = policy_types;
            } else {
                this.policy_types.putAll(policy_types);
            }

            return this;
        }

        public Builder addPolicy_types(String name, TPolicyType policy_type) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addPolicy_types(Collections.singletonMap(name, policy_type));
        }

        public TServiceTemplate build() {
            return new TServiceTemplate(this);
        }
    }
}
