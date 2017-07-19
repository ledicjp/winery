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

import org.eclipse.winery.yaml.common.Exception.InvalidSyntax;
import org.eclipse.winery.yaml.common.Exception.InvalidType;
import org.eclipse.winery.yaml.common.Exception.YAMLParserException;

import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public class ExceptionInterpreter {
	public YAMLParserException interpret(ConstructorException e) {
		String context = "";
		if (e.getCause() instanceof MarkedYAMLException) {
			context = ((MarkedYAMLException) e.getCause()).getContext();
		} else {
			return new InvalidSyntax(e.toString());
		}
		String invalidDescription = "Cannot create property=description.*";
		if (context.matches(invalidDescription)) {
			return new InvalidType("description", e);
		}
		String invalidType = "Cannot create property=metadata.*";
		if (context.matches(invalidType)) {
			return new InvalidType("metadata", e);
		}

		return new InvalidSyntax(e.toString());
	}
}
