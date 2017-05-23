package org.eclipse.winery.repository.patterndetection.patterntaxonomies;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by marvin on 12.05.2017.
 */
public class PaaSTaxonomie {

	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> paasTaxonomie;
	private String paaS = "Platform-as-a-Service";
	private String elasticPlatform = "Elastic Platform";
	private String environmentBasedAvailability = "Environment-based Availability";
	private String nodeBasedAvailability = "Node-based Availability";
	private String elasticLoadBalancer = "elasticLoadBalancer";

	public PaaSTaxonomie() {
		paasTaxonomie = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		paasTaxonomie.addVertex(paaS);
		paasTaxonomie.addVertex(elasticPlatform);
		paasTaxonomie.addVertex(elasticLoadBalancer);
		paasTaxonomie.addVertex(environmentBasedAvailability);
		paasTaxonomie.addVertex(nodeBasedAvailability);

		paasTaxonomie.addEdge(paaS, elasticPlatform);
		paasTaxonomie.addEdge(elasticPlatform, elasticLoadBalancer);
		paasTaxonomie.addEdge(elasticPlatform, environmentBasedAvailability);
		paasTaxonomie.addEdge(elasticPlatform, nodeBasedAvailability);

	}

	public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> getPaasTaxonomie() {
		return paasTaxonomie;
	}

	public String getPaaS() {
		return paaS;
	}

	public String getElasticPlatform() {
		return elasticPlatform;
	}

	public String getEnvironmentBasedAvailability() {
		return environmentBasedAvailability;
	}

	public String getNodeBasedAvailability() {
		return nodeBasedAvailability;
	}

	public String getElasticLoadBalancer() {
		return elasticLoadBalancer;
	}
}
