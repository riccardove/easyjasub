package com.github.riccardove.easyjasub;

import java.io.File;

class PictureSubtitleLine {

	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

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

	private int startTime;
	private int endTime;

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setStartTime(int mSeconds) {
		startTime = mSeconds;
	}

	public void setEndTime(int mSeconds) {
		endTime = mSeconds;
	}
}
