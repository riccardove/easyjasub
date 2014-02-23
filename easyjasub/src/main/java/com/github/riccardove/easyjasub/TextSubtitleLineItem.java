package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
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

import org.rendersnake.HtmlCanvas;
import static org.rendersnake.HtmlAttributesFactory.class_;

public class TextSubtitleLineItem extends SubtitleLineItem {
	protected String text;
	
	public TextSubtitleLineItem(SubtitleLine subtitleLine, String text) {
		super(subtitleLine);
		if (text == null) {
			throw new NullPointerException("Invalid text");
		}
		this.text = text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public void renderOn(HtmlCanvas html) throws IOException {
		html.write(text);
	}

	public void renderOnCenter(HtmlCanvas html) throws IOException {
		renderText(null, html);
	}
	
	protected void renderText(String styleClass, HtmlCanvas html) throws IOException {
		String trimmedText = text.replace('　', ' ').trim();
		if (styleClass == null) {
			html.td();
		}
		else {
			html.td(class_(styleClass));
		}
		html.write(trimmedText, false);
		if (trimmedText.length() < text.length()) {
			html.write("&thinsp;", false);
		}
		html._td();
	}
}
