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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.namespace.QName;

@XmlRootElement(name = "Properties")
public class PropertiesMap {
	@XmlAnyElement
	public List<JAXBElement> entries = new ArrayList<>();

	@XmlAttribute(name = "xmlns", required = true)
	@XmlSchemaType(name = "anyURI")
	protected String targetNamespace;

	public PropertiesMap() {
	}

	public PropertiesMap(Map<String, String> map, String targetNamespace) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			entries.add(new JAXBElement(new QName(entry.getKey()), String.class, entry.getValue()));
		}

		this.targetNamespace = targetNamespace;
	}
}
