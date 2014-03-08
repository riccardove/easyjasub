package com.github.riccardove.easyjasub.mecab;

import java.util.ArrayList;
import java.util.Iterator;

class MeCabSubtitleList implements Iterable<MeCabSubtitleLine> {

	public MeCabSubtitleList() {
		lines = new ArrayList<MeCabSubtitleLine>();
	}

	private final ArrayList<MeCabSubtitleLine> lines;

	public MeCabSubtitleLine add() {
		MeCabSubtitleLine line = new MeCabSubtitleLine();
		lines.add(line);
		return line;
	}

	public MeCabSubtitleLine get(int index) {
		return lines.get(index);
	}

	@Override
	public Iterator<MeCabSubtitleLine> iterator() {
		return lines.iterator();
	}

	public int size() {
		return lines.size();
	}

}
