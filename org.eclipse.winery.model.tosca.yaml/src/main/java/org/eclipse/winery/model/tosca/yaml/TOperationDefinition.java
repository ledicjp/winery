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
@XmlType(name = "tOperationDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"description",
		"inputs",
		"implementation"
})
public class TOperationDefinition {
	private String description;
	private Map<String, TPropertyAssignmentOrDefinition> inputs;
	private TImplementation implementation;

	public TOperationDefinition() {

	}

	public TOperationDefinition(String implementation) {
		this.implementation = new TImplementation(implementation);
	}

	public TOperationDefinition(Builder builder) {
		this.setDescription(builder.description);
		this.setInputs(builder.inputs);
		this.setImplementation(builder.implementation);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public TImplementation getImplementation() {
		return implementation;
	}

	public void setImplementation(TImplementation implementation) {
		this.implementation = implementation;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private String description;
		private Map<String, TPropertyAssignmentOrDefinition> inputs;
		private TImplementation implementation;

		public Builder() {

		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setInputs(Map<String, TPropertyAssignmentOrDefinition> inputs) {
			this.inputs = inputs;
			return this;
		}

		public Builder setImplementation(TImplementation implementation) {
			this.implementation = implementation;
			return this;
		}

		public TOperationDefinition build() {
			return new TOperationDefinition(this);
		}
	}
}
