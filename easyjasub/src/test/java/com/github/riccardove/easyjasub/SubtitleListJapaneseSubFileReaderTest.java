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

public class SubtitleListJapaneseSubFileReaderTest extends EasyJaSubTestCase {

	@Test
	public void test() throws Exception {
		File file = getSampleFile("sample1.ja.ass");
		
		SubtitleList s = new SubtitleList();
		new SubtitleListJapaneseSubFileReader().
			readJapaneseSubtitles(s, file, SubtitleFileType.ASS, new Observer(), null);
		assertTrue(s.size() > 0);
		for (SubtitleLine line : s) {
			if (line.getJapanese() == null) {
				assertNotNull(toString(line), line.getSubText());
				assertNull(toString(line), line.getJapaneseSubKey());
			}
			else {
				assertNotNull(toString(line), line.getJapaneseSubKey());
			}
			assertNull(toString(line), line.getTranslation());
		}
	}

	private static String toString(SubtitleLine line) {
		return line.getIndex() + " (" + line.getStartTime() + "->" + line.getEndTime() + ") " + line.getJapanese();
	}
	
	private class Observer extends FakeEasyJaSubObserver {
		
	}
}
