package com.github.riccardove.easyjasub.lucene;

import java.io.IOException;
import java.util.List;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class LuceneParserTest extends EasyJaSubTestCase {

	public void testParse() throws Exception {
		LuceneParser parser = new LuceneParser();

		String text = "魔法は　普通に売り買いされ 人々の生活に根づいていた。";
		List<LuceneToken> tokens = parse(parser, text);
		assertEquals(6, tokens.size());

		tokens = parse(parser, "依頼に応じて仕事をする。");
		assertNotNull(tokens);
		assertEquals(3, tokens.size());

		parser.close();
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
