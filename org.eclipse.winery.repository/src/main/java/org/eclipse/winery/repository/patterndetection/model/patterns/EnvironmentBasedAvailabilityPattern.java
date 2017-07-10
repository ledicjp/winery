package org.eclipse.winery.repository.patterndetection.model.patterns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.winery.repository.patterndetection.model.RelationshipEdge;
import org.eclipse.winery.repository.patterndetection.model.PatternComponent;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Created by marvin.wohlfarth on 08.06.2017.
 */
public class EnvironmentBasedAvailabilityPattern {
	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String virtualHardware;
	private String os;
	private String hostedOn;

	private DirectedGraph<PatternComponent, RelationshipEdge> pattern;

	public EnvironmentBasedAvailabilityPattern() {
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		os = properties.getProperty("labelOS");
		virtualHardware = properties.getProperty("labelVirtualHardware");
		hostedOn = properties.getProperty("relationHostedOn");

		pattern = new DefaultDirectedGraph<>(RelationshipEdge.class);

		PatternComponent virtualHardwareComponent = new PatternComponent(virtualHardware, 1, 1);
		PatternComponent operatingSystem = new PatternComponent(os, 1, 1);

		pattern.addVertex(operatingSystem);
		pattern.addVertex(virtualHardwareComponent);

		pattern.addEdge(operatingSystem, virtualHardwareComponent, new RelationshipEdge(operatingSystem, virtualHardwareComponent, hostedOn));
	}

	public DirectedGraph<PatternComponent, RelationshipEdge> getPatternGraph() {
		return pattern;
	}
}
