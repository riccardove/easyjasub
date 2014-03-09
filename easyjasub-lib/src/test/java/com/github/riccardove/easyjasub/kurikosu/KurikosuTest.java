package com.github.riccardove.easyjasub.kurikosu;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class KurikosuTest extends EasyJaSubTestCase {

	public void test() throws Exception {
		assertKatakanaToHiragana("シ", "し");
	}

	private void assertKatakanaToHiragana(String katakana, String hiragana)
			throws Exception {
		assertEquals(hiragana, Kurikosu.convertKatakanaToHiragana(katakana));
	}
}
