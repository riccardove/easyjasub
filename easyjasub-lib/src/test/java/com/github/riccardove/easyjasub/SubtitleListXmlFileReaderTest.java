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


public class SubtitleListXmlFileReaderTest extends EasyJaSubTestCase {

	public void testSample() throws Exception {
		SubtitleList list = new SubtitleList();
		new SubtitleListXmlFileReader(list).read(getSampleFile("sample1.xml"));

		assertEquals("Beginning", list.getTitle());
		assertEquals(49, list.size());
		assertEquals("The Kingdom of Fiore... ", list.get(0).getTranslation());
		assertEquals("人口１，７００万の永世中立国。", list.get(1).getJapanese());
	}
}
