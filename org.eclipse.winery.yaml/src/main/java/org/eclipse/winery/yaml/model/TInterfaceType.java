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

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInterfaceType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"operations",
		"inputs"
})
public class TInterfaceType extends TEntityType {
	private Map<String, TOperationDefinition> operations;
	private Map<String, TPropertyDefinition> inputs;

	public Map<String, TOperationDefinition> getOperations() {
		return operations;
	}

	public void setOperations(Map<String, TOperationDefinition> operations) {
		this.operations = operations;
	}

	public Map<String, TPropertyDefinition> getInputs() {
		return inputs;
	}

	public void setInputs(Map<String, TPropertyDefinition> inputs) {
		this.inputs = inputs;
	}
}
