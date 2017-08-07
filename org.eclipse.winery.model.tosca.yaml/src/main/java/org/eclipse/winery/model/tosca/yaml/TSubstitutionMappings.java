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

import org.eclipse.winery.model.tosca.yaml.support.TListString;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Used in Topology Template Definition
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSubstitutionMapping", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"node_type",
		"capabilities",
		"requirements"
})
public class TSubstitutionMappings {
	private QName node_type;
	private Map<String, TListString> capabilities;
	private Map<String, TListString> requirements;

	public TSubstitutionMappings() {
	}

	public TSubstitutionMappings(Builder builder) {
		this.setNode_type(builder.node_type);
		this.setCapabilities(builder.capabilities);
		this.setRequirements(builder.requirements);
	}

	@Nullable
	public QName getNode_type() {
		return node_type;
	}

	public void setNode_type(QName node_type) {
		this.node_type = node_type;
	}

	@NonNull
	public Map<String, TListString> getCapabilities() {
		if (this.capabilities == null) {
			this.capabilities = new LinkedHashMap<>();
		}

		return capabilities;
	}

	public void setCapabilities(Map<String, TListString> capabilities) {
		this.capabilities = capabilities;
	}

	@NonNull
	public Map<String, TListString> getRequirements() {
		if (this.requirements == null) {
			this.requirements = new LinkedHashMap<>();
		}

		return requirements;
	}

	public void setRequirements(Map<String, TListString> requirements) {
		this.requirements = requirements;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private QName node_type;
		private Map<String, TListString> capabilities;
		private Map<String, TListString> requirements;

		public Builder() {

		}

		public Builder setNode_type(QName node_type) {
			this.node_type = node_type;
			return this;
		}

		public Builder setCapabilities(Map<String, TListString> capabilities) {
			this.capabilities = capabilities;
			return this;
		}

		public Builder setRequirements(Map<String, TListString> requirements) {
			this.requirements = requirements;
			return this;
		}

		public Builder addCapabilities(Map<String, TListString> capabilities) {
			if (capabilities == null || capabilities.isEmpty()) {
				return this;
			}

			if (this.capabilities == null) {
				this.capabilities = capabilities;
			} else {
				this.capabilities.putAll(capabilities);
			}

			return this;
		}

		public Builder addCapabilities(String name, TListString capability) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addCapabilities(Collections.singletonMap(name, capability));
		}

		public Builder addRequirements(Map<String, TListString> requirements) {
			if (requirements == null || requirements.isEmpty()) {
				return this;
			}

			if (this.requirements == null) {
				this.requirements = requirements;
			} else {
				this.requirements.putAll(requirements);
			}

			return this;
		}

		public Builder addRequirements(String name, TListString requirement) {
			if (name == null || name.isEmpty()) {
				return this;
			}

			return addRequirements(Collections.singletonMap(name, requirement));
		}

		public TSubstitutionMappings build() {
			return new TSubstitutionMappings(this);
		}
	}
}
