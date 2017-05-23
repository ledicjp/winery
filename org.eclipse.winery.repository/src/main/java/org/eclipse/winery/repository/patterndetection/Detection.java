package org.eclipse.winery.repository.patterndetection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.winery.common.ModelUtilities;
import org.eclipse.winery.common.ids.definitions.ServiceTemplateId;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TTopologyTemplate;
import org.eclipse.winery.repository.patterndetection.keywords.OperatingSystem;
import org.eclipse.winery.repository.patterndetection.keywords.Server;
import org.eclipse.winery.repository.patterndetection.keywords.Service;
import org.eclipse.winery.repository.patterndetection.keywords.VirtualHardware;
import org.eclipse.winery.repository.patterndetection.patterntaxonomies.IaaSTaxonomie;
import org.eclipse.winery.repository.patterndetection.patterntaxonomies.PaaSTaxonomie;
import org.eclipse.winery.repository.resources.AbstractComponentsResource;
import org.eclipse.winery.repository.resources.servicetemplates.ServiceTemplateResource;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by marvin on 12.05.2017.
 */
public class Detection {

	private static final String labelServer = "Server";
	private static final String labelService = "Service";
	private static final String labelOS = "OperatingSystem";
	private static final String labelVirtualHardware = "virtual_hardware";

	private boolean isPaaS = false;
	private boolean isIaaS = false;

	private List<String> detectedPattern = new ArrayList<>();
	private List<String> matchedKeywords = new ArrayList<>();
	private List<String> patternProbabilityHigh = new ArrayList<>();
	private List<String> patternProbabilityMedium = new ArrayList<>();
	private List<String> patternProbabilityLow = new ArrayList<>();
	private List<TNodeTemplateExtended> labeledNodeTemplates = new ArrayList<>();
	private PaaSTaxonomie paas = new PaaSTaxonomie();
	private IaaSTaxonomie iaas = new IaaSTaxonomie();
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> paasGraph;
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> iaasGraph;
	private DirectedMultigraph<TNodeTemplateExtended, AbstractTopology.RelationshipEdge> topology;
	private AbstractTopology abstractTopology;

	public Detection(ServiceTemplateId serviceTemplateId) {
		ServiceTemplateResource serviceTempateResource = (ServiceTemplateResource) AbstractComponentsResource.getComponentInstaceResource(serviceTemplateId);
		TTopologyTemplate tTopologyTemplate = serviceTempateResource.getServiceTemplate().getTopologyTemplate();
		searchForKeywords(tTopologyTemplate);
		detectPatternLayerOne(tTopologyTemplate);
		//detectPatternLayerTwo(tTopologyTemplate);

	}

	/**
	 * search for keywords
	 * @param tTopologyTemplate
	 */
	private void searchForKeywords(TTopologyTemplate tTopologyTemplate) {
		List<TNodeTemplate> tNodeTemplateList = ModelUtilities.getAllNodeTemplates(tTopologyTemplate);
		List<Server> serverList = new ArrayList<>(EnumSet.allOf(Server.class));
		List<Service> serviceList = new ArrayList<>(EnumSet.allOf(Service.class));
		List<VirtualHardware> virtualHardwareList = new ArrayList<>(EnumSet.allOf(VirtualHardware.class));
		List<OperatingSystem> operatingSystemList = new ArrayList<>(EnumSet.allOf(OperatingSystem.class));

		//TODO labeling if keyword was detected
		// labeling with detected keywords
		for (TNodeTemplate tNodeTemplate: tNodeTemplateList) {
			for (Server server: serverList) {
				if (tNodeTemplate.getName().contains(server.toString())) {
					matchedKeywords.add(server.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelServer);
					labeledNodeTemplates.add(temp);
				}
			}
			for (Service service: serviceList) {
				if (tNodeTemplate.getName().contains(service.toString())) {
					matchedKeywords.add(service.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelService);
					labeledNodeTemplates.add(temp);
				}
			}
			for (VirtualHardware virtualHardware: virtualHardwareList) {
				if (tNodeTemplate.getName().contains(virtualHardware.toString())) {
					matchedKeywords.add(virtualHardware.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelVirtualHardware);
					labeledNodeTemplates.add(temp);
				}
			}
			for (OperatingSystem operatingSystem: operatingSystemList) {
				if (tNodeTemplate.getName().contains(operatingSystem.toString())) {
					matchedKeywords.add(operatingSystem.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelOS);
					labeledNodeTemplates.add(temp);
				}
			}
		}

		// create all taxonomies
		paasGraph = paas.getPaasTaxonomie();
		iaasGraph = iaas.getIaasTaxonomie();

		// set propabilities for possible patterns according to detected keywords
		if (!matchedKeywords.isEmpty()) {
			for (String keyword: matchedKeywords) {
				DefaultWeightedEdge tempEdge;
				switch (keyword) {
					case "AmazonBeanstalk":
						tempEdge = paasGraph.getEdge(paas.getElasticPlatform(), paas.getEnvironmentBasedAvailability());
						// set 100% probability of the pattern this edge is pointing to
						paasGraph.setEdgeWeight(tempEdge, 0.75);
						tempEdge = paasGraph.getEdge(paas.getElasticPlatform(), paas.getElasticLoadBalancer());
						paasGraph.setEdgeWeight(tempEdge, 0.75);
						detectedPattern.add("Environment-based Availability");
						detectedPattern.add("Platform-as-a-Service");
						detectedPattern.add("Elastic Platform");
						isPaaS = true;
						// TODO bottom-up-search & set probabilities for patterns depending on
					case "EC2":
						isIaaS = true;

					case "OpenStack":
						isIaaS = true;

					default:
				}
			}

			//TODO check if paas or iaas, select graph accordingly
			Set<DefaultWeightedEdge> edgeSet = paasGraph.edgeSet();
			Iterator iterator = edgeSet.iterator();
			while (iterator.hasNext()) {
				DefaultWeightedEdge edge = (DefaultWeightedEdge) iterator.next();
				double weight = paasGraph.getEdgeWeight(edge);
				if (weight == 0.75) {
					patternProbabilityHigh.add(paasGraph.getEdgeTarget(edge));
				} else if (weight == 0.5) {
					patternProbabilityMedium.add(paasGraph.getEdgeTarget(edge));
				} else if (weight == 0.25) {
					patternProbabilityLow.add(paasGraph.getEdgeTarget(edge));
				}
			}
		}
	}

	private void detectPatternLayerOne(TTopologyTemplate tTopologyTemplate) {
		abstractTopology = new AbstractTopology(tTopologyTemplate, labeledNodeTemplates);

		List<TNodeTemplate> tNodeTemplateList = ModelUtilities.getAllNodeTemplates(tTopologyTemplate);
		List<TRelationshipTemplate> tRelationshipTemplateList = ModelUtilities.getAllRelationshipTemplates(tTopologyTemplate);
		//TODO multiple baseNodes
		TNodeTemplate baseNodeTemplate = getLowestNode(tNodeTemplateList.get(0), tRelationshipTemplateList);

		Set<TNodeTemplateExtended> allNodes = abstractTopology.getGraph().vertexSet();
		TNodeTemplateExtended baseNodeExtended = new TNodeTemplateExtended();
		Iterator iterator = allNodes.iterator();
		while (iterator.hasNext()) {
			TNodeTemplateExtended temp = (TNodeTemplateExtended) iterator.next();
			if (temp.getNodeTemplate().getId().equals(baseNodeTemplate.getId())) {
				baseNodeExtended = temp;
			}
		}
		abstractTopology.map(baseNodeExtended);
		System.out.println("Base: " + baseNodeTemplate.getName());
		System.out.println(abstractTopology.getGraph().toString());
		for (String string: detectedPattern) {
			System.out.println(string);
		}
	}

	/**
	 * Get node with no outgoing relation
	 * @param baseNodeTemplate
	 * @param tRelationshipTemplateList
	 * @return
	 */
	private TNodeTemplate getLowestNode(TNodeTemplate baseNodeTemplate, List<TRelationshipTemplate> tRelationshipTemplateList) {
		for (TRelationshipTemplate tRelationshipTemplate: tRelationshipTemplateList) {
			if (baseNodeTemplate.equals((tRelationshipTemplate.getSourceElement().getRef()))) {
				getLowestNode((TNodeTemplate) tRelationshipTemplate.getTargetElement().getRef(), tRelationshipTemplateList);
			}
		}
		return baseNodeTemplate;
	}

	private void detectPatternLayerTwo(TTopologyTemplate tTopologyTemplate) {

	}
}
