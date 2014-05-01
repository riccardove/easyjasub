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
