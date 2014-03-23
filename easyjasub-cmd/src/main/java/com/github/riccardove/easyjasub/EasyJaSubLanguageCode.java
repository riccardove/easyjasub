package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub-cmd
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
