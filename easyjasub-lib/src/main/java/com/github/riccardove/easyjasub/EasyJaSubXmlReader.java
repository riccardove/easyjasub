package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub-lib
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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
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

	public void setEntityExpansionLimit(int count)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		if (SystemProperty.isVendorOracle()) {
			saxParser
					.setProperty(
							"http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit",
							count);
		}
		// TODO: find how to set this with other JVM
	}

	private final XMLReader saxParser;

	public void parse(File file) throws IOException, SAXException {
		EasyJaSubReader reader = new EasyJaSubReader(file);
		parse(reader);
		reader.close();
	}

	public void parse(InputStream inputStream) throws IOException, SAXException {
		EasyJaSubReader reader = new EasyJaSubReader(inputStream);
		parse(reader);
		reader.close();
	}

	private void parse(EasyJaSubReader reader) throws IOException, SAXException {
		saxParser.parse(reader.getInputSource());
	}
}
