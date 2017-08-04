package org.eclipse.winery.yaml.converter.support; /*******************************************************************************
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.winery.model.tosca.yaml.TPropertyDefinition;
import org.eclipse.winery.yaml.common.Namespaces;

import org.eclipse.jdt.annotation.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SchemaBuilder {
	private String namespace;

	private Document document;
	private Element schema;
	private List<Element> elements;
	private List<Element> complexTypes;
	private List<Element> imports;
	private Map<String, String> namespaces;

	private int uniqueNumber;

	public SchemaBuilder(String namespace) {
		reset(namespace);
	}

	public SchemaBuilder reset(String namespace) {
		this.namespace = namespace;

		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		schema = document.createElementNS(Namespaces.XML_NS, "schema");
		schema.setAttribute("attributeFormDefault", "unqualified");
		schema.setAttribute("elementFormDefault", "qualified");
		schema.setAttribute("targetNamespace", this.namespace);

		this.elements = new ArrayList<>();
		this.complexTypes = new ArrayList<>();
		this.imports = new ArrayList<>();
		this.namespaces = new LinkedHashMap<>();

		this.uniqueNumber = 1;

		return this;
	}

	public String getNamespace() {
		return this.namespace;
	}

	@NonNull
	private List<Element> getElements() {
		if (this.elements == null) {
			this.elements = new ArrayList<>();
		}
		return elements;
	}

	public SchemaBuilder setElements(List<Element> elements) {
		this.elements = elements;
		return this;
	}

	public SchemaBuilder addElements(List<Element> elements) {
		if (elements == null) {
			return this;
		}

		if (this.elements == null) {
			this.elements = elements;
		} else {
			this.elements.addAll(elements);
		}
		return this;
	}

	public SchemaBuilder addElements(Element element) {
		if (element == null) {
			return this;
		}

		List<Element> tmp = new ArrayList<>();
		tmp.add(element);
		return addElements(tmp);
	}

	public SchemaBuilder addElements(String key, TPropertyDefinition propertyDefinition) {
		Element element = document.createElement("element");
		element.setAttribute("name", key);
		QName type = TypeConverter.INSTANCE.convert(propertyDefinition.getType());
		String prefix = type.getPrefix();
		if (prefix == null || prefix.isEmpty()) {
			prefix = "pfx" + this.uniqueNumber++;
		}
		if (!this.namespace.equals(type.getNamespaceURI())) {
			this.namespaces.put(prefix, type.getNamespaceURI());
			element.setAttribute("type", prefix + ":" + type.getLocalPart());
		} else {
			element.setAttribute("type", "pfx0:" + type.getLocalPart());
		}
		if (propertyDefinition.getRequired() != null && !propertyDefinition.getRequired()) {
			element.setAttribute("minOccurs", "0");
		}
		// TODO default value
		this.addElements(element);
		return this;
	}

	public SchemaBuilder setImports(List<Element> imports) {
		this.imports = imports;
		return this;
	}

	public SchemaBuilder addImports(List<Element> imports) {
		if (imports == null) {
			return this;
		}

		this.imports.addAll(imports);

		return this;
	}

	public SchemaBuilder addImports(Element _import) {
		if (_import == null) {
			return this;
		}

		List<Element> tmp = new ArrayList<>();
		tmp.add(_import);
		return addImports(tmp);
	}

	public SchemaBuilder addImports(String namespace, String location) {
		Element _import = document.createElement("import");
		_import.setAttribute("namespace", namespace);
		_import.setAttribute("schemaLocation", location);

		return addImports(_import);
	}

	public SchemaBuilder buildComplexType(String name, Boolean wrapped) {
		Element complexType = document.createElement("complexType");
		if (wrapped) {
			Element element = document.createElement("element");
			element.setAttribute("name", name);
			this.complexTypes.add(element);
			element.appendChild(complexType);
		} else {
			complexType.setAttribute("name", name);
			this.complexTypes.add(complexType);
		}

		Element sequence = document.createElement("sequence");
		sequence.setAttribute("xmlns:pfx0", namespace);
		complexType.appendChild(sequence);

		this.getElements().forEach(sequence::appendChild);
		this.elements.clear();
		return this;
	}

	public Document build() {
		for (Map.Entry<String, String> entry : namespaces.entrySet()) {
			schema.setAttribute("xmlns:" + entry.getKey(), entry.getValue());
		}

		for (Element _import : imports) {
			schema.appendChild(_import);
		}

		for (Element entry : complexTypes) {
			schema.appendChild(entry);
		}

		document.appendChild(schema);
		return document;
	}

	public String buildString() {
		build();
		try {
			DOMSource source = new DOMSource(document);

			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			return writer.toString();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void buildFile(String filename) {
		build();
		DOMSource source = new DOMSource(document);
		try {
			File file = new File(filename);
			file.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(file);
			StreamResult result = new StreamResult(writer);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (TransformerException | IOException e) {
			e.printStackTrace();
		}
	}
}

