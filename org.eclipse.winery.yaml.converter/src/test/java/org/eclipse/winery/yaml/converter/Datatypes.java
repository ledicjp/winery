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

import javax.xml.bind.JAXBException;

import org.eclipse.winery.model.tosca.Definitions;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.yaml.common.Exception.YAMLParserException;

import org.junit.Test;

public class Datatypes extends AbstractTest {
	public Datatypes() {
		super("src/test/resources/Datatypes/");
	}

	@Test
	public void testDataTypes() throws YAMLParserException, JAXBException {
		String name = "data_types";
		String namespace = "http://www.example.com/DataTypesTest";

		TServiceTemplate serviceTemplate = readServiceTemplate(name, namespace);
		Definitions definitions = convert(serviceTemplate, name, namespace, PATH);
		writeXml(definitions, name, namespace);
	}

	@Test
	public void testDataTypesWithImport() throws YAMLParserException, JAXBException {
		String name = "data_types-with_import";
		String namespace = "http://www.example.com/DataTypesWithImportTest";

		TServiceTemplate serviceTemplate = readServiceTemplate(name, namespace);
		Definitions definitions = convert(serviceTemplate, name, namespace, PATH);
		writeXml(definitions, name, namespace);
	}

	@Test
	public void testNodeTemplateWithDataTypes() throws YAMLParserException, JAXBException {
		String name = "node_template-using-data_types";
		String namespace = "http://www.example.com/NodeTemplateUsingDataType";

		TServiceTemplate serviceTemplate = readServiceTemplate(name, namespace);
		Definitions definitions = convert(serviceTemplate, name, namespace, PATH);
		writeXml(definitions, name, namespace);
	}
}
