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

import org.eclipse.winery.model.tosca.yaml.support.ObjectValue;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

import org.eclipse.jdt.annotation.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPolicyType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"targets",
		"triggers"
})
public class TPolicyType extends TEntityType {
	private List<String> targets;
	private ObjectValue triggers;

	public TPolicyType() {
	}

	public TPolicyType(Builder builder) {
		super(builder);
		this.setTargets(builder.targets);
		this.setTriggers(builder.triggers);
	}

	@NonNull
	public List<String> getTargets() {
		if (this.targets == null) {
			this.targets = new ArrayList<>();
		}

		return targets;
	}

	public void setTargets(List<String> targets) {
		this.targets = targets;
	}

	public ObjectValue getTriggers() {
		return triggers;
	}

	public void setTriggers(ObjectValue triggers) {
		this.triggers = triggers;
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

	public static class Builder extends TEntityType.Builder {
		private List<String> targets;
		private ObjectValue triggers;

		public Builder() {

		}

		public Builder(TEntityType entityType) {
			super(entityType);
		}

		public Builder setTargets(List<String> targets) {
			this.targets = targets;
			return this;
		}

		public Builder setTriggers(ObjectValue triggers) {
			this.triggers = triggers;
			return this;
		}

		public TPolicyType build() {
			return new TPolicyType(this);
		}
	}
}
