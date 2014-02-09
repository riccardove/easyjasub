package com.github.riccardove.easyjasub.inputnihongojtalk;

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
				sectionParser = new HiraganaDivHtmlHandler(subtitleList);
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
