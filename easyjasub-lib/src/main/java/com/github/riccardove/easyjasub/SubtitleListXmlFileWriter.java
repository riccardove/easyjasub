package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
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
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.riccardove.easyjasub.SubtitleItem.Inner;

class SubtitleListXmlFileWriter {

	public void write(SubtitleList s, File file) throws IOException,
			FileNotFoundException {
		f = new EasyJaSubXmlWriter(file);

		groupOpen(SubtitleListXmlElement.easyjasub);
		tag(SubtitleListXmlElement.title, s.getTitle());
		groupOpen(SubtitleListXmlElement.lines);
		for (SubtitleLine l : s) {
			groupOpen(SubtitleListXmlElement.line);
			tag(SubtitleListXmlElement.start,
					Integer.toString(l.getStartTime()));
			tag(SubtitleListXmlElement.end, Integer.toString(l.getEndTime()));
			tag(SubtitleListXmlElement.text, l.getSubText());
			tag(SubtitleListXmlElement.translation, l.getTranslation());
			if (l.getItems() != null) {
				groupOpen(SubtitleListXmlElement.items);
				for (SubtitleItem item : l.getItems()) {
					groupOpen(SubtitleListXmlElement.item);
					tag(SubtitleListXmlElement.grammar,
							item.getGrammarElement());
					tag(SubtitleListXmlElement.dictionary, item.getDictionary());
					tag(SubtitleListXmlElement.furigana, item.getFurigana());
					tag(SubtitleListXmlElement.romaji, item.getRomaji());
					tag(SubtitleListXmlElement.itemtext, item.getText());
					tag(SubtitleListXmlElement.comment, item.getComment());
					if (item.getElements() != null) {
						groupOpen(SubtitleListXmlElement.inner);
						for (Inner inner : item.getElements()) {
							tag(SubtitleListXmlElement.kanji, inner.getKanji());
							tag(SubtitleListXmlElement.chars, inner.getText());
						}
						groupClose(SubtitleListXmlElement.inner);
					}
					groupClose(SubtitleListXmlElement.item);
				}
				groupClose(SubtitleListXmlElement.items);
			}
			groupClose(SubtitleListXmlElement.line);
		}
		groupClose(SubtitleListXmlElement.lines);
		groupClose(SubtitleListXmlElement.easyjasub);
		f.close();
	}

	private EasyJaSubXmlWriter f;

	private void groupOpen(SubtitleListXmlElement name) throws IOException {
		f.groupOpen(name.toString());
	}

	private void groupClose(SubtitleListXmlElement name) throws IOException {
		f.groupClose(name.toString());
	}

	private void tag(SubtitleListXmlElement name, String content)
			throws IOException {
		f.tag(name.toString(), content);
	}
}
