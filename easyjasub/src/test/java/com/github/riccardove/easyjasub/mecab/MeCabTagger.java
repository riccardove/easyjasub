package com.github.riccardove.easyjasub.mecab;

import org.chasen.mecab.Tagger;

public class MeCabTagger {

	protected MeCabTagger(Tagger tagger) {
		this.tagger = tagger;
	}

	private final Tagger tagger;

	public boolean parse(MeCabLattice lattice) {
		return tagger.parse(lattice.getLattice());
	}

	@Override
	protected void finalize() throws Throwable {
		tagger.delete();
		super.finalize();
	}
}
