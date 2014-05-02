package com.github.riccardove.easyjasub;

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
