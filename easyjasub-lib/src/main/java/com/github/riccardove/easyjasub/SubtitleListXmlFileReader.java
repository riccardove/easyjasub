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
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.github.riccardove.easyjasub.SubtitleItem.Inner;

class SubtitleListXmlFileReader {

	private final SubtitleList list;
	private SubtitleLine line;
	private ArrayList<SubtitleItem> items;
	private SubtitleItem item;
	private ArrayList<Inner> elements;

	public SubtitleListXmlFileReader(SubtitleList list) {
		this.list = list;
	}

	public void read(File file) throws IOException,
			SAXException {
		Handler handler = new Handler();
		new EasyJaSubXmlReader(handler).parse(file);
	}

	private void onStartElement(SubtitleListXmlElement element, String text) {
		switch (element) {
		case line: {
			line = list.add();
			break;
		}
		case items: {
			items = new ArrayList<SubtitleItem>();
		}
		case item: {
			item = new SubtitleItem();
			items.add(item);
		}
		case inner: {
			elements = new ArrayList<Inner>();
			break;
		}
		default: {
			break;
		}
		}
	}

	private void onEndElement(SubtitleListXmlElement element, String text) {
		switch (element) {
		case title:
		{
			list.setTitle(text);
			break;
		}
		case items: {
			line.setItems(items);
			items = null;
			break;
		}
		case translation:{
			line.setTranslatedText(text);
			break;
		} 
		case text: {
			line.setSubText(text);
			break;
		}
		case item: {
			item = null;
		}
		case grammar:
		{
			item.setGrammarElement(text);
			break;
		}
		case dictionary: {
			item.setDictionary(text);
			break;
		} 
		case furigana: 
			{
				item.setFurigana(text);
				break;
			}
		case romaji: 
		{
			item.setRomaji(text);
			break;
		}
		case itemtext: 
		{
			item.setText(text);
			break;
		}
		case inner: {
			item.setElements(elements);
			break;
		}
		case comment: {
			item.setComment(text);
		}
		case chars: {
			Inner inner = new Inner();
			inner.setText(text);
			elements.add(inner);
		}
		case kanji: {
			Inner inner = new Inner();
			inner.setKanji(text);
			elements.add(inner);
		}
		case start: {
			line.setStartTime(Integer.parseInt(text));
			break;
		}
		case end: {
			line.setEndTime(Integer.parseInt(text));
			break;
		}
		case line: {
			line = null;
			break;
		}
		default:
			break;
		}
	}

	private class Handler extends DefaultHandler {
		private SubtitleListXmlElement element;
		private final StringBuilder text;

		public Handler() {
			element = SubtitleListXmlElement.undef;
			text = new StringBuilder();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			element = SubtitleListXmlElement.valueOf(qName);
			onStartElement(element, text.toString());
			text.setLength(0);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			onEndElement(element, text.toString());
			element = SubtitleListXmlElement.undef;
			text.setLength(0);
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			text.append(ch, start, length);
		}
	}
}
