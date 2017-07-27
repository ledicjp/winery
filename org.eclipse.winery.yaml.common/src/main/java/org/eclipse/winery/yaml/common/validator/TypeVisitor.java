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
package org.eclipse.winery.yaml.common.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TCapabilityType;
import org.eclipse.winery.model.tosca.yaml.TDataType;
import org.eclipse.winery.model.tosca.yaml.TGroupType;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TNodeType;
import org.eclipse.winery.model.tosca.yaml.TPolicyType;
import org.eclipse.winery.model.tosca.yaml.TRelationshipType;
import org.eclipse.winery.model.tosca.yaml.support.Metadata;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;
import org.eclipse.winery.yaml.common.Namespaces;

public class TypeVisitor extends ImportVisitor {
	private Map<String, List<String>> artifactTypes;
	private Map<String, List<String>> dataTypes;
	private Map<String, List<String>> capabilityTypes;
	private Map<String, List<String>> interfaceTypes;
	private Map<String, List<String>> relationshipTypes;
	private Map<String, List<String>> nodeTypes;
	private Map<String, List<String>> groupTypes;
	private Map<String, List<String>> policyTypes;

	public TypeVisitor(String namespace, String path) {
		super(namespace, path);
		this.artifactTypes = new LinkedHashMap<>();
		this.dataTypes = new LinkedHashMap<>();
		this.capabilityTypes = new LinkedHashMap<>();
		this.interfaceTypes = new LinkedHashMap<>();
		this.relationshipTypes = new LinkedHashMap<>();
		this.nodeTypes = new LinkedHashMap<>();
		this.groupTypes = new LinkedHashMap<>();
		this.policyTypes = new LinkedHashMap<>();
	}

	@Override
	public IResult visit(TArtifactType node, IParameter parameter) throws IException {
		this.setArtifactTypes(namespace, parameter.getKey());
		setNormativeTypes(parameter.getKey(), node.getMetadata(), artifactTypes);
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TDataType node, IParameter parameter) throws IException {
		this.setDataTypes(namespace, parameter.getKey());
		setNormativeTypes(parameter.getKey(), node.getMetadata(), dataTypes);
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TCapabilityType node, IParameter parameter) throws IException {
		this.setCapabilityTypes(namespace, parameter.getKey());
		setNormativeTypes(parameter.getKey(), node.getMetadata(), capabilityTypes);
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TInterfaceType node, IParameter parameter) throws IException {
		this.setInterfaceTypes(namespace, parameter.getKey());
		setNormativeTypes(parameter.getKey(), node.getMetadata(), interfaceTypes);
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TRelationshipType node, IParameter parameter) throws IException {
		this.setRelationshipTypes(namespace, parameter.getKey());
		setNormativeTypes(parameter.getKey(), node.getMetadata(), relationshipTypes);
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TNodeType node, IParameter parameter) throws IException {
		this.setNodeTypes(namespace, parameter.getKey());
		setNormativeTypes(parameter.getKey(), node.getMetadata(), nodeTypes);
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TGroupType node, IParameter parameter) throws IException {
		this.setGroupTypes(namespace, parameter.getKey());
		setNormativeTypes(parameter.getKey(), node.getMetadata(), groupTypes);
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TPolicyType node, IParameter parameter) throws IException {
		this.setPolicyTypes(namespace, parameter.getKey());
		setNormativeTypes(parameter.getKey(), node.getMetadata(), policyTypes);
		super.visit(node, parameter);
		return null;
	}

	public void setNormativeTypes(String name, Metadata metadata, Map<String, List<String>> map) {
		if (namespace.equals(Namespaces.TOSCA_NS) && metadata != null) {
			String shorthand_name = metadata.get("shorthand_name");
			String type_uri = metadata.get("type_uri");

			if (shorthand_name != null && !shorthand_name.equals(name)) {
				if (map.containsKey(namespace)) {
					map.get(namespace).add(shorthand_name);
				} else {
					map.put(namespace, new ArrayList<>(Arrays.asList(shorthand_name)));
				}
			} else if (type_uri != null && !type_uri.equals(name)) {
				if (map.containsKey(namespace)) {
					map.get(namespace).add(type_uri);
				} else {
					map.put(namespace, new ArrayList<>(Arrays.asList(type_uri)));
				}
			}
		}
	}

	private void setArtifactTypes(String namespace, String name) {
		if (artifactTypes.containsKey(namespace)) {
			artifactTypes.get(namespace).add(name);
		} else {
			artifactTypes.put(namespace, new ArrayList<>(Arrays.asList(name)));
		}
	}

	private void setDataTypes(String namespace, String name) {
		if (dataTypes.containsKey(namespace)) {
			dataTypes.get(namespace).add(name);
		} else {
			dataTypes.put(namespace, new ArrayList<>(Collections.singletonList(name)));
		}
	}

	private void setCapabilityTypes(String namespace, String name) {
		if (capabilityTypes.containsKey(namespace)) {
			capabilityTypes.get(namespace).add(name);
		} else {
			capabilityTypes.put(namespace, new ArrayList<>(Arrays.asList(name)));
		}
	}

	private void setInterfaceTypes(String namespace, String name) {
		if (interfaceTypes.containsKey(namespace)) {
			interfaceTypes.get(namespace).add(name);
		} else {
			interfaceTypes.put(namespace, new ArrayList<>(Arrays.asList(name)));
		}
	}

	private void setRelationshipTypes(String namespace, String name) {
		if (relationshipTypes.containsKey(namespace)) {
			relationshipTypes.get(namespace).add(name);
		} else {
			relationshipTypes.put(namespace, new ArrayList<>(Arrays.asList(name)));
		}
	}

	private void setNodeTypes(String namespace, String name) {
		if (nodeTypes.containsKey(namespace)) {
			nodeTypes.get(namespace).add(name);
		} else {
			nodeTypes.put(namespace, new ArrayList<>(Arrays.asList(name)));
		}
	}

	private void setGroupTypes(String namespace, String name) {
		if (groupTypes.containsKey(namespace)) {
			groupTypes.get(namespace).add(name);
		} else {
			groupTypes.put(namespace, new ArrayList<>(Arrays.asList(name)));
		}
	}

	private void setPolicyTypes(String namespace, String name) {
		if (policyTypes.containsKey(namespace)) {
			policyTypes.get(namespace).add(name);
		} else {
			policyTypes.put(namespace, new ArrayList<>(Arrays.asList(name)));
		}
	}

	public void addDataTypes(List<String> types, String namespace) {
		for (String entry : types) {
			setDataTypes(namespace, entry);
		}
	}

	public Map<String, List<String>> getArtifactTypes() {
		return artifactTypes;
	}

	public Map<String, List<String>> getDataTypes() {
		return dataTypes;
	}

	public Map<String, List<String>> getCapabilityTypes() {
		return capabilityTypes;
	}

	public Map<String, List<String>> getInterfaceTypes() {
		return interfaceTypes;
	}

	public Map<String, List<String>> getRelationshipTypes() {
		return relationshipTypes;
	}

	public Map<String, List<String>> getNodeTypes() {
		return nodeTypes;
	}

	public Map<String, List<String>> getGroupTypes() {
		return groupTypes;
	}

	public Map<String, List<String>> getPolicyTypes() {
		return policyTypes;
	}
}
