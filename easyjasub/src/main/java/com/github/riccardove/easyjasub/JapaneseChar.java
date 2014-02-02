package com.github.riccardove.easyjasub;


class JapaneseChar {

	public static boolean isJapaneseChar(char c)
	{
		return isLittle(c) || Character.isIdeographic(c);
	}
	
	public static boolean isLittle(char c) {
		return
				Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HIRAGANA ||
				Character.UnicodeBlock.of(c) == Character.UnicodeBlock.KATAKANA ||
				Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION;
		
	}

	public static boolean is(char c) {
		return Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
				Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS ||
				Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}
}
