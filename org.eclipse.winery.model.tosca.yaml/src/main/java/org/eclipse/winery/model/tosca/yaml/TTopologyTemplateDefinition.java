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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

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

	@Nullable
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

	@Nullable
	public TSubstitutionMappings getSubstitution_mappings() {
		return substitution_mappings;
	}

	public void setSubstitution_mappings(TSubstitutionMappings substitution_mappings) {
		this.substitution_mappings = substitution_mappings;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
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

		public Builder addInputs(Map<String, TParameterDefinition> inputs) {
			if (inputs == null || inputs.isEmpty()) {
				return this;
			}

			if (this.inputs == null) {
				this.inputs = inputs;
			} else {
				this.inputs.putAll(inputs);
			}

			return this;
		}

		public Builder addInputs(String name, TParameterDefinition input) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addInputs(Collections.singletonMap(name, input));
		}

		public Builder addNode_templates(Map<String, TNodeTemplate> node_templates) {
			if (node_templates == null || node_templates.isEmpty()) {
				return this;
			}

			if (this.node_templates == null) {
				this.node_templates = node_templates;
			} else {
				this.node_templates.putAll(node_templates);
			}

			return this;
		}

		public Builder addNode_templates(String name, TNodeTemplate node_template) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addNode_templates(Collections.singletonMap(name, node_template));
		}

		public Builder addRelationship_templates(Map<String, TRelationshipTemplate> relationship_templates) {
			if (relationship_templates == null || relationship_templates.isEmpty()) {
				return this;
			}

			if (this.relationship_templates == null) {
				this.relationship_templates = relationship_templates;
			} else {
				this.relationship_templates.putAll(relationship_templates);
			}
			
			return this;
		}

		public Builder addRelationship_templates(String name, TRelationshipTemplate relationship_template) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addRelationship_templates(Collections.singletonMap(name, relationship_template));
		}

		public Builder addGroups(Map<String, TGroupDefinition> groups) {
			if (groups == null || groups.isEmpty()) {
				return this;
			}

			if (this.groups == null) {
				this.groups = groups;
			} else {
				this.groups.putAll(groups);
			}

			return this;
		}

		public Builder addGroups(String name, TGroupDefinition group) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addGroups(Collections.singletonMap(name, group));
		}

		public Builder addPolicies(Map<String, TPolicyDefinition> policies) {
			if (policies == null || policies.isEmpty()) {
				return this;
			}

			if (this.policies == null) {
				this.policies = policies;
			} else {
				this.policies.putAll(policies);
			}

			return this;
		}

		public Builder addPolicies(String name, TPolicyDefinition policy) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addPolicies(Collections.singletonMap(name, policy));
		}

		public Builder addOutputs(Map<String, TParameterDefinition> outputs) {
			if (outputs == null || outputs.isEmpty()) {
				return this;
			}

			if (this.outputs == null) {
				this.outputs = outputs;
			} else {
				this.outputs.putAll(outputs);
			}

			return this;
		}

		public Builder addOutputs(String name, TParameterDefinition output) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addOutputs(Collections.singletonMap(name, output));
		}

		public TTopologyTemplateDefinition build() {
			return new TTopologyTemplateDefinition(this);
		}
	}
}
