package com.github.riccardove.easyjasub;

public interface EasyJaSubWordTranslationSense {
	Iterable<String> getPartOfSpeech();

	Iterable<String> getGloss();
}
