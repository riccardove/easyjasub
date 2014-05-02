package com.github.riccardove.easyjasub.jmdict;

public interface IJMDictSense {
	Iterable<String> getPartOfSpeech();

	Iterable<String> getGloss();

	Iterable<String> getGlossInLang();
}