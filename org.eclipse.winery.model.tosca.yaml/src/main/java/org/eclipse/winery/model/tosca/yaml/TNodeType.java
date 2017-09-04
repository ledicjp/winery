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
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.TMapRequirementDefinition;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tNodeType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
    "attributes",
    "requirements",
    "capabilities",
    "interfaces",
    "artifacts"
})
public class TNodeType extends TNodeOrGroupType {
    private Map<String, TAttributeDefinition> attributes;
    private List<TMapRequirementDefinition> requirements;
    private Map<String, TCapabilityDefinition> capabilities;
    private Map<String, TInterfaceDefinition> interfaces;
    private Map<String, TArtifactDefinition> artifacts;

    public TNodeType() {
    }

    public TNodeType(Builder builder) {
        super(builder);
        this.setAttributes(builder.attributes);
        this.setRequirements(builder.requirements);
        this.setCapabilities(builder.capabilities);
        this.setInterfaces(builder.interfaces);
        this.setArtifacts(builder.artifacts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TNodeType)) return false;
        if (!super.equals(o)) return false;
        TNodeType tNodeType = (TNodeType) o;
        return Objects.equals(attributes, tNodeType.attributes) &&
            Objects.equals(requirements, tNodeType.requirements) &&
            Objects.equals(capabilities, tNodeType.capabilities) &&
            Objects.equals(interfaces, tNodeType.interfaces) &&
            Objects.equals(artifacts, tNodeType.artifacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attributes, requirements, capabilities, interfaces, artifacts);
    }

    @NonNull
    public Map<String, TAttributeDefinition> getAttributes() {
        if (attributes == null) {
            attributes = new LinkedHashMap<>();
        }
        return attributes;
    }

    public void setAttributes(Map<String, TAttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    @NonNull
    public List<TMapRequirementDefinition> getRequirements() {
        if (requirements == null) {
            requirements = new ArrayList<>();
        }

        return requirements;
    }

    public void setRequirements(List<TMapRequirementDefinition> requirements) {
        this.requirements = requirements;
    }

    @NonNull
    public Map<String, TCapabilityDefinition> getCapabilities() {
        if (this.capabilities == null) {
            this.capabilities = new LinkedHashMap<>();
        }

        return capabilities;
    }

    public void setCapabilities(Map<String, TCapabilityDefinition> capabilities) {
        this.capabilities = capabilities;
    }

    @NonNull
    public Map<String, TInterfaceDefinition> getInterfaces() {
        if (this.interfaces == null) {
            this.interfaces = new LinkedHashMap<>();
        }

        return interfaces;
    }

    public void setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
        this.interfaces = interfaces;
    }

    @NonNull
    public Map<String, TArtifactDefinition> getArtifacts() {
        if (this.artifacts == null) {
            this.artifacts = new LinkedHashMap<>();
        }

        return artifacts;
    }

    public void setArtifacts(Map<String, TArtifactDefinition> artifacts) {
        this.artifacts = artifacts;
    }

    public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) {
        R ir1 = super.accept(visitor, parameter);
        R ir2 = visitor.visit(this, parameter);
        if (ir1 == null) {
            return ir2;
        } else {
            return ir1.add(ir2);
        }
    }

    public static class Builder extends TEntityType.Builder {
        private Map<String, TAttributeDefinition> attributes;
        private List<TMapRequirementDefinition> requirements;
        private Map<String, TCapabilityDefinition> capabilities;
        private Map<String, TInterfaceDefinition> interfaces;
        private Map<String, TArtifactDefinition> artifacts;

        public Builder() {

        }

        public Builder(TEntityType entityType) {
            super(entityType);
        }

        public Builder setAttributes(Map<String, TAttributeDefinition> attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder setRequirements(List<TMapRequirementDefinition> requirements) {
            this.requirements = requirements;
            return this;
        }

        public Builder setCapabilities(Map<String, TCapabilityDefinition> capabilities) {
            this.capabilities = capabilities;
            return this;
        }

        public Builder setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
            this.interfaces = interfaces;
            return this;
        }

        public Builder setArtifacts(Map<String, TArtifactDefinition> artifacts) {
            this.artifacts = artifacts;
            return this;
        }

        public Builder addAttributes(Map<String, TAttributeDefinition> attributes) {
            if (attributes == null || attributes.isEmpty()) {
                return this;
            }

            if (this.attributes == null) {
                this.attributes = attributes;
            } else {
                this.attributes.putAll(attributes);
            }

            return this;
        }

        public Builder addAttributes(String name, TAttributeDefinition attribute) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addAttributes(Collections.singletonMap(name, attribute));
        }

        public Builder addRequirements(List<TMapRequirementDefinition> requirements) {
            if (requirements == null || requirements.isEmpty()) {
                return this;
            }

            if (this.requirements == null) {
                this.requirements = requirements;
            } else {
                this.requirements.addAll(requirements);
            }

            return this;
        }

        public Builder addRequirements(TMapRequirementDefinition requirement) {
            if (requirement == null || requirement.isEmpty()) {
                return this;
            }

            return addRequirements(Collections.singletonList(requirement));
        }

        public Builder addRequirements(Map<String, TRequirementDefinition> requirements) {
            if (requirements == null || requirements.isEmpty()) {
                return this;
            }

            requirements.forEach((key, value) -> {
                TMapRequirementDefinition tmp = new TMapRequirementDefinition();
                tmp.put(key, value);
                addRequirements(tmp);
            });

            return this;
        }

        public Builder addRequirements(String name, TRequirementDefinition requirement) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addRequirements(Collections.singletonMap(name, requirement));
        }

        public Builder addCapabilities(Map<String, TCapabilityDefinition> capabilities) {
            if (capabilities == null || capabilities.isEmpty()) {
                return this;
            }

            if (this.capabilities == null) {
                this.capabilities = capabilities;
            } else {
                this.capabilities.putAll(capabilities);
            }

            return this;
        }

        public Builder addCapabilities(String name, TCapabilityDefinition capability) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addCapabilities(Collections.singletonMap(name, capability));
        }

        public Builder addInterfaces(Map<String, TInterfaceDefinition> interfaces) {
            if (interfaces == null || interfaces.isEmpty()) {
                return this;
            }

            if (this.interfaces == null) {
                this.interfaces = interfaces;
            } else {
                this.interfaces.putAll(interfaces);
            }

            return this;
        }

        public Builder addInterfaces(String name, TInterfaceDefinition interfaceDefinition) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addInterfaces(Collections.singletonMap(name, interfaceDefinition));
        }

        public Builder addArtifacts(Map<String, TArtifactDefinition> artifacts) {
            if (artifacts == null || artifacts.isEmpty()) {
                return this;
            }

            if (this.artifacts == null) {
                this.artifacts = artifacts;
            } else {
                this.artifacts.putAll(artifacts);
            }

            return this;
        }

        public Builder addArtifacts(String name, TArtifactDefinition artifact) {
            if (name == null || name.isEmpty()) {
                return this;
            }

            return addArtifacts(Collections.singletonMap(name, artifact));
        }

        public TNodeType build() {
            return new TNodeType(this);
        }
    }
}
