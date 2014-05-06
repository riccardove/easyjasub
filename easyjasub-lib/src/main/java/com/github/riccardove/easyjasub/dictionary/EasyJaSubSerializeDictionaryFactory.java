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

import com.github.riccardove.easyjasub.EasyJaSubObserver;
import com.github.riccardove.easyjasub.EasyJaSubSerialize;

public class EasyJaSubSerializeDictionaryFactory {

	private final EasyJaSubSerialize<EasyJaSubDictionary> serialize;
	private final File dictionaryFile;
	private final File cacheFile;
	private final EasyJaSubObserver observer;

	public EasyJaSubSerializeDictionaryFactory(File dictionaryFile,
			File cacheFile, EasyJaSubObserver observer) {
		this.dictionaryFile = dictionaryFile;
		this.cacheFile = cacheFile;
		this.observer = observer;
		serialize = new EasyJaSubSerialize<EasyJaSubDictionary>();
	}

	public EasyJaSubDictionary createDictionary() {
		EasyJaSubDictionary dictionary = deserialize();
		if (dictionary == null) {
			dictionary = createNew();
			// TODO serialize(dictionary);
		}
		return dictionary;
	}

	private void serialize(EasyJaSubDictionary dictionary) {
		if (cacheFile == null) {
			return;
		}
		try {
			observer.onDictionarySerialize(cacheFile);
			if (cacheFile.exists()) {
				cacheFile.delete();
			}
			serialize.serializeToFile(cacheFile, dictionary);
			observer.onDictionarySerialized(cacheFile);
		} catch (Exception ex) {
			observer.onDictionarySerializeError(cacheFile, ex);
			return;
		}
	}

	private EasyJaSubDictionary createNew() {
		if (dictionaryFile == null || !dictionaryFile.exists()) {
			return null;
		}
		EasyJaSubDictionary dictionary;
		dictionary = new EasyJaSubDictionary(null);
		try {
			observer.onDictionaryJMDictParse(dictionaryFile);
			dictionary.addJMDict(dictionaryFile);
			observer.onDictionaryJMDictParsed(dictionaryFile);
		} catch (Exception ex) {
			observer.onDictionaryJMDictParseError(dictionaryFile, ex);
			return null;
		}
		return dictionary;
	}

	private EasyJaSubDictionary deserialize() {
		if (cacheFile == null || !cacheFile.exists()) {
			return null;
		}
		try {
			observer.onDictionaryDeserialize(cacheFile);
			EasyJaSubDictionary dictionary = serialize
					.deserializeFromFile(cacheFile);
			observer.onDictionaryDeserialized(cacheFile, dictionary);
			return dictionary;
		} catch (Exception ex) {
			observer.onDictionaryDeserializeError(cacheFile, ex);
			return null;
		}
	}
}
