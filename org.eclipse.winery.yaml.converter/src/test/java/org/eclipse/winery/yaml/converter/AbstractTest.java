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

import org.eclipse.winery.common.Util;
import org.eclipse.winery.model.tosca.Definitions;
import org.eclipse.winery.model.tosca.TDefinitions;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.yaml.common.Exception.YAMLParserException;
import org.eclipse.winery.yaml.common.reader.YAML.Reader;
import org.eclipse.winery.yaml.common.writer.XML.Writer;

public abstract class AbstractTest {

	private final static String FILE_EXTENSION = ".yml";
	protected final String PATH;

	public AbstractTest(String PATH) {
		this.PATH = PATH;
	}

	public TServiceTemplate readServiceTemplate(String name) throws YAMLParserException {
		Reader reader = new Reader();
		return reader.parse(PATH + name + FILE_EXTENSION);
	}

	public TServiceTemplate readServiceTemplate(String name, String namespace) throws YAMLParserException {
		Reader reader = new Reader();
		return reader.parse(PATH + name + FILE_EXTENSION, namespace);
	}

	public Definitions convert(org.eclipse.winery.model.tosca.yaml.TServiceTemplate serviceTemplate, String name, String namespace, String path) {
		Converter converter = new Converter();
		return converter.convertY2X(serviceTemplate, name, namespace, path);
	}

	public void writeXml(TDefinitions definitions, String name, String namespace) throws JAXBException {
		Writer writer = new Writer();
		writer.writeXML(definitions, PATH + Util.URLencode(namespace) + "/" + name + ".tosca");
	}
}
