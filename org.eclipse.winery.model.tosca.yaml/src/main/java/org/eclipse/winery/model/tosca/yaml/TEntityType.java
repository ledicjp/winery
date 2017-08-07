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
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

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
	private QName derived_from;
	private Map<String, TPropertyDefinition> properties;
	private Map<String, TAttributeDefinition> attributes;
	private Metadata metadata;

	public TEntityType() {
	}

	public TEntityType(Builder builder) {
		this.setDescription(builder.description);
		this.setVersion(builder.version);
		this.setDerived_from(builder.derived_from);
		this.setProperties(builder.properties);
		this.setAttributes(builder.attributes);
		this.setMetadata(builder.metadata);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TEntityType)) return false;
		TEntityType that = (TEntityType) o;
		return Objects.equals(description, that.description) &&
				Objects.equals(version, that.version) &&
				Objects.equals(derived_from, that.derived_from) &&
				Objects.equals(properties, that.properties) &&
				Objects.equals(attributes, that.attributes) &&
				Objects.equals(metadata, that.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, version, derived_from, properties, attributes, metadata);
	}

	@Nullable
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Nullable
	public TVersion getVersion() {
		return version;
	}

	public void setVersion(String version) {
		TVersion tmp = new TVersion(version);
		setVersion(tmp);
	}

	public void setVersion(TVersion version) {
		this.version = version;
	}

	@Nullable
	public QName getDerived_from() {
		return derived_from;
	}

	public void setDerived_from(QName derived_from) {
		this.derived_from = derived_from;
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

	@NonNull
	public Metadata getMetadata() {
		if (this.metadata == null) {
			this.metadata = new Metadata();
		}
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private String description;
		private TVersion version;
		private QName derived_from;
		private Map<String, TPropertyDefinition> properties;
		private Map<String, TAttributeDefinition> attributes;
		private Metadata metadata;

		public Builder() {

		}

		public Builder(TEntityType entityType) {
			this.description = entityType.getDescription();
			this.version = entityType.getVersion();
			this.derived_from = entityType.getDerived_from();
			this.properties = entityType.getProperties();
			this.attributes = entityType.getAttributes();
			this.metadata = entityType.getMetadata();
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setVersion(TVersion version) {
			this.version = version;
			return this;
		}

		public Builder setDerived_from(QName derived_from) {
			this.derived_from = derived_from;
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

		public Builder setMetadata(Metadata metadata) {
			if (this.metadata == null) {
				this.metadata = metadata;
			} else {
				this.metadata.putAll(metadata);
			}
			return this;
		}

		public Builder setMetadata(String key, String value) {
			Metadata metadata = new Metadata();
			metadata.put(key, value);
			return this.setMetadata(metadata);
		}

		public Builder addProperties(Map<String, TPropertyDefinition> properties) {
			if (properties == null || properties.isEmpty()) {
				return this;
			}

			if (this.properties == null) {
				this.properties = properties;
			} else {
				this.properties.putAll(properties);
			}

			return this;
		}

		public Builder addProperties(String name, TPropertyDefinition property) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addProperties(Collections.singletonMap(name, property));
		}

		public Builder addAttributes(Map<String, TAttributeDefinition> attributes) {
			if (attributes == null || attributes.isEmpty()) {
				return this;
			}

			if (this.attributes == null) {
				this.attributes = attributes;
			} else {
				this.attributes.putAll(attributes);
			}

			return this;
		}

		public Builder addAttributes(String name, TAttributeDefinition attribute) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addAttributes(Collections.singletonMap(name, attribute));
		}

		public TEntityType build() {
			return new TEntityType(this);
		}
	}
}
