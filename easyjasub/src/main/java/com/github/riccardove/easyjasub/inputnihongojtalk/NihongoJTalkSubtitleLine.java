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
import java.util.HashMap;
import java.util.List;

import com.github.riccardove.easyjasub.SubtitleItem;

class NihongoJTalkSubtitleLine {

	private String nihongoJTalkJapaneseText;
	private ArrayList<NihongoJTalkSubtitleLineItem> items;
	private final int index;
	private String translation;
	
	public NihongoJTalkSubtitleLine(int index) {
		items = new ArrayList<NihongoJTalkSubtitleLineItem>();
		this.index = index;
	}
	
	public void setNihongoJTalkJapaneseText(String text) {
		nihongoJTalkJapaneseText = text;
	}
	
	public String getJapaneseText() {
		return nihongoJTalkJapaneseText;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean isJa() {
		return nihongoJTalkJapaneseText != null;
	}

	public void addItem(NihongoJTalkSubtitleLineItem item) {
		item.setIndex(items.size());
		items.add(item);
	}

	public NihongoJTalkSubtitleLineItem getLastItem() 
	{
		return items.get(items.size()-1);
	}
	
	public List<SubtitleItem> toItems() {
		ArrayList<SubtitleItem> subItems = new ArrayList<SubtitleItem>();
		for (NihongoJTalkSubtitleLineItem item : items) {
			SubtitleItem subItem = new SubtitleItem();
			item.toItem(subItem);
			subItems.add(subItem);
		}
		return subItems;
	}
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append(nihongoJTalkJapaneseText);
		text.append(" - ");
		for (NihongoJTalkSubtitleLineItem item : items) {
			text.append(item.toString());
		}
		return text.toString();
	}
	
	// TODO: be a class
	private static final HashMap<String, String> matches = new HashMap<String, String>();

	private void storeDictionaryMatch(String jaText, String dictItem) {
		matches.put(jaText, dictItem);
	}

	private boolean matchTranslation(String dictItem) {
		return translation.contains(dictItem);
	}

	public String getDictionaryMatch(String jaText, Iterable<String> dict) {
		if (translation == null) {
			return null;
		}
		for (String dictItem : dict) {
			// matches completely
			if (matchTranslation(dictItem)) {
				storeDictionaryMatch(jaText, dictItem);
				return dictItem;
			}
		}
		String prevMatch = matches.get(jaText);
		if (prevMatch != null) {
			// searches previous matches
			return prevMatch;
		}
		for (String dictItem : dict) {
			// matches but a small prefix or suffix
			if (dictItem.length()>5 &&
					(matchTranslation(dictItem.substring(2)) ||
							matchTranslation(dictItem.substring(0, dictItem.length()-2)))) {
				storeDictionaryMatch(jaText, dictItem);
				return dictItem;
			}
		}
		for (String dictItem : dict) {
			if (dictItem.length()>7 && dictItem.contains(" ")) {
				for (String part : dictItem.split(" ")) {
					if (part.length() > 4 &&
							matchTranslation(part)) {
//						storeDictionaryMatch(jaText, dictItem);
						return dictItem;
					}
				}
			}
		}
		return null;
	}

	public boolean isTranslation() {
		return translation != null;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslatedText(String text) {
		translation = text;
	}
}
