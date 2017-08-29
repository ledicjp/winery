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

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tConstraintClause", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
    "equal",
    "greater_than",
    "greater_or_equal",
    "less_than",
    "in_range",
    "valid_values",
    "length",
    "min_length",
    "max_length",
    "pattern"
})
public class TConstraintClause {
    private Object equal;
    private Object greater_than;
    private Object greater_or_equal;
    private Object less_than;
    private Object less_or_equal;
    private List<Object> in_range;
    private List<Object> valid_values;
    private Object length;
    private Object min_length;
    private Object max_length;
    private Object pattern;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TConstraintClause)) return false;
        TConstraintClause that = (TConstraintClause) o;
        return Objects.equals(equal, that.equal) &&
            Objects.equals(greater_than, that.greater_than) &&
            Objects.equals(greater_or_equal, that.greater_or_equal) &&
            Objects.equals(less_than, that.less_than) &&
            Objects.equals(less_or_equal, that.less_or_equal) &&
            Objects.equals(in_range, that.in_range) &&
            Objects.equals(valid_values, that.valid_values) &&
            Objects.equals(length, that.length) &&
            Objects.equals(min_length, that.min_length) &&
            Objects.equals(max_length, that.max_length) &&
            Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equal, greater_than, greater_or_equal, less_than, less_or_equal, in_range, valid_values, length, min_length, max_length, pattern);
    }

    public Object getEqual() {
        return equal;
    }

    public void setEqual(Object equal) {
        this.equal = equal;
    }

    public Object getGreater_than() {
        return greater_than;
    }

    public void setGreater_than(Object greater_than) {
        this.greater_than = greater_than;
    }

    public Object getGreater_or_equal() {
        return greater_or_equal;
    }

    public void setGreater_or_equal(Object greater_or_equal) {
        this.greater_or_equal = greater_or_equal;
    }

    public Object getLess_than() {
        return less_than;
    }

    public void setLess_than(Object less_than) {
        this.less_than = less_than;
    }

    public Object getLess_or_equal() {
        return less_or_equal;
    }

    public void setLess_or_equal(Object less_or_equal) {
        this.less_or_equal = less_or_equal;
    }

    public List<Object> getIn_range() {
        return in_range;
    }

    public void setIn_range(List<Object> in_range) {
        this.in_range = in_range;
    }

    public List<Object> getValid_values() {
        return valid_values;
    }

    public void setValid_values(List<Object> valid_values) {
        this.valid_values = valid_values;
    }

    public Object getLength() {
        return length;
    }

    public void setLength(Object length) {
        this.length = length;
    }

    public Object getMin_length() {
        return min_length;
    }

    public void setMin_length(Object min_length) {
        this.min_length = min_length;
    }

    public Object getMax_length() {
        return max_length;
    }

    public void setMax_length(Object max_length) {
        this.max_length = max_length;
    }

    public Object getPattern() {
        return pattern;
    }

    public void setPattern(Object pattern) {
        this.pattern = pattern;
    }

    public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
        return visitor.visit(this, parameter);
    }
}
