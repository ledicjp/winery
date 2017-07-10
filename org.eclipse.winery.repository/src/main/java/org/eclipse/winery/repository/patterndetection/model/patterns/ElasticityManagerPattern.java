package org.eclipse.winery.repository.patterndetection.model.patterns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.winery.repository.patterndetection.model.PatternComponent;
import org.eclipse.winery.repository.patterndetection.model.RelationshipEdge;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Created by marvin on 03.07.2017.
 */
public class ElasticityManagerPattern {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String virtualHardware;
	private String service;

	private String connectsTo;

	private DirectedGraph<PatternComponent, RelationshipEdge> pattern;

	public ElasticityManagerPattern() {
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		virtualHardware = properties.getProperty("labelVirtualHardware");
		service = properties.getProperty("labelService");

		connectsTo = properties.getProperty("relationConnectsTo");

		pattern = new DefaultDirectedGraph<>(RelationshipEdge.class);

		PatternComponent virtualHardwareComponent = new PatternComponent(virtualHardware, 1, 1);
		PatternComponent serviceComponent = new PatternComponent(service, 1, 1);

		pattern.addVertex(virtualHardwareComponent);
		pattern.addVertex(serviceComponent);

		pattern.addEdge(serviceComponent, virtualHardwareComponent, new RelationshipEdge(service, virtualHardwareComponent, connectsTo));

	}

	public DirectedGraph<PatternComponent, RelationshipEdge> getPatternGraph() {
		return pattern;
	}
}
