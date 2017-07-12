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
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.yaml.model.support.TMapRequirementAssignment;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tNodeTemplate", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"description",
		"directives",
		"properties",
		"attributes",
		"requirements",
		"capabilities",
		"interfaces",
		"artifacts",
		"node_filter",
		"copy",
		"metadata"
})
public class TNodeTemplate {
	@XmlAttribute(name = "type", required = true)
	private String type;
	private String description;
	private Map<String, String> metadata;
	private List<String> directives;
	private Map<String, TPropertyAssignment> properties;
	private Map<String, TAttributeAssignment> attributes;
	private List<TMapRequirementAssignment> requirements;
	private Map<String, TCapabilityAssignment> capabilities;
	private Map<String, TInterfaceDefinition> interfaces;
	private Map<String, TArtifactDefinition> artifacts;
	private TNodeFilterDefinition node_filter;
	private String copy;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public List<String> getDirectives() {
		return directives;
	}

	public void setDirectives(List<String> directives) {
		this.directives = directives;
	}

	public Map<String, TPropertyAssignment> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, TPropertyAssignment> properties) {
		this.properties = properties;
	}

	public Map<String, TAttributeAssignment> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, TAttributeAssignment> attributes) {
		this.attributes = attributes;
	}

	public List<TMapRequirementAssignment> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<TMapRequirementAssignment> requirements) {
		this.requirements = requirements;
	}

	public Map<String, TCapabilityAssignment> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Map<String, TCapabilityAssignment> capabilities) {
		this.capabilities = capabilities;
	}

	public Map<String, TInterfaceDefinition> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
		this.interfaces = interfaces;
	}

	public Map<String, TArtifactDefinition> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(Map<String, TArtifactDefinition> artifacts) {
		this.artifacts = artifacts;
	}

	public TNodeFilterDefinition getNode_filter() {
		return node_filter;
	}

	public void setNode_filter(TNodeFilterDefinition node_filter) {
		this.node_filter = node_filter;
	}

	public String getCopy() {
		return copy;
	}

	public void setCopy(String copy) {
		this.copy = copy;
	}
}
