package com.github.riccardove.easyjasub;

public class EasyJaSubLanguageCodeTest extends EasyJaSubCmdTestCase {

	public void testRemoveLanguageCodeFromFileName() {
		assertEquals("aname.txt",
				EasyJaSubLanguageCode
						.removeLanguageCodeFromFileName("aname.it.txt"));
		assertEquals("aname.txt",
				EasyJaSubLanguageCode
						.removeLanguageCodeFromFileName("aname.ita.txt"));
		assertEquals("aname.txt",
				EasyJaSubLanguageCode
						.removeLanguageCodeFromFileName("aname.jpn.txt"));
		assertEquals("aname.txt",
				EasyJaSubLanguageCode
						.removeLanguageCodeFromFileName("aname.ja.txt"));
		assertEquals("aname.txt",
				EasyJaSubLanguageCode
						.removeLanguageCodeFromFileName("aname.en.txt"));
	}

	public void testGetLanguageCodeFromFileName() {
		assertEquals("it",
				EasyJaSubLanguageCode
						.getLanguageCodeFromFileName("aname.it.txt"));
		assertEquals("ita",
				EasyJaSubLanguageCode
						.getLanguageCodeFromFileName("aname.ita.txt"));
		assertEquals("jpn",
				EasyJaSubLanguageCode
						.getLanguageCodeFromFileName("aname.jpn.txt"));
		assertEquals("ja",
				EasyJaSubLanguageCode
						.getLanguageCodeFromFileName("aname.ja.txt"));
		assertEquals("en",
				EasyJaSubLanguageCode
						.getLanguageCodeFromFileName("aname.en.txt"));
	}

	public void testIsJapaneseLanguageFromFileName() {
		assertTrue(EasyJaSubLanguageCode
				.isJapaneseLanguageFromFileName("aname.ja.txt"));
		assertTrue(EasyJaSubLanguageCode
				.isJapaneseLanguageFromFileName("aname.mec.ja.txt"));
		assertTrue(EasyJaSubLanguageCode
				.isJapaneseLanguageFromFileName("aname.mec.jp.ja.txt"));
		assertFalse(EasyJaSubLanguageCode
				.isJapaneseLanguageFromFileName("aname.mec.ja"));
		assertFalse(EasyJaSubLanguageCode
				.isJapaneseLanguageFromFileName("aname.mec.en.ja"));
	}

}
