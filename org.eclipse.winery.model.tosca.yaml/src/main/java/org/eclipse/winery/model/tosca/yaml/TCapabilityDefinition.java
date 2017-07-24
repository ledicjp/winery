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
import java.util.List;
import java.util.Map;

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
	private Map<String, TAttributeDefinition> attributes;

	public TCapabilityDefinition() {

	}

	public TCapabilityDefinition(String type) {
		this.type = type;
	}

	public TCapabilityDefinition(Builder builder) {
		this.setType(builder.type);
		this.setDescription(builder.description);
		this.setOccurrences(builder.occurrences);
		this.setValid_source_types(builder.valid_source_types);
		this.setProperties(builder.properties);
		this.setAttributes(builder.attributes);
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

	@NonNull
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NonNull
	public Map<String, TPropertyDefinition> getProperties() {
		if (this.properties == null) {
			this.properties = new LinkedHashMap<>();
		}

		return properties;
	}

	public void setProperties(Map<String, TPropertyDefinition> properties) {
		this.properties = properties;
	}

	@NonNull
	public Map<String, TAttributeDefinition> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new LinkedHashMap<>();
		}

		return attributes;
	}

	public void setAttributes(Map<String, TAttributeDefinition> attributes) {
		this.attributes = attributes;
	}

	public String getUpperBound() {
		if (occurrences == null || occurrences.size() <= 1) {
			return "1";
		} else {
			return occurrences.get(1);
		}
	}

	public Integer getLowerBound() {
		if (occurrences == null || occurrences.isEmpty()) {
			return 1;
		} else {
			return Integer.valueOf(occurrences.get(0));
		}
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String type;
		private String description;
		private List<String> occurrences;
		private List<String> valid_source_types;
		private Map<String, TPropertyDefinition> properties;
		private Map<String, TAttributeDefinition> attributes;

		public Builder(String type) {
			this.type = type;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setOccurrences(List<String> occurrences) {
			this.occurrences = occurrences;
			return this;
		}

		public Builder setValid_source_types(List<String> valid_source_types) {
			this.valid_source_types = valid_source_types;
			return this;
		}

		public Builder setProperties(Map<String, TPropertyDefinition> properties) {
			this.properties = properties;
			return this;
		}

		public Builder setAttributes(Map<String, TAttributeDefinition> attributes) {
			this.attributes = attributes;
			return this;
		}

		public TCapabilityDefinition build() {
			return new TCapabilityDefinition(this);
		}
	}
}
