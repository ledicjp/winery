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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.ObjectValue;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPropertyDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"description",
		"required",
		"_default",
		"status",
		"constraints",
		"entry_schema"
})
public class TPropertyDefinition extends TPropertyAssignmentOrDefinition {
	@XmlAttribute(name = "type", required = true)
	private String type;
	private String description;
	private Boolean required;
	@XmlElement(name = "default")
	private ObjectValue _default;
	private TStatusValue status;
	@XmlElement
	private List<TConstraintClause> constraints;
	private TEntrySchema entry_schema;

	public TPropertyDefinition() {
	}

	public TPropertyDefinition(Builder builder) {
		this.setType(builder.type);
		this.setDescription(builder.description);
		this.setRequired(builder.required);
		this.setDefault(builder._default);
		this.setStatus(builder.status);
		this.setConstraints(builder.constraints);
		this.setEntry_schema(builder.entry_schema);
	}

	public static void init(TPropertyDefinition base, TPropertyDefinition.Builder builder) {
		base.setType(builder.type);
		base.setDescription(builder.description);
		base.setRequired(builder.required);
		base.setDefault(builder._default);
		base.setStatus(builder.status);
		base.setConstraints(builder.constraints);
		base.setEntry_schema(builder.entry_schema);
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

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public ObjectValue getDefault() {
		return _default;
	}

	public void setDefault(ObjectValue _default) {
		this._default = _default;
	}

	public void setDefault(String _default) {
		ObjectValue tmp = new ObjectValue(_default);
		setDefault(tmp);
	}

	public TStatusValue getStatus() {
		return status;
	}

	public void setStatus(String status) {
		switch (status) {
			case "supported":
				setStatus(TStatusValue.supported);
				break;
			case "unsupported":
				setStatus(TStatusValue.unsupported);
				break;
			case "experimental":
				setStatus(TStatusValue.experimental);
				break;
			case "deprecated":
				setStatus(TStatusValue.deprecated);
				break;
			default:
				setStatus(TStatusValue.invalidStatusValue);
		}
	}

	public void setStatus(TStatusValue status) {
		this.status = status;
	}

	public List<TConstraintClause> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<TConstraintClause> constraints) {
		this.constraints = constraints;
	}

	public TEntrySchema getEntry_schema() {
		return entry_schema;
	}

	public void setEntry_schema(TEntrySchema entry_schema) {
		this.entry_schema = entry_schema;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String type;
		private String description;
		private Boolean required;
		private ObjectValue _default;
		private TStatusValue status;
		private List<TConstraintClause> constraints;
		private TEntrySchema entry_schema;

		public Builder(String type) {
			this.type = type;
		}

		public Builder(TPropertyDefinition propertyDefinition) {
			this.type = propertyDefinition.type;
			this.description = propertyDefinition.description;
			this.required = propertyDefinition.required;
			this._default = propertyDefinition._default;
			this.status = propertyDefinition.status;
			this.constraints = propertyDefinition.constraints;
			this.entry_schema = propertyDefinition.entry_schema;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setRequired(Boolean required) {
			this.required = required;
			return this;
		}

		public Builder set_default(ObjectValue _default) {
			this._default = _default;
			return this;
		}

		public Builder setStatus(TStatusValue status) {
			this.status = status;
			return this;
		}

		public Builder setConstraints(List<TConstraintClause> constraints) {
			this.constraints = constraints;
			return this;
		}

		public Builder setEntry_schema(TEntrySchema entry_schema) {
			this.entry_schema = entry_schema;
			return this;
		}

		public TPropertyDefinition build() {
			return new TPropertyDefinition(this);
		}
	}
}
