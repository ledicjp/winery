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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tArtifactType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"mime_type",
		"file_ext"
})
public class TArtifactType extends TEntityType {
	private String mime_type;
	private List<String> file_ext;

	public TArtifactType() {
	}

	public TArtifactType(Builder builder) {
		super(builder);
		this.setMime_type(builder.mime_type);
		this.setFile_ext(builder.file_ext);
	}

	@Nullable
	public String getMime_type() {
		return mime_type;
	}

	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}

	@NonNull
	public List<String> getFile_ext() {
		if (this.file_ext == null) {
			this.file_ext = new ArrayList<>();
		}

		return file_ext;
	}

	public void setFile_ext(List<String> file_ext) {
		this.file_ext = file_ext;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		R ir1 = super.accept(visitor, parameter);
		R ir2 = visitor.visit(this, parameter);
		if (ir1 == null) {
			return ir2;
		} else {
			return ir1.add(ir2);
		}
	}

	public static class Builder extends TEntityType.Builder {
		private String mime_type;
		private List<String> file_ext;

		public Builder() {

		}

		public Builder(TEntityType entityType) {
			super(entityType);
		}

		public Builder setMime_type(String mime_type) {
			this.mime_type = mime_type;
			return this;
		}

		public Builder setFile_ext(List<String> file_ext) {
			this.file_ext = file_ext;
			return this;
		}

		public Builder addFile_ext(List<String> file_ext) {
			if (file_ext == null || file_ext.isEmpty()) {
				return this;
			}

			if (this.file_ext == null) {
				this.file_ext = file_ext;
			} else {
				this.file_ext.addAll(file_ext);
			}

			return this;
		}

		public Builder addFile_ext(String file_ext) {
			if (file_ext == null || file_ext.isEmpty()) {
				return this;
			}

			return addFile_ext(Collections.singletonList(file_ext));
		}

		public TArtifactType build() {
			return new TArtifactType(this);
		}
	}
}