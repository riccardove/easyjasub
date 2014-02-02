package com.github.riccardove.easyjasub;

import org.xml.sax.*;

public class TextareaParser implements SectionParser {

	private final SubtitleList subtitleList;
	
	public TextareaParser(SubtitleList subtitleList) {
		text = new StringBuffer();
		this.subtitleList = subtitleList; 
	}
	
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		
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
//				System.out.println(text);
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
		SubtitleLine line = subtitleList.add();
		line.setOriginalText(text.toString());
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
//		text.append(ch, start, length);
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		
	}

	public void skippedEntity(String name) throws SAXException {
		
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
		System.out.println("textarea" + subtitleList.size());
	}

}
