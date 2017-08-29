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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
@XmlType(name = "tRelationshipType", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"valid_target_types",
		"interfaces"
})
public class TRelationshipType extends TEntityType {
	private List<QName> valid_target_types;
	private Map<String, TInterfaceDefinition> interfaces;

	public TRelationshipType() {
	}

	public TRelationshipType(Builder builder) {
		super(builder);
		this.setValid_target_types(builder.valid_target_types);
		this.setInterfaces(builder.interfaces);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TRelationshipType)) return false;
		if (!super.equals(o)) return false;
		TRelationshipType that = (TRelationshipType) o;
		return Objects.equals(valid_target_types, that.valid_target_types) &&
				Objects.equals(interfaces, that.interfaces);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), valid_target_types, interfaces);
	}

	@NonNull
	public List<QName> getValid_target_types() {
		if (this.valid_target_types == null) {
			this.valid_target_types = new ArrayList<>();
		}

		return valid_target_types;
	}

	public void setValid_target_types(List<QName> valid_target_types) {
		this.valid_target_types = valid_target_types;
	}

	@NonNull
	public Map<String, TInterfaceDefinition> getInterfaces() {
		if (this.interfaces == null) {
			this.interfaces = new LinkedHashMap<>();
		}

		return interfaces;
	}

	public void setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
		this.interfaces = interfaces;
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
		private List<QName> valid_target_types;
		private Map<String, TInterfaceDefinition> interfaces;

		public Builder() {

		}

		public Builder(TEntityType entityType) {
			super(entityType);
		}

		public Builder setValid_target_types(List<QName> valid_target_types) {
			this.valid_target_types = valid_target_types;
			return this;
		}

		public Builder setInterfaces(Map<String, TInterfaceDefinition> interfaces) {
			this.interfaces = interfaces;
			return this;
		}

		public Builder addValid_target_types(List<QName> valid_target_types) {
			if (valid_target_types == null || valid_target_types.isEmpty()) {
				return this;
			}

			if (this.valid_target_types == null) {
				this.valid_target_types = valid_target_types;
			} else {
				this.valid_target_types.addAll(valid_target_types);
			}

			return this;
		}

		public Builder addValid_target_types(QName valid_target_type) {
			if (valid_target_type == null) {
				return this;
			}

			return addValid_target_types(Collections.singletonList(valid_target_type));
		}

		public TRelationshipType build() {
			return new TRelationshipType(this);
		}
	}
}
