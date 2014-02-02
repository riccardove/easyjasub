package com.github.riccardove.easyjasub;

import static org.rendersnake.HtmlAttributesFactory.class_;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.rendersnake.HtmlCanvas;

public class DictSubtitleLineItem extends RedSubtitleLineItem {

	public DictSubtitleLineItem(SubtitleLine subtitleLine, String text, String dictionaryInfo,
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
			dictElem = ItemClearPattern.matcher(dictElem).replaceAll("").trim();
			// simplify verbs
			if (pos.contains("verb") && dictElem.startsWith("to ")) {
				dictElem = dictElem.substring(3);
			}
			// discard elements of more than 4 words
			if (StringUtils.countMatches(" ", dictElem) > 4) {
				continue;
			}
			// single letters are not considered, nor excessively long items
			if (dictElem.length() > 1 &&
				dictElem.length() < 20 &&
				dictElem.length() < (romaji.length() * 3 + 5)) {
				dict.add(dictElem);
			}
		}
	}
	
	private static final Pattern ListPattern = Pattern.compile("\\([0-9]+\\)");
	private static final Pattern InnerListPattern = Pattern.compile("; ");
	private static final Pattern ItemClearPattern = Pattern.compile("\\([^)]+\\)|!|\\?|(euph\\. for )|\\.");
	
	protected String dictionaryInfo;
	
	@Override
	public String toString() {
		return super.toString() + "[" + dictionaryInfo + "]";
	}

	@Override
	public void renderOnLast(HtmlCanvas html) throws IOException {
		if (dict != null) {
			String englishTranslation = subtitleLine.getDictionaryMatch(text, dict);
			if (englishTranslation == null) {
				englishTranslation = dict.get(0);
			}
			html.td().content(englishTranslation);
		}
		else {
			super.renderOnLast(html);
		}
	}
	
	@Override
	public String getComment() {
		if (dict != null) {
			return text + ": " + StringUtils.join(dict, ';');
		}
		return null;
	}
}
