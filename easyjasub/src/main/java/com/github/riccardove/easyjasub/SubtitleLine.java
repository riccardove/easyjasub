package com.github.riccardove.easyjasub;

import java.io.File;
import java.util.List;

public class SubtitleLine {


	public String getTranslation() {
		return translation;
	}

	public void setTranslatedText(String text) {
		translation = text;
	}
	
	private int index;

	private File htmlFile;
	
	public void setHtmlFile(File file) {
		htmlFile = file;
	}
	
	public File getHtmlFile() {
		return htmlFile;
	}

	private File pngFile;
	public void setPngFile(File string) {
		pngFile = string;
	}
	
	public File getPngFile() {
		return pngFile;
	}

	private List<SubtitleItem> items;
	private String translation;
	private int startTime;
	private int endTime;
	private String japanese;
	
	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public String getJapanese() {
		return japanese;
	}

	public void setStartTime(int mSeconds) {
		startTime = mSeconds;
	}

	public void setEndTime(int mSeconds) {
		endTime = mSeconds;
	}

	public void setJapaneseSubText(String content) {
		japanese = content;
	}

	public List<SubtitleItem> getItems() {
		return items;
	}

	public void setItems(List<SubtitleItem> items) {
		this.items = items;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
