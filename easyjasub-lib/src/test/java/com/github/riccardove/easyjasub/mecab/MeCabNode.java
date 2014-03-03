package com.github.riccardove.easyjasub.mecab;

import org.chasen.mecab.Node;

public class MeCabNode {

	protected MeCabNode(Node node) {
		this.node = node;
	}

	private final Node node;

	public MeCabNode next() {
		return new MeCabNode(node.getNext());
	}

}
