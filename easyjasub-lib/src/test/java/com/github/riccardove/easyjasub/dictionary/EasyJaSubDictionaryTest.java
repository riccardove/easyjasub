package com.github.riccardove.easyjasub.dictionary;

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

import com.github.riccardove.easyjasub.EasyJaSubTestCase;

public class EasyJaSubDictionaryTest extends EasyJaSubTestCase {
	/**
	 * This test will not run if JMDict_e.xml is not found in easyjasub-cmd home
	 * dir
	 */
	public void testReadFile() throws Exception {
		File jmDictFile = getJMDictFile();
		if (!jmDictFile.exists()) {
			return;
		}

		EasyJaSubDictionary dictionary = new EasyJaSubDictionary(null);
		dictionary.addJMDict(jmDictFile);

		EasyJaSubDictionaryEntry entry = dictionary.getEntry("産");
		assertNotNull(entry);

		entry = dictionary.getEntry("厚生労働大臣");
		assertNotNull(entry);
		final String gloss = "Minister of Health, Labour and Welfare";
		assertTrue(entry.getSenses().iterator().next().getGloss().iterator()
				.next().startsWith(gloss.substring(0, 14)));

	}

}
