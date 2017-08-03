package org.eclipse.winery.repository.patterndetection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.winery.common.ModelUtilities;
import org.eclipse.winery.common.ids.definitions.ServiceTemplateId;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TTopologyTemplate;
import org.eclipse.winery.repository.patterndetection.keywords.Messaging;
import org.eclipse.winery.repository.patterndetection.keywords.OperatingSystem;
import org.eclipse.winery.repository.patterndetection.keywords.Server;
import org.eclipse.winery.repository.patterndetection.keywords.Service;
import org.eclipse.winery.repository.patterndetection.keywords.Storage;
import org.eclipse.winery.repository.patterndetection.keywords.VirtualHardware;
import org.eclipse.winery.repository.patterndetection.model.AbstractTopology;
import org.eclipse.winery.repository.patterndetection.model.PatternComponent;
import org.eclipse.winery.repository.patterndetection.model.RelationshipEdge;
import org.eclipse.winery.repository.patterndetection.model.TNodeTemplateExtended;
import org.eclipse.winery.repository.patterndetection.model.patterns.ElasticLoadBalancerPattern;
import org.eclipse.winery.repository.patterndetection.model.patterns.ElasticQueuePattern;
import org.eclipse.winery.repository.patterndetection.model.patterns.ElasticityManagerPattern;
import org.eclipse.winery.repository.patterndetection.model.patterns.EnvironmentBasedAvailabilityPattern;
import org.eclipse.winery.repository.patterndetection.model.patterns.ExecutionEnvironmentPattern;
import org.eclipse.winery.repository.patterndetection.model.patterns.KeyValueStoragePattern;
import org.eclipse.winery.repository.patterndetection.model.patterns.MessageOrientedMiddlewarePattern;
import org.eclipse.winery.repository.patterndetection.model.patterns.NodeBasedAvailabilityPattern;
import org.eclipse.winery.repository.patterndetection.model.patterns.RelationalDatabasePattern;
import org.eclipse.winery.repository.patterndetection.model.patterntaxonomies.IaaSTaxonomy;
import org.eclipse.winery.repository.patterndetection.model.patterntaxonomies.PaaSTaxonomy;
import org.eclipse.winery.repository.resources.AbstractComponentsResource;
import org.eclipse.winery.repository.resources.servicetemplates.ServiceTemplateResource;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.isomorphism.IsomorphicGraphMapping;
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by marvin.wohlfarth on 12.05.2017.
 */
public class Detection {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String labelServer;
	private String labelService;
	private String labelOS;
	private String labelVirtualHardware;
	private String labelMessaging;
	private String labelStorage;

	private String keywordBeanstalk;
	private String keywordOpenstack;
	private String keywordEC2;
	private String keywordJava;
	private String keywordPython;
	private String keywordApache;
	private String keywordTomcat;
	private String keywordMosquitto;
	private String keywordMongoDB;
	private String keywordMySQL;

	private String patternEnvBasedAvail;
	private String patternElasticLoadBalancer;
	private String patternExecEnv;
	private String patternElasticityManager;
	private String patternElasticQueue;
	private String patternMessageMiddleware;
	private String patternNodeBasedAvail;
	private String patternRelationalDatabase;
	private String patternElasticInfrastructure;
	private String patternElasticPlatform;
	private String patternPaaS;
	private String patternIaaS;
	private String patternPublicCloud;
	private String patternKeyValueStorage;

	private boolean isPaaS;
	private boolean isIaaS;

	private List<String> detectedPattern = new ArrayList<>();
	private List<String> impossiblePattern = new ArrayList<>();

	private List<String> matchedKeywords = new ArrayList<>();
	private List<String> patternProbabilityHigh = new ArrayList<>();
	private List<String> patternProbabilityMedium = new ArrayList<>();
	private List<String> patternProbabilityLow = new ArrayList<>();
	private List<TNodeTemplateExtended> labeledNodeTemplates = new ArrayList<>();
	private PaaSTaxonomy paas = new PaaSTaxonomy();
	private IaaSTaxonomy iaas = new IaaSTaxonomy();
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> paasGraph;
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> iaasGraph;
	private AbstractTopology abstractTopology;
	private TNodeTemplate basisNodeTemplate;

	private ServiceTemplateId serviceTemplateId;

	public Detection(ServiceTemplateId serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		isPaaS = false;
		isIaaS = false;

		labelServer = properties.getProperty("labelServer");
		labelService = properties.getProperty("labelService");
		labelOS = properties.getProperty("labelOS");
		labelVirtualHardware = properties.getProperty("labelVirtualHardware");
		labelMessaging = properties.getProperty("labelMessaging");
		labelStorage = properties.getProperty("labelStorage");

		keywordBeanstalk = properties.getProperty("keywordBeanstalk");
		keywordEC2 = properties.getProperty("keywordEC2");
		keywordOpenstack = properties.getProperty("keywordOpenstack");
		keywordJava = properties.getProperty("keywordJava");
		keywordPython = properties.getProperty("keywordPython");
		keywordApache = properties.getProperty("keywordApache");
		keywordTomcat = properties.getProperty("keywordTomcat");
		keywordMongoDB = properties.getProperty("keywordMongoDB");
		keywordMySQL = properties.getProperty("keywordMySQL");
		keywordMosquitto = properties.getProperty("keywordMosquitto");

		patternElasticLoadBalancer = properties.getProperty("nodeElasticLoadBalancer");
		patternEnvBasedAvail = properties.getProperty("nodeEnvBasedAv");
		patternExecEnv = properties.getProperty("nodeExecEnv");
		patternElasticityManager = properties.getProperty("nodeElasticityManager");
		patternElasticQueue = properties.getProperty("nodeElasticQueue");
		patternNodeBasedAvail = properties.getProperty("nodeNodeBasedAv");
		patternRelationalDatabase = properties.getProperty("nodeRelationalDatabase");
		patternMessageMiddleware = properties.getProperty("nodeMessaging");
		patternElasticPlatform = properties.getProperty("nodeElasticPlatform");
		patternElasticInfrastructure = properties.getProperty("nodeElasticInfrastructure");
		patternIaaS = properties.getProperty("nodeIaaS");
		patternPaaS = properties.getProperty("nodePaaS");
		patternPublicCloud = properties.getProperty("nodePublicCloud");
		patternKeyValueStorage = properties.getProperty("nodeKeyValue");
	}

	public List<List<String>> detectPattern() {
		ServiceTemplateResource serviceTempateResource = (ServiceTemplateResource) AbstractComponentsResource.getComponentInstaceResource(serviceTemplateId);
		TTopologyTemplate tTopologyTemplate = serviceTempateResource.getServiceTemplate().getTopologyTemplate();
		searchForKeywords(tTopologyTemplate);
		detectPattern(tTopologyTemplate);
		List<List<String>> listLists = new ArrayList<>();
		listLists.add(detectedPattern);
		listLists.add(patternProbabilityHigh);
		listLists.add(patternProbabilityMedium);
		listLists.add(patternProbabilityLow);
		listLists.add(impossiblePattern);
		return listLists;
	}

	/**
	 * search for keywords using predefined keywords in enums
	 */
	private void searchForKeywords(TTopologyTemplate tTopologyTemplate) {
		List<TNodeTemplate> tNodeTemplateList = ModelUtilities.getAllNodeTemplates(tTopologyTemplate);
		List<Server> serverList = new ArrayList<>(EnumSet.allOf(Server.class));
		List<Service> serviceList = new ArrayList<>(EnumSet.allOf(Service.class));
		List<VirtualHardware> virtualHardwareList = new ArrayList<>(EnumSet.allOf(VirtualHardware.class));
		List<OperatingSystem> operatingSystemList = new ArrayList<>(EnumSet.allOf(OperatingSystem.class));
		List<Messaging> messagingList = new ArrayList<>(EnumSet.allOf(Messaging.class));
		List<Storage> storageList = new ArrayList<>(EnumSet.allOf(Storage.class));

		for (TNodeTemplate tNodeTemplate : tNodeTemplateList) {
			for (Server server : serverList) {
				if (tNodeTemplate.getName().toLowerCase().contains(server.toString().toLowerCase())) {
					matchedKeywords.add(server.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelServer, server.toString());
					labeledNodeTemplates.add(temp);
					isPaaS = true;
				}
			}
			for (Service service : serviceList) {
				if (tNodeTemplate.getName().toLowerCase().contains(service.toString().toLowerCase())) {
					matchedKeywords.add(service.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelService, service.toString());
					labeledNodeTemplates.add(temp);
					isPaaS = true;
				}
			}
			for (VirtualHardware virtualHardware : virtualHardwareList) {
				if (tNodeTemplate.getName().toLowerCase().contains(virtualHardware.toString().toLowerCase())) {
					matchedKeywords.add(virtualHardware.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelVirtualHardware, virtualHardware.toString());
					labeledNodeTemplates.add(temp);
					isIaaS = true;
				}
			}
			for (OperatingSystem operatingSystem : operatingSystemList) {
				if (tNodeTemplate.getName().toLowerCase().contains(operatingSystem.toString().toLowerCase())) {
					matchedKeywords.add(operatingSystem.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelOS, operatingSystem.toString());
					labeledNodeTemplates.add(temp);
					isPaaS = true;
				}
			}
			for (Messaging messaging : messagingList) {
				if (tNodeTemplate.getName().toLowerCase().contains(messaging.toString().toLowerCase())) {
					matchedKeywords.add(messaging.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelMessaging, messaging.toString());
					labeledNodeTemplates.add(temp);
					isPaaS = true;
				}
			}
			for (Storage storage : storageList) {
				if (tNodeTemplate.getName().toLowerCase().contains(storage.toString().toLowerCase())) {
					matchedKeywords.add(storage.toString());
					TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, labelStorage, storage.toString());
					labeledNodeTemplates.add(temp);
					isPaaS = true;
				}
			}
		}

		// create all taxonomies
		paasGraph = paas.getPaasTaxonomie();
		iaasGraph = iaas.getIaasTaxonomie();

		// set propabilities for possible patterns according to detected keywords
		if (!matchedKeywords.isEmpty()) {
			for (String keyword : matchedKeywords) {
				DefaultWeightedEdge tempEdge;
				if (keyword.equals(keywordBeanstalk)) {
					detectedPattern.add(patternPaaS);
					tempEdge = paasGraph.getEdge(paas.getElasticPlatform(), paas.getEnvBasedAv());
					// set 100% probability of the pattern this edge is pointing to
					paasGraph.setEdgeWeight(tempEdge, 0.99);
					tempEdge = paasGraph.getEdge(paas.getElasticPlatform(), paas.getElasticLoadBalancer());
					paasGraph.setEdgeWeight(tempEdge, 0.99);
					tempEdge = paasGraph.getEdge(paas.getElasticPlatform(), paas.getElasticityManager());
					paasGraph.setEdgeWeight(tempEdge, 0.99);
				} else if (keyword.equals(keywordOpenstack) || keyword.equals(keywordEC2)) {
					tempEdge = iaasGraph.getEdge(iaas.getIaas(), iaas.getElasticInfrastructure());
					iaasGraph.setEdgeWeight(tempEdge, 0.75);
				} else if (keyword.equals(keywordMongoDB) || keyword.equals(keywordMySQL)) {
					tempEdge = paasGraph.getEdge(paas.getPaas(), paas.getRelationalDatabase());
					paasGraph.setEdgeWeight(tempEdge, 0.99);
				} else if (keyword.equals(keywordJava) || keyword.equals(keywordPython) || keyword.equals(keywordTomcat) || keyword.equals(keywordApache)) {
					tempEdge = paasGraph.getEdge(paas.getPaas(), paas.getExecEnvironment());
					paasGraph.setEdgeWeight(tempEdge, 0.75);
				}
			}

			if (isIaaS && !isPaaS) {
				detectedPattern.add(patternIaaS);
				impossiblePattern.add(patternPaaS);
				impossiblePattern.add(patternElasticPlatform);
				Set<DefaultWeightedEdge> edgeSet;
				edgeSet = iaasGraph.edgeSet();
				Iterator iterator2 = edgeSet.iterator();
				while (iterator2.hasNext()) {
					DefaultWeightedEdge edge = (DefaultWeightedEdge) iterator2.next();
					double weight = iaasGraph.getEdgeWeight(edge);
					if (weight == 0.75) {
						patternProbabilityHigh.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 0.5) {
						patternProbabilityMedium.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 0.25) {
						patternProbabilityLow.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 0.99) {
						detectedPattern.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 0.0) {
						impossiblePattern.add(iaasGraph.getEdgeTarget(edge));
					} else if (weight == 1.0) {
						//for all other patterns add low probability
						patternProbabilityLow.add(iaasGraph.getEdgeTarget(edge));
					}
				}
			} else {
				detectedPattern.add(patternPaaS);
				impossiblePattern.add(patternIaaS);
				impossiblePattern.add(patternElasticInfrastructure);
				Set<DefaultWeightedEdge> edgeSet;
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
					} else if (weight == 0.99) {
						detectedPattern.add(paasGraph.getEdgeTarget(edge));
					} else if (weight == 0.0) {
						impossiblePattern.add(paasGraph.getEdgeTarget(edge));
					} else if (weight == 1.0) {
						//for all other patterns add low probability
						patternProbabilityLow.add(paasGraph.getEdgeTarget(edge));
					}
				}
			}
		}
	}

	/**
	 * Create all subgraphs of the topology graph and test for isomorphism with pattern graphs
	 * @param tTopologyTemplate the TOSCA topology will be labeled
	 */
	private void detectPattern(TTopologyTemplate tTopologyTemplate) {
		abstractTopology = new AbstractTopology(tTopologyTemplate, labeledNodeTemplates);

		List<TNodeTemplate> tNodeTemplateList = ModelUtilities.getAllNodeTemplates(tTopologyTemplate);
		List<TRelationshipTemplate> tRelationshipTemplateList = ModelUtilities.getAllRelationshipTemplates(tTopologyTemplate);
		getLowestNode(tNodeTemplateList.get(0), tRelationshipTemplateList);

		Set<TNodeTemplateExtended> allNodes = abstractTopology.getGraph().vertexSet();
		TNodeTemplateExtended baseNodeExtended = new TNodeTemplateExtended();
		Iterator iterator = allNodes.iterator();
		while (iterator.hasNext()) {
			TNodeTemplateExtended temp = (TNodeTemplateExtended) iterator.next();
			if (temp.getNodeTemplate().getId().equals(basisNodeTemplate.getId())) {
				baseNodeExtended = temp;
				break;
			}
		}
		abstractTopology.map(baseNodeExtended);
		//List<DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge>> subgraphList = abstractTopology.createSubgraphs(abstractTopology.getGraph(), baseNodeExtended);
		List<DirectedGraph<PatternComponent, RelationshipEdge>> patternList = new ArrayList<>();
		HashMap<Integer, String> patternNames = new HashMap<>();

		ExecutionEnvironmentPattern executionEnvironmentPattern = new ExecutionEnvironmentPattern();
		NodeBasedAvailabilityPattern nodeBasedAvailabilityPattern = new NodeBasedAvailabilityPattern();
		ElasticityManagerPattern elasticityManagerPattern = new ElasticityManagerPattern();
		ElasticLoadBalancerPattern elasticLoadBalancerPattern = new ElasticLoadBalancerPattern();
		ElasticQueuePattern elasticQueuePattern = new ElasticQueuePattern();
		EnvironmentBasedAvailabilityPattern environmentBasedAvailabilityPattern = new EnvironmentBasedAvailabilityPattern();
		MessageOrientedMiddlewarePattern messageOrientedMiddlewarePattern = new MessageOrientedMiddlewarePattern();
		RelationalDatabasePattern relationalDatabasePattern = new RelationalDatabasePattern();
		KeyValueStoragePattern keyValueStoragePattern = new KeyValueStoragePattern();

		patternNames.put(0, patternExecEnv);
		patternNames.put(1, patternNodeBasedAvail);
		patternNames.put(2, patternElasticityManager);
		patternNames.put(3, patternElasticLoadBalancer);
		patternNames.put(4, patternElasticQueue);
		patternNames.put(5, patternEnvBasedAvail);
		patternNames.put(6, patternMessageMiddleware);
		patternNames.put(7, patternRelationalDatabase);
		patternNames.put(8, patternKeyValueStorage);

		patternList.add(executionEnvironmentPattern.getPatternGraph());
		patternList.add(nodeBasedAvailabilityPattern.getPatternGraph());
		patternList.add(elasticityManagerPattern.getPatternGraph());
		patternList.add(elasticLoadBalancerPattern.getPatternGraph());
		patternList.add(elasticQueuePattern.getPatternGraph());
		patternList.add(environmentBasedAvailabilityPattern.getPatternGraph());
		patternList.add(messageOrientedMiddlewarePattern.getPatternGraph());
		patternList.add(relationalDatabasePattern.getPatternGraph());
		patternList.add(keyValueStoragePattern.getPatternGraph());

		int countIndex = 0;

		for (DirectedGraph<PatternComponent, RelationshipEdge> pattern : patternList) {
			VF2SubgraphIsomorphismInspector<TNodeTemplateExtended, RelationshipEdge> inspector = new VF2SubgraphIsomorphismInspector(abstractTopology.getGraph(), pattern);
			if (inspector.isomorphismExists()) {
				Iterator it = inspector.getMappings();
				while (it.hasNext()) {
					IsomorphicGraphMapping mapping = (IsomorphicGraphMapping) it.next();
					List<Boolean> matched = new ArrayList<>();
					DirectedGraph<TNodeTemplateExtended, RelationshipEdge> originalGraph = new SimpleDirectedGraph<>(RelationshipEdge.class);
					for (PatternComponent p : pattern.vertexSet()) {
						//check if matched subgraph and topology have the same components
						TNodeTemplateExtended v = (TNodeTemplateExtended) mapping.getVertexCorrespondence(p, false);
						if (p.getName().equals(v.getLabel())) {
							matched.add(true);
							originalGraph.addVertex(v);
						} else {
							matched.add(false);
						}
					}
					/*
					for (RelationshipEdge r : pattern.edgeSet()) {
						RelationshipEdge re = (RelationshipEdge) mapping.getEdgeCorrespondence(r, false);
						System.out.println("added " + re.toString() + " as an edge to original graph");
						originalGraph.addEdge((TNodeTemplateExtended) re.getV1(), (TNodeTemplateExtended) re.getV2());
					}*/

					if (!matched.contains(false)) {
						detectedPattern.add(patternNames.get(countIndex));
						VF2GraphIsomorphismInspector<TNodeTemplateExtended, RelationshipEdge> inspector2 = new VF2GraphIsomorphismInspector(abstractTopology.getGraph(), pattern);
						Iterator it2 = inspector2.getMappings();
						while (it2.hasNext()) {
							IsomorphicGraphMapping mapping2 = (IsomorphicGraphMapping) it.next();
						}

						if (patternNames.get(countIndex).equals(patternEnvBasedAvail)) {
							patternProbabilityHigh.add(patternPublicCloud);
						} else if (patternNames.get(countIndex).equals(patternEnvBasedAvail)) {
							impossiblePattern.add(patternNodeBasedAvail);
						} else if (patternNames.get(countIndex).equals(patternNodeBasedAvail)) {
							impossiblePattern.add(patternEnvBasedAvail);
						}
					}
				}
			}
			countIndex++;
		}
	}

	/**
	 * Get the lowest node in a topology, this is the only node with any outgoing relation
	 */
	private void getLowestNode(TNodeTemplate baseNodeTemplate, List<TRelationshipTemplate> tRelationshipTemplateList) {
		List<TRelationshipTemplate> outgoing = new ArrayList<>();
		List<TRelationshipTemplate> incoming = new ArrayList<>();
		for (TRelationshipTemplate tRelationshipTemplate : tRelationshipTemplateList) {
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
		}
	}
}
