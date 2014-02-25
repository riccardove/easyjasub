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


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import com.github.riccardove.easyjasub.SubtitleItem;

import static org.rendersnake.HtmlAttributesFactory.class_;

public class NihongoJTalkSubtitleLine implements Renderable {

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
		return japanese;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean isJa() {
		return japanese != null;
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

	public static final String Newline = "\r\n";
	
	public void renderOn(HtmlCanvas html) throws IOException {
		if (isJa()) {
			html.write(Newline, false).table().write(Newline, false).tr(class_("top")).write(Newline, false);
			for (NihongoJTalkSubtitleLineItem item : items) {
				html.write("  ");
				item.renderOnTop(html);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("center")).write(Newline, false);
			for (NihongoJTalkSubtitleLineItem item : items) {
				html.write("  ");
				item.renderOnCenter(html);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("bottom")).write(Newline, false);
			for (NihongoJTalkSubtitleLineItem item : items) {
				html.write("  ");
				item.renderOnBottom(html);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("translation")).write(Newline, false);
			for (NihongoJTalkSubtitleLineItem item : items) {
				html.write("  ");
				item.renderOnLast(html);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false)._table();
		}
		if (translation != null) {
			html.write(Newline, false)
			.p().write(translation, false)._p();
		}
		if (isJa()) {
			html.write("<!--" + Newline, false);
			for (NihongoJTalkSubtitleLineItem item : items) {
				String comment = item.getComment();
				if (comment!=null) {
					html.write(comment + Newline, false);
				}
			}
			html.write("-->" + Newline, false);
		}
	}

	private String htmlFile;
	
	public void setHtmlFile(String file) {
		htmlFile = file;
	}
	
	public String getHtmlFile() {
		return htmlFile;
	}

	private String pngFile;
	public void setPngFile(String string) {
		pngFile = string;
	}
	
	public String getPngFile() {
		return pngFile;
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
	
	private int startTime;
	private int endTime;
	private String japanese;
	
	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public String getJapanese() {
		return japanese;
	}

	public void setStartTime(int mSeconds) {
		startTime = mSeconds;
	}

	public void setEndTime(int mSeconds) {
		endTime = mSeconds;
	}

	public void setJapaneseSubText(String content) {
		japanese = content;
	}
}
