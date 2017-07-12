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
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.yaml.model.support.ObjectValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tAttributeAssignment", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"description",
		"value"
})
public class TAttributeAssignment {
	private String description;
	private ObjectValue value;

	public TAttributeAssignment() {

	}

	public TAttributeAssignment(Object value) {
		this.value = new ObjectValue(value);
	}

	public TAttributeAssignment(String value) {
		this.value = new ObjectValue(value);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ObjectValue getValue() {
		return value;
	}

	public void setValue(ObjectValue value) {
		this.value = value;
	}
}
