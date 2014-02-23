package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

public class SubtitleList implements Iterable<SubtitleLine>, Renderable {

	public SubtitleList(String name) {
		lines = new ArrayList<SubtitleLine>();
		this.name = name;
	}
	private String name;
	private ArrayList<SubtitleLine> lines;
	
	public SubtitleLine add() {
		SubtitleLine line = createSubtitleLine();
		lines.add(line);
		return line;
	}
	
	public SubtitleLine add(int index) {
		SubtitleLine line = createExtraSubtitleLine();
		lines.add(index, line);
		return line;
	}
	
	public SubtitleLine get(int index) {
		while (lines.size() <= index) {
			add();
		}
		return lines.get(index);
	}

	private SubtitleLine createSubtitleLine() {
		SubtitleLine line = new SubtitleLine(++count);
		String b = name + "_line" +String.format("%04d", line.getIndex());
		line.setHtmlFile(b + ".html");
		line.setPngFile(b + ".png");
		return line;
	}

	private SubtitleLine createExtraSubtitleLine() {
		SubtitleLine line = new SubtitleLine(10000 + ++extraCount);
		String b = name + "_eline" +String.format("%05d", line.getIndex());
		line.setHtmlFile(b + ".html");
		line.setPngFile(b + ".png");
		return line;
	}

	private int count;
	private int extraCount;

	public Iterator<SubtitleLine> iterator() {
		return lines.iterator();
	}
	
	public int size() {
		return lines.size();
	}
	
	public SubtitleLine first() {
		return lines.get(0);
	}
	
	public SubtitleLine last() {
		return lines.get(lines.size()-1);
	}

	public void renderOn(HtmlCanvas html) throws IOException {
		html.div();
		for (SubtitleLine item : lines) {
			item.renderOn(html);
		}
		html._div();
	}

	private String title;
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setTranslationTitle(String title2) {
		if (StringUtils.isEmpty(title)) {
			title = title2;
		}
	}
	
	public String getTitle() {
		return title;
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
}
