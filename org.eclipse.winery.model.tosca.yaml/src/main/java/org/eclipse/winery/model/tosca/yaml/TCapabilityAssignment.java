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

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCapabilityAssignment", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"properties",
		"attributes"
})
public class TCapabilityAssignment {
	Map<String, TPropertyAssignment> properties;
	Map<String, TAttributeAssignment> attributes;

	public TCapabilityAssignment() {
	}

	public TCapabilityAssignment(Builder builder) {
		this.setProperties(builder.properties);
		this.setAttributes(builder.attributes);
	}

	@NonNull
	public Map<String, TPropertyAssignment> getProperties() {
		if (this.properties == null) {
			this.properties = new LinkedHashMap<>();
		}

		return properties;
	}

	public void setProperties(Map<String, TPropertyAssignment> properties) {
		this.properties = properties;
	}

	@NonNull
	public Map<String, TAttributeAssignment> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new LinkedHashMap<>();
		}

		return attributes;
	}

	public void setAttributes(Map<String, TAttributeAssignment> attributes) {
		this.attributes = attributes;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private Map<String, TPropertyAssignment> properties;
		private Map<String, TAttributeAssignment> attributes;

		public Builder() {

		}

		public Builder setProperties(Map<String, TPropertyAssignment> properties) {
			this.properties = properties;
			return this;
		}

		public Builder setAttributes(Map<String, TAttributeAssignment> attributes) {
			this.attributes = attributes;
			return this;
		}

		public TCapabilityAssignment build() {
			return new TCapabilityAssignment(this);
		}
	}
}
