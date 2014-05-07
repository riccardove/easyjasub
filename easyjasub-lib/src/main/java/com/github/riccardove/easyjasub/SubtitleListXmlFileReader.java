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

import com.github.riccardove.easyjasub.SubtitleItem.Inner;

class SubtitleListXmlFileReader implements
		EasyJaSubXmlHandler<SubtitleListXmlElement> {

	private final SubtitleList list;
	private SubtitleLine line;
	private ArrayList<SubtitleItem> items;
	private SubtitleItem item;
	private ArrayList<Inner> elements;
	private ArrayList<SubtitleTranslatedLine> translation;
	private SubtitleTranslatedLine tline;

	public SubtitleListXmlFileReader(SubtitleList list) {
		this.list = list;
	}

	public void read(File file) throws IOException, SAXException {
		new EasyJaSubXmlReader(
				new EasyJaSubXmlHandlerAdapter<SubtitleListXmlElement>(
						SubtitleListXmlElement.undef,
						SubtitleListXmlElement.values(), this))
				.parse(file);
	}

	private SubtitleLine previous;

	@Override
	public void onStartElement(SubtitleListXmlElement element, Attributes attributes) {
		switch (element) {
		case line: {
			previous = line;
			line = list.add();
			if (previous != null) {
				previous.setNext(line);
				line.setPrevious(previous);
			}
			break;
		}
		case items: {
			items = new ArrayList<SubtitleItem>();
			break;
		}
		case tline: {
			tline = new SubtitleTranslatedLine();
			break;
		}
		case item: {
			item = new SubtitleItem();
			items.add(item);
			break;
		}
		case inner: {
			elements = new ArrayList<Inner>();
			break;
		}
		case translation: {
			translation = new ArrayList<SubtitleTranslatedLine>();
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	public void onEndElement(SubtitleListXmlElement element, String text) {
		switch (element) {
		case title: {
			list.setTitle(text);
			break;
		}
		case items: {
			if (items.size() > 0) {
				line.setItems(items);
			}
			items = null;
			break;
		}
		case translation: {
			if (translation.size() > 0) {
				line.setTranslation(translation);
			}
			translation = null;
			break;
		}
		case tline: {
			if (tline.getText() != null) {
				translation.add(tline);
			}
			tline = null;
			break;
		}
		case ttext: {
			tline.setText(text);
			break;
		}
		case tstart: {
			tline.setStartTime(parseInt(text));
			break;
		}
		case tend: {
			tline.setEndTime(parseInt(text));
			break;
		}
		case text: {
			line.setSubText(text);
			break;
		}
		case item: {
			item = null;
			break;
		}
		case grammar: {
			item.setGrammarElement(text);
			break;
		}
		case dictionary: {
			item.setDictionary(text);
			break;
		}
		case furigana: {
			item.setFurigana(text);
			break;
		}
		case romaji: {
			item.setRomaji(text);
			break;
		}
		case itemtext: {
			item.setText(text);
			break;
		}
		case inner: {
			item.setElements(elements);
			break;
		}
		case comment: {
			item.setComment(text);
			break;
		}
		case chars: {
			Inner inner = new Inner();
			inner.setText(text);
			elements.add(inner);
			break;
		}
		case kanji: {
			Inner inner = new Inner();
			inner.setKanji(text);
			elements.add(inner);
			break;
		}
		case start: {
			line.setStartTime(parseInt(text));
			break;
		}
		case end: {
			line.setEndTime(parseInt(text));
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

	private int parseInt(String text) {
		return Integer.parseInt(text);
	}
}
