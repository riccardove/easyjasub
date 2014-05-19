package com.github.riccardove.easyjasub.dictionary;

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class DictionaryGlossTest extends EasyJaSubTestCase {

	public void testClean() {
		assertClean("counter for buildings", "buildings");
	}

	private void assertClean(String dictElem, String result) {
		assertEquals(result, DictionaryGloss.clean(dictElem));
	}
}
