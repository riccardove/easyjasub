package com.github.riccardove.easyjasub.mecab;

import org.chasen.mecab.Model;

public class MeCabModel {

	protected MeCabModel(String args) {
		if (args == null) {
			model = new Model();
		} else {
			model = new Model(args);
		}
	}

	private final Model model;

	public MeCabLattice createLattice() {
		return new MeCabLattice(model.createLattice());
	}

	public MeCabTagger createTagger() {
		return new MeCabTagger(model.createTagger());
	}

	@Override
	protected void finalize() throws Throwable {
		model.delete();
		super.finalize();
	}
}
