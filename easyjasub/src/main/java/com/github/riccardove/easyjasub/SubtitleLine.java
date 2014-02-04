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

	private String originalText;
	private ArrayList<SubtitleLineItem> items;
	private final int index;
	private String english;
	
	public SubtitleLine(int index) {
		items = new ArrayList<SubtitleLineItem>();
		this.index = index;
	}
	
	public void setOriginalText(String text) {
		originalText = text;
		ja = true;
	}
		
	public int getIndex() {
		return index;
	}
	
	private boolean ja;
	
	public boolean isJa() {
		return ja;
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
		text.append(originalText);
		text.append(" - ");
		for (SubtitleLineItem item : items) {
			text.append(item.toString());
		}
		return text.toString();
	}

	public static final String Newline = "\r\n";
	
	public void renderOn(HtmlCanvas html) throws IOException {
		if (ja) {
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
		if (english != null) {
			html.write(Newline, false)
			.p().write(english, false)._p();
		}
		if (ja) {
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
	
	public void setCaption(InputTextSubCaption next) {
		caption = next;
	}

	public String getIdxStartTime() {
		return caption.getIdxStartTime();
	}

	private static final Pattern EnglishReplace = Pattern.compile("<br ?/>");
	private static final String BreakStr = "&nbsp;&nbsp;&nbsp;";
	
	public void addEnglish(String content) {
		System.out.println(content + " in line " + index + ": " + originalText);
		String text = EnglishReplace.matcher(content).replaceAll(" ");
		if (english == null) {
			english = text;
		}
		else {
			english += BreakStr + text;
		}
	}

	public boolean compatibleWith(InputTextSubCaption enCaption) {
		int startDiff = caption.getStart().getMSeconds() - enCaption.getStart().getMSeconds();
		int endDiff = caption.getEnd().getMSeconds() - enCaption.getEnd().getMSeconds();
		// both start and end of the line are nearby
		return 
				(Math.abs(startDiff) < 2000 || Math.abs(endDiff) < 2000);
	}

	public boolean approxCompatibleWith(InputTextSubCaption enCaption) {
		int startDiff = caption.getStart().getMSeconds() - enCaption.getStart().getMSeconds();
		int endDiff = caption.getEnd().getMSeconds() - enCaption.getEnd().getMSeconds();
		// ja: ------------***************--------------
		// en: -------**************************--------
		if (startDiff > -500 && endDiff < 500) {
			// japanese line is a part of english translation
			return true;
		}
		// ja: -------**************************--------
		// en: ------------***************--------------
		if (startDiff < 500 && endDiff > -500) {
			// english line is a part of japanese translation
			return true;
		}
		
		int interStartDiff = enCaption.getEnd().getMSeconds() - caption.getStart().getMSeconds();
		int interEndDiff = caption.getEnd().getMSeconds() - enCaption.getStart().getMSeconds();
		// ja: -------************----------------------
		// en: ------------***************--------------
		if (startDiff < 0 && endDiff < 0 && interEndDiff > 500) {
			// japanese line starts before english line but they share 0.5 seconds
			return true;
		}
		// ja: ------------***************--------------
		// en: -------************----------------------
		if (startDiff > 0 && endDiff > 0 && interStartDiff > 500) {
			// japanese line starts after english line but they share 0.5 seconds
			return true;
		}
		return false;
	}
	
	// TODO: be a class
	private static final HashMap<String, String> matches = new HashMap<String, String>();

	private void storeDictionaryMatch(String jaText, String dictItem) {
		matches.put(jaText, dictItem);
	}

	private boolean matchEnglish(String dictItem) {
		return english.contains(dictItem);
	}

	public String getDictionaryMatch(String jaText, Iterable<String> dict) {
		if (english == null) {
			return null;
		}
		for (String dictItem : dict) {
			// matches completely
			if (matchEnglish(dictItem)) {
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
					(matchEnglish(dictItem.substring(2)) ||
							matchEnglish(dictItem.substring(0, dictItem.length()-2)))) {
				storeDictionaryMatch(jaText, dictItem);
				return dictItem;
			}
		}
		for (String dictItem : dict) {
			if (dictItem.length()>7 && dictItem.contains(" ")) {
				for (String part : dictItem.split(" ")) {
					if (part.length() > 4 &&
							matchEnglish(part)) {
//						storeDictionaryMatch(jaText, dictItem);
						return dictItem;
					}
				}
			}
		}
		return null;
	}

	public boolean startsAfter(InputTextSubCaption enCaption) {
		return caption.getStart().compareTo(enCaption.getEnd()) >= 0;
	}

	public boolean endsBefore(InputTextSubCaption enCaption) {
		return caption.getEnd().compareTo(enCaption.getStart()) <= 0;
	}

	public boolean isEnglish() {
		return english != null;
	}

	public String getEnglish() {
		return english;
	}
}
