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
public class MessageOrientedMiddlewarePattern {

	private static final String propertiesFilename = "patterndetection.properties";

	private Properties properties;

	private String os;
	private String application;
	private String messaging;

	private String hostedOn;
	private String connectsTo;

	private DirectedGraph<PatternComponent, RelationshipEdge> pattern;

	public MessageOrientedMiddlewarePattern() {
		properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		os = properties.getProperty("labelOS");
		messaging = properties.getProperty("labelMessaging");
		application = properties.getProperty("labelApp");

		hostedOn = properties.getProperty("relationHostedOn");
		connectsTo = properties.getProperty("relationConnectsTo");

		pattern = new DefaultDirectedGraph<>(RelationshipEdge.class);

		PatternComponent operatingSystem = new PatternComponent(os, 1, 1);
		PatternComponent appComponent = new PatternComponent(application, 1, 1);
		PatternComponent messagingComponent1 = new PatternComponent(messaging, 1, 1);
		PatternComponent messagingComponent2 = new PatternComponent(messaging, 1, 1);

		pattern.addVertex(operatingSystem);
		pattern.addVertex(messagingComponent1);
		pattern.addVertex(messagingComponent2);
		pattern.addVertex(appComponent);

		pattern.addEdge(messagingComponent1, operatingSystem, new RelationshipEdge(messagingComponent1, operatingSystem, hostedOn));
		pattern.addEdge(messagingComponent2, messagingComponent1, new RelationshipEdge(messagingComponent2, messagingComponent1, hostedOn));

		pattern.addEdge(appComponent, messagingComponent2, new RelationshipEdge(appComponent, messagingComponent2, connectsTo));

	}

	public DirectedGraph<PatternComponent, RelationshipEdge> getPatternGraph() {
		return pattern;
	}
}
