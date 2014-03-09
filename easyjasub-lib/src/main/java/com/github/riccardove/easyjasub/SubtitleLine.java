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

	private String japaneseSubKey;
	
	public void setJapaneseSubKey(String japaneseSubKey) {
		this.japaneseSubKey = japaneseSubKey;
	}

	/**
	 * Key string containing only japanese chars in the caption, used for comparisons
	 */
	public String getJapaneseSubKey() {
		return japaneseSubKey;
	}
	
	private String subText;
	
	public void setSubText(String subText) {
		this.subText = subText;
	}

	/**
	 * Text of the caption, for captions without any japanese char that do not need translation
	 */
	public String getSubText() {
		return subText;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (items != null) {
			for (SubtitleItem item : items) {
				result.append(" ");
				result.append(item.toString());
			}
		}
		return result.toString();
	}
}
