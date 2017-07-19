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

import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.yaml.common.Exception.MissingImportFile;

import org.junit.Test;

public class BuilderTests {

	public final static String PATH = "src/test/resources/builder/";
	public final static String FILE_TYPE = ".yml";
	public final static Reader reader = new Reader();

	public String getName(String name) {
		return PATH + name + FILE_TYPE;
	}

	@Test
	public void tosca_definitions_version() throws Exception {
		String name = "3_9_3_1-tosca_definitions_version-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
		String tosca_definitions_version = serviceTemplate.getTosca_definitions_version();

		assert (tosca_definitions_version.equals("http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0")
				|| tosca_definitions_version.equals("tosca_simple_yaml_1_0")
				|| tosca_definitions_version.equals("http://docs.oasis-open.org/tosca/ns/simple/yaml/1.1")
				|| tosca_definitions_version.equals("tosca_simple_yaml_1_1"));
	}

	@Test
	public void metadata() throws Exception {
		String name = "3_9_3_2-metadata-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));

		assert (serviceTemplate.getMetadata().get("template_author").equals("kleinech"));
	}

	@Test
	public void dsl_definitions() throws Exception {
		String name = "3_9_3_7-dsl_definitions-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void repositories() throws Exception {
		String name = "3_9_3_8-repositories-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test(expected = MissingImportFile.class)
	public void imports() throws Exception {
		String name = "3_9_3_9-imports-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void artifact_types() throws Exception {
		String name = "3_9_3_10-artifact_types-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void data_types() throws Exception {
		String name = "3_9_3_11-data_types-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void capability_types() throws Exception {
		String name = "3_9_3_12-capability_types-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void interface_types() throws Exception {
		String name = "3_9_3_13-interface_types-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void relationship_types() throws Exception {
		String name = "3_9_3_14-relationship_types-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void node_types() throws Exception {
		String name = "3_9_3_15-node_types-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void group_types() throws Exception {
		String name = "3_9_3_16-group_types-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void policy_types() throws Exception {
		String name = "3_9_3_17-policy_types-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}

	@Test
	public void example_16() throws Exception {
		String name = "example_16-topology_templates-1_1";
		TServiceTemplate serviceTemplate = reader.parse(getName(name));
	}
}
