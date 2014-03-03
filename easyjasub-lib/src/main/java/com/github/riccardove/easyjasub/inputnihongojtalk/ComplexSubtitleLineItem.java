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


import java.util.ArrayList;

import com.github.riccardove.easyjasub.JapaneseChar;
import com.github.riccardove.easyjasub.SubtitleItem;

class ComplexSubtitleLineItem extends DictSubtitleLineItem {

	public ComplexSubtitleLineItem(NihongoJTalkSubtitleLine subtitleLine, String text, String furigana,
			String dictionaryInfo, 
			String pos, String romaji)  
	{
		super(subtitleLine, text, dictionaryInfo, pos, romaji);
		if (furigana == null) {
			throw new NullPointerException("Invalid furigana for text " + text);
		}
		this.furigana = furigana;
	}
	
	private final String furigana; 
	
	@Override
	public String toString() {
		return super.toString() + "(" + furigana + ")";
	}
	
	@Override
	public void toItem(SubtitleItem item) {
		item.setFurigana(furigana);

		ArrayList<SubtitleItem.Inner> list = new ArrayList<SubtitleItem.Inner>();
		String kanjiChars = null;
		String chars = null;
		for (int i = 0; i < text.length(); i++){
		    char c = text.charAt(i);        
		    if (!JapaneseChar.isSmallSizeJapaneseChar(c)) {
		    	if (chars != null) {
		    		addText(list, chars);
			    	chars = null;
		    	}
		    	if (kanjiChars == null) {
		    		kanjiChars = Character.toString(c);
		    	}
		    	else {
		    		kanjiChars +=  c;
		    	}
		    }
		    else {
		    	if (kanjiChars != null) {
		    		addKanji(list, kanjiChars);
		    		kanjiChars = null;
		    	}
		    	if (chars == null) {
		    		chars = Character.toString(c);
		    	}
		    	else {
		    		chars +=  c;
		    	}
		    }
		}		
    	if (kanjiChars != null) {
    		addKanji(list, kanjiChars);
    	}
    	if (chars != null) {
    		addText(list, chars);
    	}
    	item.setElements(list);
		super.toItem(item);
	}

	private void addText(ArrayList<SubtitleItem.Inner> list, String chars) {
		SubtitleItem.Inner inner = new SubtitleItem.Inner();
		inner.setText(chars);
		list.add(inner);
	}

	private void addKanji(ArrayList<SubtitleItem.Inner> list, String chars) {
		SubtitleItem.Inner inner = new SubtitleItem.Inner();
		inner.setKanji(chars);
		list.add(inner);
	}
}
