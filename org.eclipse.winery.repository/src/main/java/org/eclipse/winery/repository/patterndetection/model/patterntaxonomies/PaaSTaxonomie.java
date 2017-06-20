package org.eclipse.winery.repository.patterndetection.model.patterntaxonomies;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by marvin on 12.05.2017.
 */
public class PaaSTaxonomie {

	private static final String propertiesFilename = "patterndetection.properties";

	private String paaS;
	private String elasticPlatform;
	private String nodeBasedAvailability;
	private String environmentBasedAvailability;
	private String elasticLoadBalancer;

	private Properties properties;

	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> paasTaxonomie;

	public PaaSTaxonomie() {
		properties = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		paaS = properties.getProperty("nodePaaS");
		elasticPlatform = properties.getProperty("nodeElasticPlatform");
		nodeBasedAvailability = properties.getProperty("nodeNodeBasedAv");
		environmentBasedAvailability = properties.getProperty("nodeEnvBasedAv");
		elasticLoadBalancer = properties.getProperty("nodeElasticLoadBalancer");

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
