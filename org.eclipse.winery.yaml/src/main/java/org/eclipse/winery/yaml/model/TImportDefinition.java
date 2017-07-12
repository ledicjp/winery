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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tImportDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"file",
		"repository",
		"namespace_uri",
		"namespace_prefix"
})
public class TImportDefinition {
	@XmlAttribute(name = "file", required = true)
	private String file;
	private String repository;
	private String namespace_uri;
	private String namespace_prefix;

	public TImportDefinition() {

	}

	public TImportDefinition(String file) {
		this.file = file;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getNamespace_uri() {
		return namespace_uri;
	}

	public void setNamespace_uri(String namespace_uri) {
		this.namespace_uri = namespace_uri;
	}

	public String getNamespace_prefix() {
		return namespace_prefix;
	}

	public void setNamespace_prefix(String namespace_prefix) {
		this.namespace_prefix = namespace_prefix;
	}
}
