package org.eclipse.winery.repository.patterndetection.model.patterntaxonomies;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by marvin on 14.05.2017.
 */
public class IaaSTaxonomie {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String iaas;
	private String elasticInfrastructure;

	private String envBasedAv;
	private String nodeBasedAv;

	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> iaasTaxonomie;

	public IaaSTaxonomie() {
		iaasTaxonomie = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		properties = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		iaas = properties.getProperty("nodeIaaS");
		elasticInfrastructure = properties.getProperty("nodeElasticInfrastructure");
		envBasedAv = properties.getProperty("nodeEnvBasedAv");
		nodeBasedAv = properties.getProperty("nodeNodeBasedAv");

		iaasTaxonomie.addVertex(iaas);
		iaasTaxonomie.addVertex(elasticInfrastructure);
		iaasTaxonomie.addVertex(envBasedAv);
		iaasTaxonomie.addVertex(nodeBasedAv);

		iaasTaxonomie.addEdge(iaas, elasticInfrastructure);
		iaasTaxonomie.addEdge(elasticInfrastructure, envBasedAv);
		iaasTaxonomie.addEdge(elasticInfrastructure, nodeBasedAv);
	}

	public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> getIaasTaxonomie() {
		return iaasTaxonomie;
	}

	public String getIaas() {
		return iaas;
	}

	public String getElasticInfrastructure() {
		return elasticInfrastructure;
	}

	public String getEnvBasedAv() {
		return envBasedAv;
	}

	public String getNodeBasedAv() {
		return nodeBasedAv;
	}



}
