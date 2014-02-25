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

import com.github.riccardove.easyjasub.inputnihongojtalk.NihongoJTalkSubtitleList;

public class SubtitleListTranslatedSubFileReaderTest {

	@Test
	public void test() throws Exception {
		NihongoJTalkSubtitleList s = new NihongoJTalkSubtitleList("test");

		File fileJa = new File("samples\\sample1.ja.ass");
		assertTrue(fileJa.exists());
		new SubtitleListJapaneseSubFileReader().
			readJapaneseSubtitles(s, fileJa, SubtitleFileType.ASS, new Observer());

		File file = new File("samples\\sample1.en.srt");
		assertTrue(file.exists());
		new SubtitleListJapaneseSubFileReader().
			readJapaneseSubtitles(s, file, SubtitleFileType.SRT, new Observer());

	}

	private class Observer extends FakeEasyJaSubObserver {
		
	}
}
