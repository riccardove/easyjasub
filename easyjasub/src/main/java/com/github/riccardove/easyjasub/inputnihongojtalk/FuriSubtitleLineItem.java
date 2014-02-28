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


import static org.rendersnake.HtmlAttributesFactory.class_;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;

import com.github.riccardove.easyjasub.SubtitleItem;

class FuriSubtitleLineItem extends RedSubtitleLineItem {

	public FuriSubtitleLineItem(NihongoJTalkSubtitleLine subtitleLine, String text, String furigana,
			String pos, String romaji)  
	{
		super(subtitleLine, text, pos, romaji);
		if (furigana == null) {
			throw new NullPointerException("Invalid furigana for text " + text);
		}
		this.furigana = furigana;
	}
	
	private String furigana; 
	
	@Override
	public void toItem(SubtitleItem item) {
		item.setFurigana(furigana);
		super.toItem(item);
	}
	
	@Override
	public String toString() {
		return super.toString() + "(" + furigana + ")";
	}

}
