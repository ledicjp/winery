package org.eclipse.winery.repository.patterndetection.pattern;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * Created by marvin on 15.05.2017.
 */
public class ExecutionEnvironmentPattern {

	private static final String infrastructure = "Infrastructure";
	private static final String os = "OperatingSystem";
	private static final String service = "Service";
	private static final String server = "Server";
	private static final String hostedOn = "HostedOn";


	private DirectedMultigraph<PatternComponent, RelationshipEdge> pattern;

	public ExecutionEnvironmentPattern() {
		pattern = new DirectedMultigraph<>(RelationshipEdge.class);


		PatternComponent infrastructureComponent = new PatternComponent(infrastructure, 1, 1);
		PatternComponent operatingSystem = new PatternComponent(os, 1, 1);
		PatternComponent serviceComponent = new PatternComponent(service, 1, Integer.MAX_VALUE);
		PatternComponent serverComponent = new PatternComponent(server, 1, Integer.MAX_VALUE);

		pattern.addVertex(operatingSystem);
		pattern.addVertex(infrastructureComponent);
		pattern.addVertex(serverComponent);
		pattern.addVertex(serviceComponent);

		pattern.addEdge(operatingSystem, infrastructureComponent, new RelationshipEdge(operatingSystem, infrastructureComponent, hostedOn));
		pattern.addEdge(serverComponent, operatingSystem, new RelationshipEdge(serverComponent, operatingSystem, hostedOn));
		pattern.addEdge(serviceComponent, operatingSystem, new RelationshipEdge(serviceComponent, operatingSystem, hostedOn));

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
}
