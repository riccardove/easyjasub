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


import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class SubtitleListJapaneseSubFileReaderTest {

	@Test
	public void test() throws Exception {
		//System.out.println(SystemProperty.getUserDir());
		File file = new File("samples\\sample1.ja.ass");
		assertTrue(file.exists());
		SubtitleList s = new SubtitleList("test");
		new SubtitleListJapaneseSubFileReader().
			readJapaneseSubtitles(s, file, SubtitleFileType.ASS, new Observer());
		assertTrue(s.size() > 0);
		for (SubtitleLine line : s) {
			assertTrue(line.isJa());
			assertFalse(line.isTranslation());
			//System.out.println(line.getIndex() + " (" + line.getStartTime() + "->" + line.getEndTime() + ") " + line.getJapaneseText());
		}
		//System.out.println(s.size());
	}

	private class Observer extends FakeEasyJaSubObserver {
		
	}
}