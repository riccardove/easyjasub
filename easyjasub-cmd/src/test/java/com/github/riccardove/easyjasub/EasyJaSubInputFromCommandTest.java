package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
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


import java.io.File;

import org.junit.Test;

public class EasyJaSubInputFromCommandTest extends EasyJaSubCmdTestCase {

	private static final String Sample1JaSub = "samples" + File.separator
			+ "sample1.jp.ass";
	private static final String Sample1TrSub = "samples" + File.separator
			+ "sample1.en.srt";
	
	@Test
	public void testGiveOnlyJapaneseSubtitleFile() throws Exception {
		assertTrue(new File(Sample1JaSub).exists());
		
		FakeEasyJaSubInputCommand fakeInput = new FakeEasyJaSubInputCommand();
		fakeInput.setJapaneseSubFileName(Sample1JaSub);
		EasyJaSubInputFromCommand obj = new EasyJaSubInputFromCommand(fakeInput);

		assertEquals("sample1", obj.getDefaultFileNamePrefix());
		assertInputFile(obj.getJapaneseSubFile(), "sample1.jp.ass");
		assertInputFile(obj.getTranslatedSubFile(), "sample1.en.srt");
		assertEquals(SubtitleFileType.ASS, obj.getJapaneseSubFileType());
		assertEquals(SubtitleFileType.SRT, obj.getTranslatedSubFileType());
		assertNotNull(obj.getCssFile());
		assertNotNull(obj.getOutputHtmlDirectory());
		assertEquals(obj.getOutputHtmlDirectory(), obj.getCssFile().getParentFile());
		assertNotNull(obj.getBdnXmlFile());
		assertNotNull(obj.getOutputIdxFile());
		assertNotNull(obj.getOutputJapaneseTextFile());
	}
	
	private static void assertInputFile(File file, String fileName) {
		assertNotNull(file);
		assertTrue(file.exists());
		assertEquals(fileName, file.getName());
	}
	
	@Test
	public void testGiveOnlyTranslatedSubtitleFile() throws Exception {
		assertTrue(new File(Sample1TrSub).exists());
		
		FakeEasyJaSubInputCommand fakeInput = new FakeEasyJaSubInputCommand();
		fakeInput.setTranslatedSubFileName(Sample1TrSub);
		EasyJaSubInputFromCommand obj = new EasyJaSubInputFromCommand(fakeInput);

		assertEquals("sample1", obj.getDefaultFileNamePrefix());
		assertInputFile(obj.getTranslatedSubFile(), "sample1.en.srt");
		assertInputFile(obj.getJapaneseSubFile(), "sample1.jp.ass");
		assertEquals(SubtitleFileType.ASS, obj.getJapaneseSubFileType());
		assertEquals(SubtitleFileType.SRT, obj.getTranslatedSubFileType());
	}
}
