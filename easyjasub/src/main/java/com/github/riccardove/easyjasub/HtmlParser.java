package com.github.riccardove.easyjasub;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class HtmlParser extends DefaultHandler {

	public HtmlParser(SubtitleList subtitleList) {
		this.subtitleList = subtitleList;
	}
	
	private final SubtitleList subtitleList;
	private SectionParser sectionParser;
	private int divCount;
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (sectionParser != null) {
			sectionParser.startElement(uri, localName, qName, attributes);
		}
		else if ("textarea".equals(qName)) {
			System.out.println("textarea section start");
			sectionParser = new TextareaParser(subtitleList);
			sectionParser.startSection(uri, localName, qName, attributes);
		}
		else if ("div".equals(qName)) {
			if ("rollover".equals(attributes.getValue("id"))) {
				System.out.println("hiragana section start");
				sectionParser = new HiraganaSectionParser(subtitleList);
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
			if ("div".equals(qName)) {
				divCount--;
			}
			if (("textarea".equals(qName) || divCount == 0)) {
				System.out.println("section end");
				sectionParser.endSection(uri, localName, qName);
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
/*@Override
public void warning(SAXParseException e) throws SAXException {

}
*/
@Override
public void unparsedEntityDecl(String name, String publicId,
		String systemId, String notationName) throws SAXException {

}


/*
@Override
public InputSource resolveEntity(String publicId, String systemId)
		throws IOException, SAXException {
	return null;
}
*/
/*	@Override
	public void error(SAXParseException e) throws SAXException {
		
	}
	
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		
	}
*/}
