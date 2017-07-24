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
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRequirementDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"occurrences",
		"capability",
		"node",
		"relationship"
})
public class TRequirementDefinition {
	@XmlAttribute(name = "capability", required = true)
	private String capability;
	private String node;
	private TRelationshipDefinition relationship;
	private List<String> occurrences;

	// experimental: not defined in the specification but used in the specification
	private String description;

	public TRequirementDefinition() {

	}

	public TRequirementDefinition(Builder builder) {
		this.setCapability(builder.capability);
		this.setOccurrences(builder.occurrences);
		this.setNode(builder.node);
		this.setRelationship(builder.relationship);
		this.setDescription(builder.description);
	}

	@NonNull
	public List<String> getOccurrences() {
		if (this.occurrences == null) {
			this.occurrences = new ArrayList<>(Arrays.asList("1", "1"));
		}

		// set default lower bound
		if (this.occurrences.size() < 1) {
			this.occurrences.add("1");
		}

		// set default upper bound
		if (this.occurrences.size() < 2) {
			this.occurrences.add("1");
		}

		return occurrences;
	}

	public void setOccurrences(List<String> occurrences) {
		this.occurrences = occurrences;
	}

	@NonNull
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

	public TRelationshipDefinition getRelationship() {
		return relationship;
	}

	public void setRelationship(TRelationshipDefinition relationship) {
		this.relationship = relationship;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NonNull
	public String getUpperBound() {
		return this.getOccurrences().get(1);
	}

	@NonNull
	public Integer getLowerBound() {
		return Integer.valueOf(this.getOccurrences().get(0));
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String capability;
		private String description;
		private List<String> occurrences;
		private String node;
		private TRelationshipDefinition relationship;

		public Builder(String capability) {
			this.capability = capability;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setOccurrences(List<String> occurrences) {
			this.occurrences = occurrences;
			return this;
		}

		public Builder setNode(String node) {
			this.node = node;
			return this;
		}

		public Builder setRelationship(TRelationshipDefinition relationship) {
			this.relationship = relationship;
			return this;
		}

		public TRequirementDefinition build() {
			return new TRequirementDefinition(this);
		}
	}
}
