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


import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.EasyJaSubObserver;
import com.github.riccardove.easyjasub.JapaneseChar;

class HiraganaDivHtmlHandler implements SectionHtmlHandler {

	private static final String BASE_CLASS = "base";
	private static final String BR_ELEMENT = "br";
	private static final String CLASS_ATTRIBUTE = "class";
	private static final String ENGLISHRUBY_CLASS = "englishruby";
	private static final String ErrorStr ="[?]";
	private static final String POS_CLASS = "pos";
	private static final String READING_CLASS = "reading";
	private static final String SPAN_ELEMENT = "span";
	private static final String SYLN_CLASS = "syln";
	private static final String TRG_ELEMENT = "trg";
	private final Iterator<NihongoJTalkSubtitleLine> iter;
	private String baseText;
	private String classAttribute;
	private String dictreadingText;
	private String englishrubyText;
	private String lastText;
	private NihongoJTalkSubtitleLine line;
	private String posText;
	private int span;
	private boolean trg;
	private String triggerStr;
	private final EasyJaSubObserver observer;

	public HiraganaDivHtmlHandler(NihongoJTalkSubtitleList subtitleList,
			EasyJaSubObserver observer) {
		this.observer = observer;
		iter = subtitleList.iterator();
		next();
	}

	private void addNonText(NihongoJTalkSubtitleLineItem item) {
		lastText = null;
		line.addItem(item);
	}

	private void addSpan() {
		if (triggerStr == null) {
			throw new NullPointerException("Invalid trigger");
		}
		if (triggerStr.length() == 1 && !JapaneseChar.isJapaneseChar(triggerStr.charAt(0))) {
			addText(triggerStr);
			return;
		}
		if (baseText != null) {
			if (baseText.length() == 1) {
				char c = baseText.charAt(0);
				if (!JapaneseChar.isJapaneseChar(c)) {
					addText(baseText);
					return;
				}
			}
			if (englishrubyText == null) {
				addNonText(new FuriSubtitleLineItem(line, baseText, triggerStr,
						posText, dictreadingText));
			}
			else {
				addNonText(new ComplexSubtitleLineItem(line, baseText, triggerStr, englishrubyText,
						posText, dictreadingText));
			}
		}
		else {
			if (englishrubyText == null) {
				addNonText(new RedSubtitleLineItem(line, triggerStr,
						posText, dictreadingText));
			}
			else {
				addNonText(new DictSubtitleLineItem(line, triggerStr, englishrubyText,
						posText, dictreadingText));
			}
		}
	}

	private void addText(String text) {
		if (ErrorStr.equals(text)) {
			return;
		}
		if (text.contains(ErrorStr)) {
			text = text.replace(ErrorStr, "");
		}
 		if (lastText == null) {
			lastText = text;
			TextSubtitleLineItem item = new TextSubtitleLineItem(line, text);
			line.addItem(item);
		}
		else {
			lastText = lastText + text;
			((TextSubtitleLineItem) line.getLastItem()).setText(lastText);
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String text = String.valueOf(ch, start, length);
		if (span == 0) {
			if (line != null) {
				addText(text);
			}
		}
		else if (trg) {
			triggerStr = text;
		}
		else if (BASE_CLASS.equals(classAttribute) && !text.equals(triggerStr)) {
			baseText = text;
		} 
		else if (READING_CLASS.equals(classAttribute) || 
				SYLN_CLASS.equals(classAttribute)) {
			if (dictreadingText != null) {
				dictreadingText = dictreadingText + text;
			}
			else {
				dictreadingText = text;
			}
		}
		else if (POS_CLASS.equals(classAttribute)) {
			posText = text;
		}
		else if (ENGLISHRUBY_CLASS.equals(classAttribute) && !"*".equals(text)) {
			englishrubyText = text;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (BR_ELEMENT.equals(qName)) {
			return;
		}
		if (SPAN_ELEMENT.equals(qName))
		{
			span--;
		}
		if (span == 0) {
			if (line != null) {
				addSpan();
			}
			triggerStr = null;
			baseText = null;
			dictreadingText = null;
			posText = null;
			englishrubyText = null;
		}
		trg = false;
		if (span < 6) {
			classAttribute = null;
		}
	}
	
	@Override
	public void endSection(String uri, String localName, String qName)
			throws SAXException {
		
	}

	
	private void next() {
		if (iter.hasNext()) {
			line = iter.next();
			lastText = null;
		}
		else {
			line = null;
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		classAttribute = null;
		trg = false;
		if (SPAN_ELEMENT.equals(qName))
		{
			span++;
			classAttribute = attributes.getValue(CLASS_ATTRIBUTE);
		}
		else if (span == 1 && TRG_ELEMENT.equals(qName)) {
			trg = true;
		}
		else if (BR_ELEMENT.equals(qName) && line != null) {
			observer.onInputNihongoJTalkHtmlLine(line.getJapaneseText());
			next();
		}
	}

	@Override
	public void startSection(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
	}
}
