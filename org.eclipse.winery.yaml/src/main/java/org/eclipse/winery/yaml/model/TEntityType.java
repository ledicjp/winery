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

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tEntityType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"description",
		"version",
		"derived_from",
		"properties",
		"attributes",
		"metadata"
})
public class TEntityType {
	private String description;
	private TVersion version;
	private String derived_from;
	private Map<String, TPropertyDefinition> properties;
	private Map<String, TAttributeDefinition> attributes;
	private Map<String, String> metadata;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TVersion getVersion() {
		return version;
	}

	public void setVersion(TVersion version) {
		this.version = version;
	}

	public String getDerived_from() {
		return derived_from;
	}

	public void setDerived_from(String derived_from) {
		this.derived_from = derived_from;
	}

	public Map<String, TPropertyDefinition> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, TPropertyDefinition> properties) {
		this.properties = properties;
	}

	public Map<String, TAttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, TAttributeDefinition> attributes) {
		this.attributes = attributes;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
}
