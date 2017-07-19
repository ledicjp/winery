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
import javax.xml.bind.annotation.XmlType;

import org.eclipse.winery.model.tosca.yaml.support.ObjectValue;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tParameterDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"value"
})
public class TParameterDefinition extends TPropertyDefinition {
	private ObjectValue value;

	public TParameterDefinition() {
	}

	public TParameterDefinition(Builder builder) {
		TPropertyDefinition.init(this, builder);
		this.setValue(builder.value);
	}

	public ObjectValue getValue() {
		return value;
	}

	public void setValue(ObjectValue value) {
		this.value = value;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		IResult ir1 = super.accept(visitor, parameter);
		IResult ir2 = visitor.visit(this, parameter);
		if (ir1 == null) {
			return ir2;
		} else {
			return ir1.add(ir2);
		}
	}

	public static class Builder extends TPropertyDefinition.Builder {
		private ObjectValue value;

		public Builder(String type) {
			super(type);
		}

		public Builder(TPropertyDefinition propertyDefinition) {
			super(propertyDefinition);
		}

		public Builder setValue(ObjectValue value) {
			this.value = value;
			return this;
		}

		public TParameterDefinition build() {
			return new TParameterDefinition(this);
		}
	}
}
