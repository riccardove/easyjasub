package com.github.riccardove.easyjasub.inputnihongojtalk;

import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.SubtitleLine;
import com.github.riccardove.easyjasub.SubtitleList;

class HiraganaDivHtmlHandler implements SectionHtmlHandler {

	private int span;
	private String classAttribute;
	private boolean trg;
	private String triggerStr;

	private final Iterator<SubtitleLine> iter;
	private SubtitleLine line;
	
	private String baseText;
	private String dictreadingText;
	private String posText;
	private String englishrubyText;
	
	public HiraganaDivHtmlHandler(SubtitleList subtitleList) {
		iter = subtitleList.iterator();
		next();
	}
	
	private void next() {
		if (iter.hasNext()) {
			line = iter.next();
		}
		else {
			line = null;
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		classAttribute = null;
		trg = false;
		if ("span".equals(qName))
		{
			span++;
			classAttribute = attributes.getValue("class");
//			System.out.println(classAttribute);
		}
		else if (span == 1 && "trg".equals(qName)) {
			trg = true;
		}
		else if ("br".equals(qName)) {
			next();
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("br".equals(qName)) {
			return;
		}
		if ("span".equals(qName))
		{
			span--;
		}
		if (span == 0) {
			if (line != null) {
				line.addSpan(triggerStr, baseText, dictreadingText, posText, englishrubyText);
			}
			triggerStr = null;
			baseText = null;
			dictreadingText = null;
			posText = null;
			englishrubyText = null;;
		}
		trg = false;
		if (span < 6) {
			classAttribute = null;
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String text = String.valueOf(ch, start, length);
		if (span == 0) {
			if (line != null) {
				line.addText(text);
			}
			
		}
		else if (trg) {
			triggerStr = text;
		}
		else if ("base".equals(classAttribute) && !text.equals(triggerStr)) {
			baseText = text;
		} 
		else if ("reading".equals(classAttribute) || 
				"syln".equals(classAttribute)) {
			if (dictreadingText != null) {
				dictreadingText = dictreadingText + text;
			}
			else {
				dictreadingText = text;
			}
		}
		else if ("pos".equals(classAttribute)) {
			posText = text;
		}
		else if ("englishruby".equals(classAttribute) && !"*".equals(text)) {
			englishrubyText = text;
		}

	}

	public void startSection(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
	}

	public void endSection(String uri, String localName, String qName)
			throws SAXException {
		
	}
}
