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


import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handler for SAX parser events that identifies a set of elements defined in an
 * enum and notifies the text content
 * 
 * @param <T>
 *            Enum identifying interesting elements
 */
public class EasyJaSubXmlHandlerAdapter<T extends Enum<?>> extends
		DefaultHandler {

	private T element;
	private final StringBuilder text;
	private final EasyJaSubXmlHandler<T> handler;
	private final T undefElement;
	private final HashMap<String, T> map;

	public EasyJaSubXmlHandlerAdapter(T undefElement, T[] values,
			EasyJaSubXmlHandler<T> handler) {
		this.undefElement = undefElement;
		element = undefElement;
		this.handler = handler;
		map = new HashMap<String, T>();
		text = new StringBuilder();
		for (T value : values) {
			if (value != undefElement) {
				map.put(value.toString(), value);
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		element = valueOf(qName);
		if (element != undefElement) {
			clearText();
			handler.onStartElement(element, attributes);
		}
	}

	private T valueOf(String qName) {
		T value = map.get(qName);
		if (value == null) {
			return undefElement;
		}
		return value;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		element = valueOf(qName);
		if (element != undefElement) {
			handler.onEndElement(element, text.toString());
			element = undefElement;
			clearText();
		}
	}

	private void clearText() {
		text.setLength(0);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (element != undefElement) {
			text.append(ch, start, length);
		}
	}

}
