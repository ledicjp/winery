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
package org.eclipse.winery.model.tosca.yaml.visitor;

import org.eclipse.winery.model.tosca.yaml.TArtifactDefinition;
import org.eclipse.winery.model.tosca.yaml.TArtifactType;
import org.eclipse.winery.model.tosca.yaml.TAttributeAssignment;
import org.eclipse.winery.model.tosca.yaml.TAttributeDefinition;
import org.eclipse.winery.model.tosca.yaml.TCapabilityAssignment;
import org.eclipse.winery.model.tosca.yaml.TCapabilityDefinition;
import org.eclipse.winery.model.tosca.yaml.TCapabilityType;
import org.eclipse.winery.model.tosca.yaml.TConstraintClause;
import org.eclipse.winery.model.tosca.yaml.TDataType;
import org.eclipse.winery.model.tosca.yaml.TEntityType;
import org.eclipse.winery.model.tosca.yaml.TEntrySchema;
import org.eclipse.winery.model.tosca.yaml.TGroupDefinition;
import org.eclipse.winery.model.tosca.yaml.TGroupType;
import org.eclipse.winery.model.tosca.yaml.TImplementation;
import org.eclipse.winery.model.tosca.yaml.TImportDefinition;
import org.eclipse.winery.model.tosca.yaml.TInterfaceAssignment;
import org.eclipse.winery.model.tosca.yaml.TInterfaceDefinition;
import org.eclipse.winery.model.tosca.yaml.TInterfaceType;
import org.eclipse.winery.model.tosca.yaml.TNodeFilterDefinition;
import org.eclipse.winery.model.tosca.yaml.TNodeTemplate;
import org.eclipse.winery.model.tosca.yaml.TNodeType;
import org.eclipse.winery.model.tosca.yaml.TOperationDefinition;
import org.eclipse.winery.model.tosca.yaml.TParameterDefinition;
import org.eclipse.winery.model.tosca.yaml.TPolicyDefinition;
import org.eclipse.winery.model.tosca.yaml.TPolicyType;
import org.eclipse.winery.model.tosca.yaml.TPropertyAssignment;
import org.eclipse.winery.model.tosca.yaml.TPropertyDefinition;
import org.eclipse.winery.model.tosca.yaml.TPropertyFilterDefinition;
import org.eclipse.winery.model.tosca.yaml.TRelationshipAssignment;
import org.eclipse.winery.model.tosca.yaml.TRelationshipDefinition;
import org.eclipse.winery.model.tosca.yaml.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.yaml.TRelationshipType;
import org.eclipse.winery.model.tosca.yaml.TRepositoryDefinition;
import org.eclipse.winery.model.tosca.yaml.TRequirementAssignment;
import org.eclipse.winery.model.tosca.yaml.TRequirementDefinition;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;
import org.eclipse.winery.model.tosca.yaml.TSubstitutionMappings;
import org.eclipse.winery.model.tosca.yaml.TTopologyTemplateDefinition;
import org.eclipse.winery.model.tosca.yaml.TVersion;
import org.eclipse.winery.model.tosca.yaml.support.Metadata;

public interface IVisitor {
	IResult visit(TArtifactDefinition node, IParameter parameter) throws IException;

	IResult visit(TArtifactType node, IParameter parameter) throws IException;

	IResult visit(TAttributeAssignment node, IParameter parameter) throws IException;

	IResult visit(TAttributeDefinition node, IParameter parameter) throws IException;

	IResult visit(TCapabilityAssignment node, IParameter parameter) throws IException;

	IResult visit(TCapabilityDefinition node, IParameter parameter) throws IException;

	IResult visit(TCapabilityType node, IParameter parameter) throws IException;

	IResult visit(TConstraintClause node, IParameter parameter) throws IException;

	IResult visit(TDataType node, IParameter parameter) throws IException;

	IResult visit(TEntityType node, IParameter parameter) throws IException;

	IResult visit(TEntrySchema node, IParameter parameter) throws IException;

	IResult visit(TGroupDefinition node, IParameter parameter) throws IException;

	IResult visit(TGroupType node, IParameter parameter) throws IException;

	IResult visit(TImplementation node, IParameter parameter) throws IException;

	IResult visit(TImportDefinition node, IParameter parameter) throws IException;

	IResult visit(TInterfaceAssignment node, IParameter parameter) throws IException;

	IResult visit(TInterfaceDefinition node, IParameter parameter) throws IException;

	IResult visit(TInterfaceType node, IParameter parameter) throws IException;

	IResult visit(TNodeFilterDefinition node, IParameter parameter) throws IException;

	IResult visit(TNodeTemplate node, IParameter parameter) throws IException;

	IResult visit(TNodeType node, IParameter parameter) throws IException;

	IResult visit(TOperationDefinition node, IParameter parameter) throws IException;

	IResult visit(TParameterDefinition node, IParameter parameter) throws IException;

	IResult visit(TPolicyDefinition node, IParameter parameter) throws IException;

	IResult visit(TPolicyType node, IParameter parameter) throws IException;

	IResult visit(TPropertyAssignment node, IParameter parameter) throws IException;

	IResult visit(TPropertyDefinition node, IParameter parameter) throws IException;

	IResult visit(TPropertyFilterDefinition node, IParameter parameter) throws IException;

	IResult visit(TRelationshipAssignment node, IParameter parameter) throws IException;

	IResult visit(TRelationshipDefinition node, IParameter parameter) throws IException;

	IResult visit(TRelationshipTemplate node, IParameter parameter) throws IException;

	IResult visit(TRelationshipType node, IParameter parameter) throws IException;

	IResult visit(TRepositoryDefinition node, IParameter parameter) throws IException;

	IResult visit(TRequirementAssignment node, IParameter parameter) throws IException;

	IResult visit(TRequirementDefinition node, IParameter parameter) throws IException;

	IResult visit(TServiceTemplate node, IParameter parameter) throws IException;

	IResult visit(TSubstitutionMappings node, IParameter parameter) throws IException;

	IResult visit(TTopologyTemplateDefinition node, IParameter parameter) throws IException;

	IResult visit(TVersion node, IParameter parameter) throws IException;

	IResult visit(Metadata node, IParameter parameter) throws IException;
}
