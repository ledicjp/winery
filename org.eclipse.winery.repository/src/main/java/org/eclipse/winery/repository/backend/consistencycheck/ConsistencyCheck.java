/**
 * Copyright (c) 2017 University of Stuttgart.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 * Oliver Kopp - initial API and implementation
 *******************************************************************************/
package org.eclipse.winery.repository.backend.consistencycheck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.winery.common.ids.definitions.ArtifactTemplateId;
import org.eclipse.winery.common.ids.definitions.ArtifactTypeId;
import org.eclipse.winery.common.ids.definitions.CapabilityTypeId;
import org.eclipse.winery.common.ids.definitions.NodeTypeId;
import org.eclipse.winery.common.ids.definitions.NodeTypeImplementationId;
import org.eclipse.winery.common.ids.definitions.PolicyTemplateId;
import org.eclipse.winery.common.ids.definitions.RelationshipTypeImplementationId;
import org.eclipse.winery.common.ids.definitions.RequirementTypeId;
import org.eclipse.winery.common.ids.definitions.ServiceTemplateId;
import org.eclipse.winery.common.ids.definitions.TOSCAComponentId;
import org.eclipse.winery.repository.backend.IRepository;
import org.eclipse.winery.repository.backend.Repository;
import org.eclipse.winery.repository.exceptions.RepositoryCorruptException;
import org.eclipse.winery.repository.export.TOSCAExportUtil;

public class ConsistencyCheck {

	private IRepository repository;

	public void checkConsistency(IRepository repository) {
		if (this.repository == null) {
			this.repository = repository;
		}
	}

	@POST
	@Path("/checkConsistencyForToscaId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkConsistencyForToscaId(TOSCAComponentId toCheck) {
		
		this.checkConsistency(Repository.INSTANCE); // Create Instance.
		List<String> errors = new ArrayList<>(); // collect Errors in list.

		if (toCheck instanceof ArtifactTemplateId) {
			Set<ArtifactTemplateId> list = this.repository.getAllTOSCAComponentIds(ArtifactTemplateId.class);
			errors = checkConsistencyArtifactTemplateId(list);
		} else if (toCheck instanceof PolicyTemplateId) {
			Set<PolicyTemplateId> list = this.repository.getAllTOSCAComponentIds(PolicyTemplateId.class);
			errors = checkConsistencyPolicyTemplateId(list);
		} else if (toCheck instanceof ServiceTemplateId) {
			Set<ServiceTemplateId> list = this.repository.getAllTOSCAComponentIds(ServiceTemplateId.class);
			errors = checkConsistencyServiceTemplateId(list);
		} else if (toCheck instanceof NodeTypeImplementationId) {
			Set<NodeTypeImplementationId> list = this.repository.getAllTOSCAComponentIds(NodeTypeImplementationId.class);
			errors = checkConsistencyNodeTypeImplementationId(list);
		} else if (toCheck instanceof RelationshipTypeImplementationId) {
			Set<RelationshipTypeImplementationId> list = this.repository.getAllTOSCAComponentIds(RelationshipTypeImplementationId.class);
			errors = checkConsistencyRelationshipTypeImplementationId(list);
		} else if (toCheck instanceof ArtifactTypeId) {
			Set<ArtifactTypeId> list = this.repository.getAllTOSCAComponentIds(ArtifactTypeId.class);
			errors = checkConsistencyArtifactTypeId(list);
		} else if (toCheck instanceof CapabilityTypeId) {
			Set<CapabilityTypeId> list = this.repository.getAllTOSCAComponentIds(CapabilityTypeId.class);
			errors = checkConsistencyCapabilityTypeId(list);
		} else if (toCheck instanceof NodeTypeId) {
			Set<NodeTypeId> list = this.repository.getAllTOSCAComponentIds(NodeTypeId.class);
			errors = checkConsistencyNodeTypeId(list);
		} else if (toCheck instanceof RequirementTypeId) {
			Set<RequirementTypeId> list = this.repository.getAllTOSCAComponentIds(RequirementTypeId.class);
			errors = checkConsistencyRequirementTypeId(list);
		}
		return Response.status(200).entity(errors).build(); // format Return Value
	}

	private List<String> checkConsistencyArtifactTemplateId(Set<ArtifactTemplateId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (TOSCAComponentId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}

	private List<String> checkConsistencyPolicyTemplateId(Set<PolicyTemplateId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (PolicyTemplateId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}
	
	private List<String> checkConsistencyServiceTemplateId(Set<ServiceTemplateId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (ServiceTemplateId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}

	private List<String> checkConsistencyNodeTypeImplementationId(Set<NodeTypeImplementationId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (NodeTypeImplementationId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}
	
	private List<String> checkConsistencyRelationshipTypeImplementationId(Set<RelationshipTypeImplementationId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (RelationshipTypeImplementationId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}

	private List<String> checkConsistencyArtifactTypeId(Set<ArtifactTypeId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (ArtifactTypeId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}
	
	private List<String> checkConsistencyCapabilityTypeId(Set<CapabilityTypeId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (CapabilityTypeId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}

	private List<String> checkConsistencyNodeTypeId(Set<NodeTypeId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (NodeTypeId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}

	private List<String> checkConsistencyRequirementTypeId(Set<RequirementTypeId> checkForLinkedInRepository) {
		// Get All PolicyTemplateIds from Repository 
		List<String> errors = new ArrayList<>();
		for (RequirementTypeId id : checkForLinkedInRepository) checkRepository(errors, id, repository);
		return errors;
	}
	
	private static void checkRepository(List<String> errors, TOSCAComponentId id, IRepository repository) {
		// Iterate all PolicyTemplateIds
		Collection<TOSCAComponentId> linked;
		try {
            linked = TOSCAExportUtil.getReferencedTOSCAComponentIds(id);
            // get All Linke TOSCAComponentIds
            for (TOSCAComponentId l : linked) {
                // Iterate all linked TOSCAComponentIds
                if (!repository.exists(l)) { // If Repository already has the linked TOSCAComponentId in it.
					String errorMessage = id.getQName().toString() + l.getQName().toString();
					errors.add(errorMessage); // Collect found Error.
                }
            }
        } catch (RepositoryCorruptException e) {
            // collectError;
			String errorMessage = e.getMessage();
			errors.add(errorMessage); // Collect found Error.
        }
	}
}
