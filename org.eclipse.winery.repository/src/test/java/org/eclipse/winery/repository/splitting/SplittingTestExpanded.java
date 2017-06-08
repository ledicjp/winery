/*******************************************************************************
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Marvin Wohlfarth
 *     Nicole Keppler
 *******************************************************************************/

package org.eclipse.winery.repository.splitting;

import java.util.List;

import org.eclipse.winery.common.ModelUtilities;
import org.eclipse.winery.model.tosca.TCapability;
import org.eclipse.winery.model.tosca.TEntityTemplate;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TRequirement;
import org.eclipse.winery.model.tosca.TTopologyTemplate;
import org.eclipse.winery.repository.PrefsTestEnabledGitBackedRepository;
import org.eclipse.winery.repository.resources.AbstractResourceTest;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 * VM = baobab
 * Server = grape
 * Service = lemon
 * App = mango
 * HostedOn = kiwi
 */
public class SplittingTestExpanded extends AbstractResourceTest {

	@BeforeClass
	public static void init() throws Exception {
		// enable git-backed repository
		new PrefsTestEnabledGitBackedRepository();
	}

	@Before
	public void inititalize() throws Exception {
		this.setRevisionTo("1e2054315f18e80c466c26e6918d6506ce53f7f7");
		TTopologyTemplate fruitsTestTopology = new TTopologyTemplate();

		TNodeTemplate baobabNode = new TNodeTemplate();
		baobabNode.setId("babobab");

		TNodeTemplate mangoNode = new TNodeTemplate();
		mangoNode.setId("mango");
		ModelUtilities.setTargetLabel(mangoNode, "Philippines");

		TNodeTemplate lemonNode = new TNodeTemplate();
		lemonNode.setId("lemon");

		TNodeTemplate grapeNode = new TNodeTemplate();
		grapeNode.setId("grape");

		TNodeTemplate mangoNode2 = new TNodeTemplate();
		mangoNode2.setId("mango2");
		ModelUtilities.setTargetLabel(mangoNode, "Mexico");

		//mangoLemonRelation

		TRelationshipTemplate mangoLemonRelation = new TRelationshipTemplate();
		mangoLemonRelation.setId("mangoLemonRelation");

		TRelationshipTemplate.TargetElement mangoLemonTarget = new TRelationshipTemplate.TargetElement();
		mangoLemonTarget.setRef(lemonNode);
		mangoLemonRelation.setTargetElement(mangoLemonTarget);

		TRelationshipTemplate.SourceElement mangoLemonSource = new TRelationshipTemplate.SourceElement();
		mangoLemonSource.setRef(mangoNode);
		mangoLemonRelation.setSourceElement(mangoLemonSource);

		//mangoLemon2

		TRelationshipTemplate mango2LemonRelation = new TRelationshipTemplate();
		mango2LemonRelation.setId("mango2LemonRelation");

		TRelationshipTemplate.TargetElement mango2LemonTarget = new TRelationshipTemplate.TargetElement();
		mango2LemonTarget.setRef(lemonNode);
		mango2LemonRelation.setTargetElement(mango2LemonTarget);

		TRelationshipTemplate.SourceElement mango2LemonSource = new TRelationshipTemplate.SourceElement();
		mango2LemonSource.setRef(mangoNode2);
		mango2LemonRelation.setSourceElement(mango2LemonSource);

		//lemonGrapeRelation

		TRelationshipTemplate lemonGrapeRelation = new TRelationshipTemplate();
		lemonGrapeRelation.setId("lemonGrapeRelation");

		TRelationshipTemplate.TargetElement lemonGrapeTarget = new TRelationshipTemplate.TargetElement();
		lemonGrapeTarget.setRef(grapeNode);
		lemonGrapeRelation.setTargetElement(lemonGrapeTarget);

		TRelationshipTemplate.SourceElement lemonGrapeSource = new TRelationshipTemplate.SourceElement();
		lemonGrapeSource.setRef(lemonNode);
		lemonGrapeRelation.setSourceElement(lemonGrapeSource);

		//grapeBaobabRelation

		TRelationshipTemplate grapeBaobabRelation = new TRelationshipTemplate();
		lemonGrapeRelation.setId("grapeBaobabRelation");

		TRelationshipTemplate.TargetElement grapeBaobabTarget = new TRelationshipTemplate.TargetElement();
		grapeBaobabTarget.setRef(baobabNode);
		grapeBaobabRelation.setTargetElement(grapeBaobabTarget);

		TRelationshipTemplate.SourceElement grapeBaobabSource = new TRelationshipTemplate.SourceElement();
		grapeBaobabSource.setRef(grapeNode);
		grapeBaobabRelation.setSourceElement(grapeBaobabSource);

		// reqCanHostLemon

		TNodeTemplate.Requirements reqCanHostLemon = new TNodeTemplate.Requirements();
		TRequirement t = new TRequirement();
		t.setName("reqCanHostLemon");
		reqCanHostLemon.getRequirement().add(t);
		lemonNode.setRequirements(reqCanHostLemon);

		// capCanHostLemon

		TNodeTemplate.Capabilities capCanHostLemon = new TNodeTemplate.Capabilities();
		TCapability c = new TCapability();
		c.setName("capCanHostLemon");
		capCanHostLemon.getCapability().add(c);
		grapeNode.setCapabilities(capCanHostLemon);

		// reqCanHostGrape

		TNodeTemplate.Requirements reqCanHostGrape = new TNodeTemplate.Requirements();
		TRequirement t2 = new TRequirement();
		t2.setName("reqCanHostGrape");
		reqCanHostGrape.getRequirement().add(t2);
		grapeNode.setRequirements(reqCanHostGrape);

		// capCanHostGrape

		TNodeTemplate.Capabilities capCanHostGrape = new TNodeTemplate.Capabilities();
		TCapability c2 = new TCapability();
		c2.setName("capCanHostGrape");
		capCanHostGrape.getCapability().add(c2);
		baobabNode.setCapabilities(capCanHostGrape);

		// reqCanHostmMango

		TNodeTemplate.Requirements reqCanHostMango = new TNodeTemplate.Requirements();
		TRequirement t3 = new TRequirement();
		t3.setName("reqCanHostMango");
		reqCanHostMango.getRequirement().add(t3);
		mangoNode.setRequirements(reqCanHostMango);
		mangoNode2.setRequirements(reqCanHostMango);

		// capCanHostMango

		TNodeTemplate.Capabilities capCanHostMango = new TNodeTemplate.Capabilities();
		TCapability c3 = new TCapability();
		c3.setName("capCanHostMango");
		capCanHostMango.getCapability().add(c3);
		lemonNode.setCapabilities(capCanHostMango);

		List<TEntityTemplate> entityTemplateList = fruitsTestTopology.getNodeTemplateOrRelationshipTemplate();
		entityTemplateList.add(baobabNode);
		entityTemplateList.add(grapeNode);
		entityTemplateList.add(lemonNode);
		entityTemplateList.add(mangoNode);
		entityTemplateList.add(mangoNode2);
		entityTemplateList.add(mangoLemonRelation);
		entityTemplateList.add(mango2LemonRelation);
		entityTemplateList.add(lemonGrapeRelation);
		entityTemplateList.add(grapeBaobabRelation);

	}


}
