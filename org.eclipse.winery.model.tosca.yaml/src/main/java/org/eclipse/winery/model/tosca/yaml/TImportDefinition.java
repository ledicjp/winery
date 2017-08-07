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

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.support.Defaults;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

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
	private QName repository;
	private String namespace_uri;
	private String namespace_prefix;

	public TImportDefinition() {

	}

	public TImportDefinition(String file) {
		this.file = file;
	}

	public TImportDefinition(Builder builder) {
		this.setFile(builder.file);
		this.setRepository(builder.repository);
		this.setNamespace_uri(builder.namespace_uri);
		this.setNamespace_prefix(builder.namespace_prefix);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TImportDefinition)) return false;
		TImportDefinition that = (TImportDefinition) o;
		return Objects.equals(file, that.file) &&
				Objects.equals(repository, that.repository) &&
				Objects.equals(namespace_uri, that.namespace_uri) &&
				Objects.equals(namespace_prefix, that.namespace_prefix);
	}

	@Override
	public int hashCode() {
		return Objects.hash(file, repository, namespace_uri, namespace_prefix);
	}

	@NonNull
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Nullable
	public QName getRepository() {
		return repository;
	}

	public void setRepository(QName repository) {
		this.repository = repository;
	}

	@NonNull
	public String getNamespace_uri() {
		if (namespace_uri == null || namespace_uri.isEmpty()) {
			this.namespace_uri = Defaults.DEFAULT_NS;
		}
		return namespace_uri;
	}

	public void setNamespace_uri(String namespace_uri) {
		this.namespace_uri = namespace_uri;
	}

	@Nullable
	public String getNamespace_prefix() {
		return namespace_prefix;
	}

	public void setNamespace_prefix(String namespace_prefix) {
		this.namespace_prefix = namespace_prefix;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private final String file;
		private QName repository;
		private String namespace_uri;
		private String namespace_prefix;

		public Builder(String file) {
			this.file = file;
		}

		public Builder setRepository(QName repository) {
			this.repository = repository;
			return this;
		}

		public Builder setNamespace_uri(String namespace_uri) {
			this.namespace_uri = namespace_uri;
			return this;
		}

		public Builder setNamespace_prefix(String namespace_prefix) {
			this.namespace_prefix = namespace_prefix;
			return this;
		}

		public TImportDefinition build() {
			return new TImportDefinition(this);
		}
	}
}
