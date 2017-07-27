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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.support.ObjectValue;
import org.eclipse.winery.model.tosca.yaml.support.TMapImportDefinition;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tServiceTemplate", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"tosca_definitions_version",
		"metadata",
		"description",
		"dsl_definitions",
		"repositories",
		"imports",
		"artifact_types",
		"data_types",
		"capability_types",
		"interface_types",
		"relationship_types",
		"node_types",
		"group_types",
		"policy_types",
		"topology_template"
})
public class TServiceTemplate {
	@XmlAttribute(name = "tosca_definitions_version", required = true)
	private String tosca_definitions_version;
	private Metadata metadata;
	private String description;
	private Map<String, ObjectValue> dsl_definitions;
	private Map<String, TRepositoryDefinition> repositories;
	private List<TMapImportDefinition> imports;
	private Map<String, TArtifactType> artifact_types;
	private Map<String, TDataType> data_types;
	private Map<String, TCapabilityType> capability_types;
	private Map<String, TInterfaceType> interface_types;
	private Map<String, TRelationshipType> relationship_types;
	private Map<String, TNodeType> node_types;
	private Map<String, TGroupType> group_types;
	private Map<String, TPolicyType> policy_types;
	private TTopologyTemplateDefinition topology_template;

	public TServiceTemplate() {

	}

	public TServiceTemplate(Builder builder) {
		this.setTosca_definitions_version(builder.tosca_definitions_version);
		this.setMetadata(builder.metadata);
		this.setDescription(builder.description);
		this.setDsl_definitions(builder.dsl_definitions);
		this.setRepositories(builder.repositories);
		this.setImports(builder.imports);
		this.setArtifact_types(builder.artifact_types);
		this.setData_types(builder.data_types);
		this.setCapability_types(builder.capability_types);
		this.setInterface_types(builder.interface_types);
		this.setRelationship_types(builder.relationship_types);
		this.setNode_types(builder.node_types);
		this.setGroup_types(builder.group_types);
		this.setPolicy_types(builder.policy_types);
		this.setTopology_template(builder.topology_template);
	}

	@NonNull
	public String getTosca_definitions_version() {
		return tosca_definitions_version;
	}

	public void setTosca_definitions_version(String tosca_definitions_version) {
		this.tosca_definitions_version = tosca_definitions_version;
	}

	@Nullable
	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@NonNull
	public Map<String, ObjectValue> getDsl_definitions() {
		if (this.dsl_definitions == null) {
			this.dsl_definitions = new LinkedHashMap<>();
		}

		return dsl_definitions;
	}

	public void setDsl_definitions(Map<String, ObjectValue> dsl_definitions) {
		this.dsl_definitions = dsl_definitions;
	}

	@Nullable
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NonNull
	public Map<String, TRepositoryDefinition> getRepositories() {
		if (this.repositories == null) {
			this.repositories = new LinkedHashMap<>();
		}

		return repositories;
	}

	public void setRepositories(Map<String, TRepositoryDefinition> repositories) {
		this.repositories = repositories;
	}

	@NonNull
	public List<TMapImportDefinition> getImports() {
		if (this.imports == null) {
			this.imports = new ArrayList<>();
		}

		return imports;
	}

	public void setImports(List<TMapImportDefinition> imports) {
		this.imports = imports;
	}

	@NonNull
	public Map<String, TArtifactType> getArtifact_types() {
		if (this.artifact_types == null) {
			this.artifact_types = new LinkedHashMap<>();
		}

		return artifact_types;
	}

	public void setArtifact_types(Map<String, TArtifactType> artifact_types) {
		this.artifact_types = artifact_types;
	}

	@NonNull
	public Map<String, TDataType> getData_types() {
		if (this.data_types == null) {
			this.data_types = new LinkedHashMap<>();
		}

		return data_types;
	}

	public void setData_types(Map<String, TDataType> data_types) {
		this.data_types = data_types;
	}

	@NonNull
	public Map<String, TCapabilityType> getCapability_types() {
		if (this.capability_types == null) {
			this.capability_types = new LinkedHashMap<>();
		}

		return capability_types;
	}

	public void setCapability_types(Map<String, TCapabilityType> capability_types) {
		this.capability_types = capability_types;
	}

	@NonNull
	public Map<String, TInterfaceType> getInterface_types() {
		if (this.interface_types == null) {
			this.interface_types = new LinkedHashMap<>();
		}

		return interface_types;
	}

	public void setInterface_types(Map<String, TInterfaceType> interface_types) {
		this.interface_types = interface_types;
	}

	@NonNull
	public Map<String, TRelationshipType> getRelationship_types() {
		if (this.relationship_types == null) {
			this.relationship_types = new LinkedHashMap<>();
		}

		return relationship_types;
	}

	public void setRelationship_types(Map<String, TRelationshipType> relationship_types) {
		this.relationship_types = relationship_types;
	}

	@NonNull
	public Map<String, TNodeType> getNode_types() {
		if (this.node_types == null) {
			this.node_types = new LinkedHashMap<>();
		}

		return node_types;
	}

	public void setNode_types(Map<String, TNodeType> node_types) {
		this.node_types = node_types;
	}

	@NonNull
	public Map<String, TGroupType> getGroup_types() {
		if (this.group_types == null) {
			this.group_types = new LinkedHashMap<>();
		}

		return group_types;
	}

	public void setGroup_types(Map<String, TGroupType> group_types) {
		this.group_types = group_types;
	}

	@NonNull
	public Map<String, TPolicyType> getPolicy_types() {
		if (this.policy_types == null) {
			this.policy_types = new LinkedHashMap<>();
		}

		return policy_types;
	}

	public void setPolicy_types(Map<String, TPolicyType> policy_types) {
		this.policy_types = policy_types;
	}

	@Nullable
	public TTopologyTemplateDefinition getTopology_template() {
		return topology_template;
	}

	public void setTopology_template(TTopologyTemplateDefinition topology_template) {
		this.topology_template = topology_template;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String tosca_definitions_version;
		private Metadata metadata;
		private String description;
		private Map<String, ObjectValue> dsl_definitions;
		private Map<String, TRepositoryDefinition> repositories;
		private List<TMapImportDefinition> imports;
		private Map<String, TArtifactType> artifact_types;
		private Map<String, TDataType> data_types;
		private Map<String, TCapabilityType> capability_types;
		private Map<String, TInterfaceType> interface_types;
		private Map<String, TRelationshipType> relationship_types;
		private Map<String, TNodeType> node_types;
		private Map<String, TGroupType> group_types;
		private Map<String, TPolicyType> policy_types;
		private TTopologyTemplateDefinition topology_template;

		public Builder(String tosca_definitions_version) {
			this.tosca_definitions_version = tosca_definitions_version;
		}

		public Builder setMetadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setDsl_definitions(Map<String, ObjectValue> dsl_definitions) {
			this.dsl_definitions = dsl_definitions;
			return this;
		}

		public Builder setRepositories(Map<String, TRepositoryDefinition> repositories) {
			this.repositories = repositories;
			return this;
		}

		public Builder setImports(List<TMapImportDefinition> imports) {
			this.imports = imports;
			return this;
		}

		public Builder setArtifact_types(Map<String, TArtifactType> artifact_types) {
			this.artifact_types = artifact_types;
			return this;
		}

		public Builder setData_types(Map<String, TDataType> data_types) {
			this.data_types = data_types;
			return this;
		}

		public Builder setCapability_types(Map<String, TCapabilityType> capability_types) {
			this.capability_types = capability_types;
			return this;
		}

		public Builder setInterface_types(Map<String, TInterfaceType> interface_types) {
			this.interface_types = interface_types;
			return this;
		}

		public Builder setRelationship_types(Map<String, TRelationshipType> relationship_types) {
			this.relationship_types = relationship_types;
			return this;
		}

		public Builder setNode_types(Map<String, TNodeType> node_types) {
			this.node_types = node_types;
			return this;
		}

		public Builder setNode_type(String key, TNodeType node_type) {
			if (this.node_types == null) {
				this.node_types = new LinkedHashMap<>();
			}
			this.node_types.put(key, node_type);
			return this;
		}

		public Builder setGroup_types(Map<String, TGroupType> group_types) {
			this.group_types = group_types;
			return this;
		}

		public Builder setPolicy_types(Map<String, TPolicyType> policy_types) {
			this.policy_types = policy_types;
			return this;
		}

		public Builder setTopology_template(TTopologyTemplateDefinition topology_template) {
			this.topology_template = topology_template;
			return this;
		}

		public TServiceTemplate build() {
			return new TServiceTemplate(this);
		}
	}
}
