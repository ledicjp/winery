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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.TMapObjectValue;
import org.eclipse.winery.model.tosca.yaml.support.TMapPropertyFilterDefinition;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tNodeFilter", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"properties",
		"capabilities"
})
public class TNodeFilterDefinition {
	private List<TMapPropertyFilterDefinition> properties;
	private List<TMapObjectValue> capabilities;

	public TNodeFilterDefinition() {
	}

	public TNodeFilterDefinition(Builder builder) {
		this.setProperties(builder.properties);
		this.setCapabilities(builder.capabilities);
	}

	public List<TMapPropertyFilterDefinition> getProperties() {
		return properties;
	}

	public void setProperties(List<TMapPropertyFilterDefinition> properties) {
		this.properties = properties;
	}

	public List<TMapObjectValue> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(List<TMapObjectValue> capabilities) {
		this.capabilities = capabilities;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private List<TMapPropertyFilterDefinition> properties;
		private List<TMapObjectValue> capabilities;


		public Builder() {

		}

		public Builder setProperties(List<TMapPropertyFilterDefinition> properties) {
			this.properties = properties;
			return this;
		}

		public Builder setCapabilities(List<TMapObjectValue> capabilities) {
			this.capabilities = capabilities;
			return this;
		}

		public TNodeFilterDefinition build() {
			return new TNodeFilterDefinition(this);
		}
	}
}
