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
import java.util.List;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class LuceneParserTest extends EasyJaSubTestCase {

	public void testParseIgnoreDefaultWordSet() throws Exception {
		LuceneParser parser = new LuceneParser(true);

		String text = "魔法は　普通に売り買いされ 人々の生活に根づいていた。";
		List<LuceneToken> tokens = parse(parser, text);
		assertEquals(6, tokens.size());

		tokens = parse(parser, "依頼に応じて仕事をする。");
		assertNotNull(tokens);
		assertEquals(3, tokens.size());

		parser.close();
	}

	public void testParse() throws Exception {
		LuceneParser parser = new LuceneParser(false);

		String text = "魔法は　普通に売り買いされ 人々の生活に根づいていた。";
		List<LuceneToken> tokens = parse(parser, text);
		assertEquals(15, tokens.size());

		tokens = parse(parser, "依頼に応じて仕事をする。");
		assertNotNull(tokens);
		assertEquals(7, tokens.size());

		parser.close();
	}

	/**
	 * "魔導士" should be a single word
	 */
	public void testUnknownCompound() throws Exception {
		String text = "人々は　彼らを魔導士と呼んだ。";

		LuceneParser parser = new LuceneParser(false);
		List<LuceneToken> tokens = parse(parser, text);
		assertEquals(10, tokens.size());
	}

	private List<LuceneToken> parse(LuceneParser parser, String text)
			throws IOException {
		println(text);
		List<LuceneToken> tokens = parser.parse("testname", text);
		assertNotNull(tokens);
		for (LuceneToken token : tokens) {
			println(text
					.substring(token.getStartOffset(), token.getEndOffset())
					+ " ("
					+ token.getStartOffset()
					+ ","
					+ token.getEndOffset()
					+ ") "
					+ token.getPartOfSpeech()
					+ " "
					+ token.getBaseForm()
					+ " "
					+ token.getInflectionType()
					+ ":"
					+ token.getInflectionForm()
					+ " "
					+ token.getPronunciation() + " " + token.getReading());
		}
		return tokens;
	}

}
