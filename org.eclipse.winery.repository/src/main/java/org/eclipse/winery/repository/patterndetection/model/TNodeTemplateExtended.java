package org.eclipse.winery.repository.patterndetection.model;

import org.eclipse.winery.model.tosca.TNodeTemplate;

/**
 * Created by marvin.wohlfarth on 13.05.2017.
 * objects of this class hold an original NodeTemplate, a label and the keyword belonging to this
 */
public class TNodeTemplateExtended {

	private TNodeTemplate tNodeTemplate;
	private String label;
	private String keyword;


	public TNodeTemplateExtended() {

	}

	public TNodeTemplateExtended(TNodeTemplate tNodeTemplate, String label, String keyword) {
		this.tNodeTemplate = tNodeTemplate;
		this.label = label;
		this.keyword = keyword;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
