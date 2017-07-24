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

import org.eclipse.winery.model.tosca.yaml.support.StringList;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSubstitutionMapping", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"node_type",
		"capabilities",
		"requirements"
})
public class TSubstitutionMappings {
	private String node_type;
	private Map<String, StringList> capabilities;
	private Map<String, StringList> requirements;

	public TSubstitutionMappings() {
	}

	public TSubstitutionMappings(Builder builder) {
		this.setNode_type(builder.node_type);
		this.setCapabilities(builder.capabilities);
		this.setRequirements(builder.requirements);
	}

	public String getNode_type() {
		return node_type;
	}

	public void setNode_type(String node_type) {
		this.node_type = node_type;
	}

	@NonNull
	public Map<String, StringList> getCapabilities() {
		if (this.capabilities == null) {
			this.capabilities = new LinkedHashMap<>();
		}

		return capabilities;
	}

	public void setCapabilities(Map<String, StringList> capabilities) {
		this.capabilities = capabilities;
	}

	@NonNull
	public Map<String, StringList> getRequirements() {
		if (this.requirements == null) {
			this.requirements = new LinkedHashMap<>();
		}

		return requirements;
	}

	public void setRequirements(Map<String, StringList> requirements) {
		this.requirements = requirements;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private String node_type;
		private Map<String, StringList> capabilities;
		private Map<String, StringList> requirements;

		public Builder() {

		}

		public Builder setNode_type(String node_type) {
			this.node_type = node_type;
			return this;
		}

		public Builder setCapabilities(Map<String, StringList> capabilities) {
			this.capabilities = capabilities;
			return this;
		}

		public Builder setRequirements(Map<String, StringList> requirements) {
			this.requirements = requirements;
			return this;
		}

		public TSubstitutionMappings build() {
			return new TSubstitutionMappings(this);
		}
	}
}
