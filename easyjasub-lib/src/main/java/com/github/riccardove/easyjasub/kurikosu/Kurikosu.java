package com.github.riccardove.easyjasub.kurikosu;

import org.kurikosu.lang.JapaneseWord;

public final class Kurikosu {

	public static String convertKatakanaToHiragana(String katakana) {
		try {
			return new Katakana2Hiragana().transcribe(
					new JapaneseWord(katakana).getKatakana()).getValue();
		} catch (RuntimeException ex) {
			return null;
		}
	}
}
