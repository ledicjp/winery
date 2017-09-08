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
    public void toscaDefinitionsVersion() throws Exception {
        String name = "3_9_3_1-tosca_definitions_version-1_1";
        writer.write(builderTests.toscaDefinitionsVersion(), getName(name));
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
    public void dslDefinitions() throws Exception {
        String name = "3_9_3_7-dsl_definitions-1_1";
        writer.write(builderTests.dslDefinitions(), getName(name));
    }

    @Test
    public void repositories() throws Exception {
        String name = "3_9_3_8-repositories-1_1";
        writer.write(builderTests.repositories(), getName(name));
    }

    @Test
    public void artifactTypes() throws Exception {
        String name = "3_9_3_10-artifact_types-1_1";
        writer.write(builderTests.artifactTypes(), getName(name));
    }

    @Test
    public void dataTypes() throws Exception {
        String name = "3_9_3_11-data_types-1_1";
        writer.write(builderTests.dataTypes(), getName(name));
    }

    @Test
    public void capabilityTypes() throws Exception {
        String name = "3_9_3_12-capability_types-1_1";
        writer.write(builderTests.capabilityTypes(), getName(name));
    }

    @Test
    public void interfaceTypes() throws Exception {
        String name = "3_9_3_13-interface_types-1_1";
        writer.write(builderTests.interfaceTypes(), getName(name));
    }

    @Test
    public void relationshipTypes() throws Exception {
        String name = "3_9_3_14-relationship_types-1_1";
        writer.write(builderTests.relationshipTypes(), getName(name));
    }

    @Test
    public void nodeTypes() throws Exception {
        String name = "3_9_3_15-node_types-1_1";
        writer.write(builderTests.nodeTypes(), getName(name));
    }

    @Test
    public void groupTypes() throws Exception {
        String name = "3_9_3_16-group_types-1_1";
        writer.write(builderTests.groupTypes(), getName(name));
    }

    @Test
    public void policyTypes() throws Exception {
        String name = "3_9_3_17-policy_types-1_1";
        writer.write(builderTests.policyTypes(), getName(name));
    }

    @Test
    public void example16() throws Exception {
        String name = "example_16-topology_templates-1_1";
        writer.write(builderTests.example16(), getName(name));
    }
}
