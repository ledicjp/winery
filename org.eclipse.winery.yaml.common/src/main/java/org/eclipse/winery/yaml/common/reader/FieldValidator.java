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
package org.eclipse.winery.yaml.common.reader;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;

public class FieldValidator {
	public void validateServiceTemplate(Map<String, Object> fields) {
		Arrays.stream(TServiceTemplate.class.getDeclaredFields()).map(field -> field.getName()).collect(Collectors.toList());
	}
}
