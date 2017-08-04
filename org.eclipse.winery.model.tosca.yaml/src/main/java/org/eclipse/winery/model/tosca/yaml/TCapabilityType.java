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
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCapabilityType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"valid_source_types"
})
public class TCapabilityType extends TEntityType {
	private List<QName> valid_source_types;

	public TCapabilityType() {
	}

	public TCapabilityType(Builder builder) {
		super(builder);
		this.setValid_source_types(builder.valid_source_types);
	}

	@NonNull
	public List<QName> getValid_source_types() {
		if (this.valid_source_types == null) {
			this.valid_source_types = new ArrayList<>();
		}

		return valid_source_types;
	}

	public void setValid_source_types(List<QName> valid_source_types) {
		this.valid_source_types = valid_source_types;
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
		private List<QName> valid_source_types;

		public Builder() {

		}

		public Builder(TEntityType entityType) {
			super(entityType);
		}

		public Builder setValid_source_types(List<QName> valid_source_types) {
			this.valid_source_types = valid_source_types;
			return this;
		}

		public TCapabilityType build() {
			return new TCapabilityType(this);
		}
	}
}
