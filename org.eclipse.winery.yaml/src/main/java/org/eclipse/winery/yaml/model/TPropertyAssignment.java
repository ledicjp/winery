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
@XmlType(name = "tPropertyAssignment", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"value"
})
public class TPropertyAssignment extends TPropertyAssignmentOrDefinition {
	private ObjectValue value;

	public TPropertyAssignment() {
	}

	public TPropertyAssignment(Object value) {
		this.value = new ObjectValue(value);
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(ObjectValue value) {
		this.value = value;
	}

	public ObjectValue getGet_input() {
		return this.value;
	}

	public void setGet_input(ObjectValue get_input) {
		this.value = get_input;
	}

	public ObjectValue getGet_property() {
		return this.value;
	}

	public void setGet_property(ObjectValue get_property) {
		this.value = get_property;
	}
}
