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
package org.eclipse.winery.yaml.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.yaml.model.support.TMapRequirementDefinition;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGroupType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"attributes",
		"members",
		"requirements",
		"capabilities",
		"interfaces"
})
public class TGroupType extends TNodeOrGroupType {
	private Map<String, TAttributeDefinition> attributes;
	private List<String> members;
	private List<TMapRequirementDefinition> requirements;
	private Map<String, TCapabilityDefinition> capabilities;
	private Map<String, TInterfaceDefinition> interfaces;

	public Map<String, TAttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, TAttributeDefinition> attributes) {
		this.attributes = attributes;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public List<TMapRequirementDefinition> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<TMapRequirementDefinition> requirements) {
		this.requirements = requirements;
	}

	public Map<String, TCapabilityDefinition> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Map<String, TCapabilityDefinition> capabilities) {
		this.capabilities = capabilities;
	}

	public Map<String, TInterfaceDefinition> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
		this.interfaces = interfaces;
	}
}
