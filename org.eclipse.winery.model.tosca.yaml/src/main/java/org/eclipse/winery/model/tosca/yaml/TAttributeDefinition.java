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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tAttributeDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"description",
		"type",
		"_default",
		"status",
		"entry_schema"
})
public class TAttributeDefinition {
	private String description;
	@XmlAttribute(name = "type", required = true)
	private QName type;
	@XmlElement(name = "default")
	private Object _default;
	private TStatusValue status;
	private TEntrySchema entry_schema;

	public TAttributeDefinition() {
	}

	public TAttributeDefinition(Builder builder) {
		this.setType(builder.type);
		this.setDescription(builder.description);
		this.setDefault(builder._default);
		this.setStatus(builder.status);
		this.setEntry_schema(builder.entry_schema);
	}

	@Nullable
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NonNull
	public QName getType() {
		return type;
	}

	public void setType(QName type) {
		this.type = type;
	}

	@Nullable
	public Object getDefault() {
		return _default;
	}

	public void setDefault(Object _default) {
		this._default = _default;
	}

	@Nullable
	public TStatusValue getStatus() {
		return status;
	}

	public void setStatus(TStatusValue status) {
		this.status = status;
	}

	@Nullable
	public TEntrySchema getEntry_schema() {
		return entry_schema;
	}

	public void setEntry_schema(TEntrySchema entry_schema) {
		this.entry_schema = entry_schema;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final QName type;
		private String description;
		private Object _default;
		private TStatusValue status;
		private TEntrySchema entry_schema;

		public Builder(QName type) {
			this.type = type;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder set_default(Object _default) {
			this._default = _default;
			return this;
		}

		public Builder setStatus(TStatusValue status) {
			this.status = status;
			return this;
		}

		public Builder setEntry_schema(TEntrySchema entry_schema) {
			this.entry_schema = entry_schema;
			return this;
		}

		public TAttributeDefinition build() {
			return new TAttributeDefinition(this);
		}
	}
}

