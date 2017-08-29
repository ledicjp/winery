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
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRequirementAssignment", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
    "capability",
    "node",
    "relationship",
    "node_filter",
    "occurrences"
})
public class TRequirementAssignment {
    private QName node;
    private TRelationshipAssignment relationship;
    private QName capability;

    private TNodeFilterDefinition node_filter;
    private List<String> occurrences;

    public TRequirementAssignment() {

    }

    public TRequirementAssignment(QName node) {
        this.node = node;
    }

    public TRequirementAssignment(Builder builder) {
        this.setCapability(builder.capability);
        this.setNode(builder.node);
        this.setRelationship(builder.relationship);
        this.setNode_filter(builder.node_filter);
        this.setOccurrences(builder.occurrences);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TRequirementAssignment)) return false;
        TRequirementAssignment that = (TRequirementAssignment) o;
        return Objects.equals(node, that.node) &&
            Objects.equals(relationship, that.relationship) &&
            Objects.equals(capability, that.capability) &&
            Objects.equals(node_filter, that.node_filter) &&
            Objects.equals(occurrences, that.occurrences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, relationship, capability, node_filter, occurrences);
    }

    @Nullable
    public QName getCapability() {
        return capability;
    }

    public void setCapability(QName capability) {
        this.capability = capability;
    }

    @Nullable
    public QName getNode() {
        return node;
    }

    public void setNode(QName node) {
        this.node = node;
    }

    @Nullable
    public TRelationshipAssignment getRelationship() {
        return relationship;
    }

    public void setRelationship(TRelationshipAssignment relationship) {
        this.relationship = relationship;
    }

    @Nullable
    public TNodeFilterDefinition getNode_filter() {
        return node_filter;
    }

    public void setNode_filter(TNodeFilterDefinition node_filter) {
        this.node_filter = node_filter;
    }

    @NonNull
    public List<String> getOccurrences() {
        if (this.occurrences == null) {
            this.occurrences = new ArrayList<>();
        }

        return occurrences;
    }

    public void setOccurrences(List<String> occurrences) {
        this.occurrences = occurrences;
    }

    public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
        return visitor.visit(this, parameter);
    }

    public static class Builder {
        private QName capability;
        private QName node;
        private TRelationshipAssignment relationship;
        private TNodeFilterDefinition node_filter;
        private List<String> occurrences;

        public Builder setCapability(QName capability) {
            this.capability = capability;
            return this;
        }

        public Builder setNode(QName node) {
            this.node = node;
            return this;
        }

        public Builder setRelationship(TRelationshipAssignment relationship) {
            this.relationship = relationship;
            return this;
        }

        public Builder setNode_filter(TNodeFilterDefinition node_filter) {
            this.node_filter = node_filter;
            return this;
        }

        public Builder setOccurrences(List<String> occurrences) {
            this.occurrences = occurrences;
            return this;
        }

        public TRequirementAssignment build() {
            return new TRequirementAssignment(this);
        }
    }
}
