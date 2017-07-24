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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tArtifactDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"file",
		"repository",
		"description",
		"deploy_path"
})
public class TArtifactDefinition {
	@XmlAttribute(name = "type", required = true)
	private String type;
	@XmlAttribute(name = "file", required = true)
	private String file;
	private String repository;
	private String description;
	private String deploy_path;

	public TArtifactDefinition() {
	}

	public TArtifactDefinition(Builder builder) {
		this.setType(builder.type);
		this.setFile(builder.file);
		this.setRepository(builder.repository);
		this.setDescription(builder.description);
		this.setDeploy_path(builder.deploy_path);
	}

	@NonNull
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NonNull
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeploy_path() {
		return deploy_path;
	}

	public void setDeploy_path(String deploy_path) {
		this.deploy_path = deploy_path;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String type;
		private final String file;

		private String repository;
		private String description;
		private String deploy_path;

		public Builder(String type, String file) {
			this.type = type;
			this.file = file;
		}

		public Builder setRepository(String repository) {
			this.repository = repository;
			return this;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setDeploy_path(String deploy_path) {
			this.deploy_path = deploy_path;
			return this;
		}

		public TArtifactDefinition build() {
			return new TArtifactDefinition(this);
		}
	}
}
