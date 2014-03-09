package com.github.riccardove.easyjasub.mecab;

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
