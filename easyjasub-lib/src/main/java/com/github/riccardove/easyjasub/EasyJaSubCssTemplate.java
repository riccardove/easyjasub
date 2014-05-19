package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.io.InputStream;

import com.github.riccardove.easyjasub.commons.CommonsIOUtils;
import com.github.riccardove.easyjasub.stringtemplate.StringTemplateRenderer;

class EasyJaSubCssTemplate {

	static {
		template = readResource();
	}

	private static final String template;
	private static final String templateResourceName = "EasyJaSubCssTemplate.css";
	private static final String parameterName = "css";

	private static String readResource() {
		InputStream is = EasyJaSubCssTemplate.class
				.getResourceAsStream(templateResourceName);
		try {
			return CommonsIOUtils.streamToString(is);
		} catch (IOException e) {
			return null;
		}
	}

	public String render(EasyJaSubCssTemplateParameter parameter)
			throws IOException {
		if (template == null) {
			throw new IOException("Could not find .CSS resource file "
					+ templateResourceName);
		}
		StringTemplateRenderer renderer = new StringTemplateRenderer(template);
		renderer.addAggr(parameterName, parameter);
		return renderer.render();
	}
}
