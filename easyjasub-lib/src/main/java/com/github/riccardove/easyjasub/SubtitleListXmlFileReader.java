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

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.SubtitleItem.Inner;

class SubtitleListXmlFileReader implements
		EasyJaSubXmlHandler<SubtitleListXmlElement> {

	private final SubtitleList list;
	private SubtitleLine line;
	private ArrayList<SubtitleItem> items;
	private SubtitleItem item;
	private ArrayList<Inner> elements;

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

	@Override
	public void onStartElement(SubtitleListXmlElement element) {
		switch (element) {
		case line: {
			line = list.add();
			break;
		}
		case items: {
			items = new ArrayList<SubtitleItem>();
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
			line.setItems(items);
			items = null;
			break;
		}
		case translation: {
			line.setTranslatedText(text);
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
}
