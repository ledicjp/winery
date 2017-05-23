package org.eclipse.winery.repository.patterndetection.patterntaxonomies;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by marvin on 14.05.2017.
 */
public class IaaSTaxonomie {

	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> iaasTaxonomie;

	public IaaSTaxonomie() {
		iaasTaxonomie = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

	}

	public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> getIaasTaxonomie() {
		return iaasTaxonomie;
	}
}
