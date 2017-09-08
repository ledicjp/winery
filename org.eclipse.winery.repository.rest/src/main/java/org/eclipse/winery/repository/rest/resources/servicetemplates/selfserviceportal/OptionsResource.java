/*******************************************************************************
 * Copyright (c) 2012-2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Oliver Kopp - initial API and implementation
 *******************************************************************************/
package org.eclipse.winery.repository.rest.resources.servicetemplates.selfserviceportal;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.winery.common.RepositoryFileReference;
import org.eclipse.winery.model.selfservice.ApplicationOption;
import org.eclipse.winery.repository.datatypes.ids.elements.SelfServiceMetaDataId;
import org.eclipse.winery.repository.rest.RestUtils;
import org.eclipse.winery.repository.rest.resources._support.collections.withid.EntityWithIdCollectionResource;
import org.eclipse.winery.repository.rest.resources.servicetemplates.ServiceTemplateResource;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptionsResource extends EntityWithIdCollectionResource<OptionResource, ApplicationOption> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OptionsResource.class);

	/**
	 * @param res is the parent of the SelfServicePortalResource, which is a parent of this resource
	 */
	public OptionsResource(List<ApplicationOption> list, ServiceTemplateResource res) {
		super(OptionResource.class, ApplicationOption.class, list, res);
	}

	@Override
	public String getId(ApplicationOption entity) {
		return entity.getId();
	}

	@POST
	@ApiOperation(value = "Adds a new option<p>TODO: @return JSON with .tableData: Array with row data for dataTable</p>")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @formatter:off
	public Response onPost(
			@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("planServiceName") String planServiceName,
			@FormDataParam("planInputMessage") String planInputMessage,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("file") FormDataBodyPart body
	) {
		// @formatter:on
		if (StringUtils.isEmpty(name)) {
			return Response.status(Status.BAD_REQUEST).entity("planName must be given").build();
		}
		if (StringUtils.isEmpty(description)) {
			return Response.status(Status.BAD_REQUEST).entity("description must be given").build();
		}
		if (StringUtils.isEmpty(planServiceName)) {
			return Response.status(Status.BAD_REQUEST).entity("planServiceName must be given").build();
		}
		if (StringUtils.isEmpty(planInputMessage)) {
			return Response.status(Status.BAD_REQUEST).entity("planInputMessage must be given").build();
		}
		if (uploadedInputStream == null) {
			return Response.status(Status.BAD_REQUEST).entity("file has to be provided").build();
		}
		ApplicationOption option = new ApplicationOption();

		String id = RestUtils.createXMLidAsString(name);

		String fileNamePrefix = OptionResource.getFileNamePrefix(id);
		String iconFileName = fileNamePrefix + OptionResource.ICON_JPG;
		String planInputMessageFileName = fileNamePrefix + OptionResource.PLAN_INPUT_XML;

		// create option data
		option.setId(id);
		option.setName(name);
		option.setDescription(description);
		option.setIconUrl(iconFileName);
		option.setPlanInputMessageUrl(planInputMessageFileName);
		option.setPlanServiceName(planServiceName);

		// BEGIN: store icon and planInputMessage

		SelfServiceMetaDataId ssmdId = ((SelfServicePortalResource) this.res).getId();

		RepositoryFileReference iconRef = new RepositoryFileReference(ssmdId, iconFileName);
		RestUtils.putContentToFile(iconRef, uploadedInputStream, body.getMediaType());

		RepositoryFileReference planInputMessageRef = new RepositoryFileReference(ssmdId, planInputMessageFileName);
		RestUtils.putContentToFile(planInputMessageRef, planInputMessage, MediaType.TEXT_XML_TYPE);

		// END: store icon and planInputMessage

		this.list.add(option);
		return RestUtils.persist(this.res);
	}
}
