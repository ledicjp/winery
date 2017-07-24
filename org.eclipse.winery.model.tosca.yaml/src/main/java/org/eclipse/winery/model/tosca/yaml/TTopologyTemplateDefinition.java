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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tTopologyTemplate", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"description",
		"inputs",
		"node_templates",
		"relationship_templates",
		"groups",
		"policies",
		"outputs",
		"substitution_mappings"
})
public class TTopologyTemplateDefinition {
	private String description;
	private Map<String, TParameterDefinition> inputs;
	private Map<String, TNodeTemplate> node_templates;
	private Map<String, TRelationshipTemplate> relationship_templates;
	private Map<String, TGroupDefinition> groups;
	private Map<String, TPolicyDefinition> policies;
	private Map<String, TParameterDefinition> outputs;
	private TSubstitutionMappings substitution_mappings;

	public TTopologyTemplateDefinition() {

	}

	public TTopologyTemplateDefinition(Builder builder) {
		this.setDescription(builder.description);
		this.setInputs(builder.inputs);
		this.setNode_templates(builder.node_templates);
		this.setRelationship_templates(builder.relationship_templates);
		this.setGroups(builder.groups);
		this.setPolicies(builder.policies);
		this.setOutputs(builder.outputs);
		this.setSubstitution_mappings(builder.substitution_mappings);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NonNull
	public Map<String, TParameterDefinition> getInputs() {
		if (this.inputs == null) {
			this.inputs = new LinkedHashMap<>();
		}

		return inputs;
	}

	public void setInputs(Map<String, TParameterDefinition> inputs) {
		this.inputs = inputs;
	}

	@NonNull
	public Map<String, TNodeTemplate> getNode_templates() {
		if (this.node_templates == null) {
			this.node_templates = new LinkedHashMap<>();
		}

		return node_templates;
	}

	public void setNode_templates(Map<String, TNodeTemplate> node_templates) {
		this.node_templates = node_templates;
	}

	@NonNull
	public Map<String, TRelationshipTemplate> getRelationship_templates() {
		if (this.relationship_templates == null) {
			this.relationship_templates = new LinkedHashMap<>();
		}

		return relationship_templates;
	}

	public void setRelationship_templates(Map<String, TRelationshipTemplate> relationship_templates) {
		this.relationship_templates = relationship_templates;
	}

	@NonNull
	public Map<String, TGroupDefinition> getGroups() {
		if (this.groups == null) {
			this.groups = new LinkedHashMap<>();
		}

		return groups;
	}

	public void setGroups(Map<String, TGroupDefinition> groups) {
		this.groups = groups;
	}

	@NonNull
	public Map<String, TPolicyDefinition> getPolicies() {
		if (this.policies == null) {
			this.policies = new LinkedHashMap<>();
		}

		return policies;
	}

	public void setPolicies(Map<String, TPolicyDefinition> policies) {
		this.policies = policies;
	}

	@NonNull
	public Map<String, TParameterDefinition> getOutputs() {
		if (this.outputs == null) {
			this.outputs = new LinkedHashMap<>();
		}

		return outputs;
	}

	public void setOutputs(Map<String, TParameterDefinition> outputs) {
		this.outputs = outputs;
	}

	public TSubstitutionMappings getSubstitution_mappings() {
		return substitution_mappings;
	}

	public void setSubstitution_mappings(TSubstitutionMappings substitution_mappings) {
		this.substitution_mappings = substitution_mappings;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private String description;
		private Map<String, TParameterDefinition> inputs;
		private Map<String, TNodeTemplate> node_templates;
		private Map<String, TRelationshipTemplate> relationship_templates;
		private Map<String, TGroupDefinition> groups;
		private Map<String, TPolicyDefinition> policies;
		private Map<String, TParameterDefinition> outputs;
		private TSubstitutionMappings substitution_mappings;

		public Builder() {

		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setInputs(Map<String, TParameterDefinition> inputs) {
			this.inputs = inputs;
			return this;
		}

		public Builder setNode_templates(Map<String, TNodeTemplate> node_templates) {
			this.node_templates = node_templates;
			return this;
		}

		public Builder setRelationship_templates(Map<String, TRelationshipTemplate> relationship_templates) {
			this.relationship_templates = relationship_templates;
			return this;
		}

		public Builder setGroups(Map<String, TGroupDefinition> groups) {
			this.groups = groups;
			return this;
		}

		public Builder setPolicies(Map<String, TPolicyDefinition> policies) {
			this.policies = policies;
			return this;
		}

		public Builder setOutputs(Map<String, TParameterDefinition> outputs) {
			this.outputs = outputs;
			return this;
		}

		public Builder setSubstitution_mappings(TSubstitutionMappings substitution_mappings) {
			this.substitution_mappings = substitution_mappings;
			return this;
		}

		public TTopologyTemplateDefinition build() {
			return new TTopologyTemplateDefinition(this);
		}
	}
}
