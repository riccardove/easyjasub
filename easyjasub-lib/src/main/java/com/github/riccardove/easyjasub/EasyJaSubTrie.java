package com.github.riccardove.easyjasub;

import java.util.ArrayList;
import java.util.HashMap;

// TODO
class EasyJaSubTrie<T> {

	public EasyJaSubTrie() {
		nodes = new HashMap<Character, EasyJaSubTrie.Node<T>>(1000);
	}

	public HashMap<Character, Node<T>> nodes;

	public void add(String word, T obj) {

	}

	private static class Node<T> {
		public HashMap<Character, Node<T>> nextNodes;
		public ArrayList<T> objects;
	}
}
