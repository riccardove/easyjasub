package com.github.riccardove.easyjasub.lucene;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.ja.tokenattributes.BaseFormAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.InflectionAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.ReadingAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

/**
 * Parser using Lucene JapaneseAnalyzer
 */
public class LuceneParser {

	public LuceneParser(boolean ignoreDefaultWordSet) throws IOException {
		CharArraySet stopSet = ignoreDefaultWordSet ? JapaneseAnalyzer
				.getDefaultStopSet() : new CharArraySet(
				new ArrayList<String>(), true);
		Set<String> stopTags = ignoreDefaultWordSet ? JapaneseAnalyzer
				.getDefaultStopTags() : new HashSet<String>();
		analyzer = new JapaneseAnalyzer(null,
				JapaneseTokenizer.Mode.NORMAL, stopSet, stopTags);
	}

	private final JapaneseAnalyzer analyzer;

	/**
	 * Parses one line of text
	 */
	public List<LuceneToken> parse(String fieldName, String text)
			throws IOException {
		return readTokens(analyzer.tokenStream(fieldName, text));
	}

	private List<LuceneToken> readTokens(TokenStream tokenStream)
			throws IOException {
		ArrayList<LuceneToken> tokens = new ArrayList<LuceneToken>();
		HashMap<Integer, LuceneToken> tokensByStartOffset = new HashMap<Integer, LuceneToken>();
		addAttributes(tokenStream);
		tokenStream.reset();

		while (tokenStream.incrementToken()) {
			if (tokenStream.hasAttributes()) {
				LuceneToken token = new LuceneToken();

				readOffset(tokenStream, token);

				// Lucene may output multiple tokens for compound words
				LuceneToken tokenWithSameStartOffset = tokensByStartOffset
						.get(token.getStartOffset());
				if (tokenWithSameStartOffset != null) {
					if (token.getEndOffset() >= tokenWithSameStartOffset
							.getEndOffset()) {
						continue;
					} else {
						tokens.remove(tokenWithSameStartOffset);
					}
				}

				readReading(tokenStream, token);
				readPartOfSpeech(tokenStream, token);
				readInflection(tokenStream, token);
				readBaseForm(tokenStream, token);

				tokensByStartOffset.put(token.getStartOffset(), token);
				tokens.add(token);
			}
		}

		tokenStream.end();
		tokenStream.close();
		return tokens;
	}

	private void addAttributes(TokenStream tokenStream) {
		tokenStream.addAttribute(OffsetAttribute.class);
		tokenStream.addAttribute(ReadingAttribute.class);
		tokenStream.addAttribute(PartOfSpeechAttribute.class);
		tokenStream.addAttribute(InflectionAttribute.class);
		tokenStream.addAttribute(BaseFormAttribute.class);
	}

	private void readBaseForm(TokenStream tokenStream, LuceneToken token) {
		BaseFormAttribute baseForm = tokenStream
				.getAttribute(BaseFormAttribute.class);
		if (baseForm != null) {
			token.setBaseForm(baseForm.getBaseForm());
		}
	}

	private void readInflection(TokenStream tokenStream, LuceneToken token) {
		InflectionAttribute inflection = tokenStream
				.getAttribute(InflectionAttribute.class);
		if (inflection != null) {
			token.setInflectionForm(LuceneUtil
					.TranslateInflectedForm(inflection.getInflectionForm()));
			token.setInflectionType(LuceneUtil
					.TranslateInflectionType(inflection.getInflectionType()));
		}
	}

	private void readPartOfSpeech(TokenStream tokenStream, LuceneToken token) {
		PartOfSpeechAttribute partOfSpeech = tokenStream
				.getAttribute(PartOfSpeechAttribute.class);
		if (partOfSpeech != null) {
			String str = partOfSpeech.getPartOfSpeech();
			if (str != null) {
				token.setPartOfSpeech(LuceneUtil.TranslatePartOfSpeech(str));
			}
		}
	}

	private void readReading(TokenStream tokenStream, LuceneToken token) {
		ReadingAttribute reading = tokenStream
				.getAttribute(ReadingAttribute.class);
		if (reading != null) {
			token.setPronunciation(reading.getPronunciation());
			token.setReading(reading.getReading());
		}
	}

	private void readOffset(TokenStream tokenStream, LuceneToken token) {
		OffsetAttribute offset = tokenStream
				.getAttribute(OffsetAttribute.class);
		if (offset != null) {
			token.setStartOffset(offset.startOffset());
			token.setEndOffset(offset.endOffset());
		}
	}

	public void close() {
		analyzer.close();
	}
}
