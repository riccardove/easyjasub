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

import org.junit.Test;

import com.github.riccardove.easyjasub.EasyJaSubSerialize;
import com.github.riccardove.easyjasub.EasyJaSubTestCase;
import com.github.riccardove.easyjasub.EasyJaSubObserverBase;

public class EasyJaSubSerializeDictionaryFactoryTest extends EasyJaSubTestCase {

	@Test
	public void testSerialize() throws Exception {
		File sampleFile = getJMDictSampleFile();
		if (sampleFile == null || !sampleFile.exists()) {
			return;
		}

		File serializeFile = getJMDictSampleSerializeFile();
		if (serializeFile == null) {
			return;
		}

		if (serializeFile.exists()) {
			serializeFile.delete();
		}

		EasyJaSubSerializeDictionaryFactory obj = new EasyJaSubSerializeDictionaryFactory(
				sampleFile, serializeFile,
				new EasyJaSubObserverBase());
		EasyJaSubDictionary dictionary1 = obj.createDictionary();
		assertNotNull(dictionary1);
		assertEquals(3, dictionary1.getEntry("基地局").getLength());

		EasyJaSubSerialize<EasyJaSubDictionary> serialize = new EasyJaSubSerialize<EasyJaSubDictionary>();
		EasyJaSubDictionary dictionary2 = serialize
				.deserializeFromFile(serializeFile);
		assertNotNull(dictionary2);

		assertEquals(3, dictionary2.getEntry("基地局").getLength());
	}
}
