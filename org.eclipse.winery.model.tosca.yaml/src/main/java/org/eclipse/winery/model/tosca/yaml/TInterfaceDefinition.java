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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInterfaceDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"inputs",
		"operations"
})
public class TInterfaceDefinition {
	private QName type;
	private Map<String, TPropertyAssignmentOrDefinition> inputs;
	private Map<String, TOperationDefinition> operations;

	public TInterfaceDefinition() {
	}

	public TInterfaceDefinition(Builder builder) {
		this.setType(builder.type);
		this.setInputs(builder.inputs);
		this.setOperations(builder.operations);
	}

	@Nullable
	public QName getType() {
		return type;
	}

	public void setType(QName type) {
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

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private QName type;
		private Map<String, TPropertyAssignmentOrDefinition> inputs;
		private Map<String, TOperationDefinition> operations;

		public Builder() {

		}

		public Builder setType(QName type) {
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

		public Builder addInputs(Map<String, TPropertyAssignmentOrDefinition> inputs) {
			if (inputs == null || inputs.isEmpty()) {
				return this;
			}

			if (this.inputs == null) {
				this.inputs = inputs;
			} else {
				this.inputs.putAll(inputs);
			}

			return this;
		}

		public Builder addInputs(String name, TPropertyAssignmentOrDefinition input) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addInputs(Collections.singletonMap(name, input));
		}

		public Builder addOperations(Map<String, TOperationDefinition> operations) {
			if (operations == null || operations.isEmpty()) {
				return this;
			}

			if (this.operations == null) {
				this.operations = operations;
			} else {
				this.operations.putAll(operations);
			}

			return this;
		}

		public Builder addOperations(String name, TOperationDefinition operation) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addOperations(Collections.singletonMap(name, operation));
		}

		public TInterfaceDefinition build() {
			return new TInterfaceDefinition(this);
		}
	}
}
