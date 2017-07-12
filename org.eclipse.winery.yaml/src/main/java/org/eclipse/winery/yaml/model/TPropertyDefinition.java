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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.yaml.model.support.ObjectValue;
import org.eclipse.winery.yaml.model.support.TSequencedConstraintClause;

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
public class TPropertyDefinition {
	@XmlAttribute(name = "type", required = true)
	private String type;
	private String description;
	private Boolean required;
	@XmlElement(name = "default")
	private ObjectValue _default;
	private TStatusValue status;
	@XmlElement
	private List<TSequencedConstraintClause> constraints;
	private TEntrySchema entry_schema;

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

	public TStatusValue getStatus() {
		return status;
	}

	public void setStatus(TStatusValue status) {
		this.status = status;
	}

	public List<TSequencedConstraintClause> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<TSequencedConstraintClause> constraints) {
		this.constraints = constraints;
	}

	public TEntrySchema getEntry_schema() {
		return entry_schema;
	}

	public void setEntry_schema(TEntrySchema entry_schema) {
		this.entry_schema = entry_schema;
	}
}
