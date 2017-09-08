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

import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.yaml.common.Defaults;
import org.eclipse.winery.yaml.common.Exception.YAMLParserException;
import org.eclipse.winery.yaml.common.Namespaces;
import org.eclipse.winery.yaml.common.reader.YAML.Reader;
import org.eclipse.winery.yaml.common.validator.support.ExceptionVisitor;
import org.eclipse.winery.yaml.common.validator.support.Parameter;
import org.eclipse.winery.yaml.common.validator.support.Result;

public class ImportVisitor extends ExceptionVisitor<Result, Parameter> {
    protected final String path;
    protected String namespace;

    public ImportVisitor(String namespace, String path) {
        this.path = path;
        this.namespace = namespace;
    }

    @Override
    public Result visit(TServiceTemplate node, Parameter parameter) {
        Reader reader = new Reader();
        if (!this.namespace.equals(Namespaces.TOSCA_NS)) {
            TServiceTemplate serviceTemplate;
            try {
                serviceTemplate = reader.parseSkipTest(
                    this.getClass().getClassLoader().getResource(Defaults.TOSCA_NORMATIVE_TYPES).getFile(), Namespaces.TOSCA_NS);
                String tmpNamespace = this.namespace;
                this.namespace = Namespaces.TOSCA_NS;
                this.visit(serviceTemplate, new Parameter());
                this.namespace = tmpNamespace;
            } catch (YAMLParserException e) {
                setException(e);
            }
        }

        super.visit(node, parameter);
        return null;
    }

    @Override
    public Result visit(TImportDefinition node, Parameter parameter) {
        Reader reader = new Reader();
        String importNamespace = node.getNamespaceUri() == null ? this.namespace : node.getNamespaceUri();
        try {
            TServiceTemplate serviceTemplate = reader.parse(node, path, importNamespace);
            if (serviceTemplate != null) {
                String tmpNamespace = this.namespace;
                this.namespace = importNamespace;
                this.visit(serviceTemplate, new Parameter());
                this.namespace = tmpNamespace;
            }
            super.visit(node, parameter);
        } catch (YAMLParserException e) {
            this.setException(e);
        }
        return null;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
