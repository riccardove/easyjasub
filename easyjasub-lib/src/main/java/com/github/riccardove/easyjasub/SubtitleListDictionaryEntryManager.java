package com.github.riccardove.easyjasub;

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
