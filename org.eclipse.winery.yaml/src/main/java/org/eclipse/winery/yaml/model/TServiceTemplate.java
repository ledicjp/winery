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
package org.eclipse.winery.yaml.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.yaml.model.support.ObjectValue;
import org.eclipse.winery.yaml.model.support.TMapImportDefinition;

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
	private Map<String, String> metadata;
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

	public String getTosca_definitions_version() {
		return tosca_definitions_version;
	}

	public void setTosca_definitions_version(String tosca_definitions_version) {
		this.tosca_definitions_version = tosca_definitions_version;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public Map<String, ObjectValue> getDsl_definitions() {
		return dsl_definitions;
	}

	public void setDsl_definitions(Map<String, ObjectValue> dsl_definitions) {
		this.dsl_definitions = dsl_definitions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, TRepositoryDefinition> getRepositories() {
		return repositories;
	}

	public void setRepositories(Map<String, TRepositoryDefinition> repositories) {
		this.repositories = repositories;
	}

	public List<TMapImportDefinition> getImports() {
		return imports;
	}

	public void setImports(List<TMapImportDefinition> imports) {
		this.imports = imports;
	}

	public Map<String, TArtifactType> getArtifact_types() {
		return artifact_types;
	}

	public void setArtifact_types(Map<String, TArtifactType> artifact_types) {
		this.artifact_types = artifact_types;
	}

	public Map<String, TDataType> getData_types() {
		return data_types;
	}

	public void setData_types(Map<String, TDataType> data_types) {
		this.data_types = data_types;
	}

	public Map<String, TCapabilityType> getCapability_types() {
		return capability_types;
	}

	public void setCapability_types(Map<String, TCapabilityType> capability_types) {
		this.capability_types = capability_types;
	}

	public Map<String, TInterfaceType> getInterface_types() {
		return interface_types;
	}

	public void setInterface_types(Map<String, TInterfaceType> interface_types) {
		this.interface_types = interface_types;
	}

	public Map<String, TRelationshipType> getRelationship_types() {
		return relationship_types;
	}

	public void setRelationship_types(Map<String, TRelationshipType> relationship_types) {
		this.relationship_types = relationship_types;
	}

	public Map<String, TNodeType> getNode_types() {
		return node_types;
	}

	public void setNode_types(Map<String, TNodeType> node_types) {
		this.node_types = node_types;
	}

	public Map<String, TGroupType> getGroup_types() {
		return group_types;
	}

	public void setGroup_types(Map<String, TGroupType> group_types) {
		this.group_types = group_types;
	}

	public Map<String, TPolicyType> getPolicy_types() {
		return policy_types;
	}

	public void setPolicy_types(Map<String, TPolicyType> policy_types) {
		this.policy_types = policy_types;
	}

	public TTopologyTemplateDefinition getTopology_template() {
		return topology_template;
	}

	public void setTopology_template(TTopologyTemplateDefinition topology_template) {
		this.topology_template = topology_template;
	}
}
