package org.eclipse.winery.repository.patterndetection.model.patterntaxonomies;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by marvin.wohlfarth on 12.05.2017.
 */
public class PaaSTaxonomy {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String paas;
	private String elasticPlatform;

	private String publicCloud;
	private String envBasedAv;
	private String nodeBasedAv;
	private String elasticityManager;
	private String elasticLoadBalancer;
	private String elasticQueue;
	private String relationalDatabase;
	private String execEnvironment;
	private String messagingMiddleware;


	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> paasTaxonomie;

	public PaaSTaxonomy() {
		paasTaxonomie = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		properties = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		paas = properties.getProperty("nodePaaS");
		elasticPlatform = properties.getProperty("nodeElasticPlatform");
		envBasedAv = properties.getProperty("nodeEnvBasedAv");
		nodeBasedAv = properties.getProperty("nodeNodeBasedAv");
		publicCloud = properties.getProperty("nodePublicCloud");
		elasticityManager = properties.getProperty("nodeElasticityManager");
		elasticLoadBalancer = properties.getProperty("nodeElasticLoadBalancer");
		elasticQueue = properties.getProperty("nodeElasticQueue");
		relationalDatabase = properties.getProperty("nodeRelationalDatabase");
		execEnvironment = properties.getProperty("nodeExecEnv");
		messagingMiddleware = properties.getProperty("nodeMessaging");

		paasTaxonomie.addVertex(paas);
		paasTaxonomie.addVertex(elasticPlatform);
		paasTaxonomie.addVertex(envBasedAv);
		paasTaxonomie.addVertex(nodeBasedAv);
		paasTaxonomie.addVertex(publicCloud);
		paasTaxonomie.addVertex(elasticityManager);
		paasTaxonomie.addVertex(elasticLoadBalancer);
		paasTaxonomie.addVertex(elasticQueue);
		paasTaxonomie.addVertex(relationalDatabase);
		paasTaxonomie.addVertex(execEnvironment);
		paasTaxonomie.addVertex(messagingMiddleware);

		paasTaxonomie.addEdge(paas, elasticPlatform);
		paasTaxonomie.addEdge(elasticPlatform, envBasedAv);
		paasTaxonomie.addEdge(elasticPlatform, nodeBasedAv);
		paasTaxonomie.addEdge(elasticPlatform, elasticityManager);
		paasTaxonomie.addEdge(elasticPlatform, elasticLoadBalancer);
		paasTaxonomie.addEdge(elasticPlatform, elasticQueue);
		paasTaxonomie.addEdge(elasticPlatform, relationalDatabase);
		paasTaxonomie.addEdge(elasticPlatform, messagingMiddleware);
		paasTaxonomie.addEdge(envBasedAv, publicCloud);
		paasTaxonomie.addEdge(paas, nodeBasedAv);
		paasTaxonomie.addEdge(paas, envBasedAv);
		paasTaxonomie.addEdge(paas, messagingMiddleware);
		paasTaxonomie.addEdge(paas, relationalDatabase);
		paasTaxonomie.addEdge(paas, execEnvironment);
		paasTaxonomie.addEdge(messagingMiddleware, elasticQueue);

	}

	public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> getPaasTaxonomie() {
		return paasTaxonomie;
	}

	public String getPaas() {
		return paas;
	}

	public String getElasticPlatform() {
		return elasticPlatform;
	}

	public String getEnvBasedAv() {
		return envBasedAv;
	}

	public String getNodeBasedAv() {
		return nodeBasedAv;
	}

	public String getPublicCloud() {
		return publicCloud;
	}

	public String getElasticityManager() {
		return elasticityManager;
	}

	public String getElasticQueue() {
		return elasticQueue;
	}

	public String getRelationalDatabase() {
		return relationalDatabase;
	}

	public String getExecEnvironment() {
		return execEnvironment;
	}

	public String getMessagingMiddleware() {
		return messagingMiddleware;
	}

	public String getElasticLoadBalancer() {
		return elasticLoadBalancer;
	}

}
