package org.eclipse.winery.repository.patterndetection.model;

import org.jgrapht.graph.DefaultEdge;

/**
 * Created by marvin on 09.06.2017.
 */
public class RelationshipEdge<V> extends DefaultEdge {
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
