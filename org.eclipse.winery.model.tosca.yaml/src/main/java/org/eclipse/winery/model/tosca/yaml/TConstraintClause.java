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

import org.eclipse.winery.model.tosca.yaml.support.ObjectValue;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tConstraintClause", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"equal",
		"greater_than",
		"greater_or_equal",
		"less_than",
		"in_range",
		"valid_values",
		"length",
		"min_length",
		"max_length",
		"pattern"
})
public class TConstraintClause {
	private ObjectValue equal;
	private ObjectValue greater_than;
	private ObjectValue greater_or_equal;
	private ObjectValue less_than;
	private ObjectValue less_or_equal;
	private List<ObjectValue> in_range;
	private List<ObjectValue> valid_values;
	private ObjectValue length;
	private ObjectValue min_length;
	private ObjectValue max_length;
	private ObjectValue pattern;

	public ObjectValue getEqual() {
		return equal;
	}

	public void setEqual(ObjectValue equal) {
		this.equal = equal;
	}

	public ObjectValue getGreater_than() {
		return greater_than;
	}

	public void setGreater_than(ObjectValue greater_than) {
		this.greater_than = greater_than;
	}

	public ObjectValue getGreater_or_equal() {
		return greater_or_equal;
	}

	public void setGreater_or_equal(ObjectValue greater_or_equal) {
		this.greater_or_equal = greater_or_equal;
	}

	public ObjectValue getLess_than() {
		return less_than;
	}

	public void setLess_than(ObjectValue less_than) {
		this.less_than = less_than;
	}

	public ObjectValue getLess_or_equal() {
		return less_or_equal;
	}

	public void setLess_or_equal(ObjectValue less_or_equal) {
		this.less_or_equal = less_or_equal;
	}

	public List<ObjectValue> getIn_range() {
		return in_range;
	}

	public void setIn_range(List<ObjectValue> in_range) {
		this.in_range = in_range;
	}

	public List<ObjectValue> getValid_values() {
		return valid_values;
	}

	public void setValid_values(List<ObjectValue> valid_values) {
		this.valid_values = valid_values;
	}

	public ObjectValue getLength() {
		return length;
	}

	public void setLength(ObjectValue length) {
		this.length = length;
	}

	public ObjectValue getMin_length() {
		return min_length;
	}

	public void setMin_length(ObjectValue min_length) {
		this.min_length = min_length;
	}

	public ObjectValue getMax_length() {
		return max_length;
	}

	public void setMax_length(ObjectValue max_length) {
		this.max_length = max_length;
	}

	public ObjectValue getPattern() {
		return pattern;
	}

	public void setPattern(ObjectValue pattern) {
		this.pattern = pattern;
	}

	public IResult accept(IVisitor visitor, IParameter parameter) throws IException {
		return visitor.visit(this, parameter);
	}
}
