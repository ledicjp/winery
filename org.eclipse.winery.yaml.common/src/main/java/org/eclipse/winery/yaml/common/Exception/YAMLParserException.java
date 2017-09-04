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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class YAMLParserException extends Exception {
    private String file_context;
    private List<YAMLParserException> exceptions;

    public YAMLParserException(List<YAMLParserException> exceptionList) {
        this.exceptions = exceptionList;
    }

    public YAMLParserException(YAMLParserException exception) {
        this(new ArrayList<>(Collections.singletonList(exception)));
    }

    public YAMLParserException(String msg) {
        super(msg);
    }

    public void setFile_context(String msg) {
        this.file_context = "Context::FILE = " + msg;
    }

    public String getMessage() {
        if (Objects.nonNull(this.exceptions)) {
            return this.exceptions.stream().map(YAMLParserException::getMessage).collect(Collectors.joining("\n\n"));
        } else {
            return super.getMessage() + "\n" + this.file_context + "\n";
        }
    }

    public void addException(YAMLParserException exception) {
        if (Objects.nonNull(exception.getExceptions())) {
            this.exceptions.addAll(exception.getExceptions());
        } else {
            this.exceptions.add(exception);
        }
    }

    public List<YAMLParserException> getExceptions() {
        return this.exceptions;
    }
}
