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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPropertyFilterDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"constraints"
})
public class TPropertyFilterDefinition {
	private List<TConstraintClause> constraints;

	public TPropertyFilterDefinition() {
	}

	public TPropertyFilterDefinition(Builder builder) {
		this.setConstraints(builder.constraints);
	}

	@NonNull
	public List<TConstraintClause> getConstraints() {
		if (this.constraints == null) {
			this.constraints = new ArrayList<>();
		}

		return constraints;
	}

	public void setConstraints(List<TConstraintClause> constraints) {
		this.constraints = constraints;
	}

	public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) throws IException {
		return visitor.visit(this, parameter);
	}

	public static class Builder {
		private List<TConstraintClause> constraints;

		public Builder() {

		}

		public Builder setConstraints(List<TConstraintClause> constraints) {
			this.constraints = constraints;
			return this;
		}

		public TPropertyFilterDefinition build() {
			return new TPropertyFilterDefinition(this);
		}
	}
}
