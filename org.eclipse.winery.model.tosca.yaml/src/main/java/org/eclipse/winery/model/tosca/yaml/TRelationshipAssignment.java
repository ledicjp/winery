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
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Used in Requirement Assignments
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRelationshipAssignment", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"properties",
		"interfaces"
})
public class TRelationshipAssignment {
	private QName type;
	private Map<String, TPropertyAssignment> properties;
	private Map<String, TInterfaceAssignment> interfaces;

	public TRelationshipAssignment() {
	}

	public TRelationshipAssignment(QName type) {
		this.type = type;
	}

	public TRelationshipAssignment(Builder builder) {
		this.setType(builder.type);
		this.setProperties(builder.properties);
		this.setInterfaces(builder.interfaces);
	}

	@Nullable
	public QName getType() {
		return type;
	}

	public void setType(QName type) {
		this.type = type;
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
	public Map<String, TInterfaceAssignment> getInterfaces() {
		if (this.interfaces == null) {
			this.interfaces = new LinkedHashMap<>();
		}

		return interfaces;
	}

	public void setInterfaces(Map<String, TInterfaceAssignment> interfaces) {
		this.interfaces = interfaces;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final QName type;
		private Map<String, TPropertyAssignment> properties;
		private Map<String, TInterfaceAssignment> interfaces;

		public Builder(QName type) {
			this.type = type;
		}

		public Builder setProperties(Map<String, TPropertyAssignment> properties) {
			this.properties = properties;
			return this;
		}

		public Builder setInterfaces(Map<String, TInterfaceAssignment> interfaces) {
			this.interfaces = interfaces;
			return this;
		}

		public TRelationshipAssignment build() {
			return new TRelationshipAssignment(this);
		}
	}
}
