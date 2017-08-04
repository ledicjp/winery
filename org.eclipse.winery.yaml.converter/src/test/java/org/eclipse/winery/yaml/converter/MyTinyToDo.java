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

public class MyTinyToDo extends AbstractTest {
	public MyTinyToDo() {
		super("src/test/resources/MyTinyToDo/");
	}

	@Test
	public void testArtifactTypes() throws YAMLParserException, JAXBException {
		String name = "artifact_types";
		String namespace = "http://www.example.com/MyTinyToDo/ArtifactTypes";

		TServiceTemplate serviceTemplate = readServiceTemplate(name);
		Definitions definitions = convert(serviceTemplate, name, namespace, PATH);
		writeXml(definitions, name, namespace);
	}

	@Test
	public void testNodeTypes() throws YAMLParserException, JAXBException {
		String name = "node_types";
		String namespace = "http://www.example.com/MyTinyToDo/NodeTypes";

		TServiceTemplate serviceTemplate = readServiceTemplate(name);
		Definitions definitions = convert(serviceTemplate, name, namespace, PATH);
		writeXml(definitions, name, namespace);
	}

	@Test
	public void testServiceTemplate() throws YAMLParserException, JAXBException {
		String name = "service_template";
		String namespace = "http://www.example.com/MyTinyToDo/ServiceTemplate";

		TServiceTemplate serviceTemplate = readServiceTemplate(name);
		Definitions definitions = convert(serviceTemplate, name, namespace, PATH);
		writeXml(definitions, name, namespace);
	}
}
