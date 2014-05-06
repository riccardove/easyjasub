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

	public void addDictionaryEntries(SubtitleList s,
			EasyJaSubWordTranslator dictionary) {

		for (SubtitleLine line : s) {
			Iterable<SubtitleItem> items = line.getItems();
			if (items != null) {
				for (SubtitleItem item : items) {
					// TODO: translation should be applied to anything but
					// particles
					if (item.getElements() != null) {
						EasyJaSubWordTranslation translation = dictionary
								.translate(item.getText());
						if (translation != null) {
							String translationStr = translation.getSenses()
									.iterator().next().getGloss().iterator()
									.next();
							item.setDictionary(translationStr);
						}
					}
				}
			}
		}
	}
}
