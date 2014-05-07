package com.github.riccardove.easyjasub;

public class SubtitleTranslatedLine {

	private String translation;
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

	public String getText() {
		return translation;
	}

	public void setText(String text) {
		translation = text;
	}
}
