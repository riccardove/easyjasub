package com.github.riccardove.easyjasub;

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


import java.io.File;

public class InputMeCabTest extends EasyJaSubTestCase {

	public void test() throws Exception {
		File meCabOutputFile = getSampleFile("samplemecabout.txt");
		File subtitleFile = getSampleFile("sample2.jp.ass");

		Observer observer = new Observer();

		SubtitleList subs = SubtitleListJapaneseSubFileReaderTest
				.getSubtitleList(observer, subtitleFile);
		assertEquals(3, subs.size());

		new InputMeCab(observer, null).run(subs, meCabOutputFile);


		for (SubtitleLine line : subs) {
			println(line.toString());
		}

		SubtitleLine line1 = subs.get(0);
		SubtitleLine line2 = subs.get(1);
		SubtitleLine line3 = subs.get(2);

		assertNotNull(line1.getItems());
		assertNotNull(line2.getItems());
		assertNotNull(line3.getItems());

	}

	private class Observer extends FakeEasyJaSubObserver {

	}
}

