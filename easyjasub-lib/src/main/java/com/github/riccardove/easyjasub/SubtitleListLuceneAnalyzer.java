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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.riccardove.easyjasub.lucene.LuceneParser;
import com.github.riccardove.easyjasub.lucene.LuceneToken;

public class SubtitleListLuceneAnalyzer {

	private final EasyJaSubObserver observer;

	public SubtitleListLuceneAnalyzer(EasyJaSubObserver observer) {
		this.observer = observer;
	}

	private void analyzeLine(SubtitleLine line, List<LuceneToken> tokens,
			List<String> pronunciationErrors) {
		ArrayList<SubtitleItem> items = new ArrayList<SubtitleItem>();

		int position = 0;
		for (LuceneToken token : tokens) {
			if (token.getStartOffset() > position) {
				SubtitleItem subsItem = new SubtitleItem();
				// we have missed a symbol or otherwise unparsed text
				String text = line.getJapanese().substring(position,
						token.getStartOffset());
				subsItem.setText(text);
				addItem(items, subsItem);
			}

			SubtitleItem subsItem = new SubtitleItem();
			// TODO: merge suffix tokens
			String text = line.getJapanese().substring(token.getStartOffset(),
					token.getEndOffset());
			String pos = token.getPartOfSpeech();
			subsItem.setPartOfSpeech(pos);
			subsItem.setText(text);
			subsItem.setBaseForm(token.getBaseForm());

			String reading = token.getReading();
			if (reading != null) {
				EasyJaSubJapaneseConvertedText word = EasyJaSubJapaneseTextConverter
						.convertKatakanaToHiragana(reading, pronunciationErrors);
				if (word != null) {
					if (!word.getHiragana().equals(text)) {
						trySetFurigana(text, word.getHiragana(), subsItem);
					}
					subsItem.setRomaji(word.getRomaji());
				}
			}

			addItem(items, subsItem);
			position = token.getEndOffset();
		}
		if (position < line.getJapanese().length()) {
			// unparsed text at the end
			SubtitleItem subsItem = new SubtitleItem();
			String text = line.getJapanese().substring(position,
					line.getJapanese().length());
			subsItem.setText(text);
			addItem(items, subsItem);
		}
		if (items.size() > 0) {
			line.setItems(items);
		}
	}

	private void addItem(ArrayList<SubtitleItem> items, SubtitleItem subsItem) {
		items.add(subsItem);
	}

	public void run(SubtitleList subs) throws EasyJaSubException {
		List<String> pronunciationErrors = new ArrayList<String>();

		try {
			LuceneParser parser = new LuceneParser(false);
			for (SubtitleLine line : subs) {
				if (line.getJapaneseSubKey() != null) {
					parseLine(pronunciationErrors, parser, line);
				}
			}
			parser.close();
		} catch (IOException ex) {
			throw new EasyJaSubException("I/O Error while parsing", ex);
		}

		if (pronunciationErrors.size() > 0) {
			observer.onLuceneErrors(pronunciationErrors);
		}
	}

	private void parseLine(List<String> pronunciationErrors,
			LuceneParser parser, SubtitleLine line) throws IOException,
			EasyJaSubException {
		try {
			List<LuceneToken> tokens = parser.parse("easyjasub",
					line.getJapanese());
			if (tokens != null && tokens.size() > 0) {
				analyzeLine(line, tokens, pronunciationErrors);
			}
		} catch (IOException ex) {
			throw new EasyJaSubException("I/O Error while reading line "
					+ line.getIndex() + ": " + line.getSubText(), ex);
		} catch (Throwable ex) {
			throw new IllegalArgumentException("Error while processing line "
					+ line.getIndex() + ": " + line.getSubText(), ex);
		}
	}

	private void trySetFurigana(String text, String furigana, SubtitleItem item) {

		List<SubtitleItem.Inner> list = new SubtitleItemElementsList()
				.createElementsList(text, furigana, item);
		if (list != null) {
			item.setElements(list);
			item.setFurigana(furigana);
		}
	}
}
