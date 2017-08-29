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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.winery.model.tosca.Definitions;

public class XmlReader {
    private final Unmarshaller unmarshaller;

    public XmlReader() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Definitions.class);
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    public Definitions parse(File file) throws JAXBException {
        return (Definitions) unmarshaller.unmarshal(file);
    }
}
