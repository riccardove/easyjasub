package com.github.riccardove.easyjasub.mecab;

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
