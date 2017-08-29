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

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPropertyAssignment", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
    "value"
})
public class TPropertyAssignment extends TPropertyAssignmentOrDefinition {
    private Object value;

    public TPropertyAssignment() {
    }

    public TPropertyAssignment(Object value) {
        this.value = value;
    }

    public TPropertyAssignment(Builder builder) {
        this.setValue(builder.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TPropertyAssignment)) return false;
        TPropertyAssignment that = (TPropertyAssignment) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Nullable
    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Nullable
    public Object getGet_input() {
        return this.value;
    }

    public void setGet_input(Object get_input) {
        this.value = get_input;
    }

    @Nullable
    public Object getGet_property() {
        return this.value;
    }

    public void setGet_property(Object get_property) {
        this.value = get_property;
    }

    public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
        return visitor.visit(this, parameter);
    }

    public static class Builder {
        private Object value;
        
        public Builder setValue(Object value) {
            this.value = value;
            return this;
        }

        public TPropertyAssignment build() {
            return new TPropertyAssignment(this);
        }
    }
}
