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
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGroupDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"description",
		"metadata",
		"properties",
		"members",
		"interfaces"
})
public class TGroupDefinition {
	@XmlAttribute(name = "type", required = true)
	private QName type;
	private String description;
	private Metadata metadata;
	private Map<String, TPropertyAssignment> properties;
	private List<QName> members;
	private Map<String, TInterfaceDefinition> interfaces;

	public TGroupDefinition() {
	}

	public TGroupDefinition(Builder builder) {
		this.setType(builder.type);
		this.setDescription(builder.description);
		this.setMetadata(builder.metadata);
		this.setProperties(builder.properties);
		this.setMembers(builder.members);
		this.setInterfaces(builder.interfaces);
	}

	@NonNull
	public QName getType() {
		return type;
	}

	public void setType(QName type) {
		this.type = type;
	}

	@Nullable
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Nullable
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
	public List<QName> getMembers() {
		if (this.members == null) {
			this.members = new ArrayList<>();
		}

		return members;
	}

	public void setMembers(List<QName> members) {
		this.members = members;
	}

	@NonNull
	public Map<String, TInterfaceDefinition> getInterfaces() {
		if (this.interfaces == null) {
			this.interfaces = new LinkedHashMap<>();
		}

		return interfaces;
	}

	public void setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
		this.interfaces = interfaces;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final QName type;
		private String description;
		private Metadata metadata;
		private Map<String, TPropertyAssignment> properties;
		private List<QName> members;
		private Map<String, TInterfaceDefinition> interfaces;

		public Builder(QName type) {
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

		public Builder setMembers(List<QName> members) {
			this.members = members;
			return this;
		}

		public Builder setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
			this.interfaces = interfaces;
			return this;
		}

		public TGroupDefinition build() {
			return new TGroupDefinition(this);
		}
	}
}
