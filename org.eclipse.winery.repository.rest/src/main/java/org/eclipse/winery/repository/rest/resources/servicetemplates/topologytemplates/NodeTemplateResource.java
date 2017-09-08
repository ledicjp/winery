/*******************************************************************************
 * Copyright (c) 2012-2014 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Oliver Kopp - initial API and implementation
 *******************************************************************************/
package org.eclipse.winery.repository.rest.resources.servicetemplates.topologytemplates;

import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;

import org.eclipse.winery.common.ids.Namespace;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.constants.Namespaces;
import org.eclipse.winery.repository.rest.RestUtils;
import org.eclipse.winery.repository.rest.resources.INodeTemplateResourceOrNodeTypeImplementationResource;
import org.eclipse.winery.repository.rest.resources._support.IPersistable;
import org.eclipse.winery.repository.rest.resources._support.collections.IIdDetermination;
import org.eclipse.winery.repository.rest.resources.artifacts.DeploymentArtifactsResource;
import org.eclipse.winery.repository.rest.resources.entitytemplates.TEntityTemplateResource;
import org.eclipse.winery.repository.rest.resources.servicetemplates.ServiceTemplateResource;

import io.swagger.annotations.ApiOperation;

public class NodeTemplateResource extends TEntityTemplateResource<TNodeTemplate> implements INodeTemplateResourceOrNodeTypeImplementationResource {

	private final QName qnameX = new QName(Namespaces.TOSCA_WINERY_EXTENSIONS_NAMESPACE, "x");
	private final QName qnameY = new QName(Namespaces.TOSCA_WINERY_EXTENSIONS_NAMESPACE, "y");

	public NodeTemplateResource(IIdDetermination<TNodeTemplate> idDetermination, TNodeTemplate o, int idx, List<TNodeTemplate> list, IPersistable res) {
		super(idDetermination, o, idx, list, res);
	}

	@Path("deploymentartifacts/")
	public DeploymentArtifactsResource getDeploymentArtifacts() {
		return new DeploymentArtifactsResource(this.o, this);
	}

	@GET
	@ApiOperation(value = "* The following methods are currently *not* used by the topology modeler.<br />" + "The modeler is using the repository client to interact with the repository")
	@Path("minInstances")
	public String getMinInstances() {
		return Integer.toString(this.o.getMinInstances());
	}

	@PUT
	@Path("minInstances")
	public Response setMinInstances(@FormParam(value = "minInstances") String minInstances) {
		int min = Integer.parseInt(minInstances);
		this.o.setMinInstances(min);
		return RestUtils.persist(this.res);
	}

	@GET
	@Path("maxInstances")
	public String getMaxInstances() {
		return this.o.getMaxInstances();
	}

	@PUT
	@Path("maxInstances")
	public Response setMaxInstances(@FormParam(value = "maxInstances") String maxInstances) {
		// TODO: check for valid integer | "unbound"
		this.o.setMaxInstances(maxInstances);
		return RestUtils.persist(this.res);
	}


	/* * *
	 * The visual appearance
	 *
	 * We do not use a subresource "visualappearance" here to avoid generation of more objects
	 * * */

	@Path("x")
	@GET
	@ApiOperation(value = "@return the x coordinate of the node template")
	public String getX() {
		Map<QName, String> otherAttributes = this.o.getOtherAttributes();
		return otherAttributes.get(this.qnameX);
	}

	@Path("x")
	@PUT
	public Response setX(String x) {
		this.o.getOtherAttributes().put(this.qnameX, x);
		return RestUtils.persist(this.res);
	}

	@Path("y")
	@GET
	@ApiOperation(value = "@return the y coordinate of the node template")
	public String getY() {
		Map<QName, String> otherAttributes = this.o.getOtherAttributes();
		return otherAttributes.get(this.qnameY);
	}

	@Path("y")
	@PUT
	public Response setY(String y) {
		this.o.getOtherAttributes().put(this.qnameY, y);
		return RestUtils.persist(this.res);
	}

	@Override
	public Namespace getNamespace() {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}

	/**
	 * Required for persistence after a change of the deployment artifact. Required by DeploymentArtifactResource to be
	 * able to persist
	 *
	 * @return the service template this node template belongs to
	 */
	public ServiceTemplateResource getServiceTemplateResource() {
		return (ServiceTemplateResource) this.res;
	}

	/**
	 * required for topology modeler to check for existence of a node template at the server
	 *
	 * @return empty response
	 */
	@HEAD
	public Response getHEAD() {
		return Response.noContent().build();
	}
}
