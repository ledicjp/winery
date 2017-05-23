package org.eclipse.winery.repository.patterndetection.pattern;

/**
 * Created by marvin on 15.05.2017.
 */
public class PatternComponent {

	private String name;
	private int min;
	private int max;

	public PatternComponent(String name, int min, int max) {
		this.name = name;
		this.min = min;
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
