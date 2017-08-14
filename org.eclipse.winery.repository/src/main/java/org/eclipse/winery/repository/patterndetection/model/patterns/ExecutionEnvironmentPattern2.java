package org.eclipse.winery.repository.patterndetection.model.patterns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.winery.repository.patterndetection.model.PatternComponent;
import org.eclipse.winery.repository.patterndetection.model.RelationshipEdge;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class ExecutionEnvironmentPattern2 {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String os;
	private String server;
	private String application;
	private String hostedOn;
	private String deployedOn;

	private DirectedGraph<PatternComponent, RelationshipEdge> pattern;

	public ExecutionEnvironmentPattern2() {
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server = properties.getProperty("labelServer");
		os = properties.getProperty("labelOS");
		application = properties.getProperty("labelApp");

		hostedOn = properties.getProperty("relationHostedOn");
		deployedOn = properties.getProperty("relationDeployedOn");

		pattern = new DefaultDirectedGraph<>(RelationshipEdge.class);

		PatternComponent operatingSystem = new PatternComponent(os, 1, 1);
		PatternComponent serverComponent = new PatternComponent(server, 1, Integer.MAX_VALUE);
		PatternComponent appComponent1 = new PatternComponent(application, 1, 1);
		PatternComponent appComponent2 = new PatternComponent(application, 1 ,1);

		pattern.addVertex(operatingSystem);
		pattern.addVertex(serverComponent);
		pattern.addVertex(appComponent1);
		pattern.addVertex(appComponent2);

		pattern.addEdge(serverComponent, operatingSystem, new RelationshipEdge(serverComponent, operatingSystem, hostedOn));
		pattern.addEdge(appComponent1, serverComponent, new RelationshipEdge(appComponent1, serverComponent, deployedOn));
		pattern.addEdge(appComponent2, serverComponent, new RelationshipEdge(appComponent2, serverComponent, deployedOn));
	}

	public DirectedGraph<PatternComponent, RelationshipEdge> getPatternGraph() {
		return pattern;
	}
}
