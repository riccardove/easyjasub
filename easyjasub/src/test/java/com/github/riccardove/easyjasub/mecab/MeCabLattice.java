package com.github.riccardove.easyjasub.mecab;

import org.chasen.mecab.Lattice;

public class MeCabLattice {

	protected MeCabLattice(Lattice lattice) {
		this.lattice = lattice;
	}

	private final Lattice lattice;
	private String sentence;

	public void setSentence(String sentence) {
		this.sentence = sentence;
		lattice.set_sentence(sentence);
	}

	public String getSentence() {
		return sentence;
	}

	public MeCabNode node() {
		return new MeCabNode(lattice.bos_node());
	}

	@Override
	protected void finalize() throws Throwable {
		lattice.delete();
		super.finalize();
	}

	protected Lattice getLattice() {
		return lattice;
	}
}
