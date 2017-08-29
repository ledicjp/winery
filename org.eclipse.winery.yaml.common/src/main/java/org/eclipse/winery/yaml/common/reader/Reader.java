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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.yaml.common.Exception.MissingFile;
import org.eclipse.winery.yaml.common.Exception.MissingImportFile;
import org.eclipse.winery.yaml.common.Exception.YAMLParserException;
import org.eclipse.winery.yaml.common.Namespaces;
import org.eclipse.winery.yaml.common.validator.ExceptionInterpreter;
import org.eclipse.winery.yaml.common.validator.ObjectValidator;
import org.eclipse.winery.yaml.common.validator.Validator;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.scanner.ScannerException;

public class Reader {
    private Yaml yaml;

    public Reader() {
        this.yaml = new Yaml();
    }

    public TServiceTemplate parse(String file) throws YAMLParserException {
        return this.parse(file, Namespaces.DEFAULT_NS);
    }

    public TServiceTemplate parse(String file, String namespace) throws YAMLParserException {
        return this.readServiceTemplate(file, namespace);
    }

    public TServiceTemplate parse(TImportDefinition definition, String context) throws YAMLParserException {
        return this.parse(definition, context, Namespaces.DEFAULT_NS);
    }

    public TServiceTemplate parse(TImportDefinition definition, String context, String namespace) throws YAMLParserException {
        return this.readImportDefinition(definition, context, namespace);
    }

    public TServiceTemplate parseSkipTest(String file) throws YAMLParserException {
        return this.parseSkipTest(file, Namespaces.DEFAULT_NS);
    }

    public TServiceTemplate parseSkipTest(String file, String namespace) throws YAMLParserException {
        return this.readServiceTemplateSkipTest(file, namespace);
    }

    private TServiceTemplate readServiceTemplateSkipTest(String uri, String namespace) throws YAMLParserException {
        Object object = readObject(uri);
        return buildServiceTemplate(object, namespace);
    }

    private TServiceTemplate buildServiceTemplate(Object object, String namespace) throws YAMLParserException {
        Builder builder = new Builder(namespace);
        return builder.buildServiceTemplate(object);
    }

    /**
     * Uses snakeyaml to convert a file into an Object
     *
     * @param fileName name
     * @return Object (Lists, Maps, Strings, Integers, Dates)
     * @throws MissingFile if the file could not be found.
     */
    private Object readObject(String fileName) throws MissingFile {
        InputStream inputStream;
        try {
            File file = new File(fileName);
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            MissingFile ex = new MissingFile("The file \"" + fileName + "\" could not be found!");
            ex.setFile_context(fileName);
            throw ex;
        }
        return this.yaml.load(inputStream);
    }

    /**
     * Reads a file and converts it to a ServiceTemplate
     *
     * @param uri of the file
     * @return ServiceTemplate
     * @throws YAMLParserException the ServiceTemplate or the file is invalid.
     */
    private TServiceTemplate readServiceTemplate(String uri, String namespace) throws YAMLParserException {
        // pre parse checking
        try {
            ObjectValidator objectValidator = new ObjectValidator();
            objectValidator.validateObject(readObject(uri));
        } catch (ConstructorException e) {
            ExceptionInterpreter interpreter = new ExceptionInterpreter();
            throw interpreter.interpret(e);
        } catch (ScannerException e) {
            ExceptionInterpreter interpreter = new ExceptionInterpreter();
            throw interpreter.interpret(e);
        }

        // parse checking
        TServiceTemplate result;
        try {
            result = buildServiceTemplate(readObject(uri), namespace);
        } catch (YAMLParserException e) {
            e.setFile_context(uri);
            throw e;
        }

        // post parse checking
        Validator validator = new Validator(new File(uri).getParentFile().getPath());
        try {
            validator.validate(result, namespace);
        } catch (YAMLParserException e) {
            e.setFile_context(uri);
            throw e;
        } catch (IException e) {
            assert false;
        }

        return result;
    }

    public TServiceTemplate readImportDefinition(TImportDefinition definition, String context, String namespace) throws YAMLParserException {
        if (definition == null) {
            return null;
        }

        String importNamespace = definition.getNamespace_uri() == null ? namespace : definition.getNamespace_uri();
        if (definition.getRepository() == null) {
            return readURI(definition.getFile(), context, importNamespace);
        } else {
            // TODO Support Repositories
            return null;
        }
    }

    /**
     * Read Imports
     *
     * @param uri     value of ImportDefinition::file
     * @param context Either the path of the ServiceTemplate file or RepositoryDefinition context
     * @return ServiceTemplate
     */
    private TServiceTemplate readURI(String uri, String context, String namespace) throws YAMLParserException {
        File file = new File(uri);
        // Check if file uri is absolute path
        if (!file.exists() || file.isDirectory()) {
            file = new File(context + "/" + uri);
        }
        // Check if file uri is relative to context
        if (!file.exists() || file.isDirectory()) {
            String msg = "(The keyname file (Context: \"" + context + "\", \"" + uri + "\") could not be found. \nONLY absolute or relative file PATHS supported";
            throw new MissingImportFile(msg);
        }

        return readServiceTemplate(file.getPath(), namespace);
    }
}
