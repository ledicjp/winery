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
import org.eclipse.winery.yaml.common.reader.YAML.Reader;

import org.junit.Ignore;
import org.junit.Test;

public class BuilderTests {

    public final static String PATH = "src/test/resources/builder/";
    public final static String FILE_TYPE = ".yml";
    public final static Reader reader = new Reader();

    public String getName(String name) {
        return PATH + name + FILE_TYPE;
    }

    @Test
    public void builderTests() throws Exception {
        tosca_definitions_version();
        metadata();
        description();
        dsl_definitions();
        repositories();
        artifact_types();
        data_types();
        capability_types();
        interface_types();
        relationship_types();
        node_types();
        group_types();
        policy_types();
        example_16();
    }

    public TServiceTemplate tosca_definitions_version() throws Exception {
        String name = "3_9_3_1-tosca_definitions_version-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        String tosca_definitions_version = serviceTemplate.getToscaDefinitionsVersion();

        assert (tosca_definitions_version.equals("http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0")
            || tosca_definitions_version.equals("tosca_simple_yaml_1_0")
            || tosca_definitions_version.equals("http://docs.oasis-open.org/tosca/ns/simple/yaml/1.1")
            || tosca_definitions_version.equals("tosca_simple_yaml_1_1"));
        return serviceTemplate;
    }


    public TServiceTemplate metadata() throws Exception {
        String name = "3_9_3_2-metadata-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));

        assert (serviceTemplate.getMetadata().get("template_author").equals("kleinech"));
        return serviceTemplate;
    }


    public TServiceTemplate description() throws Exception {
        String name = "3_5_1_3-description-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate dsl_definitions() throws Exception {
        String name = "3_9_3_7-dsl_definitions-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate repositories() throws Exception {
        String name = "3_9_3_8-repositories-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }

    @Ignore
    @Test(expected = MissingImportFile.class)
    public void imports() throws Exception {
        String name = "3_9_3_9-imports-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
    }


    public TServiceTemplate artifact_types() throws Exception {
        String name = "3_9_3_10-artifact_types-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate data_types() throws Exception {
        String name = "3_9_3_11-data_types-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate capability_types() throws Exception {
        String name = "3_9_3_12-capability_types-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate interface_types() throws Exception {
        String name = "3_9_3_13-interface_types-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate relationship_types() throws Exception {
        String name = "3_9_3_14-relationship_types-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate node_types() throws Exception {
        String name = "3_9_3_15-node_types-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate group_types() throws Exception {
        String name = "3_9_3_16-group_types-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate policy_types() throws Exception {
        String name = "3_9_3_17-policy_types-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }


    public TServiceTemplate example_16() throws Exception {
        String name = "example_16-topology_templates-1_1";
        TServiceTemplate serviceTemplate = reader.parse(getName(name));
        return serviceTemplate;
    }
}
