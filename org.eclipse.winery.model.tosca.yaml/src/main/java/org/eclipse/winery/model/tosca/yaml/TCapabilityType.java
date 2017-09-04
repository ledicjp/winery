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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCapabilityType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
    "validSourceTypes"
})
public class TCapabilityType extends TEntityType {
    @XmlAttribute(name = "valid_source_type")
    private List<QName> validSourceTypes;

    public TCapabilityType() {
    }

    public TCapabilityType(Builder builder) {
        super(builder);
        this.setValidSourceTypes(builder.validSourceTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TCapabilityType)) return false;
        TCapabilityType that = (TCapabilityType) o;
        return Objects.equals(validSourceTypes, that.validSourceTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validSourceTypes);
    }

    @NonNull
    public List<QName> getValidSourceTypes() {
        if (this.validSourceTypes == null) {
            this.validSourceTypes = new ArrayList<>();
        }

        return validSourceTypes;
    }

    public void setValidSourceTypes(List<QName> validSourceTypes) {
        this.validSourceTypes = validSourceTypes;
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
        private List<QName> validSourceTypes;

        public Builder() {

        }

        public Builder(TEntityType entityType) {
            super(entityType);
        }

        public Builder setValidSourceTypes(List<QName> validSourceTypes) {
            this.validSourceTypes = validSourceTypes;
            return this;
        }

        public Builder addValidSourceTypes(List<QName> validSourceTypes) {
            if (validSourceTypes == null || validSourceTypes.isEmpty()) {
                return this;
            }

            if (this.validSourceTypes == null) {
                this.validSourceTypes = validSourceTypes;
            } else {
                this.validSourceTypes.addAll(validSourceTypes);
            }

            return this;
        }

        public Builder addValidSourceTypes(QName validSourceType) {
            if (validSourceType == null) {
                return this;
            }

            return addValidSourceTypes(Collections.singletonList(validSourceType));
        }

        public TCapabilityType build() {
            return new TCapabilityType(this);
        }
    }
}
