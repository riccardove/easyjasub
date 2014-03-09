package com.github.riccardove.easyjasub;

import org.junit.Ignore;

@Ignore
public class JapaneseCharTest extends EasyJaSubTestCase {

	public void test() {
		assertKatakanaToHiragana('シ', 'し');
	}

	private void assertKatakanaToHiragana(char katakanaChar, char hiraganaChar) {
		assertEquals(hiraganaChar,
				JapaneseChar.katakanaToHiragana(katakanaChar));
	}
}
