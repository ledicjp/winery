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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.yaml.model.support.ObjectValue;

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
	private String type;
	@XmlElement(name = "default")
	private ObjectValue _default;
	private TStatusValue status;
	private TEntrySchema entry_schema;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public TEntrySchema getEntry_schema() {
		return entry_schema;
	}

	public void setEntry_schema(TEntrySchema entry_schema) {
		this.entry_schema = entry_schema;
	}
}

