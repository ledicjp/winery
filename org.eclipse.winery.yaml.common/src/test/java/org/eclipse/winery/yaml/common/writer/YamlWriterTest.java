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
package org.eclipse.winery.yaml.common.writer;

import org.eclipse.winery.yaml.common.reader.BuilderTests;
import org.eclipse.winery.yaml.common.writer.YAML.Writer;

import org.junit.Test;

public class YamlWriterTest {
    public final static String PATH = "src/test/resources/writer/";
    public final static String FILE_TYPE = ".yml";
    public final static Writer writer = new Writer();
    public BuilderTests builderTests = new BuilderTests();

    public String getName(String name) {
        return PATH + name + FILE_TYPE;
    }

    @Test
    public void tosca_definitions_version() throws Exception {
        String name = "3_9_3_1-tosca_definitions_version-1_1";
        writer.write(builderTests.tosca_definitions_version(), getName(name));
    }

    @Test
    public void metadata() throws Exception {
        String name = "3_9_3_2-metadata-1_1";
        writer.write(builderTests.metadata(), getName(name));
    }

    @Test
    public void description() throws Exception {
        String name = "3_5_1_3-description-1_1.yml";
        writer.write(builderTests.description(), getName(name));
    }

    @Test
    public void dsl_definitions() throws Exception {
        String name = "3_9_3_7-dsl_definitions-1_1";
        writer.write(builderTests.dsl_definitions(), getName(name));
    }

    @Test
    public void repositories() throws Exception {
        String name = "3_9_3_8-repositories-1_1";
        writer.write(builderTests.repositories(), getName(name));
    }

    @Test
    public void artifact_types() throws Exception {
        String name = "3_9_3_10-artifact_types-1_1";
        writer.write(builderTests.artifact_types(), getName(name));
    }

    @Test
    public void data_types() throws Exception {
        String name = "3_9_3_11-data_types-1_1";
        writer.write(builderTests.data_types(), getName(name));
    }

    @Test
    public void capability_types() throws Exception {
        String name = "3_9_3_12-capability_types-1_1";
        writer.write(builderTests.capability_types(), getName(name));
    }

    @Test
    public void interface_types() throws Exception {
        String name = "3_9_3_13-interface_types-1_1";
        writer.write(builderTests.interface_types(), getName(name));
    }

    @Test
    public void relationship_types() throws Exception {
        String name = "3_9_3_14-relationship_types-1_1";
        writer.write(builderTests.relationship_types(), getName(name));
    }

    @Test
    public void node_types() throws Exception {
        String name = "3_9_3_15-node_types-1_1";
        writer.write(builderTests.node_types(), getName(name));
    }

    @Test
    public void group_types() throws Exception {
        String name = "3_9_3_16-group_types-1_1";
        writer.write(builderTests.group_types(), getName(name));
    }

    @Test
    public void policy_types() throws Exception {
        String name = "3_9_3_17-policy_types-1_1";
        writer.write(builderTests.policy_types(), getName(name));
    }

    @Test
    public void example_16() throws Exception {
        String name = "example_16-topology_templates-1_1";
        writer.write(builderTests.example_16(), getName(name));
    }
}
