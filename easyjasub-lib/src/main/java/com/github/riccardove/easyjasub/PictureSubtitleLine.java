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
