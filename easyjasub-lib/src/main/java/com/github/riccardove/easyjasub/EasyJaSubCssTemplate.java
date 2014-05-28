package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub-lib
 * %%
 * Copyright (C) 2014 Riccardo Vestrini
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
