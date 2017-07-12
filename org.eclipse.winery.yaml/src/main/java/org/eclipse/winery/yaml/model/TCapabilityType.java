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
@XmlType(name = "tCapabilityType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"valid_source_types",
		"attributes"
})
public class TCapabilityType extends TEntityType {
	private List<String> valid_source_types;
	private Map<String, TAttributeDefinition> attributes;

	public List<String> getValid_source_types() {
		return valid_source_types;
	}

	public void setValid_source_types(List<String> valid_source_types) {
		this.valid_source_types = valid_source_types;
	}

	public Map<String, TAttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, TAttributeDefinition> attributes) {
		this.attributes = attributes;
	}
}
