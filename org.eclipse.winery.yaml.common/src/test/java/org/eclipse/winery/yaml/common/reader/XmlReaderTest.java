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

import javax.xml.bind.JAXBException;

import org.eclipse.winery.model.tosca.Definitions;
import org.eclipse.winery.yaml.common.reader.XML.XmlReader;

import org.junit.Test;

public class XmlReaderTest {
    public final static String PATH = "src/test/resources/reader/";
    public final XmlReader reader;

    public XmlReaderTest() throws JAXBException {
        reader = new XmlReader();
    }

    @Test
    public void TestReader() throws Exception {
        String file = "ArtifactType_WAR.xml";
        Definitions definitions = reader.parse(getFile(file));
        System.out.println(definitions);
    }

    private File getFile(String file) {
        return new File(PATH + file);
    }
}
