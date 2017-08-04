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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.support.Annotations;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

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
	private QName type;
	@XmlAttribute(name = "file", required = true)
	private List<String> file;
	private String repository;
	private String description;
	private String deploy_path;

	@Annotations.StandardExtension
	private Map<String, TPropertyAssignment> properties;

	public TArtifactDefinition() {
	}

	public TArtifactDefinition(Builder builder) {
		this.setType(builder.type);
		this.setFiles(builder.file);
		this.setRepository(builder.repository);
		this.setDescription(builder.description);
		this.setDeploy_path(builder.deploy_path);
		this.setProperties(builder.properties);
	}

	@NonNull
	public QName getType() {
		return type;
	}

	public void setType(QName type) {
		this.type = type;
	}

	@Annotations.StandardExtension
	@NonNull
	public List<String> getFiles() {
		return file;
	}

	@Annotations.StandardExtension
	public void setFiles(List<String> file) {
		this.file = file;
	}

	@Deprecated
	@NonNull
	public String getFile() {
		return this.file.get(0);
	}

	@Deprecated
	public void setFile(String file) {
		this.file = new ArrayList<>(Collections.singleton(file));
	}

	public void setFile(List<String> file) {
		this.file = file;
	}

	@Nullable
	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	@Nullable
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Nullable
	public String getDeploy_path() {
		return deploy_path;
	}

	public void setDeploy_path(String deploy_path) {
		this.deploy_path = deploy_path;
	}

	public Map<String, TPropertyAssignment> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, TPropertyAssignment> properties) {
		this.properties = properties;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final QName type;
		private final List<String> file;

		private String repository;
		private String description;
		private String deploy_path;

		@Annotations.StandardExtension
		private Map<String, TPropertyAssignment> properties;

		public Builder(QName type, List<String> file) {
			this.type = type;
			this.file = file;
		}

		public Builder(TArtifactDefinition artifactDefinition) {
			this.type = artifactDefinition.getType();
			this.file = artifactDefinition.getFiles();
			this.repository = artifactDefinition.getRepository();
			this.description = artifactDefinition.getDescription();
			this.deploy_path = artifactDefinition.getDeploy_path();
			this.properties = artifactDefinition.getProperties();
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

		public Builder setProperties(Map<String, TPropertyAssignment> properties) {
			this.properties = properties;
			return this;
		}

		public TArtifactDefinition build() {
			return new TArtifactDefinition(this);
		}
	}
}
