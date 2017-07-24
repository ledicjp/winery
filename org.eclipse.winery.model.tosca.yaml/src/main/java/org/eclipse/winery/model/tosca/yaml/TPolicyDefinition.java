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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPolicyDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"description",
		"metadata",
		"properties",
		"targets"
})
public class TPolicyDefinition {
	@XmlAttribute(name = "type", required = true)
	private String type;
	private String description;
	private Metadata metadata;
	private Map<String, TPropertyAssignment> properties;
	private List<String> targets;

	public TPolicyDefinition() {
	}

	public TPolicyDefinition(Builder builder) {
		this.setType(builder.type);
		this.setDescription(builder.description);
		this.setMetadata(builder.metadata);
		this.setProperties(builder.properties);
		this.setTargets(builder.targets);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
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
	public List<String> getTargets() {
		if (this.targets == null) {
			this.targets = new ArrayList<>();
		}

		return targets;
	}

	public void setTargets(List<String> targets) {
		this.targets = targets;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String type;
		private String description;
		private Metadata metadata;
		private Map<String, TPropertyAssignment> properties;
		private List<String> targets;


		public Builder(String type) {
			this.type = type;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setMetadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder setProperties(Map<String, TPropertyAssignment> properties) {
			this.properties = properties;
			return this;
		}

		public Builder setTargets(List<String> targets) {
			this.targets = targets;
			return this;
		}

		public TPolicyDefinition build() {
			return new TPolicyDefinition(this);
		}
	}
}
