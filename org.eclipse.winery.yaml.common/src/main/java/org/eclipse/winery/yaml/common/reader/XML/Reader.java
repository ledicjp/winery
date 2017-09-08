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
package org.eclipse.winery.yaml.common.reader.XML;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.winery.model.tosca.Definitions;

public class Reader {
    private Unmarshaller unmarshaller;

    public Reader() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Definitions.class);
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Definitions parse(File file) throws JAXBException {
        return (Definitions) unmarshaller.unmarshal(file);
    }

    public Definitions parse(String fileName) throws JAXBException {
        File file = new File(fileName);
        return parse(file);
    }
}
