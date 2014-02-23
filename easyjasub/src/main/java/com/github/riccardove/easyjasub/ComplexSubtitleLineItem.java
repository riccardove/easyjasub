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


import static org.rendersnake.HtmlAttributesFactory.class_;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;

public class ComplexSubtitleLineItem extends DictSubtitleLineItem {

	public ComplexSubtitleLineItem(SubtitleLine subtitleLine, String text, String furigana,
			String dictionaryInfo, 
			String pos, String romaji)  
	{
		super(subtitleLine, text, dictionaryInfo, pos, romaji);
		if (furigana == null) {
			throw new NullPointerException("Invalid furigana for text " + text);
		}
		this.furigana = furigana;
	}
	
	private String furigana; 
	
	@Override
	public String toString() {
		return super.toString() + "(" + furigana + ")";
	}
	

	public void renderOnTop(HtmlCanvas html) throws IOException {
		html.td(class_(posc)).content(furigana);
	}
	
	public void renderOnCenter(HtmlCanvas html) throws IOException {
		html.td(class_(posc));
	
		String littleChars = null;
		for (int i = 0; i < text.length(); i++){
		    char c = text.charAt(i);        
		    if (!JapaneseChar.isLittle(c)) {
		    	if (littleChars == null) {
		    		littleChars = Character.toString(c);
		    	}
		    	else {
		    		littleChars +=  c;
		    	}
		    }
		    else {
		    	if (littleChars!=null) {
		    		html.span(class_("kk")).content(littleChars);
		    		littleChars = null;
		    	}
		    	html.write(c);
		    }
		}		
    	if (littleChars!=null) {
    		html.span(class_("kk")).content(littleChars);
    		littleChars = null;
    	}

		html._td();
	}
	
	public void renderOn(HtmlCanvas html) throws IOException {
		
		String kanji = text;
		
		html.table().tr().td(class_(pos)).content(furigana)._tr()
		.tr().td(class_(pos));
		
		html.content(kanji)._tr()
		.tr().td(class_("r")).content(romaji)._tr()._table();
		//html.ruby().write(text).rt().content(furigana)._ruby();
	}

}
