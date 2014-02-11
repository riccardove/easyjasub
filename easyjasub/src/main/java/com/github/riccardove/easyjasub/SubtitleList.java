package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;

import subtitleFile.*;

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
	
	public void insertEnglish(InputTextSubCaption enCaption) {
		for (int i = 0; i<lines.size(); ++i) {
			SubtitleLine line = lines.get(i);
			if (line.isJa() && line.startsAfter(enCaption)) {
				if (i>0 && !lines.get(i-1).endsBefore(enCaption)) {
					lines.get(i-1).addEnglish(enCaption.getContent());
				}
				else {
					SubtitleLine englishLine = createExtraSubtitleLine();
					englishLine.setCaption(enCaption);
					englishLine.addEnglish(enCaption.getContent());
					lines.add(i, englishLine);
				}
				break;
			}
		}
		
	}

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

	public void setEnglishTitle(String title2) {
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

	public String[] getIdxTimestamps() {
		String[] result = new String[lines.size()];
		for (int i = 0; i<lines.size(); ++i) {
			result[i] = lines.get(i).getIdxStartTime();
		}
		return result;
	}
}
