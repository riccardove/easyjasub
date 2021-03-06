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


import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * An implementation of a Trie
 * 
 * @param <TValue>
 *            Class associated to each entry of the trie
 * @param <TItem>
 *            Item used to index the trie
 */
public class EasyJaSubTrie<TValue, TItem> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7781712881636111120L;

	public EasyJaSubTrie() {
		startNode = new Node<TValue, TItem>();
	}

	private final Node<TValue, TItem> startNode;

	private Node<TValue, TItem> addNode(Node<TValue, TItem> node,
			Iterator<TItem> it) {
		if (!it.hasNext()) {
			return node;
		} else {
			TItem c = it.next();
			if (node.nextNodes == null) {
				node.nextNodes = new HashMap<TItem, Node<TValue, TItem>>();
			}
			Node<TValue, TItem> nextNode = node.nextNodes.get(c);
			if (nextNode == null) {
				nextNode = new Node<TValue, TItem>();
				node.nextNodes.put(c, nextNode);
			}
			return addNode(nextNode, it);
		}
	}

	private Node<TValue, TItem> getNode(Node<TValue, TItem> node,
			Iterator<TItem> it) {
		if (!it.hasNext()) {
			return node;
		}
		if (node.nextNodes == null) {
			return node;
		}
		Node<TValue, TItem> nextNode = node.nextNodes.get(it.next());
		if (nextNode == null) {
			return node;
		}
		return getNode(nextNode, it);
	}

	public Value<TValue> get(Iterator<TItem> word) {
		return getNode(startNode, word);
	}

	public Value<TValue> add(Iterator<TItem> word) {
		return addNode(startNode, word);
	}

	/**
	 * Allows to associate a generic object to a word
	 * 
	 * @param <TValue>
	 *            The generic object type
	 */
	public static interface Value<TValue> {
		/**
		 * @return The associated value, or null if none is set
		 */
		TValue getValue();

		/**
		 * Associates a value
		 * 
		 * @param value
		 *            The value to associate
		 */
		void setValue(TValue value);
	}

	private static class Node<TValue, TItem> implements Value<TValue>,
			Serializable {
		/**
		 * Generated code
		 */
		private static final long serialVersionUID = -5223713450547676115L;

		protected HashMap<TItem, Node<TValue, TItem>> nextNodes;
		private TValue value;

		@Override
		public TValue getValue() {
			return value;
		}

		@Override
		public void setValue(TValue value) {
			this.value = value;
		}
	}
}
