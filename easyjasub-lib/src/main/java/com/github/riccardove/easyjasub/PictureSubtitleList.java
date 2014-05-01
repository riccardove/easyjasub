package com.github.riccardove.easyjasub;

import java.util.ArrayList;
import java.util.Iterator;

class PictureSubtitleList implements Iterable<PictureSubtitleLine> {

	public PictureSubtitleList() {
		lines = new ArrayList<PictureSubtitleLine>();
	}

	private final ArrayList<PictureSubtitleLine> lines;
	private String title;

	public PictureSubtitleLine add() {
		PictureSubtitleLine line = new PictureSubtitleLine();
		lines.add(line);
		return line;
	}

	public PictureSubtitleLine add(int index) {
		PictureSubtitleLine line = new PictureSubtitleLine();
		lines.add(index, line);
		return line;
	}

	public PictureSubtitleLine get(int index) {
		while (lines.size() <= index) {
			add();
		}
		return lines.get(index);
	}

	@Override
	public Iterator<PictureSubtitleLine> iterator() {
		return lines.iterator();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int size() {
		return lines.size();
	}

	public PictureSubtitleLine first() {
		return lines.get(0);
	}

	public PictureSubtitleLine last() {
		return lines.get(lines.size() - 1);
	}

}
