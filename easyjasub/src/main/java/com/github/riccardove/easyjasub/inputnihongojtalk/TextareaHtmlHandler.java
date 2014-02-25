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


import org.xml.sax.*;

class TextareaHtmlHandler implements SectionHtmlHandler {

	private final NihongoJTalkSubtitleList subtitleList;
	private int index;
	
	public TextareaHtmlHandler(NihongoJTalkSubtitleList subtitleList) {
		text = new StringBuffer();
		this.subtitleList = subtitleList; 
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
	}

	private StringBuffer text;  
	
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		for (int i=0; i < length; ++i) {
			char c = ch[i+start];
			switch (c) {
			case '\n': {
				addText();
				text.setLength(0);
				break;
			}
			case '\r': {
				break;
			}
			case '，': {
				text.append(',');
				break;
			}
			case '１': {
				text.append('1');
				break;
			}
			case '7': {
				text.append('7');
				break;
			}
			case '０': {
				text.append('0');
				break;
			}
			default: {
				text.append(c);
				break;
			}
			
			}
		}
	}

	private void addText() {
		NihongoJTalkSubtitleLine line = subtitleList.get(index++);
		line.setNihongoJTalkJapaneseText(text.toString());
	}

	public void startSection(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
	}

	public void endSection(String uri, String localName, String qName)
			throws SAXException {
		if (text.length() > 0)
		{
			addText();
		}
	}
}
