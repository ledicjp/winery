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
package org.eclipse.winery.yaml.model.support;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.yaml.model.TCapabilityDefinition;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tMapCapabilityDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"map"
})
public class TMapCapabilityDefinition {
	private Map<String, TCapabilityDefinition> map;

	public Map<String, TCapabilityDefinition> getMap() {
		return map;
	}

	public void setMap(Map<String, TCapabilityDefinition> map) {
		this.map = map;
	}
}
