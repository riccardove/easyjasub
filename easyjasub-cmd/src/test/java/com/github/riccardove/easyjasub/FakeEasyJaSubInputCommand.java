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


public class FakeEasyJaSubInputCommand implements EasyJaSubInputCommand {

	private String approxMatchTimeDiff;
	private String cssFileName;
	private String exactMatchTimeDiff;
	private String height;
	private boolean help;
	private String japaneseSubFileName;
	private String nihongoJtalkHtmlFileName;
	private String outputBdnDirectory;
	private String outputHtmlDirectory;
	private String outputIdxFileName;
	private String outputJapaneseTextFileName;
	private String translatedSubFileName;
	private String translatedSubLanguage;
	private String videoFileName;
	private String width;
	private String wkhtmltoimage;
	private String cssHiraganaFont;
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
	public void setHelp(boolean help) {
		this.help = help;
	}

	private String cssKanjiFont;
	private String cssTranslationFont;
	@Override
	public String getApproxMatchTimeDiff() {
		return approxMatchTimeDiff;
	}
	@Override
	public String getCssFileName() {
		return cssFileName;
	}
	@Override
	public String getExactMatchTimeDiff() {
		return exactMatchTimeDiff;
	}
	@Override
	public String getHeight() {
		return height;
	}
	@Override
	public String getJapaneseSubFileName() {
		return japaneseSubFileName;
	}
	@Override
	public String getOutputBdnDirectory() {
		return outputBdnDirectory;
	}
	@Override
	public String getOutputBdnFileName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getOutputHtmlDirectory() {
		return outputHtmlDirectory;
	}
	@Override
	public String getOutputIdxDirectory() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getOutputIdxFileName() {
		return outputIdxFileName;
	}
	@Override
	public String getOutputJapaneseTextFileName() {
		return outputJapaneseTextFileName;
	}
	@Override
	public String getTranslatedSubFileName() {
		return translatedSubFileName;
	}
	@Override
	public String getTranslatedSubLanguage() {
		return translatedSubLanguage;
	}

	@Override
	public String getVideoFileName() {
		return videoFileName;
	}

	@Override
	public String getWidth() {
		return width;
	}
	@Override
	public String getWkHtmlToImageCommand() {
		return wkhtmltoimage;
	}
	@Override
	public boolean isHelp() {
		return help;
	}
	public void setApproxMatchTimeDiff(String approxMatchTimeDiff) {
		this.approxMatchTimeDiff = approxMatchTimeDiff;
	}
	public void setCssFileName(String cssFileName) {
		this.cssFileName = cssFileName;
	}
	public void setExactMatchTimeDiff(String exactMatchTimeDiff) {
		this.exactMatchTimeDiff = exactMatchTimeDiff;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public void setJapaneseSubFileName(String japaneseSubFileName) {
		this.japaneseSubFileName = japaneseSubFileName;
	}
	public void setNihongoJtalkHtmlFileName(String nihongoJtalkHtmlFileName) {
		this.nihongoJtalkHtmlFileName = nihongoJtalkHtmlFileName;
	}
	public void setOutputBdnDirectory(String outputBdnDirectory) {
		this.outputBdnDirectory = outputBdnDirectory;
	}
	public void setOutputHtmlDirectory(String outputHtmlDirectory) {
		this.outputHtmlDirectory = outputHtmlDirectory;
	}
	public void setOutputIdxFileName(String outputIdxFileName) {
		this.outputIdxFileName = outputIdxFileName;
	}
	public void setOutputJapaneseTextFileName(String outputJapaneseTextFileName) {
		this.outputJapaneseTextFileName = outputJapaneseTextFileName;
	}
	public void setTranslatedSubFileName(String translatedSubFileName) {
		this.translatedSubFileName = translatedSubFileName;
	}
	public void setTranslatedSubLanguage(String translatedSubLanguage) {
		this.translatedSubLanguage = translatedSubLanguage;
	}

	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
	
	public void setWkhtmltoimage(String wkhtmltoimage) {
		this.wkhtmltoimage = wkhtmltoimage;
	}
	@Override
	public String getShowRomaji() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getShowTranslation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getShowKanji() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getShowFurigana() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getShowDictionary() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSelectLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVerbose() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHomeDirectoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJMDictFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}
}
