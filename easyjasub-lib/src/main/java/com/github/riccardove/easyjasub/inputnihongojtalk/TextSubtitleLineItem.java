package com.github.riccardove.easyjasub.inputnihongojtalk;

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


import com.github.riccardove.easyjasub.SubtitleItem;

class TextSubtitleLineItem extends NihongoJTalkSubtitleLineItem {
	protected String text;
	
	public TextSubtitleLineItem(NihongoJTalkSubtitleLine subtitleLine, String text) {
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

	@Override
	public void toItem(SubtitleItem item) {
		String trimmedText = text.replace('　', ' ').trim();
		if (trimmedText.length() < text.length()) {
			trimmedText += "&thinsp;";
		}
		item.setText(trimmedText);
		super.toItem(item);
	}
}
