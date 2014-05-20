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

public class SubtitleList implements Iterable<SubtitleLine> {
	
	public SubtitleList() {
		lines = new ArrayList<SubtitleLine>();
	}
	
	private final ArrayList<SubtitleLine> lines;
	private String title;

	public SubtitleLine add() {
		SubtitleLine line = new SubtitleLine();
		if (lines.size() > 0) {
			SubtitleLine previous = last();
			line.setPrevious(previous);
			previous.setNext(line);
		}
		lines.add(line);
		return line;
	}
	
	public SubtitleLine insert(int index) {
		SubtitleLine line = new SubtitleLine();
		lines.add(index, line);
		SubtitleLine previous = index > 0 ? lines.get(index - 1) : null;
		SubtitleLine next = null;
		if (previous != null) {
			previous.setNext(line);
			line.setPrevious(previous);
			next = previous.getNext();
		}
		if (next == null && lines.size() > index + 1) {
			next = lines.get(index + 1);
		}
		if (next != null) {
			next.setPrevious(line);
			line.setNext(next);
		}
		return line;
	}
	
	public SubtitleLine get(int index) {
		while (lines.size() <= index) {
			add();
		}
		return lines.get(index);
	}

	
	@Override
	public Iterator<SubtitleLine> iterator() {
		return lines.iterator();
	}

	public SubtitleLine first() {
		return lines.get(0);
	}

	public SubtitleLine last() {
		return lines.get(lines.size()-1);
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

	public void setJapaneseSubWarnings(String warnings) {
		// TODO Auto-generated method stub
		
	}

	public void setJapaneseSubDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	public void setJapaneseSubLanguage(String language) {
		// TODO Auto-generated method stub
		
	}

	public void setTranslatedSubDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	public void setTranslatedSubLanguage(String language) {
		// TODO Auto-generated method stub
		
	}

	public void setTranslatedSubWarnings(String warnings) {
		// TODO Auto-generated method stub
		
	}

	public void setTranslationTitle(String title2) {
		// TODO Auto-generated method stub
		
	}
}
