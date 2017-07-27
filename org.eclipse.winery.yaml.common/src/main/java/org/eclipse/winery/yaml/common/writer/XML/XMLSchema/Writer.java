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
package org.eclipse.winery.yaml.common.writer.XML.XMLSchema;

import java.util.List;

import javax.xml.bind.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class Writer {

	public static class Builder {
		private Document document;
		private List<Element> element;

		public Builder setDocument(Document document) {
			this.document = document;
			return this;
		}

		public Builder setElement(List<Element> element) {
			this.element = element;
			return this;
		}

		public Document build() {

			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			return document;
		}
	}
}

