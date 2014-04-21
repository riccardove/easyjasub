package com.github.riccardove.easyjasub.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.ja.tokenattributes.BaseFormAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.InflectionAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.ReadingAttribute;
import org.apache.lucene.analysis.ja.util.ToStringUtil;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;

/**
 * Parser using Lucene JapaneseAnalyzer
 */
public class LuceneParser {

	public LuceneParser() throws IOException {
		analyzer = new JapaneseAnalyzer(Version.LUCENE_47, null,
				JapaneseTokenizer.Mode.SEARCH,
				JapaneseAnalyzer.getDefaultStopSet(),
				JapaneseAnalyzer.getDefaultStopTags());
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
		addAttributes(tokenStream);
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			if (tokenStream.hasAttributes()) {
				LuceneToken token = new LuceneToken();
				readOffset(tokenStream, token);
				readReading(tokenStream, token);
				readPartOfSpeech(tokenStream, token);
				readInflection(tokenStream, token);
				readBaseForm(tokenStream, token);
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
			token.setInflectionForm(inflection.getInflectionForm());
			token.setInflectionType(inflection.getInflectionType());
		}
	}

	private void readPartOfSpeech(TokenStream tokenStream, LuceneToken token) {
		PartOfSpeechAttribute partOfSpeech = tokenStream
				.getAttribute(PartOfSpeechAttribute.class);
		if (partOfSpeech != null) {
			String str = partOfSpeech.getPartOfSpeech();
			if (str != null) {
				token.setPartOfSpeech(ToStringUtil
						.getPOSTranslation(str));
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
