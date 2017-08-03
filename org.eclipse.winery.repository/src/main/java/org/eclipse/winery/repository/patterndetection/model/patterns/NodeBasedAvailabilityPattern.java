package org.eclipse.winery.repository.patterndetection.model.patterns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.winery.repository.patterndetection.model.PatternComponent;
import org.eclipse.winery.repository.patterndetection.model.RelationshipEdge;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Created by marvin.wohlfarth on 03.07.2017.
 */
public class NodeBasedAvailabilityPattern {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String virtualHardware;
	private String os;
	private String server;
	private String application;
	private String hostedOn;
	private String connectsTo;
	private String deployedOn;

	private DirectedGraph<PatternComponent, RelationshipEdge> pattern;

	public NodeBasedAvailabilityPattern() {
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server = properties.getProperty("labelServer");
		os = properties.getProperty("labelOS");
		virtualHardware = properties.getProperty("labelVirtualHardware");
		application = properties.getProperty("labelApp");

		hostedOn = properties.getProperty("relationHostedOn");
		connectsTo = properties.getProperty("relationConnectsTo");
		deployedOn = properties.getProperty("relationDeployedOn");

		pattern = new DefaultDirectedGraph<>(RelationshipEdge.class);

		PatternComponent virtualHardwareComponent = new PatternComponent(virtualHardware, 1, 1);
		PatternComponent operatingSystem1 = new PatternComponent(os, 1, Integer.MAX_VALUE);
		PatternComponent operatingSystem2 = new PatternComponent(os, 1, Integer.MAX_VALUE);
		PatternComponent serverComponent1 = new PatternComponent(server, 1, Integer.MAX_VALUE);
		PatternComponent serverComponent2 = new PatternComponent(server, 1, Integer.MAX_VALUE);
		PatternComponent appComponent1 = new PatternComponent(application, 1, Integer.MAX_VALUE);
		PatternComponent appComponent2 = new PatternComponent(application, 1, Integer.MAX_VALUE);

		pattern.addVertex(operatingSystem1);
		pattern.addVertex(operatingSystem2);
		pattern.addVertex(virtualHardwareComponent);
		pattern.addVertex(serverComponent1);
		pattern.addVertex(serverComponent2);
		pattern.addVertex(appComponent1);
		pattern.addVertex(appComponent2);

		pattern.addEdge(operatingSystem1, virtualHardwareComponent, new RelationshipEdge(operatingSystem1, virtualHardwareComponent, hostedOn));
		pattern.addEdge(operatingSystem2, virtualHardwareComponent, new RelationshipEdge(operatingSystem2, virtualHardwareComponent, hostedOn));

		pattern.addEdge(serverComponent1, operatingSystem1, new RelationshipEdge(serverComponent1, operatingSystem1, hostedOn));
		pattern.addEdge(serverComponent2, operatingSystem2, new RelationshipEdge(serverComponent2, operatingSystem2, hostedOn));

		pattern.addEdge(appComponent1, serverComponent1, new RelationshipEdge(appComponent1, operatingSystem1, deployedOn));
		pattern.addEdge(appComponent2, serverComponent2, new RelationshipEdge(appComponent2, operatingSystem2, deployedOn));

		//pattern.addEdge(appComponent1, appComponent2, new RelationshipEdge(appComponent1, appComponent2, connectsTo));

	}

	public DirectedGraph<PatternComponent, RelationshipEdge> getPatternGraph() {
		return pattern;
	}
}
