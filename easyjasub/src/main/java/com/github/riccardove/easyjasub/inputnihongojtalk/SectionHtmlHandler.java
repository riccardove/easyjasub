package com.github.riccardove.easyjasub.inputnihongojtalk;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

interface SectionHtmlHandler {

	public void startSection(String uri, String localName, String qName,
			Attributes atts) throws SAXException;

	public void endSection(String uri, String localName, String qName)
			throws SAXException;

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException;

	public void endElement(String uri, String localName, String qName)
			throws SAXException;

	public void characters(char[] ch, int start, int length)
			throws SAXException;
}
