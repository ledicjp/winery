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
package org.eclipse.winery.yaml.common.Exception;

import org.eclipse.winery.model.tosca.yaml.visitor.IException;

public class YAMLParserException extends IException {
    private String file_context;

    public YAMLParserException(String msg) {
        super(msg);
    }

    public void setFile_context(String msg) {
        this.file_context = "Context::FILE = " + msg;
    }

    public String getMessage() {
        return super.getMessage() + "\n" + this.file_context + "\n";
    }
}
