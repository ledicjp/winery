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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRequirementAssignment", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"capability",
		"node",
		"relationship",
		"node_filter",
		"occurrences"
})
public class TRequirementAssignment {
	private String node;
	private TRelationshipAssignment relationship;
	private String capability;

	private TNodeFilterDefinition node_filter;
	private List<String> occurrences;

	public TRequirementAssignment() {

	}

	public TRequirementAssignment(String node) {
		this.node = node;
	}

	public String getCapability() {
		return capability;
	}

	public void setCapability(String capability) {
		this.capability = capability;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public TRelationshipAssignment getRelationship() {
		return relationship;
	}

	public void setRelationship(TRelationshipAssignment relationship) {
		this.relationship = relationship;
	}

	public TNodeFilterDefinition getNode_filter() {
		return node_filter;
	}

	public void setNode_filter(TNodeFilterDefinition node_filter) {
		this.node_filter = node_filter;
	}

	public List<String> getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(List<String> occurrences) {
		this.occurrences = occurrences;
	}
}
