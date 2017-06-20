package org.eclipse.winery.repository.patterndetection.model.patterns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.winery.repository.patterndetection.model.RelationshipEdge;
import org.eclipse.winery.repository.patterndetection.model.PatternComponent;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Created by marvin on 15.05.2017.
 */
public class ExecutionEnvironmentPattern {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String virtualHardware;
	private String os;
	private String service;
	private String server;
	private String hostedOn;

	private DirectedGraph<PatternComponent, RelationshipEdge> pattern;

	public ExecutionEnvironmentPattern() {
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server = properties.getProperty("labelServer");
		service = properties.getProperty("labelService");
		os = properties.getProperty("labelOS");
		virtualHardware = properties.getProperty("labelVirtualHardware");
		hostedOn = properties.getProperty("relationHostedOn");

		pattern = new DefaultDirectedGraph<>(RelationshipEdge.class);

		//PatternComponent infrastructureComponent = new PatternComponent(virtualHardware, 1, 1);
		PatternComponent operatingSystem = new PatternComponent(os, 1, 1);
		PatternComponent serviceComponent = new PatternComponent(service, 1, Integer.MAX_VALUE);
		PatternComponent serverComponent = new PatternComponent(server, 1, Integer.MAX_VALUE);

		pattern.addVertex(operatingSystem);
		//pattern.addVertex(infrastructureComponent);
		pattern.addVertex(serverComponent);
		//pattern.addVertex(serviceComponent);

		//pattern.addEdge(operatingSystem, infrastructureComponent, new RelationshipEdge(operatingSystem, infrastructureComponent, hostedOn));
		pattern.addEdge(serverComponent, operatingSystem, new RelationshipEdge(serverComponent, operatingSystem, hostedOn));
		//pattern.addEdge(serviceComponent, operatingSystem, new RelationshipEdge(serviceComponent, operatingSystem, hostedOn));

	}

	public DirectedGraph<PatternComponent, RelationshipEdge> getPatternGraph() {
		return pattern;
	}
}
