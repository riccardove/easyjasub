package com.github.riccardove.easyjasub;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;

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



public final class JapaneseChar {

	private JapaneseChar() {
		
	}
	
	public static boolean isNonJapaneseChar(char c)
	{
		return !isSmallSizeJapaneseChar(c) && !isIdeogram(c);
	}

	private static boolean isIdeogram(char c) {
		return Character.isIdeographic(c);
	}
	
	public static boolean isSmallSizeJapaneseChar(char c) {
		Character.UnicodeBlock block = getUnicodeBlock(c);
		return isHiraganaOrKatakana(block) || block == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION;
		
	}

	private static UnicodeBlock getUnicodeBlock(char c) {
		return Character.UnicodeBlock.of(c);
	}

	private static boolean isHiraganaOrKatakana(Character.UnicodeBlock block) {
		return block == Character.UnicodeBlock.HIRAGANA ||
				block == Character.UnicodeBlock.KATAKANA;
	}

	private static boolean isJapaneseIdeograph(char c) {
		return isIdeogram(c) || isHiraganaOrKatakana(getUnicodeBlock(c));
	}
	
	public static String getJapaneseKey(String content) {
		ArrayList<Character> japaneseChars = new ArrayList<Character>();
		for (char c : content.toCharArray()) {
			if (JapaneseChar.isJapaneseIdeograph(c)) {
				japaneseChars.add(c);
			}
		}
		if (japaneseChars.size() == 0) {
			return null;
		}
		return CommonsLangStringUtils.charListToString(japaneseChars);
	}

//	private static boolean is(char c) {
//		return Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
//				Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS ||
//				Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
//	}
}
