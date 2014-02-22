package com.github.riccardove.easyjasub;

import java.util.HashSet;

public class FakeEasyJaSubInputCommand implements EasyJaSubInputCommand {

	private String approxMatchTimeDiff;
	private String cssFileName;
	private String exactMatchTimeDiff;
	private int height;
	private boolean help;
	private String japaneseSubFileName;
	private String nihongoJtalkHtmlFileName;
	private String outputBdnDirectory;
	private String outputHtmlDirectory;
	private String outputIdxFileName;
	private String outputJapaneseTextFileName;
	private HashSet<Phases> phases;
	private String translatedSubFileName;
	private String translatedSubLanguage;
	private String videoFileName;
	private int width;
	private String wkhtmltoimage;
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
	public int getHeight() {
		return height;
	}
	@Override
	public String getJapaneseSubFileName() {
		return japaneseSubFileName;
	}
	@Override
	public String getNihongoJtalkHtmlFileName() {
		return nihongoJtalkHtmlFileName;
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
	public HashSet<Phases> getPhases() {
		return phases;
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
	public int getWidth() {
		return width;
	}
	@Override
	public String getWkhtmltoimage() {
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
	public void setHeight(int height) {
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
	public void setPhases(HashSet<Phases> phases) {
		this.phases = phases;
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
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setWkhtmltoimage(String wkhtmltoimage) {
		this.wkhtmltoimage = wkhtmltoimage;
	}
}
