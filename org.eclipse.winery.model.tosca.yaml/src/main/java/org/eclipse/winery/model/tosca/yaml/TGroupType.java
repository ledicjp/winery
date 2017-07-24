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
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.TMapRequirementDefinition;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGroupType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"members",
		"requirements",
		"capabilities",
		"interfaces"
})
public class TGroupType extends TNodeOrGroupType {
	private List<String> members;
	private List<TMapRequirementDefinition> requirements;
	private Map<String, TCapabilityDefinition> capabilities;
	private Map<String, TInterfaceDefinition> interfaces;

	public TGroupType() {
	}

	public TGroupType(Builder builder) {
		super(builder);
		this.setMembers(builder.members);
		this.setRequirements(builder.requirements);
		this.setCapabilities(builder.capabilities);
		this.setInterfaces(builder.interfaces);
	}

	@NonNull
	public List<String> getMembers() {
		if (this.members == null) {
			this.members = new ArrayList<>();
		}

		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	@NonNull
	public List<TMapRequirementDefinition> getRequirements() {
		if (this.requirements == null) {
			this.requirements = new ArrayList<>();
		}

		return requirements;
	}

	public void setRequirements(List<TMapRequirementDefinition> requirements) {
		this.requirements = requirements;
	}

	@NonNull
	public Map<String, TCapabilityDefinition> getCapabilities() {
		if (this.capabilities == null) {
			this.capabilities = new LinkedHashMap<>();
		}

		return capabilities;
	}

	public void setCapabilities(Map<String, TCapabilityDefinition> capabilities) {
		this.capabilities = capabilities;
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
		private List<String> members;
		private List<TMapRequirementDefinition> requirements;
		private Map<String, TCapabilityDefinition> capabilities;
		private Map<String, TInterfaceDefinition> interfaces;

		public Builder() {

		}

		public Builder(TEntityType entityType) {
			super(entityType);
		}

		public Builder setMembers(List<String> members) {
			this.members = members;
			return this;
		}

		public Builder setRequirements(List<TMapRequirementDefinition> requirements) {
			this.requirements = requirements;
			return this;
		}

		public Builder setCapabilities(Map<String, TCapabilityDefinition> capabilities) {
			this.capabilities = capabilities;
			return this;
		}

		public Builder setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
			this.interfaces = interfaces;
			return this;
		}

		public TGroupType build() {
			return new TGroupType(this);
		}
	}
}
