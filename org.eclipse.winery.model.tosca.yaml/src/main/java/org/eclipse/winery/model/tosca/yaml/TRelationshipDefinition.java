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
@XmlType(name = "tRelationshipDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"interfaces"
})
public class TRelationshipDefinition {
	private String type;
	private Map<String, TInterfaceDefinition> interfaces;

	public TRelationshipDefinition() {
	}

	public TRelationshipDefinition(String type) {
		this.type = type;
	}

	public TRelationshipDefinition(Builder builder) {
		this.setType(builder.type);
		this.setInterfaces(builder.interfaces);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, TInterfaceDefinition> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
		this.interfaces = interfaces;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String type;
		private Map<String, TInterfaceDefinition> interfaces;

		public Builder(String type) {
			this.type = type;
		}

		public Builder setProperties(Map<String, TPropertyAssignment> properties) {
			return this;
		}

		public Builder setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
			this.interfaces = interfaces;
			return this;
		}

		public TRelationshipDefinition build() {
			return new TRelationshipDefinition(this);
		}
	}
}

