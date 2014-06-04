package com.github.riccardove.easyjasub.dictionary;

import java.io.File;

import org.junit.Test;

import com.github.riccardove.easyjasub.EasyJaSubSerialize;
import com.github.riccardove.easyjasub.EasyJaSubTestCase;
import com.github.riccardove.easyjasub.FakeEasyJaSubObserver;

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
				new FakeEasyJaSubObserver());
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
