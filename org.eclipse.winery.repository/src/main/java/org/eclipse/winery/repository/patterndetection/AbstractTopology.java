package org.eclipse.winery.repository.patterndetection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.relation.Relation;

import org.eclipse.winery.common.ModelUtilities;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TTopologyTemplate;

import com.sun.org.apache.regexp.internal.RE;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.Subgraph;

/**
 * Created by marvin on 14.05.2017.
 */
public class AbstractTopology {

	private static final String labelVirtualHardware = "virtual_hardware";
	private static final String labelServer = "Server";
	private static final String labelService = "Service";
	private static final String labelOS = "OperatingSystem";

	private DirectedMultigraph<TNodeTemplateExtended, RelationshipEdge> abstractTopology;
	private List<TNodeTemplateExtended> allNodes;

	public AbstractTopology(TTopologyTemplate tTopologyTemplate, List<TNodeTemplateExtended> labeled) {
		abstractTopology = new DirectedMultigraph<>(new ClassBasedEdgeFactory<>(RelationshipEdge.class));
		allNodes = new ArrayList<>();

		List<TNodeTemplate> tNodeTemplateList = ModelUtilities.getAllNodeTemplates(tTopologyTemplate);
		List<TRelationshipTemplate> tRelationshipTemplateList = ModelUtilities.getAllRelationshipTemplates(tTopologyTemplate);

		for (TNodeTemplate tNodeTemplate: tNodeTemplateList) {
			if (!labeled.isEmpty()) {
				for (TNodeTemplateExtended tNodeTemplateExtended : labeled) {
					if (tNodeTemplateExtended.getNodeTemplate().getId().equals(tNodeTemplate.getId())) {
						abstractTopology.addVertex(tNodeTemplateExtended);
						allNodes.add(tNodeTemplateExtended);
					} else {
						TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, "");
						abstractTopology.addVertex(temp);
						allNodes.add(temp);
					}
				}
			} else {
				TNodeTemplateExtended temp = new TNodeTemplateExtended(tNodeTemplate, "");
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
				}
			}
			for (TNodeTemplateExtended node: allNodes) {
				if (node.getNodeTemplate().getId().equals(source.getId())) {
					sourceNode = node;
				}
			}

			abstractTopology.addEdge(sourceNode, targetNode, new RelationshipEdge(sourceNode, targetNode, tRelationshipTemplate.getType().getLocalPart()));
		}
	}

	public void map(TNodeTemplateExtended baseNode) {
		Set<RelationshipEdge> edges = abstractTopology.edgesOf(baseNode);
		String label = baseNode.getLabel();
		switch (label) {
			case labelVirtualHardware:
				Iterator iterator = edges.iterator();
				while (iterator.hasNext()) {
					RelationshipEdge edge = (RelationshipEdge) iterator.next();
					TNodeTemplateExtended source = (TNodeTemplateExtended) edge.getV1();
					String edgeLabel = edge.toString();
					if (source.getLabel().isEmpty()) {
						switch (edgeLabel) {
							case "DeployedOn":
								source.setLabel(labelOS);
								map(source);
							default:

						}
					}
				}
			case labelOS:
				edges = abstractTopology.edgesOf(baseNode);
				Iterator iterator2 = edges.iterator();
				while (iterator2.hasNext()) {
					RelationshipEdge edge = (RelationshipEdge) iterator2.next();
					TNodeTemplateExtended source = (TNodeTemplateExtended) edge.getV1();
					String edgeLabel = edge.toString();
					if (source.getLabel().isEmpty()) {
						switch (edgeLabel) {
							case "DeployedOn":
								source.setLabel(labelService);
							default:

						}
					}
				}
			case labelService:
				//TODO implement
		}


	}

	public static class RelationshipEdge<V> extends DefaultEdge {
		private V v1;
		private V v2;
		private String label;

		public RelationshipEdge(V v1, V v2, String label) {
			this.v1 = v1;
			this.v2 = v2;
			this.label = label;
		}

		public V getV1() {
			return v1;
		}

		public V getV2() {
			return v2;
		}

		public String toString() {
			return label;
		}
	}

	public DirectedMultigraph<TNodeTemplateExtended, RelationshipEdge> getGraph() {
		return abstractTopology;
	}
}
