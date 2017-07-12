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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCapabilityDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"description",
		"occurrences",
		"valid_source_types",
		"type",
		"properties",
		"attributes"
})
public class TCapabilityDefinition {
	private String description;
	private List<String> occurrences;
	private List<String> valid_source_types;
	@XmlAttribute(name = "type", required = true)
	private String type;
	private Map<String, TPropertyDefinition> properties;
	private Map<String, TArtifactDefinition> attributes;

	public TCapabilityDefinition() {

	}

	public TCapabilityDefinition(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(List<String> occurrences) {
		this.occurrences = occurrences;
	}

	public List<String> getValid_source_types() {
		return valid_source_types;
	}

	public void setValid_source_types(List<String> valid_source_types) {
		this.valid_source_types = valid_source_types;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, TPropertyDefinition> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, TPropertyDefinition> properties) {
		this.properties = properties;
	}

	public Map<String, TArtifactDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, TArtifactDefinition> attributes) {
		this.attributes = attributes;
	}
}
