/**
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 * Lukas Harzenetter - initial API and implementation
 */

package org.eclipse.winery.repository.resources.entitytypes.artifacttypes;

import org.eclipse.winery.repository.resources.AbstractResourceTest;

import org.junit.Test;

public class ArtifactTypeResourceTest extends AbstractResourceTest {

	private final String ENTITY_TYPE = "artifacttypes/";
	private final String INSTANCE_XML_PATH = "entitytypes/" + ENTITY_TYPE + "instance.xml";
	private final String BAOBAB_JSON_PATH = "entitytypes/" + ENTITY_TYPE + "baobab_inital.json";

	public static final String FOLDERPATH = "http%3A%2F%2Fwinery.opentosca.org%2Ftest%2Fponyuniverse/DressageEquipment_Pony";


	private final String INSTANCE_URL = ENTITY_TYPE + FOLDERPATH;





	@Test
	public void getInstanceXml() throws Exception {
		this.setRevisionTo("c25aa724201824fce6eddcc7c35a666c6e015880");
		this.assertGet(testStringConverter(INSTANCE_URL), INSTANCE_XML_PATH);
	}

	@Test
	public void getServicetemplate() throws Exception {
		this.setRevisionTo("c25aa724201824fce6eddcc7c35a666c6e015880");
		this.assertGet(ENTITY_TYPE, BAOBAB_JSON_PATH);
	}

}
