package com.github.riccardove.easyjasub;

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

	private int width;
	
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

	private int height;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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
