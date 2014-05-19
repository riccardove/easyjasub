package com.github.riccardove.easyjasub.stringtemplate;

import org.stringtemplate.v4.ST;

public class StringTemplateRenderer {

	public StringTemplateRenderer(String template) {
		st = new ST(template);
	}

	private final ST st;

	public void addAggr(String name, Object obj) {
		st.addAggr(name, obj);
	}

	public String render() {
		return st.render();
	}
}
