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


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.github.riccardove.easyjasub.EasyJaSubObserver;
import com.github.riccardove.easyjasub.SubtitleList;

class InputNihongoJTalkHtmlHandler extends DefaultHandler {

	private static final String ID_ATTRIBUTE = "id";
	private static final String DIV_ELEMENT = "div";
	private static final String TEXTAREA_ELEMENT = "textarea";
	private final EasyJaSubObserver observer;

	public InputNihongoJTalkHtmlHandler(SubtitleList subtitleList,
			EasyJaSubObserver observer) {
		this.subtitleList = subtitleList;
		this.observer = observer;
	}
	
	private final SubtitleList subtitleList;
	private SectionHtmlHandler sectionParser;
	private int divCount;
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (sectionParser != null) {
			sectionParser.startElement(uri, localName, qName, attributes);
		}
		else if (TEXTAREA_ELEMENT.equals(qName)) {
			observer.onInputNihongoJTalkHtmlFileParseTextareaStart();
			sectionParser = new TextareaHtmlHandler(subtitleList);
			sectionParser.startSection(uri, localName, qName, attributes);
		}
		else if (DIV_ELEMENT.equals(qName)) {
			if ("rollover".equals(attributes.getValue(ID_ATTRIBUTE))) {
				observer.onInputNihongoJTalkHtmlFileParseHiraganaDivStart();
				sectionParser = new HiraganaDivHtmlHandler(subtitleList, observer);
				sectionParser.startSection(uri, localName, qName, attributes);
			}
			if (sectionParser != null) {
				divCount++;
			}
		}
	}	

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (sectionParser != null) {
			if (DIV_ELEMENT.equals(qName)) {
				divCount--;
			}
			boolean endDiv = divCount == 0;
			if ((endDiv || TEXTAREA_ELEMENT.equals(qName))) {
				sectionParser.endSection(uri, localName, qName);
				if (endDiv) {
					observer.onInputNihongoJTalkHtmlFileParseHiraganaDivEnd();
				}
				else {
					observer.onInputNihongoJTalkHtmlFileParseTextareaEnd();
				}
				sectionParser = null;
			}
			else {
				sectionParser.endElement(uri, localName, qName);
			}
		}
	}

	public void characters(char data[], int start, int length)
			throws SAXException {
		if (sectionParser != null) {
			sectionParser.characters(data, start, length);
		}
	}

}
