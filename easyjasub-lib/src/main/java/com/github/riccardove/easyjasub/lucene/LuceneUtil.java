package com.github.riccardove.easyjasub.lucene;

import org.apache.lucene.analysis.ja.util.ToStringUtil;

public final class LuceneUtil {

	private LuceneUtil() {

	}

	/**
	 * Romanize katakana with modified hepburn
	 */
	public static String KatakanaToRomaji(String text) {
		return ToStringUtil.getRomanization(text);
	}

	public static String TranslatePartOfSpeech(String partOfSpeech) {
		String translation = ToStringUtil.getPOSTranslation(partOfSpeech);
		return translation != null ? translation : partOfSpeech;
	}

	public static String TranslateInflectedForm(String inflectedForm) {
		String translation = ToStringUtil
				.getInflectedFormTranslation(inflectedForm);
		return translation != null ? translation : inflectedForm;
	}

	public static String TranslateInflectionType(String inflectionType) {
		String translation = ToStringUtil
				.getInflectionTypeTranslation(inflectionType);
		return translation != null ? translation : inflectionType;
	}

}
