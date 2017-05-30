package org.eclipse.winery.repository.patterndetection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
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
import org.eclipse.winery.repository.patterndetection.pattern.ExecutionEnvironmentPattern;
import org.eclipse.winery.repository.patterndetection.pattern.PatternComponent;
import org.eclipse.winery.repository.patterndetection.patterntaxonomies.IaaSTaxonomie;
import org.eclipse.winery.repository.patterndetection.patterntaxonomies.PaaSTaxonomie;
import org.eclipse.winery.repository.resources.AbstractComponentsResource;
import org.eclipse.winery.repository.resources.servicetemplates.ServiceTemplateResource;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedSubgraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by marvin on 12.05.2017.
 */
public class Detection {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String labelServer;
	private String labelService;
	private String labelOS;
	private String labelVirtualHardware;

	private String keywordBeanstalk;
	private String keywordOpenstack;
	private String keywordEC2;
	private String keywordJava;
	private String keywordPython;
	private String keywordApache;
	private String keywordTomcat;

	private boolean isPaaS = false;
	private boolean isIaaS = false;

	private List<String> detectedPattern = new ArrayList<>();
	private List<String> impossiblePattern = new ArrayList<>();

	private List<String> matchedKeywords = new ArrayList<>();
	private List<String> patternProbabilityHigh = new ArrayList<>();
	private List<String> patternProbabilityMedium = new ArrayList<>();
	private List<String> patternProbabilityLow = new ArrayList<>();
	private List<TNodeTemplateExtended> labeledNodeTemplates = new ArrayList<>();
	private PaaSTaxonomie paas = new PaaSTaxonomie();
	private IaaSTaxonomie iaas = new IaaSTaxonomie();
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> paasGraph;
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> iaasGraph;
	private AbstractTopology abstractTopology;
	private TNodeTemplate basisNodeTemplate;

	private List<DirectedGraph<PatternComponent, ExecutionEnvironmentPattern.RelationshipEdge>> patternList;

	public Detection(ServiceTemplateId serviceTemplateId) {
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelServer = properties.getProperty("labelServer");
		labelService = properties.getProperty("labelService");
		labelOS = properties.getProperty("labelOS");
		labelVirtualHardware = properties.getProperty("labelVirtualHardware");
		keywordBeanstalk = properties.getProperty("keywordBeanstalk");
		keywordEC2 = properties.getProperty("keywordEC2");
		keywordOpenstack = properties.getProperty("keywordOpenstack");
		keywordJava = properties.getProperty("keywordJava");
		keywordPython = properties.getProperty("keywordPython");
		keywordApache = properties.getProperty("keywordApache");
		keywordTomcat = properties.getProperty("keywordTomcat");

		patternList = new ArrayList<>();
		fillPatternList();

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

		for (TNodeTemplate tNodeTemplate: tNodeTemplateList) {
			for (Server server: serverList) {
				if (tNodeTemplate.getName().toLowerCase().contains(server.toString().toLowerCase())) {
					matchedKeywords.add(server.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelServer, server.toString());
					labeledNodeTemplates.add(temp);
				}
			}
			for (Service service: serviceList) {
				if (tNodeTemplate.getName().toLowerCase().contains(service.toString().toLowerCase())) {
					matchedKeywords.add(service.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelService, service.toString());
					labeledNodeTemplates.add(temp);
				}
			}
			for (VirtualHardware virtualHardware: virtualHardwareList) {
				if (tNodeTemplate.getName().toLowerCase().contains(virtualHardware.toString().toLowerCase())) {
					matchedKeywords.add(virtualHardware.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelVirtualHardware, virtualHardware.toString());
					labeledNodeTemplates.add(temp);
				}
			}
			for (OperatingSystem operatingSystem: operatingSystemList) {
				if (tNodeTemplate.getName().toLowerCase().contains(operatingSystem.toString().toLowerCase())) {
					matchedKeywords.add(operatingSystem.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelOS, operatingSystem.toString());
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
				if (keyword == keywordBeanstalk) {
					tempEdge = paasGraph.getEdge(paas.getElasticPlatform(), paas.getEnvironmentBasedAvailability());
					// set 100% probability of the pattern this edge is pointing to
					paasGraph.setEdgeWeight(tempEdge, 0.75);
					tempEdge = paasGraph.getEdge(paas.getElasticPlatform(), paas.getElasticLoadBalancer());
					paasGraph.setEdgeWeight(tempEdge, 0.75);
					detectedPattern.add(Strings.getString("nodeEnvBasedAv"));
					detectedPattern.add(Strings.getString("nodePaaS"));
					detectedPattern.add(Strings.getString("nodeElasticLoadBalancer"));
					isPaaS = true;
					// TODO bottom-up-search & set probabilities for patterns depending on
				} else if (keyword == keywordOpenstack) {

				} else if (keyword == keywordEC2) {

				} else if (keyword == keywordJava || keyword == keywordPython) {
					isPaaS = true;
					tempEdge = paasGraph.getEdge(paas.getElasticPlatform(), paas.getEnvironmentBasedAvailability());
					paasGraph.setEdgeWeight(tempEdge, 0.75);
				} else if (keyword == keywordApache || keyword == keywordTomcat) {
					isPaaS = true;
				}
			}

			Set<DefaultWeightedEdge> edgeSet;
			if (isPaaS) {
				 edgeSet = paasGraph.edgeSet();
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
					} else if (weight == 1.0) {
						detectedPattern.add(paasGraph.getEdgeTarget(edge));
					} else if (weight == 0.0) {
						impossiblePattern.add(paasGraph.getEdgeTarget(edge));
					}
				}
			} else if (isIaaS) {
				edgeSet = iaasGraph.edgeSet();
				Iterator iterator = edgeSet.iterator();
				while (iterator.hasNext()) {
					DefaultWeightedEdge edge = (DefaultWeightedEdge) iterator.next();
					double weight = iaasGraph.getEdgeWeight(edge);
					if (weight == 0.75) {
						patternProbabilityHigh.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 0.5) {
						patternProbabilityMedium.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 0.25) {
						patternProbabilityLow.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 1.0) {
						detectedPattern.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 0.0) {
						impossiblePattern.add(iaasGraph.getEdgeTarget(edge));
					}
				}
			}
		}
	}

	private void detectPatternLayerOne(TTopologyTemplate tTopologyTemplate) {
		abstractTopology = new AbstractTopology(tTopologyTemplate, labeledNodeTemplates);

		List<TNodeTemplate> tNodeTemplateList = ModelUtilities.getAllNodeTemplates(tTopologyTemplate);
		List<TRelationshipTemplate> tRelationshipTemplateList = ModelUtilities.getAllRelationshipTemplates(tTopologyTemplate);
		//TODO multiple baseNodes
		getLowestNode(tNodeTemplateList.get(0), tRelationshipTemplateList);
		System.out.println("Base: " + basisNodeTemplate.getName());

		Set<TNodeTemplateExtended> allNodes = abstractTopology.getGraph().vertexSet();
		TNodeTemplateExtended baseNodeExtended = new TNodeTemplateExtended();
		Iterator iterator = allNodes.iterator();
		while (iterator.hasNext()) {
			TNodeTemplateExtended temp = (TNodeTemplateExtended) iterator.next();
			if (temp.getNodeTemplate().getId().equals(basisNodeTemplate.getId())) {
				baseNodeExtended = temp;
			}
		}
		abstractTopology.map(baseNodeExtended);
		List<DirectedSubgraph<TNodeTemplateExtended, AbstractTopology.RelationshipEdge>> subgraphList = abstractTopology.createSubgraphs(abstractTopology.getGraph() ,baseNodeExtended);

		for (DirectedSubgraph<TNodeTemplateExtended, AbstractTopology.RelationshipEdge> subgraph: subgraphList) {
			VF2SubgraphIsomorphismInspector<TNodeTemplateExtended, AbstractTopology.RelationshipEdge> inspector = new VF2SubgraphIsomorphismInspector(subgraph, new ExecutionEnvironmentPattern().getPatternGraph());
			if (inspector.isomorphismExists()) {
				detectedPattern.add(properties.getProperty("nodeExecEnv"));
			}
		}
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
	private void getLowestNode(TNodeTemplate baseNodeTemplate, List<TRelationshipTemplate> tRelationshipTemplateList) {
		List<TRelationshipTemplate> outgoing = new ArrayList<>();
		List<TRelationshipTemplate> incoming = new ArrayList<>();
		for (TRelationshipTemplate tRelationshipTemplate: tRelationshipTemplateList) {
			if (baseNodeTemplate.equals((tRelationshipTemplate.getSourceElement().getRef()))) {
				outgoing.add(tRelationshipTemplate);
				getLowestNode((TNodeTemplate) tRelationshipTemplate.getTargetElement().getRef(), tRelationshipTemplateList);
				break;
			} else {
				incoming.add(tRelationshipTemplate);
			}
		}
		if (outgoing.isEmpty()) {
			basisNodeTemplate = baseNodeTemplate;
			System.out.println(basisNodeTemplate.getId());
		}
	}

	/**
	 *
	 * @param tTopologyTemplate
	 */
	private void detectPatternLayerTwo(TTopologyTemplate tTopologyTemplate) {

	}

	private void fillPatternList() {
		ExecutionEnvironmentPattern executionEnvironmentPattern = new ExecutionEnvironmentPattern();
		patternList.add(executionEnvironmentPattern.getPatternGraph());
	}
}
