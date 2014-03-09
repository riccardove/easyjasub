package com.github.riccardove.easyjasub.mecab;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class MeCabLineMatcherTest extends EasyJaSubTestCase {

	public void test() {
		MeCabLineMatcher r = new MeCabLineMatcher("さ	動詞,自立,*,*,サ変・スル,未然レル接続,する,サ,サ");
		assertTrue(r.matches());
		assertEquals("さ", r.originalWord());
		assertEquals("動詞", r.grammar());
		assertEquals("サ", r.katakana());
	}

	public void test2() {
		MeCabLineMatcher r = new MeCabLineMatcher(
				"根づい	動詞,自立,*,*,五段・カ行イ音便,連用タ接続,根づく,ネヅイ,ネズイ");
		assertTrue(r.matches());
		assertEquals("根づい", r.originalWord());
		assertEquals("動詞", r.grammar());
		assertEquals("ネズイ", r.katakana());
	}

	public void test3() {
		MeCabLineMatcher r = new MeCabLineMatcher("生業	名詞,一般,*,*,*,*,生業,ナリワイ,ナリワイ");
		assertTrue(r.matches());
		assertEquals("生業", r.originalWord());
		assertEquals("名詞", r.grammar());
		assertEquals("ナリワイ", r.katakana());
	}

	public void test4() {
		MeCabLineMatcher r = new MeCabLineMatcher("フィオーレ 名詞,固有名詞,一般,*,*,*,*");
		assertTrue(r.matches());
		assertEquals("フィオーレ", r.originalWord());
		assertEquals("名詞", r.grammar());
		assertNull(r.katakana());
	}
}
