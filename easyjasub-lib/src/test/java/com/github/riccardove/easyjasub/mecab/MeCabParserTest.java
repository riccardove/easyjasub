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


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;

import com.github.riccardove.easyjasub.EasyJaSubReader;
import com.github.riccardove.easyjasub.EasyJaSubTestCase;
import com.github.riccardove.easyjasub.FakeEasyJaSubObserver;

public class MeCabParserTest extends EasyJaSubTestCase {

	public void test() throws Exception {
		MeCabParser parser = new MeCabParser();

		File file = getSampleFile("samplemecabout.txt");
		EasyJaSubReader reader = new EasyJaSubReader(file);
		ArrayList<String> meCabOutput = new ArrayList<String>();
		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
			println(line);
			meCabOutput.add(line);
		}
		assertEquals(43, meCabOutput.size());

		Observer observer = new Observer();
		MeCabSubtitleList list = parser.parse(meCabOutput, observer);
		assertNotNull(list);
		assertEquals(3, list.size());

		println(list.get(0).toString());
		println(list.get(1).toString());
		println(list.get(2).toString());
	}

	private class Observer extends FakeEasyJaSubObserver {
		@Override
		public void onMeCabUnknownGrammar(Set<String> elements,
				List<String> pronunciationErrors) {
			Assert.assertArrayEquals(new String[] {}, elements.toArray());
		}
	}
}