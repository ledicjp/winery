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

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInterfaceType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"operations",
		"inputs"
})
public class TInterfaceType extends TEntityType {
	private Map<String, TOperationDefinition> operations;
	private Map<String, TPropertyDefinition> inputs;

	public TInterfaceType() {
	}

	public TInterfaceType(Builder builder) {
		super(builder);
		this.setOperations(builder.operations);
		this.setInputs(builder.inputs);
	}

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

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		IResult ir1 = super.accept(visitor, parameter);
		IResult ir2 = visitor.visit(this, parameter);
		if (ir1 == null) {
			return ir2;
		} else {
			return ir1.add(ir2);
		}
	}

	public static class Builder extends TEntityType.Builder {
		private Map<String, TOperationDefinition> operations;
		private Map<String, TPropertyDefinition> inputs;

		public Builder() {

		}

		public Builder(TEntityType entityType) {
			super(entityType);
		}

		public Builder setOperations(Map<String, TOperationDefinition> operations) {
			this.operations = operations;
			return this;
		}

		public Builder setInputs(Map<String, TPropertyDefinition> inputs) {
			this.inputs = inputs;
			return this;
		}

		public TInterfaceType build() {
			return new TInterfaceType(this);
		}
	}
}
