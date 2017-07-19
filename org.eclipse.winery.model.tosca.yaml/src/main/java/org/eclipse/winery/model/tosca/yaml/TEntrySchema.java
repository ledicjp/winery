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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tEntrySchema", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"type",
		"description",
		"constraints"
})
public class TEntrySchema {
	private String type;
	private String description;
	private List<TConstraintClause> constraints;

	public TEntrySchema() {
	}

	public TEntrySchema(Builder builder) {
		this.setType(builder.type);
		this.setDescription(builder.description);
		this.setConstraints(builder.constraints);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TConstraintClause> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<TConstraintClause> constraints) {
		this.constraints = constraints;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private String type;
		private String description;
		private List<TConstraintClause> constraints;

		public Builder() {
		}

		public Builder setType(String type) {
			this.type = type;
			return this;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setConstraints(List<TConstraintClause> constraints) {
			this.constraints = constraints;
			return this;
		}

		public TEntrySchema build() {
			return new TEntrySchema(this);
		}
	}
}
