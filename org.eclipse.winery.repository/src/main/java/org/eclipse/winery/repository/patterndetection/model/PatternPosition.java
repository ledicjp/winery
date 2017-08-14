package org.eclipse.winery.repository.patterndetection.model;

import org.jgrapht.DirectedGraph;

public class PatternPosition {

	private String patternName;
	private DirectedGraph<TNodeTemplateExtended, RelationshipEdge> nodesOfOriginGraph;

	public PatternPosition(String patternName, DirectedGraph<TNodeTemplateExtended, RelationshipEdge> nodesOfOriginGraph) {
		this.patternName = patternName;
		this.nodesOfOriginGraph = nodesOfOriginGraph;
	}

	public String getPatternName() {
		return patternName;
	}

	public DirectedGraph<TNodeTemplateExtended, RelationshipEdge> getNodesOfOriginGraph() {
		return nodesOfOriginGraph;
	}
}
