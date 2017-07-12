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
package org.eclipse.winery.yaml.model.support;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectValue", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"value"
})
public class ObjectValue {
	private Object value;

	public ObjectValue() {
	}

	// constructor for snakeYaml
	public ObjectValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object any) {
		this.value = any;
	}

	@Override
	public String toString() {
		if (this.value == null) {
			return "";
		}
		return this.value.toString();
	}
}
