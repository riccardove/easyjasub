package com.github.riccardove.easyjasub.stringtemplate;

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


import org.stringtemplate.v4.ST;

public class StringTemplateRenderer {

	public StringTemplateRenderer(String template) {
		st = new ST(template);
	}

	private final ST st;

	public void addAggr(String name, Object obj) {
		st.addAggr(name, new Object[] { obj });
	}

	public String render() {
		return st.render();
	}
}
