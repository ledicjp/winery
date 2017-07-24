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
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TRepositoryDefinition;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.support.TMapImportDefinition;
import org.eclipse.winery.model.tosca.yaml.visitor.IException;
import org.eclipse.winery.yaml.common.Exception.MissingFile;
import org.eclipse.winery.yaml.common.Exception.MissingImportFile;
import org.eclipse.winery.yaml.common.Exception.YAMLParserException;
import org.eclipse.winery.yaml.common.validator.ExceptionInterpreter;
import org.eclipse.winery.yaml.common.validator.ObjectValidator;
import org.eclipse.winery.yaml.common.validator.TypeValidator;
import org.eclipse.winery.yaml.common.validator.Validator;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.ConstructorException;

public class Reader {

	public static Reader INSTANCE = new Reader();
	private final String TOSCA_NORMATIVE_TYPES = "tosca_simple_yaml_1_1.yml";
	private Yaml yaml;

	public Reader() {
		this.yaml = new Yaml();
	}

	public TServiceTemplate parse(String file) throws YAMLParserException {
		return this.readServiceTemplate(file);
	}

	public TServiceTemplate parseSkipTest(String file) throws YAMLParserException {
		return this.readServiceTemplateSkipTest(file);
	}

	private TServiceTemplate readServiceTemplateSkipTest(String uri) throws YAMLParserException {
		Object object = readObject(uri);
		return buildServiceTemplate(object);
	}

	private TServiceTemplate buildServiceTemplate(Object object) throws YAMLParserException {
		Builder builder = new Builder();
		return builder.buildServiceTemplate(object);
	}

	/**
	 * Uses snakeyaml to convert a file into an Object
	 *
	 * @param file name
	 * @return Object (Lists, Maps, Strings, Integers, Dates)
	 * @throws MissingFile if the file could not be found.
	 */
	private Object readObject(String file) throws MissingFile {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {
			MissingFile ex = new MissingFile("The file \"" + file + "\" could not be found!");
			ex.setFile_context(file);
			throw ex;
		}
		Object object = this.yaml.load(inputStream);
		return object;
	}

	/**
	 * Reads a file and converts it to a ServiceTemplate
	 *
	 * @param uri of the file
	 * @return ServiceTemplate
	 * @throws YAMLParserException the ServiceTemplate or the file is invalid.
	 */
	private TServiceTemplate readServiceTemplate(String uri) throws YAMLParserException {
		// pre parse checking
		ObjectValidator objectValidator = new ObjectValidator();
		objectValidator.validateObject(readObject(uri));

		// parse checking
		TServiceTemplate result = null;
		try {
			result = buildServiceTemplate(readObject(uri));
		} catch (ConstructorException e) {
			ExceptionInterpreter interpreter = new ExceptionInterpreter();
			throw interpreter.interpret(e);
		}

		Validator validator = new Validator();

		// read imports
		Map<String, TServiceTemplate> importMap = new LinkedHashMap<>();
		if (result.getImports() != null) {
			for (TMapImportDefinition map : result.getImports()) {
				Map.Entry<String, TImportDefinition> importDefinition = map.entrySet().iterator().next();
				validator.validate(importDefinition.getValue(), importDefinition.getKey());

				String prefix = importDefinition.getValue().getNamespace_prefix() == null ? "_remote" : importDefinition.getValue().getNamespace_prefix();
				TServiceTemplate impSt = null;
				if (importDefinition.getValue().getRepository() == null) {
					impSt = readURI(importDefinition.getValue().getFile(), uri);
				} else {
					// TODO setContext for repository (Credentials)
					validator.validate(result.getRepositories(), importDefinition.getValue().getRepository());
					TRepositoryDefinition repositoryDefinition = result.getRepositories().get(importDefinition.getValue().getRepository());
					String context = repositoryDefinition.getUrl();

					impSt = readURI(importDefinition.getValue().getFile(), context);
				}

				if (prefix == "tosca" && importMap.containsKey(prefix)) {
					TServiceTemplate tosca = importMap.get(prefix);
					// TODO handle collision for concatenations

					// Concat TArtifactsTypes
					if (tosca.getArtifact_types() != null && impSt.getArtifact_types() != null) {
						tosca.getArtifact_types().putAll(impSt.getArtifact_types());
					} else if (tosca.getArtifact_types() == null) {
						tosca.setArtifact_types(impSt.getArtifact_types());
					}

					// Concat TDataTypes
					if (tosca.getData_types() != null && impSt.getData_types() != null) {
						tosca.getData_types().putAll(impSt.getData_types());
					} else if (tosca.getData_types() == null) {
						tosca.setData_types(impSt.getData_types());
					}

					// Concat TCapabilityTypes
					if (tosca.getCapability_types() != null && impSt.getCapability_types() != null) {
						tosca.getCapability_types().putAll(impSt.getCapability_types());
					} else if (tosca.getCapability_types() == null) {
						tosca.setCapability_types(impSt.getCapability_types());
					}

					// Concat TInterfaceTypes
					if (tosca.getInterface_types() != null && impSt.getInterface_types() != null) {
						tosca.getInterface_types().putAll(impSt.getInterface_types());
					} else if (tosca.getInterface_types() == null) {
						tosca.setInterface_types(impSt.getInterface_types());
					}

					// Concat TRelationshipTypes
					if (tosca.getRelationship_types() != null && impSt.getRelationship_types() != null) {
						tosca.getRelationship_types().putAll(impSt.getRelationship_types());
					} else if (tosca.getRelationship_types() == null) {
						tosca.setRelationship_types(impSt.getRelationship_types());
					}

					// Concat TNodeTypes
					if (tosca.getNode_types() != null && impSt.getNode_types() != null) {
						tosca.getNode_types().putAll(impSt.getNode_types());
					} else if (tosca.getNode_types() == null) {
						tosca.setNode_types(impSt.getNode_types());
					}

					// Concat TGroupTypes
					if (tosca.getPolicy_types() != null && impSt.getPolicy_types() != null) {
						tosca.getPolicy_types().putAll(impSt.getPolicy_types());
					} else if (tosca.getPolicy_types() == null) {
						tosca.setPolicy_types(impSt.getPolicy_types());
					}
				}
				importMap.put(prefix, impSt);
			}
		}
		importMap.put("tosca", readServiceTemplateSkipTest(this.getClass().getClassLoader().getResource(TOSCA_NORMATIVE_TYPES).getFile()));

		try {
			TypeValidator typeValidator = new TypeValidator(importMap);
			typeValidator.validate(result);
			validator.validateTypeRefs(result, importMap);
		} catch (YAMLParserException e) {
			e.setFile_context(uri);
			throw e;
		} catch (IException e) {
			assert (false);
		}

		// post parse checking
		try {
			validator.validate(result);
		} catch (YAMLParserException e) {
			e.setFile_context(uri);
			throw e;
		}
		return result;
	}

	/**
	 * Read Imports
	 *
	 * @param uri     value of ImportDefinition::file
	 * @param context Either the path of the ServiceTemplate file or RepositoryDefinition context
	 * @return ServiceTemplate
	 */
	private TServiceTemplate readURI(String uri, String context) throws YAMLParserException {
		File file = new File(uri);
		// Check if file uri is absolute path
		if (!file.exists() || file.isDirectory()) {
			File ctx = new File(context);
			String importFilePrefix = ctx.getPath().substring(0, ctx.getPath().lastIndexOf("/") + 1);
			file = new File(importFilePrefix + uri);
		}
		// Check if file uri is relative to context
		if (!file.exists() || file.isDirectory()) {
			String msg = "(The keyname file (Context: \"" + context + "\", \"" + uri + "\") could not be found. \nONLY absolute or relative file PATHS supported";
			MissingImportFile ex = new MissingImportFile(msg);
			throw ex;
		}

		TServiceTemplate impSt = null;
		try {
			impSt = readServiceTemplate(file.getPath());
		} catch (MissingFile e) {
			assert (false);
		}

		return impSt;
	}
}
