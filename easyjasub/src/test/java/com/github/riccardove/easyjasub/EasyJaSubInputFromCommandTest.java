package com.github.riccardove.easyjasub;

import java.io.File;

import org.junit.Test;

import junit.framework.TestCase;

public class EasyJaSubInputFromCommandTest extends TestCase {

	private static final String Sample1 = "samples\\sample1.ja.ass";
	
	@Test
	public void test1() throws Exception {
		assertTrue(new File(Sample1).exists());
		
		FakeEasyJaSubInputCommand fakeInput = new FakeEasyJaSubInputCommand();
		fakeInput.setJapaneseSubFileName(Sample1);
		
		EasyJaSubInputFromCommand obj = new EasyJaSubInputFromCommand(fakeInput);
		assertNotNull(obj.getJapaneseSubFile());
		assertEquals("sample1", obj.getDefaultFileNamePrefix());
	}
	
	
}
