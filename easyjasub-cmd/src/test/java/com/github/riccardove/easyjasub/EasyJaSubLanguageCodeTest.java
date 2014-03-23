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
