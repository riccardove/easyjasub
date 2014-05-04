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
import java.util.regex.Pattern;

import com.github.riccardove.easyjasub.SubtitleItem;
import com.github.riccardove.easyjasub.commons.CommonsLangStringUtils;
import com.github.riccardove.easyjasub.dictionary.DictionaryGloss;

class DictSubtitleLineItem extends RedSubtitleLineItem {

	public DictSubtitleLineItem(NihongoJTalkSubtitleLine subtitleLine, String text, String dictionaryInfo,
			String pos, String romaji)  
	{
		super(subtitleLine, text, pos, romaji);
		if (dictionaryInfo == null) {
			throw new NullPointerException("Invalid dictionary for text " + text);
		}
		this.dictionaryInfo = dictionaryInfo;
		
		switch (gpos) {
		case adj: 
		case naadj:
		case prenadj:
		case noun:
		case nsuru:
		case adv:
		case vpotential:
		case verb: {
			dict = new ArrayList<String>();
			addDictInfo(dictionaryInfo);
			if (dict.size() == 0) {
				dict = null;
			}
			break;
		}
		default:
			break;
		}
	}

	private void addDictInfo(String dictionaryInfo) {
		if (dictionaryInfo.startsWith("(1)")) {
			for (String dictElem : ListPattern.split(dictionaryInfo)) {
				addDictElem(dictElem);
			}
		}
		else {
			addDictElem(dictionaryInfo);
		}
	}
	
	private ArrayList<String> dict;
	
	private void addDictElem(String dictInfo) {
		for (String dictElem : InnerListPattern.split(dictInfo)) {
			dictElem = DictionaryGloss.choose(dictElem, romaji);
			if (dictElem != null) {
				dict.add(dictElem);
			}
		}
	}
	
	private static final Pattern ListPattern = Pattern.compile("\\([0-9]+\\)");
	private static final Pattern InnerListPattern = Pattern.compile("; ");
	
	protected String dictionaryInfo;
	
	@Override
	public String toString() {
		return super.toString() + "[" + dictionaryInfo + "]";
	}

	@Override
	public void toItem(SubtitleItem item) {
		if (dict != null) {
			String translation = subtitleLine.getDictionaryMatch(text, dict);
			if (translation == null) {
				translation = dict.get(0);
			}
			item.setDictionary(translation);
			item.setComment(text + ": " + CommonsLangStringUtils.join(dict, ";"));
		}
		super.toItem(item);
	}
	
	@Override
	public String getComment() {
		if (dict != null) {
			return text + ": " + CommonsLangStringUtils.join(dict, ";");
		}
		return null;
	}
}
