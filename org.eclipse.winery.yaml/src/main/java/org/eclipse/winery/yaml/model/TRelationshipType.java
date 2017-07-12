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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRelationshipType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"valid_target_types",
		"attributes",
		"interfaces"
})
public class TRelationshipType extends TEntityType {
	private List<String> valid_target_types;
	private Map<String, TAttributeDefinition> attributes;
	private Map<String, TInterfaceDefinition> interfaces;

	public TRelationshipType() {
	}

	public List<String> getValid_target_types() {
		return valid_target_types;
	}

	public void setValid_target_types(List<String> valid_target_types) {
		this.valid_target_types = valid_target_types;
	}

	public Map<String, TAttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, TAttributeDefinition> attributes) {
		this.attributes = attributes;
	}

	public Map<String, TInterfaceDefinition> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
		this.interfaces = interfaces;
	}
}
