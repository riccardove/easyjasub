package com.github.riccardove.easyjasub.kurikosu;

import org.kurikosu.lang.Katakana;
import org.kurikosu.transcription.Katakana2Romaji;

import com.github.riccardove.easyjasub.EasyJaSubException;

public final class Kurikosu {

	public static KurikosuWord convertKatakanaToHiragana(String katakana)
			throws EasyJaSubException {
		if (!Katakana.isKatakana(katakana)) {
			return null;
		}
		try {
			KurikosuWord word = new KurikosuWord();
			Katakana k = new Katakana(katakana);
			word.setRomaji(new Katakana2Romaji().transcribe(k).getValue());
			word.setHiragana(new Katakana2Hiragana().transcribe(k).getValue());
			return word;
		} catch (Throwable e) {
			throw new EasyJaSubException("Error converting \"" + katakana
					+ "\": " + e.getMessage(), e);
		}
	}
}
