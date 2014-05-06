package com.github.riccardove.easyjasub.dictionary;

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


import java.util.regex.Pattern;

import com.github.riccardove.easyjasub.commons.CommonsLangStringUtils;

/**
 * Simplifies glosses to obtain a word that may fit in a subtitle line
 */
public class DictionaryGloss {

	// TODO this should be an instance method
	public static String choose(String dictElem, String romaji) {
		dictElem = clean(dictElem);
		// simplify verbs
		// if (pos.contains("verb") && dictElem.startsWith("to ")) {
		// dictElem = dictElem.substring(3);
		// }
		// discard elements of more than 4 words
		if (isLong(dictElem)) {
			return null;
		}
		// single letters are not considered, nor excessively long items
		if (dictElem.length() > 1 && dictElem.length() < 20
				&& dictElem.length() < (romaji.length() * 3 + 5)) {
			return dictElem;
		}
		return null;
	}

	private static boolean isLong(String dictElem) {
		return CommonsLangStringUtils.countMatches(dictElem, " ") >= 4;
	}

	private static String clean(String dictElem) {
		return ItemClearPattern.matcher(dictElem).replaceAll("").trim();
	}

	public static String getLong(String dictElem) {
		dictElem = clean(dictElem);
		if (isLong(dictElem)) {
			int lastSpace = CommonsLangStringUtils.ordinalIndexOf(dictElem,
					" ", 3);
			if (lastSpace > 0) {
				return dictElem.substring(0, lastSpace) + "...";
			}
		}
		if (dictElem.length() > 20) {
			return dictElem.substring(0, 15) + "...";
		}
		return dictElem;
	}

	private static final Pattern ItemClearPattern = Pattern
			.compile("\\([^)]+\\)|!|\\?|(euph\\. for )|\\.");

}
