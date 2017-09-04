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
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPropertyDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
    "type",
    "description",
    "required",
    "defaultValue",
    "status",
    "constraints",
    "entrySchema"
})
public class TPropertyDefinition extends TPropertyAssignmentOrDefinition {
    @XmlAttribute(name = "type", required = true)
    private QName type;
    private String description;
    private Boolean required;
    @XmlElement(name = "default")
    private Object defaultValue;
    private TStatusValue status;
    @XmlElement
    private List<TConstraintClause> constraints;
    @XmlAttribute(name = "entry_schema")
    private TEntrySchema entrySchema;

    public TPropertyDefinition() {
    }

    public TPropertyDefinition(Builder builder) {
        this.setType(builder.type);
        this.setDescription(builder.description);
        this.setRequired(builder.required);
        this.setDefault(builder.defaultValue);
        this.setStatus(builder.status);
        this.setConstraints(builder.constraints);
        this.setEntrySchema(builder.entrySchema);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TPropertyDefinition)) return false;
        TPropertyDefinition that = (TPropertyDefinition) o;
        return Objects.equals(type, that.type) &&
            Objects.equals(description, that.description) &&
            Objects.equals(required, that.required) &&
            Objects.equals(defaultValue, that.defaultValue) &&
            status == that.status &&
            Objects.equals(constraints, that.constraints) &&
            Objects.equals(entrySchema, that.entrySchema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, description, required, defaultValue, status, constraints, entrySchema);
    }

    @NonNull
    public QName getType() {
        return type;
    }

    public void setType(QName type) {
        this.type = type;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public Boolean getRequired() {
        if (required == null) {
            required = true;
        }
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Nullable
    public Object getDefault() {
        return defaultValue;
    }

    public void setDefault(String defaultValue) {
        Object tmp = defaultValue;
        setDefault(tmp);
    }

    public void setDefault(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @NonNull
    public TStatusValue getStatus() {
        if (status == null) {
            status = TStatusValue.supported;
        }
        return status;
    }

    public void setStatus(TStatusValue status) {
        this.status = status;
    }

    public void setStatus(String status) {
        switch (status) {
            case "supported":
                setStatus(TStatusValue.supported);
                break;
            case "unsupported":
                setStatus(TStatusValue.unsupported);
                break;
            case "experimental":
                setStatus(TStatusValue.experimental);
                break;
            case "deprecated":
                setStatus(TStatusValue.deprecated);
                break;
            default:
        }
    }

    @NonNull
    public List<TConstraintClause> getConstraints() {
        if (constraints == null) {
            constraints = new ArrayList<>();
        }
        return constraints;
    }

    public void setConstraints(List<TConstraintClause> constraints) {
        this.constraints = constraints;
    }

    @Nullable
    public TEntrySchema getEntrySchema() {
        return entrySchema;
    }

    public void setEntrySchema(TEntrySchema entrySchema) {
        this.entrySchema = entrySchema;
    }

    public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) {
        return visitor.visit(this, parameter);
    }

    public static class Builder {
        private final QName type;
        private String description;
        private Boolean required;
        private Object defaultValue;
        private TStatusValue status;
        private List<TConstraintClause> constraints;
        private TEntrySchema entrySchema;

        public Builder(QName type) {
            this.type = type;
        }

        public Builder(TPropertyDefinition propertyDefinition) {
            this.type = propertyDefinition.type;
            this.description = propertyDefinition.description;
            this.required = propertyDefinition.required;
            this.defaultValue = propertyDefinition.defaultValue;
            this.status = propertyDefinition.status;
            this.constraints = propertyDefinition.constraints;
            this.entrySchema = propertyDefinition.entrySchema;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setRequired(Boolean required) {
            this.required = required;
            return this;
        }

        public Builder setDefault(Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder setStatus(TStatusValue status) {
            this.status = status;
            return this;
        }

        public Builder setConstraints(List<TConstraintClause> constraints) {
            this.constraints = constraints;
            return this;
        }

        public Builder setEntrySchema(TEntrySchema entrySchema) {
            this.entrySchema = entrySchema;
            return this;
        }

        public Builder addConstraints(List<TConstraintClause> constraints) {
            if (constraints == null || constraints.isEmpty()) {
                return this;
            }

            if (this.constraints == null) {
                this.constraints = constraints;
            } else {
                this.constraints.addAll(constraints);
            }

            return this;
        }

        public Builder addConstraints(TConstraintClause constraint) {
            if (constraint == null) {
                return this;
            }

            return addConstraints(Collections.singletonList(constraint));
        }

        public TPropertyDefinition build() {
            return new TPropertyDefinition(this);
        }
    }
}
