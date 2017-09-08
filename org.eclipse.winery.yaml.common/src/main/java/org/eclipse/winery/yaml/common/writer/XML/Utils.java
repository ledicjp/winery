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
package org.eclipse.winery.yaml.common.writer.XML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.eclipse.winery.common.RepositoryFileReference;
import org.eclipse.winery.common.Util;
import org.eclipse.winery.common.ids.definitions.TOSCAComponentId;
import org.eclipse.winery.model.tosca.Definitions;
import org.eclipse.winery.model.tosca.utils.ModelUtilities;
import org.eclipse.winery.repository.backend.BackendUtils;
import org.eclipse.winery.repository.backend.IRepository;
import org.eclipse.winery.repository.backend.RepositoryFactory;
import org.eclipse.winery.repository.backend.constants.MediaTypes;
import org.eclipse.winery.repository.importing.CSARImporter;
import org.eclipse.winery.yaml.common.reader.XML.Reader;

public class Utils {

    public static void setDefinitions(Definitions definitions, boolean overwrite) {
        String path = new File(System.getProperty("java.io.tmpdir") + File.separator + "winery").getAbsolutePath();
        saveDefinitions(definitions, path, definitions.getId());
        Definitions cleanDefinitions = loadDefinitions(path, definitions.getId());

        IRepository repository = RepositoryFactory.getRepository();
        CSARImporter csarImporter = new CSARImporter();
        List<Exception> exceptions = new ArrayList<>();
        cleanDefinitions.getServiceTemplateOrNodeTypeOrNodeTypeImplementation().forEach(entry -> {
            String namespace = csarImporter.getNamespace(entry, definitions.getTargetNamespace());
            csarImporter.setNamespace(entry, namespace);

            String id = ModelUtilities.getId(entry);

            Class<? extends TOSCAComponentId> widClazz = Util.getComponentIdClassForTExtensibleElements(entry.getClass());
            final TOSCAComponentId wid = BackendUtils.getTOSCAcomponentId(widClazz, namespace, id, false);

            if (RepositoryFactory.getRepository().exists(wid)) {
                if (overwrite) {
                    try {
                        RepositoryFactory.getRepository().forceDelete(wid);
                    } catch (IOException e) {
                        exceptions.add(e);
                    }
                } else {
                    return;
                }
            }

            final Definitions part = BackendUtils.createWrapperDefinitions(wid);
            part.getServiceTemplateOrNodeTypeOrNodeTypeImplementation().add(entry);

            RepositoryFileReference ref = BackendUtils.getRefOfDefinitions(wid);
            String content = BackendUtils.getXMLAsString(part, true);
            try {
                RepositoryFactory.getRepository().putContentToFile(ref, content, MediaTypes.MEDIATYPE_TOSCA_DEFINITIONS);
            } catch (Exception e) {
                exceptions.add(e);
            }
        });
    }

    public static void saveDefinitions(Definitions definitions, String path, String name) {
        String filename = path + File.separator + name;
        Writer writer = new Writer();
        try {
            writer.writeXML(definitions, filename);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static Definitions loadDefinitions(String path, String name) {
        String filename = path + File.separator + name;
        Reader reader = new Reader();
        try {
            return reader.parse(filename);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new Definitions();
    }
}
