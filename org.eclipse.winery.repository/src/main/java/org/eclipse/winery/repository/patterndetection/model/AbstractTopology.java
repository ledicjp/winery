package org.eclipse.winery.repository.patterndetection.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.winery.common.ModelUtilities;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TTopologyTemplate;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DirectedSubgraph;

/**
 * Created by marvin.wohlfarth on 14.05.2017.
 */
public class AbstractTopology {

	private static final String propertiesFilename = "patterndetection.properties";

	private String labelVirtualHardware;
	private String labelServer;
	private String labelService;
	private String labelOS;
	private String labelApp;
	private String labelMessaging;
	private String labelStorage;

	private String relationDeployedOn;
	private String relationHostedOn;
	private String relationDependsOn;
	private String relationConnectsTo;

	private DirectedGraph<TNodeTemplateExtended, RelationshipEdge> abstractTopology;
	private List<TNodeTemplateExtended> allNodes;
	private List<DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge>> subgraphList;
	private List<TNodeTemplateExtended > visitedNodes;

	private Properties properties;

	public AbstractTopology(TTopologyTemplate tTopologyTemplate, List<TNodeTemplateExtended> labeled) {
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		labelVirtualHardware = properties.getProperty("labelVirtualHardware");
		labelServer = properties.getProperty("labelServer");
		labelService = properties.getProperty("labelService");
		labelOS = properties.getProperty("labelOS");
		labelApp = properties.getProperty("labelApp");
		labelStorage = properties.getProperty("labelStorage");
		labelMessaging = properties.getProperty("labelMessaging");

		relationDeployedOn = properties.getProperty("relationDeployedOn");
		relationHostedOn = properties.getProperty("relationHostedOn");
		relationDependsOn = properties.getProperty("relationDependsOn");
		relationConnectsTo = properties.getProperty("relationConnectsTo");

		abstractTopology = new DefaultDirectedGraph<>((RelationshipEdge.class));
		subgraphList = new ArrayList<>();
		allNodes = new ArrayList<>();
		visitedNodes = new ArrayList<>();

		List<TNodeTemplate> tNodeTemplateList = ModelUtilities.getAllNodeTemplates(tTopologyTemplate);
		List<TRelationshipTemplate> tRelationshipTemplateList = ModelUtilities.getAllRelationshipTemplates(tTopologyTemplate);

		all:
		for (TNodeTemplate tNodeTemplate: tNodeTemplateList) {
			if (!labeled.isEmpty()) {
				label:
				for (TNodeTemplateExtended tNodeTemplateExtended : labeled) {
					// if this node is already labeled
					if (tNodeTemplateExtended.getNodeTemplate().getId().equals(tNodeTemplate.getId()) && !allNodes.contains(tNodeTemplateExtended)) {
						abstractTopology.addVertex(tNodeTemplateExtended);
						allNodes.add(tNodeTemplateExtended);
						continue all;
					}
				}
				TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, "", "");
				abstractTopology.addVertex(temp);
				allNodes.add(temp);
			} else {
				TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, "", "");
				abstractTopology.addVertex(temp);
				allNodes.add(temp);
			}
		}

		for (TRelationshipTemplate tRelationshipTemplate: tRelationshipTemplateList) {
			TNodeTemplate target = (TNodeTemplate) tRelationshipTemplate.getTargetElement().getRef();
			TNodeTemplate source = (TNodeTemplate) tRelationshipTemplate.getSourceElement().getRef();
			TNodeTemplateExtended targetNode = new TNodeTemplateExtended();
			TNodeTemplateExtended sourceNode = new TNodeTemplateExtended();
			for (TNodeTemplateExtended node: allNodes) {
				if (node.getNodeTemplate().getId().equals(target.getId())) {
					targetNode = node;
				} else if (node.getNodeTemplate().getId().equals(source.getId())) {
					sourceNode = node;
				}
			}
			abstractTopology.addEdge(sourceNode, targetNode, new RelationshipEdge(sourceNode, targetNode, tRelationshipTemplate.getType().getLocalPart()));
		}
	}

	/**
	 * Map the whole TOSCA topology with labels, according to Figure 4.9 in the bachelor's thesis
	 * @param baseNode: the lowest node with no outgoing relations in the topology
	 *
	 */
	public void map(TNodeTemplateExtended baseNode) {
		Set<RelationshipEdge> edges = abstractTopology.incomingEdgesOf(baseNode);
		if  (edges.isEmpty()) {
			return;
		}
		String label = baseNode.getLabel();
		if (label.equals(labelVirtualHardware)) {
			Iterator iterator = edges.iterator();
			while (iterator.hasNext()) {
				RelationshipEdge edge = (RelationshipEdge) iterator.next();
				TNodeTemplateExtended source = (TNodeTemplateExtended) edge.getV1();
				String edgeLabel = edge.toString();
				if (source.getLabel().isEmpty()) {
					if (edgeLabel.equals(relationHostedOn)) {
						source.setLabel(labelOS);
						map(source);
					} else if (edgeLabel.equals(relationConnectsTo)) {
						source.setLabel(labelService);
						map(source);
					}
				} else {
					map(source);
				}
			}
			return;
		} else if (label.equals(labelOS)) {
			Iterator iterator = edges.iterator();
			while (iterator.hasNext()) {
				RelationshipEdge edge = (RelationshipEdge) iterator.next();
				TNodeTemplateExtended source = (TNodeTemplateExtended) edge.getV1();
				String edgeLabel = edge.toString();
				if (source.getLabel().isEmpty()) {
					if (edgeLabel.equals(relationDeployedOn)) {
						source.setLabel(labelApp);
						map(source);
					} else if (edgeLabel.equals(relationHostedOn)) {
						//probability for a service is very high because messaging, storage and server components are mostly labeled during keywords
						source.setLabel(labelService);
						map(source);
					}
				} else {
					map(source);
				}
			}
			return;
		} else if (label.equals(labelService)) {
			Iterator iterator = edges.iterator();
			while (iterator.hasNext()) {
				RelationshipEdge edge = (RelationshipEdge) iterator.next();
				TNodeTemplateExtended source = (TNodeTemplateExtended) edge.getV1();
				String edgeLabel = edge.toString();
				if (source.getLabel().isEmpty()) {
					if (edgeLabel.equals(relationDependsOn)) {
						//probability that the unlabeled node is an application is very high, because server would be detected + labeled during keyword search,
						// probability that service depends on service is very low
						source.setLabel(labelApp);
						map(source);
					}
				} else {
					map(source);
				}
			}
			return;
		} else if (label.equals(labelServer)) {
			Iterator iterator = edges.iterator();
			while (iterator.hasNext()) {
				RelationshipEdge edge = (RelationshipEdge) iterator.next();
				TNodeTemplateExtended source = (TNodeTemplateExtended) edge.getV1();
				String edgeLabel = edge.toString();
				if (source.getLabel().isEmpty()) {
					if (edgeLabel.equals(relationHostedOn)) {
						source.setLabel(labelService);
						map(source);
					} else if (edgeLabel.equals(relationDeployedOn)) {
						source.setLabel(labelApp);
						map(source);
					}
				} else {
					map(source);
				}
			}
			return;
		} else if (label.equals(labelApp)) {
			Iterator iterator = edges.iterator();
			while (iterator.hasNext()) {
				RelationshipEdge edge = (RelationshipEdge) iterator.next();
				TNodeTemplateExtended source = (TNodeTemplateExtended) edge.getV1();
				String edgeLabel = edge.toString();
				if (source.getLabel().isEmpty()) {
					if (edgeLabel.equals(relationConnectsTo)) {
						source.setLabel(labelService);
						map(source);
					}
				} else {
					map(source);
				}
			}
			return;
		}
	}

	/**
	 * Create all possible subgraphs of the mapped topology
	 * @param baseGraph the labeled topology graph
	 * @param baseNode the lowest node in the graph with no outgoing relations
	 * @return a list containing all subgraphs
	 */
	public List<DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge>> createSubgraphs(DirectedGraph<TNodeTemplateExtended, RelationshipEdge> baseGraph, TNodeTemplateExtended baseNode) {
		for (TNodeTemplateExtended node: baseGraph.vertexSet()) {
			DirectedGraph<TNodeTemplateExtended, RelationshipEdge> tempGraph = new DefaultDirectedGraph<>(RelationshipEdge.class);
			tempGraph.addVertex(node);
			DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge> subgraph = new DirectedSubgraph<>(baseGraph, tempGraph.vertexSet());
			subgraphList.add(subgraph);
		}
		DirectedGraph<TNodeTemplateExtended, RelationshipEdge> tempGraph = new DefaultDirectedGraph<>(RelationshipEdge.class);
		tempGraph.addVertex(baseNode);
		DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge> subgraph = new DirectedSubgraph<>(baseGraph, tempGraph.vertexSet(), tempGraph.edgeSet());
		DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge> subgraphOriginal = new DirectedSubgraph<>(baseGraph);
		subgraphList.add(subgraphOriginal);
		getAllPossibleSubgraphs(baseNode, baseGraph, subgraph);
		return subgraphList;
	}

	private void getAllPossibleSubgraphs(TNodeTemplateExtended base, DirectedGraph<TNodeTemplateExtended, RelationshipEdge> baseGraph, DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge> lastSubgraph) {
		if (visitedNodes.contains(base)) {
			return;
		}
		HashMap<TNodeTemplateExtended, RelationshipEdge> localList = new HashMap<>();
		for (RelationshipEdge edge: baseGraph.edgesOf(base)) {
			if (baseGraph.edgesOf(base).isEmpty()) {
				visitedNodes.add(base);
				return;
			}
			if (edge.getV2() != base) {
				if (visitedNodes.contains(edge.getV2())) {
					visitedNodes.add(base);
				} else {
					TNodeTemplateExtended target = (TNodeTemplateExtended) edge.getV2();
					DirectedGraph<TNodeTemplateExtended, RelationshipEdge> tempGraph = new DefaultDirectedGraph<>(RelationshipEdge.class);
					tempGraph.addVertex(base);
					tempGraph.addVertex(target);
					tempGraph.addEdge(base, target, edge);
					DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge> subgraph = new DirectedSubgraph<>(baseGraph, tempGraph.vertexSet(), tempGraph.edgeSet());
					lastSubgraph.addVertex(target);
					lastSubgraph.addEdge(base, target, edge);
					subgraphList.add(subgraph);
					visitedNodes.add(base);
					localList.put(target, edge);
					getAllPossibleSubgraphs((TNodeTemplateExtended) edge.getV2(), baseGraph, lastSubgraph);
				}
			} else if (edge.getV1() != base) {
				if (visitedNodes.contains(edge.getV1())) {
					visitedNodes.add(base);

				} else {
					TNodeTemplateExtended target = (TNodeTemplateExtended) edge.getV1();
					DirectedGraph<TNodeTemplateExtended, RelationshipEdge> tempGraph = new DefaultDirectedGraph<>(RelationshipEdge.class);
					tempGraph.addVertex(base);
					tempGraph.addVertex(target);
					tempGraph.addEdge(target, base, edge);
					DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge> subgraph = new DirectedSubgraph<>(baseGraph, tempGraph.vertexSet(), tempGraph.edgeSet());
					lastSubgraph.addVertex(target);
					lastSubgraph.addEdge(target, base, edge);
					subgraphList.add(subgraph);
					visitedNodes.add(base);
					localList.put(target, edge);
					getAllPossibleSubgraphs((TNodeTemplateExtended) edge.getV1(), baseGraph, lastSubgraph);
				}
			}
		}
		if (localList.size() > 1) {
			DirectedGraph<TNodeTemplateExtended, RelationshipEdge> tempGraph = new DefaultDirectedGraph<>(RelationshipEdge.class);
			tempGraph.addVertex(base);
			for (TNodeTemplateExtended tNodeTemplateExtended: localList.keySet()) {
				TNodeTemplateExtended node = tNodeTemplateExtended;
				RelationshipEdge edge = localList.get(tNodeTemplateExtended);
				tempGraph.addVertex(node);
				if (edge.getV1().equals(node)) {
					tempGraph.addEdge(node, base, edge);
				} else {
					tempGraph.addEdge(base, node, edge);
				}
			}
			DirectedSubgraph<TNodeTemplateExtended, RelationshipEdge> subgraph = new DirectedSubgraph<>(baseGraph, tempGraph.vertexSet(), tempGraph.edgeSet());
			subgraphList.add(subgraph);
		}

	}

	public DirectedGraph<TNodeTemplateExtended, RelationshipEdge> getGraph() {
		return abstractTopology;
	}

}
