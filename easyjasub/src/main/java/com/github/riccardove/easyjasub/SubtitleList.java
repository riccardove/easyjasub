package com.github.riccardove.easyjasub;

import java.util.ArrayList;
import java.util.Iterator;

public class SubtitleList implements Iterable<SubtitleLine> {
	
	public SubtitleList() {
		lines = new ArrayList<SubtitleLine>();
	}
	
	private ArrayList<SubtitleLine> lines;
	private String title;
	
	public SubtitleLine add() {
		SubtitleLine line = new SubtitleLine();
		lines.add(line);
		return line;
	}
	
	public SubtitleLine add(int index) {
		SubtitleLine line = new SubtitleLine();
		lines.add(index, line);
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
