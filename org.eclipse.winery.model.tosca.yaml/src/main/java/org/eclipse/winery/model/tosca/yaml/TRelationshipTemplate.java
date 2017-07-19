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

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRelationshipTemplate", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"description",
		"metadata",
		"properties",
		"attributes",
		"interfaces",
		"copy"
})
public class TRelationshipTemplate {
	@XmlAttribute(name = "type", required = true)
	private String type;
	private String description;
	private Metadata metadata;
	private Map<String, TPropertyAssignment> properties;
	private Map<String, TAttributeAssignment> attributes;
	private Map<String, TInterfaceDefinition> interfaces;
	private String copy;

	public TRelationshipTemplate() {
	}

	public TRelationshipTemplate(Builder builder) {
		this.setType(builder.type);
		this.setDescription(builder.description);
		this.setMetadata(builder.metadata);
		this.setAttributes(builder.attributes);
		this.setProperties(builder.properties);
		this.setInterfaces(builder.interfaces);
		this.setCopy(builder.copy);
	}

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

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public Map<String, TPropertyAssignment> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, TPropertyAssignment> properties) {
		this.properties = properties;
	}

	public Map<String, TInterfaceDefinition> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
		this.interfaces = interfaces;
	}

	public String getCopy() {
		return copy;
	}

	public void setCopy(String copy) {
		this.copy = copy;
	}

	public Map<String, TAttributeAssignment> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, TAttributeAssignment> attributes) {
		this.attributes = attributes;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String type;
		private String description;
		private Metadata metadata;
		private Map<String, TAttributeAssignment> attributes;
		private Map<String, TPropertyAssignment> properties;
		private Map<String, TInterfaceDefinition> interfaces;
		private String copy;

		public Builder(String type) {
			this.type = type;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setMetadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder setAttributes(Map<String, TAttributeAssignment> attributes) {
			this.attributes = attributes;
			return this;
		}

		public Builder setProperties(Map<String, TPropertyAssignment> properties) {
			this.properties = properties;
			return this;
		}

		public Builder setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
			this.interfaces = interfaces;
			return this;
		}

		public Builder setCopy(String copy) {
			this.copy = copy;
			return this;
		}

		public TRelationshipTemplate build() {
			return new TRelationshipTemplate(this);
		}
	}
}
