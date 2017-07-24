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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

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

	public TInterfaceDefinition() {
	}

	public TInterfaceDefinition(Builder builder) {
		this.setType(builder.type);
		this.setInputs(builder.inputs);
		this.setOperations(builder.operations);
	}

	public static void init(TInterfaceDefinition base, Builder builder) {
		base.setType(builder.type);
		base.setInputs(builder.inputs);
		base.setOperations(builder.operations);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NonNull
	public Map<String, TPropertyAssignmentOrDefinition> getInputs() {
		if (this.inputs == null) {
			this.inputs = new LinkedHashMap<>();
		}

		return inputs;
	}

	public void setInputs(Map<String, TPropertyAssignmentOrDefinition> inputs) {
		this.inputs = inputs;
	}

	@NonNull
	public Map<String, TOperationDefinition> getOperations() {
		if (this.operations == null) {
			this.operations = new LinkedHashMap<>();
		}

		return operations;
	}

	public void setOperations(Map<String, TOperationDefinition> operations) {
		this.operations = operations;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private String type;
		private Map<String, TPropertyAssignmentOrDefinition> inputs;
		private Map<String, TOperationDefinition> operations;

		public Builder() {

		}

		public Builder setType(String type) {
			this.type = type;
			return this;
		}

		public Builder setInputs(Map<String, TPropertyAssignmentOrDefinition> inputs) {
			this.inputs = inputs;
			return this;
		}

		public Builder setOperations(Map<String, TOperationDefinition> operations) {
			this.operations = operations;
			return this;
		}

		public TInterfaceDefinition build() {
			return new TInterfaceDefinition(this);
		}
	}
}
