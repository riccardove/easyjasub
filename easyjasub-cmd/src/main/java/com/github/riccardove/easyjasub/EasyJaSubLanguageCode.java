package com.github.riccardove.easyjasub;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.riccardove.easyjasub.nvi18n.NvI18NLanguageCode;

final class EasyJaSubLanguageCode {

	private static final Pattern LanguageCodePattern = Pattern
			.compile("\\.(...?)\\.");

	public static String getLanguageCodeFromFileName(String fileName) {
		Matcher m = LanguageCodePattern.matcher(fileName);
		int start = 0;
		while (m.find(start)) {
			String languageCode = m.group(1);
			if (NvI18NLanguageCode.isLanguageCode(languageCode)) {
				return languageCode;
			}
			start = m.end(1) - 1;
		}
		return null;
	}

	public static String removeLanguageCodeFromFileName(String fileName) {
		Matcher m = LanguageCodePattern.matcher(fileName);
		int start = 0;
		while (m.find(start)) {
			if (NvI18NLanguageCode.isLanguageCode(m.group(1))) {
				return fileName.substring(0, m.start())
						+ fileName.substring(m.end(1), fileName.length());
			}
			start = m.end(1) - 1;
		}
		return fileName;
	}

	public static boolean isJapaneseLanguageFromFileName(String fileName) {
		Matcher m = LanguageCodePattern.matcher(fileName);
		int start = 0;
		while (m.find(start)) {
			String languageCode = m.group(1);
			if (isJapaneseLanguageCode(languageCode)) {
				return true;
			}
			start = m.end(1) - 1;
		}
		return false;
	}

	public static String removeJapaneseLanguageCodeFromFileName(String fileName) {
		if (fileName.contains(".ja.")) {
			fileName = fileName.replace(".ja.", ".");
		} else if (fileName.contains(".jp.")) { // special case: accept country
												// code
			fileName = fileName.replace(".jp.", ".");
		} else if (fileName.contains(".jpn.")) {
			fileName = fileName.replace(".jpn.", ".");
		}
		return fileName;
	}

	public static boolean isJapaneseLanguageCode(String language) {
		return "jp".equals(language) || "ja".equals(language)
				|| "jpn".equals(language);
	}
}
