package com.github.riccardove.easyjasub;

import java.util.Iterator;

class CharacterIterator implements Iterator<Character> {

	public CharacterIterator(String word) {
		this.word = word;
		length = word.length();
	}

	private final String word;
	private int index;
	private final int length;

	@Override
	public boolean hasNext() {
		return index < length;
	}

	@Override
	public Character next() {
		return word.charAt(index++);
	}

	@Override
	public void remove() {
		throw new IllegalArgumentException();
	}

}
