package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;

import subtitleFile.*;
import static org.rendersnake.HtmlAttributesFactory.class_;

public class SubtitleLine implements Renderable {

	private String nihongoJTalkJapaneseText;
	private ArrayList<SubtitleLineItem> items;
	private final int index;
	private String translation;
	
	public SubtitleLine(int index) {
		items = new ArrayList<SubtitleLineItem>();
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

	public void addItem(SubtitleLineItem item) {
		item.setIndex(items.size());
		items.add(item);
	}

	public SubtitleLineItem getLastItem() 
	{
		return items.get(items.size()-1);
	}
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append(nihongoJTalkJapaneseText);
		text.append(" - ");
		for (SubtitleLineItem item : items) {
			text.append(item.toString());
		}
		return text.toString();
	}

	public static final String Newline = "\r\n";
	
	public void renderOn(HtmlCanvas html) throws IOException {
		if (isJa()) {
			html.write(Newline, false).table().write(Newline, false).tr(class_("top")).write(Newline, false);
			for (SubtitleLineItem item : items) {
				html.write("  ");
				item.renderOnTop(html);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("center")).write(Newline, false);
			for (SubtitleLineItem item : items) {
				html.write("  ");
				item.renderOnCenter(html);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("bottom")).write(Newline, false);
			for (SubtitleLineItem item : items) {
				html.write("  ");
				item.renderOnBottom(html);
				html.write(Newline, false);
			}
			html._tr().write(Newline, false).tr(class_("translation")).write(Newline, false);
			for (SubtitleLineItem item : items) {
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
			for (SubtitleLineItem item : items) {
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

	private InputTextSubCaption caption;
	
	public String getInTC() {
		return caption.getStart().getBDMTime();
	}
	
	public String getOutTC() {
		return caption.getEnd().getBDMTime();
	}
	
	@Deprecated
	public void setCaption(InputTextSubCaption next) {
		caption = next;
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
