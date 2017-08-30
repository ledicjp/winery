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

import org.eclipse.winery.model.tosca.Definitions;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;

public class Converter {
	private Y2XConverter y2XConverter;
	private X2YConverter x2YConverter;

	public Converter() {
		this.y2XConverter = new Y2XConverter();
		this.x2YConverter = new X2YConverter();
	}

	public Definitions convertY2X(TServiceTemplate serviceTemplate, String name, String namespace, String path) {
		return this.y2XConverter.convert(serviceTemplate, name, namespace, path);
	}

	public Map<String, TServiceTemplate> convertX2Y(Definitions definitions, String path) {
		return this.x2YConverter.convert(definitions, path);
	}
}
