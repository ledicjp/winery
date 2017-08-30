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
package org.eclipse.winery.yaml.converter;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.eclipse.winery.common.Util;
import org.eclipse.winery.model.tosca.Definitions;
import org.eclipse.winery.model.tosca.TDocumentation;
import org.eclipse.winery.model.tosca.TImport;
import org.eclipse.winery.model.tosca.yaml.TArtifactDefinition;
import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TCapabilityType;
import org.eclipse.winery.model.tosca.yaml.TDataType;
import org.eclipse.winery.model.tosca.yaml.TGroupType;
import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TNodeTemplate;
import org.eclipse.winery.model.tosca.yaml.TNodeType;
import org.eclipse.winery.model.tosca.yaml.TPolicyType;
import org.eclipse.winery.model.tosca.yaml.TPropertyDefinition;
import org.eclipse.winery.model.tosca.yaml.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.yaml.TRelationshipType;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.support.Defaults;
import org.eclipse.winery.model.tosca.yaml.support.TMapImportDefinition;
import org.eclipse.winery.yaml.common.reader.XmlReader;

import org.eclipse.jdt.annotation.NonNull;

public class X2YConverter {
	private String PATH;
	private XmlReader reader;

	private Map<String, String> metadata;
	private Map<String, TServiceTemplate> service_templates;
	private List<TMapImportDefinition> imports;
	private Map<String, TArtifactType> artifact_types;
	private Map<String, TDataType> data_types;
	private Map<String, TCapabilityType> capability_types;
	private Map<String, TInterfaceType> interface_types;
	private Map<String, TRelationshipType> relationship_types;
	private Map<String, TNodeType> node_types;
	private Map<String, TGroupType> group_types;
	private Map<String, TPolicyType> policy_types;

	private Map<String, TNodeTemplate> node_templates;
	private Map<String, TRelationshipTemplate> relationship_templates;
	private Map<String, TPropertyDefinition> property_definitions;

	private Map<String, List<Map.Entry<String, TArtifactDefinition>>> artifactDefinitionNodeTypeMap;
	private Map<String, TArtifactDefinition> artifactDefinitions;

	private void init() {
		this.reader = new XmlReader();
	}

	/**
	 * Converts TOSCA XML Definitions to TOSCA YAML ServiceTemplates
	 */
	public Map<String, TServiceTemplate> convert(org.eclipse.winery.model.tosca.Definitions node, String PATH) {
		if (node == null) {
			return null;
		}

		this.PATH = PATH;
		this.init();

		TServiceTemplate.Builder builder = new TServiceTemplate.Builder(Defaults.TOSCA_DEFINITIONS_VERSION);
		builder.setDescription(convertDocumentation(node.getDocumentation()));
		convert(node.getServiceTemplateOrNodeTypeOrNodeTypeImplementation());
		builder.setImports(imports);
		builder.setArtifact_types(artifact_types);
		builder.setData_types(data_types);
		builder.setCapability_types(capability_types);
		builder.setInterface_types(interface_types);
		builder.setRelationship_types(relationship_types);
		builder.setNode_types(node_types);
		builder.setGroup_types(group_types);
		builder.setPolicy_types(policy_types);

		if (service_templates == null) {
			service_templates = new LinkedHashMap<>();
		}
		service_templates.put(node.getName(), builder.build());

		return service_templates;
	}

	/**
	 * Converts TOSCA XML list of Documentations to TOSCA YAML Description of type string
	 */
	public String convertDocumentation(@NonNull List<TDocumentation> doc) {
		return doc.stream().map(TDocumentation::getContent).flatMap(List::stream).map(Object::toString).collect(Collectors.joining("\n"));
	}

	/**
	 * Imports TOSCA XML Import and converts them to TOSCA YAML ServiceTemplates including import statements
	 */
	public void convert(TImport node) {
		// TODO rewrite file parser (not only relative file position)
		try {
			Definitions impt = this.reader.parse(this.PATH + "/" + node.getLocation());
			X2YConverter converter = new X2YConverter();

			Map<String, TServiceTemplate> imports = converter.convert(impt, this.PATH);
			TImportDefinition.Builder builder = new TImportDefinition.Builder(getOutputFileFromImport(node.getNamespace(), node.getLocation()));

			imports.forEach((key, value) -> {
				//Writer
				TImportDefinition.Builder iBuilder = new TImportDefinition.Builder("");
			});
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private <T> void convert(List<T> nodes) {
		nodes.stream().filter(Objects::isNull).forEach((T node) -> {
			if (node instanceof TImport) {
				convert((TImport) node);
			}
		});
	}

	private String getOutputFileFromImport(String namespace, String location) {
		String name = location.substring(location.lastIndexOf(File.separator) + 1);
		return Util.URLencode(namespace) + File.separator + name;
	}
}
