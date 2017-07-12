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

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.yaml.model.support.StringList;

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

	public String getNode_type() {
		return node_type;
	}

	public void setNode_type(String node_type) {
		this.node_type = node_type;
	}

	public Map<String, StringList> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Map<String, StringList> capabilities) {
		this.capabilities = capabilities;
	}

	public Map<String, StringList> getRequirements() {
		return requirements;
	}

	public void setRequirements(Map<String, StringList> requirements) {
		this.requirements = requirements;
	}
}
