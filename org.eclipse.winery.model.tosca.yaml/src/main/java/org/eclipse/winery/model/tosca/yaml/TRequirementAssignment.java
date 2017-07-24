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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRequirementAssignment", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"capability",
		"node",
		"relationship",
		"node_filter",
		"occurrences"
})
public class TRequirementAssignment {
	private String node;
	private TRelationshipAssignment relationship;
	private String capability;

	private TNodeFilterDefinition node_filter;
	private List<String> occurrences;

	public TRequirementAssignment() {

	}

	public TRequirementAssignment(String node) {
		this.node = node;
	}

	public TRequirementAssignment(Builder builder) {
		this.setCapability(builder.capability);
		this.setNode(builder.node);
		this.setRelationship(builder.relationship);
		this.setNode_filter(builder.node_filter);
		this.setOccurrences(builder.occurrences);
	}

	public String getCapability() {
		return capability;
	}

	public void setCapability(String capability) {
		this.capability = capability;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public TRelationshipAssignment getRelationship() {
		return relationship;
	}

	public void setRelationship(TRelationshipAssignment relationship) {
		this.relationship = relationship;
	}

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

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private String capability;
		private String node;
		private TRelationshipAssignment relationship;
		private TNodeFilterDefinition node_filter;
		private List<String> occurrences;

		public Builder() {

		}

		public Builder setCapability(String capability) {
			this.capability = capability;
			return this;
		}

		public Builder setNode(String node) {
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
