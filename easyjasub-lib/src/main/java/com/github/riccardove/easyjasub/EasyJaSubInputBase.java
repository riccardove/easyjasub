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
import java.io.Serializable;

public class EasyJaSubInputBase implements EasyJaSubInput, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 712175222448997821L;
	private int approxMatchTimeDiff;
	private File bdnXmlFile;
	private File cssFile;
	private String cssHiraganaFont;
	private String cssKanjiFont;
	private String cssTranslationFont;

	public EasyJaSubInputBase() {

	}

	public EasyJaSubInputBase(EasyJaSubInput input) {
		approxMatchTimeDiff = input.getApproxMatchTimeDiff();
		bdnXmlFile = input.getBdnXmlFile();
		cssFile = input.getCssFile();
		cssHiraganaFont = input.getCssHiraganaFont();
		cssKanjiFont = input.getCssKanjiFont();
		cssTranslationFont = input.getCssTranslationFont();
		defaultFileNamePrefix = input.getDefaultFileNamePrefix();
		endLine = input.getEndLine();
		exactMatchTimeDiff = input.getExactMatchTimeDiff();
		height = input.getHeight();
		htmlFile = input.getHtmlFile();
		isSingleLine = input.isSingleLine();
		japaneseSubFile = input.getJapaneseSubFile();
		japaneseSubFileType = input.getJapaneseSubFileType();
		jglossFile = input.getJGlossFile();
		jmDictFile = input.getJMDictFile();
		outputHtmlDirectory = input.getOutputHtmlDirectory();
		outputIdxFile = input.getOutputIdxFile();
		outputJapaneseTextFile = input.getOutputJapaneseTextFile();
		showDictionary = input.showDictionary();
		showFurigana = input.showFurigana();
		showKanji = input.showKanji();
		showRomaji = input.showRomaji();
		showTranslation = input.showTranslation();
		startLine = input.getStartLine();
		translatedSubFile = input.getTranslatedSubFile();
		translatedSubFileType = input.getTranslatedSubFileType();
		translatedSubLanguage = input.getTranslatedSubLanguage();
		videoFile = input.getVideoFile();
		width = input.getWidth();
		wkhtmltoimageFile = input.getWkHtmlToImageCommand();
		xmlFile = input.getXmlFile();
	}

	@Override
	public int getApproxMatchTimeDiff() {
		return approxMatchTimeDiff;
	}

	public void setApproxMatchTimeDiff(int approxMatchTimeDiff) {
		this.approxMatchTimeDiff = approxMatchTimeDiff;
	}

	@Override
	public File getBdnXmlFile() {
		return bdnXmlFile;
	}

	public void setBdnXmlFile(File bdnXmlFile) {
		this.bdnXmlFile = bdnXmlFile;
	}

	@Override
	public File getCssFile() {
		return cssFile;
	}

	public void setCssFile(File cssFile) {
		this.cssFile = cssFile;
	}

	@Override
	public String getCssHiraganaFont() {
		return cssHiraganaFont;
	}

	public void setCssHiraganaFont(String cssHiraganaFont) {
		this.cssHiraganaFont = cssHiraganaFont;
	}

	@Override
	public String getCssKanjiFont() {
		return cssKanjiFont;
	}

	public void setCssKanjiFont(String cssKanjiFont) {
		this.cssKanjiFont = cssKanjiFont;
	}

	@Override
	public String getCssTranslationFont() {
		return cssTranslationFont;
	}

	public void setCssTranslationFont(String cssTranslationFont) {
		this.cssTranslationFont = cssTranslationFont;
	}

	@Override
	public String getDefaultFileNamePrefix() {
		return defaultFileNamePrefix;
	}

	public void setDefaultFileNamePrefix(String defaultFileNamePrefix) {
		this.defaultFileNamePrefix = defaultFileNamePrefix;
	}

	@Override
	public int getExactMatchTimeDiff() {
		return exactMatchTimeDiff;
	}

	public void setExactMatchTimeDiff(int exactMatchTimeDiff) {
		this.exactMatchTimeDiff = exactMatchTimeDiff;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public File getJapaneseSubFile() {
		return japaneseSubFile;
	}

	public void setJapaneseSubFile(File japaneseSubFile) {
		this.japaneseSubFile = japaneseSubFile;
	}

	@Override
	public SubtitleFileType getJapaneseSubFileType() {
		return japaneseSubFileType;
	}

	public void setJapaneseSubFileType(SubtitleFileType japaneseSubFileType) {
		this.japaneseSubFileType = japaneseSubFileType;
	}

	@Override
	public File getOutputHtmlDirectory() {
		return outputHtmlDirectory;
	}

	public void setOutputHtmlDirectory(File outputHtmlDirectory) {
		this.outputHtmlDirectory = outputHtmlDirectory;
	}

	@Override
	public File getOutputIdxFile() {
		return outputIdxFile;
	}

	public void setOutputIdxFile(File outputIdxFile) {
		this.outputIdxFile = outputIdxFile;
	}

	@Override
	public File getOutputJapaneseTextFile() {
		return outputJapaneseTextFile;
	}

	public void setOutputJapaneseTextFile(File outputJapaneseTextFile) {
		this.outputJapaneseTextFile = outputJapaneseTextFile;
	}

	@Override
	public File getTranslatedSubFile() {
		return translatedSubFile;
	}

	public void setTranslatedSubFile(File translatedSubFile) {
		this.translatedSubFile = translatedSubFile;
	}

	@Override
	public SubtitleFileType getTranslatedSubFileType() {
		return translatedSubFileType;
	}

	public void setTranslatedSubFileType(SubtitleFileType translatedSubFileType) {
		this.translatedSubFileType = translatedSubFileType;
	}

	@Override
	public String getTranslatedSubLanguage() {
		return translatedSubLanguage;
	}

	public void setTranslatedSubLanguage(String translatedSubLanguage) {
		this.translatedSubLanguage = translatedSubLanguage;
	}

	@Override
	public File getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String getWkHtmlToImageCommand() {
		return wkhtmltoimageFile;
	}

	public void setWkHtmlToImageCommand(String wkhtmltoimageFile) {
		this.wkhtmltoimageFile = wkhtmltoimageFile;
	}

	@Override
	public boolean showTranslation() {
		return showTranslation;
	}

	public void setShowTranslation(boolean showTranslation) {
		this.showTranslation = showTranslation;
	}

	@Override
	public boolean showFurigana() {
		return showFurigana;
	}

	public void setShowFurigana(boolean showFurigana) {
		this.showFurigana = showFurigana;
	}

	@Override
	public boolean showDictionary() {
		return showDictionary;
	}

	public void setShowDictionary(boolean showDictionary) {
		this.showDictionary = showDictionary;
	}

	@Override
	public boolean showRomaji() {
		return showRomaji;
	}

	public void setShowRomaji(boolean showRomaji) {
		this.showRomaji = showRomaji;
	}

	@Override
	public boolean showKanji() {
		return showKanji;
	}

	public void setShowKanji(boolean showKanji) {
		this.showKanji = showKanji;
	}

	@Override
	public File getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	@Override
	public File getJGlossFile() {
		return jglossFile;
	}

	public void setJGlossFile(File jglossFile) {
		this.jglossFile = jglossFile;
	}

	@Override
	public int getStartLine() {
		return startLine;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	@Override
	public int getEndLine() {
		return endLine;
	}

	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}

	@Override
	public boolean isSingleLine() {
		return isSingleLine;
	}

	public void setSingleLine(boolean isSingleLine) {
		this.isSingleLine = isSingleLine;
	}

	@Override
	public File getHtmlFile() {
		return htmlFile;
	}

	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;
	}

	private String defaultFileNamePrefix;
	private int exactMatchTimeDiff;
	private int height;
	private File japaneseSubFile;
	private SubtitleFileType japaneseSubFileType;
	private File outputHtmlDirectory;
	private File outputIdxFile;
	private File outputJapaneseTextFile;
	private File translatedSubFile;
	private SubtitleFileType translatedSubFileType;
	private String translatedSubLanguage;
	private File videoFile;
	private int width;
	private String wkhtmltoimageFile;
	private boolean showTranslation;
	private boolean showFurigana;
	private boolean showDictionary;
	private boolean showRomaji;
	private boolean showKanji;
	private File xmlFile;
	private File jglossFile;
	private int startLine;
	private int endLine;
	private boolean isSingleLine;
	private File htmlFile;
	private File jmDictFile;

	@Override
	public File getJMDictFile() {
		return jmDictFile;
	}

	public void setJMDictFile(File file) {
		jmDictFile = file;
	}

	@Override
	public File getDictionaryCacheFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
