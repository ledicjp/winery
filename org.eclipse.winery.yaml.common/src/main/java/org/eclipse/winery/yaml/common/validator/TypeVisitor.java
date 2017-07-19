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
import java.util.List;

import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TCapabilityType;
import org.eclipse.winery.model.tosca.yaml.TDataType;
import org.eclipse.winery.model.tosca.yaml.TGroupType;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TNodeType;
import org.eclipse.winery.model.tosca.yaml.TPolicyType;
import org.eclipse.winery.model.tosca.yaml.TRelationshipType;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractVisitor;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.model.tosca.yaml.visitor.IParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.IResult;

public class TypeVisitor extends AbstractVisitor {
	private List<String> artifactTypes;
	private List<String> dataTypes;
	private List<String> capabilityTypes;
	private List<String> interfaceTypes;
	private List<String> relationshipTypes;
	private List<String> nodeTypes;
	private List<String> groupTypes;
	private List<String> policyTypes;

	private String NS_PREFIX;

	public TypeVisitor() {
		this.artifactTypes = new ArrayList<>();
		this.dataTypes = new ArrayList<>();
		this.capabilityTypes = new ArrayList<>();
		this.interfaceTypes = new ArrayList<>();
		this.relationshipTypes = new ArrayList<>();
		this.nodeTypes = new ArrayList<>();
		this.groupTypes = new ArrayList<>();
		this.policyTypes = new ArrayList<>();
		this.NS_PREFIX = "";
	}

	@Override
	public IResult visit(TArtifactType node, IParameter parameter) throws IException {
		this.artifactTypes.add(NS_PREFIX + parameter.getKey());
		if (NS_PREFIX.equals("tosca:") && node.getMetadata() != null) {
			if (node.getMetadata().get("shorthand_name") != null) {
				this.artifactTypes.add(NS_PREFIX + node.getMetadata().get("shorthand_name"));
				this.artifactTypes.add(node.getMetadata().get("shorthand_name"));
			}
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TDataType node, IParameter parameter) throws IException {
		this.dataTypes.add(NS_PREFIX + parameter.getKey());
		if (NS_PREFIX.equals("tosca:") && node.getMetadata() != null) {
			if (node.getMetadata().get("shorthand_name") != null) {
				this.dataTypes.add(NS_PREFIX + node.getMetadata().get("shorthand_name"));
				this.dataTypes.add(node.getMetadata().get("shorthand_name"));
			}
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TCapabilityType node, IParameter parameter) throws IException {
		this.capabilityTypes.add(NS_PREFIX + parameter.getKey());
		if (NS_PREFIX.equals("tosca:") && node.getMetadata() != null) {
			if (node.getMetadata().get("shorthand_name") != null) {
				this.capabilityTypes.add(NS_PREFIX + node.getMetadata().get("shorthand_name"));
				this.capabilityTypes.add(node.getMetadata().get("shorthand_name"));
			}
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TInterfaceType node, IParameter parameter) throws IException {
		this.interfaceTypes.add(NS_PREFIX + parameter.getKey());
		if (NS_PREFIX.equals("tosca:") && node.getMetadata() != null) {
			if (node.getMetadata().get("shorthand_name") != null) {
				this.interfaceTypes.add(NS_PREFIX + node.getMetadata().get("shorthand_name"));
				this.interfaceTypes.add(node.getMetadata().get("shorthand_name"));
			}
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TRelationshipType node, IParameter parameter) throws IException {
		this.relationshipTypes.add(NS_PREFIX + parameter.getKey());
		if (NS_PREFIX.equals("tosca:") && node.getMetadata() != null) {
			if (node.getMetadata().get("shorthand_name") != null) {
				this.relationshipTypes.add(NS_PREFIX + node.getMetadata().get("shorthand_name"));
				this.relationshipTypes.add(node.getMetadata().get("shorthand_name"));
			}
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TNodeType node, IParameter parameter) throws IException {
		this.nodeTypes.add(NS_PREFIX + parameter.getKey());
		if (NS_PREFIX.equals("tosca:") && node.getMetadata() != null) {
			if (node.getMetadata().get("shorthand_name") != null) {
				this.nodeTypes.add(NS_PREFIX + node.getMetadata().get("shorthand_name"));
				this.nodeTypes.add(node.getMetadata().get("shorthand_name"));
			}
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TGroupType node, IParameter parameter) throws IException {
		this.groupTypes.add(NS_PREFIX + parameter.getKey());
		if (NS_PREFIX.equals("tosca:") && node.getMetadata() != null) {
			if (node.getMetadata().get("shorthand_name") != null) {
				this.groupTypes.add(NS_PREFIX + node.getMetadata().get("shorthand_name"));
				this.groupTypes.add(node.getMetadata().get("shorthand_name"));
			}
		}
		super.visit(node, parameter);
		return null;
	}

	@Override
	public IResult visit(TPolicyType node, IParameter parameter) throws IException {
		this.policyTypes.add(NS_PREFIX + parameter.getKey());
		if (NS_PREFIX.equals("tosca:") && node.getMetadata() != null) {
			if (node.getMetadata().get("shorthand_name") != null) {
				this.policyTypes.add(NS_PREFIX + node.getMetadata().get("shorthand_name"));
				this.policyTypes.add(node.getMetadata().get("shorthand_name"));
			}
		}
		super.visit(node, parameter);
		return null;
	}

	public List<String> getArtifactTypes() {
		return artifactTypes;
	}

	public List<String> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(List<String> dataTypes) {
		for (String entry : dataTypes) {
			if (!this.dataTypes.contains(entry)) {
				this.dataTypes.add(entry);
			}
		}
	}

	public List<String> getCapabilityTypes() {
		return capabilityTypes;
	}

	public List<String> getInterfaceTypes() {
		return interfaceTypes;
	}

	public List<String> getRelationshipTypes() {
		return relationshipTypes;
	}

	public List<String> getNodeTypes() {
		return nodeTypes;
	}

	public List<String> getGroupTypes() {
		return groupTypes;
	}

	public List<String> getPolicyTypes() {
		return policyTypes;
	}

	public TypeVisitor setNS(String prefix) {
		if (prefix.equals("")) {
			this.NS_PREFIX = prefix;
		} else {
			this.NS_PREFIX = prefix + ":";
		}
		return this;
	}
}
