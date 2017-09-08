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

import java.util.Map;

import javax.xml.bind.JAXBException;

import org.eclipse.winery.common.Util;
import org.eclipse.winery.model.tosca.Definitions;
import org.eclipse.winery.model.tosca.TDefinitions;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.yaml.common.reader.XML.Reader;
import org.eclipse.winery.yaml.common.writer.XML.Writer;

public abstract class AbstractTestX2Y {

	private final static String FILE_EXTENSION = ".xml";
	protected final String PATH;

	public AbstractTestX2Y(String PATH) {
		this.PATH = PATH;
	}

	public Definitions readDefinitions(String name) throws JAXBException {
		Reader reader = new Reader();
		return reader.parse(PATH + name + FILE_EXTENSION);
	}

	public Map<String, TServiceTemplate> convert(Definitions serviceTemplate, String path) {
		Converter converter = new Converter();
		return converter.convertX2Y(serviceTemplate, path);
	}

	public void writeXml(TDefinitions definitions, String name, String namespace) throws JAXBException {
		Writer writer = new Writer();
		writer.writeXML(definitions, PATH + Util.URLencode(namespace) + "/" + name + ".xsd");
	}
}
