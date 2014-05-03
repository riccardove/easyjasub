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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EasyJaSubTrieTest extends EasyJaSubTestCase {

	public void testAdd() {
		EasyJaSubTrie<List<String>, Character> trie = new EasyJaSubTrie<List<String>, Character>();

		assertAdd(trie, "prova", "conto1");
		assertAdd(trie, "prova", "con32");
		assertAdd(trie, "prova", "cono1");
		assertAdd(trie, "prolisso", "conteo4");
		assertAdd(trie, "pesso", "cons2eo4");

	}

	private void assertAdd(EasyJaSubTrie<List<String>, Character> trie,
			String value, String content) {
		add(trie, value, content);
		assertGet(trie, value, content);
	}

	private void assertGet(EasyJaSubTrie<List<String>, Character> trie,
			String value, String content) {
		List<String> list = get(trie, value);
		assertNotNull(list);
		assertTrue(list.contains(content));
	}

	private List<String> get(EasyJaSubTrie<List<String>, Character> trie,
			String value) {
		return trie.get(new CharacterIterator(value)).getValue();
	}

	private void add(EasyJaSubTrie<List<String>, Character> trie, String value,
			String content) {
		EasyJaSubTrie.Value<List<String>> trieValue = trie
				.add(new CharacterIterator(value));
		List<String> list = trieValue.getValue();
		if (list == null) {
			list = new ArrayList<String>();
			trieValue.setValue(list);
		}
		list.add(content);
	}

	public void testSerialize() throws Exception {
		EasyJaSubTrie<List<String>, Character> trie = new EasyJaSubTrie<List<String>, Character>();
		assertAdd(trie, "prova", "conto1");
		assertAdd(trie, "prova", "con32");

		ByteArrayOutputStream stream = serialize(trie);

		EasyJaSubTrie<List<String>, Character> trie2 = deserialize(stream);

		assertGet(trie2, "prova", "conto1");
		assertGet(trie2, "prova", "con32");
	}

	@SuppressWarnings("unchecked")
	private EasyJaSubTrie<List<String>, Character> deserialize(
			ByteArrayOutputStream stream) throws Exception {

		ByteArrayInputStream fileIn = new ByteArrayInputStream(
				stream.toByteArray());
		ObjectInputStream in = new ObjectInputStream(fileIn);
		EasyJaSubTrie<List<String>, Character> trie = (EasyJaSubTrie<List<String>, Character>) in
				.readObject();
		in.close();
		fileIn.close();
		return trie;
	}

	private ByteArrayOutputStream serialize(Object e) throws Exception {
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(e);
		out.close();
		fileOut.close();
		return fileOut;
	}
}
