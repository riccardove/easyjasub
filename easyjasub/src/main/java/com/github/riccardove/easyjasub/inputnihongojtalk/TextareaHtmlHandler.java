package com.github.riccardove.easyjasub.inputnihongojtalk;

import org.xml.sax.*;

import com.github.riccardove.easyjasub.SubtitleLine;
import com.github.riccardove.easyjasub.SubtitleList;

class TextareaHtmlHandler implements SectionHtmlHandler {

	private final SubtitleList subtitleList;
	private int index;
	
	public TextareaHtmlHandler(SubtitleList subtitleList) {
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
		SubtitleLine line = subtitleList.get(index++);
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
