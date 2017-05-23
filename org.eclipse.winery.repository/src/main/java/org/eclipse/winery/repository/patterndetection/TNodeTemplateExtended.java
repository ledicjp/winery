package org.eclipse.winery.repository.patterndetection;

import org.eclipse.winery.model.tosca.TNodeTemplate;

/**
 * Created by marvin on 13.05.2017.
 */
public class TNodeTemplateExtended {

	private TNodeTemplate tNodeTemplate;
	private String label;


	public TNodeTemplateExtended() {

	}

	public TNodeTemplateExtended(TNodeTemplate tNodeTemplate, String label) {
		this.tNodeTemplate = tNodeTemplate;
		this.label = label;
	}

	public TNodeTemplate getNodeTemplate() {
		return tNodeTemplate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
