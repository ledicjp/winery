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
@XmlType(name = "tInterfaceDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"inputs",
		"operations"
})
public class TInterfaceDefinition {
	private String type;
	private Map<String, TPropertyAssignmentOrDefinition> inputs;
	private Map<String, TOperationDefinition> operations;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, TPropertyAssignmentOrDefinition> getInputs() {
		return inputs;
	}

	public void setInputs(Map<String, TPropertyAssignmentOrDefinition> inputs) {
		this.inputs = inputs;
	}

	public Map<String, TOperationDefinition> getOperations() {
		return operations;
	}

	public void setOperations(Map<String, TOperationDefinition> operations) {
		this.operations = operations;
	}
}
