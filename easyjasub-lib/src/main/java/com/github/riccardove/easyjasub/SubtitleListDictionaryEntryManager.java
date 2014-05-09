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

class SubtitleListDictionaryEntryManager {

	private final EasyJaSubObserver observer;

	public SubtitleListDictionaryEntryManager(EasyJaSubObserver observer) {
		this.observer = observer;
	}

	private boolean itemCanHaveDictionary(SubtitleItem item) {
		String pos = item.getPartOfSpeech();
		if (pos == null) {
			return item.getElements() != null;
		}
		return (item.getElements() != null || pos.startsWith("noun")
				|| pos.startsWith("verb") || pos.startsWith("adjective") || pos
					.startsWith("adverb")) && !pos.contains("auxiliary");

	}

	public void addDictionaryEntries(SubtitleList s,
			EasyJaSubWordTranslator dictionary) {

		for (SubtitleLine line : s) {
			Iterable<SubtitleItem> items = line.getItems();
			if (items != null) {
				for (SubtitleItem item : items) {
					if (itemCanHaveDictionary(item)) {
						EasyJaSubWordTranslation translation = getWordTranslation(
								item, dictionary);
						if (translation != null) {
							EasyJaSubWordTranslationSense sense = getBestSense(
									translation, item);
							String translationStr = getBestTranslation(sense,
									line);
							item.setDictionary(translationStr);
						}
					}
				}
			}
		}
	}

	private String getBestTranslation(EasyJaSubWordTranslationSense sense,
			SubtitleLine line) {
		if (line.getTranslation() == null) {
			return sense.getGloss().iterator().next();
		}
		// TODO: pick up a gloss depending on translated line
		return sense.getGloss().iterator().next();
	}

	private String getSimplifiedItemPartOfSpeech(SubtitleItem item) {
		String itemPartOfSpeech = item.getPartOfSpeech();
		if (itemPartOfSpeech != null) {
			int index = itemPartOfSpeech.indexOf('-');
			if (index > 0) {
				return itemPartOfSpeech.substring(0, index);
			}
		}
		return itemPartOfSpeech;
	}

	private EasyJaSubWordTranslationSense getBestSense(
			EasyJaSubWordTranslation translation, SubtitleItem item) {
		EasyJaSubWordTranslationSense result = null;
		String itemPartOfSpeech = getSimplifiedItemPartOfSpeech(item);
		boolean resultChecked = false;
		for (EasyJaSubWordTranslationSense sense : translation.getSenses()) {
			if (result == null) {
				result = sense;
			} else if (itemPartOfSpeech != null) {
				if (!resultChecked) {
					if (result.getPartOfSpeech() != null) {
						for (String pos : result.getPartOfSpeech()) {
							if (pos.contains(itemPartOfSpeech)) {
								return result;
							}
						}
					}
					resultChecked = true;
				}
				if (sense.getPartOfSpeech() != null) {
					for (String pos : sense.getPartOfSpeech()) {
						if (pos.contains(itemPartOfSpeech)) {
							return sense;
						}
					}
				}
			} else {
				return sense;
			}
		}
		return result;
	}

	private String getFirstTranslation(EasyJaSubWordTranslation translation) {
		return translation.getSenses().iterator().next().getGloss().iterator()
				.next();
	}

	private EasyJaSubWordTranslation getWordTranslation(SubtitleItem item,
			EasyJaSubWordTranslator dictionary) {
		EasyJaSubWordTranslation textTranslation = dictionary.translate(item
				.getText());
		if (item.getBaseForm() != null) {
			EasyJaSubWordTranslation baseFormTranslation = dictionary
					.translate(item.getBaseForm());
			if (baseFormTranslation != null
					&& isBaseFormTranslationBetter(item, textTranslation,
							baseFormTranslation)) {
				return baseFormTranslation;
			}
		}
		return textTranslation;
	}

	private boolean isBaseFormTranslationBetter(SubtitleItem item,
			EasyJaSubWordTranslation textTranslation,
			EasyJaSubWordTranslation baseFormTranslation) {
		return textTranslation == null
				|| (baseFormTranslation.getLength() >= textTranslation
						.getLength())
				|| (baseFormTranslation.getLength() == item.getBaseForm()
						.length());
	}
}
