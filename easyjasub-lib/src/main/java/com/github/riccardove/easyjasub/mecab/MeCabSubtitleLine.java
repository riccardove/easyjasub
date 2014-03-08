package com.github.riccardove.easyjasub.mecab;

import java.util.ArrayList;
import java.util.Iterator;

class MeCabSubtitleLine implements Iterable<MeCabSubtitleLineItem> {

	private final ArrayList<MeCabSubtitleLineItem> items;

	public MeCabSubtitleLine() {
		items = new ArrayList<MeCabSubtitleLineItem>();
	}

	public void addItem(MeCabSubtitleLineItem item) {
		items.add(item);
	}

	@Override
	public Iterator<MeCabSubtitleLineItem> iterator() {
		return items.iterator();
	}
}
