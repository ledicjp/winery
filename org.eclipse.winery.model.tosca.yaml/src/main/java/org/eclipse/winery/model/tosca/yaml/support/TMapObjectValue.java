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
package org.eclipse.winery.model.tosca.yaml.support;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tMapObjectValue", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
		"map"
})
public class TMapObjectValue implements Map<String, ObjectValue> {
	private Map<String, ObjectValue> map;

	public TMapObjectValue() {
		this.map = new LinkedHashMap<>();
	}

	public Map<String, ObjectValue> getMap() {
		return map;
	}

	public void setMap(Map<String, ObjectValue> map) {
		this.map = map;
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean containsKey(Object o) {
		return this.map.containsKey(o);
	}

	@Override
	public boolean containsValue(Object o) {
		return this.map.containsValue(o);
	}

	@Override
	public ObjectValue get(Object o) {
		return this.map.get(o);
	}

	@Override
	public ObjectValue put(String s, ObjectValue objectValue) {
		return this.map.put(s, objectValue);
	}

	@Override
	public ObjectValue remove(Object o) {
		return this.map.remove(o);
	}

	@Override
	public void putAll(Map<? extends String, ? extends ObjectValue> map) {
		this.map.putAll(map);
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public Set<String> keySet() {
		return this.map.keySet();
	}

	@Override
	public Collection<ObjectValue> values() {
		return this.map.values();
	}

	@Override
	public Set<Entry<String, ObjectValue>> entrySet() {
		return this.map.entrySet();
	}
}
