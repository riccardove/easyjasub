package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Simple helper to read XML files with a SAX parser
 */
public class EasyJaSubXmlReader {

	public EasyJaSubXmlReader(ContentHandler contentHandler)
			throws SAXException {
		saxParser = XMLReaderFactory.createXMLReader();
		saxParser.setContentHandler(contentHandler);
	}

	public EasyJaSubXmlReader(String parser, ContentHandler contentHandler,
			EntityResolver entityResolver) throws SAXException {
		saxParser = XMLReaderFactory.createXMLReader(parser);
		saxParser.setContentHandler(contentHandler);
		saxParser.setEntityResolver(entityResolver);
	}

	private final XMLReader saxParser;

	public void parse(File file) throws IOException, SAXException {
		EasyJaSubReader reader = new EasyJaSubReader(file);
		saxParser.parse(reader.getInputSource());
		reader.close();
	}

}
