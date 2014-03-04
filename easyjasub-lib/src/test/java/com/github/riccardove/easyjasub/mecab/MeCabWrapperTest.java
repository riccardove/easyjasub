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


import org.junit.Ignore;
import org.junit.Test;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

@Ignore
public class MeCabWrapperTest extends EasyJaSubTestCase {

	@Test
	public void testLoad() {
		MeCabWrapper mecab = MeCabWrapper
				.load("C:\\Program Files (x86)\\MeCab\\bin\\libmecab.dll");
		assertEquals("0", mecab.getVersion());
	}

	@Test
	public void testLoadFailure() {
		try {
			MeCabWrapper
					.load("C:\\Program Files (x86)\\MeCab\\binERROR\\libmecab.dll");
			fail();
		} catch (Exception ex) {

		}
	}

	@Test
	public void test() {
		MeCabWrapper mecab = MeCabWrapper
				.load("C:\\Program Files (x86)\\MeCab\\bin\\libmecab.dll");
		assertEquals("0", mecab.getVersion());
		MeCabModel model = mecab.createModel(null);
		MeCabTagger tagger = model.createTagger();
		MeCabLattice lattice = model.createLattice();
		lattice.setSentence("魔法は　普通に売り買いされ 人々の生活に根づいていた");
		boolean result = tagger.parse(lattice);
		assertTrue(result);
	}
}
