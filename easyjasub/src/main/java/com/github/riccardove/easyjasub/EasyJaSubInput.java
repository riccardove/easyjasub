package com.github.riccardove.easyjasub;

import java.io.File;
import java.util.Set;

/**
 * Input arguments for EasyJaSub
 */
public interface EasyJaSubInput {
	
	public abstract Set<Phases> getPhases();

	public abstract File getVideoFile();

	public abstract File getJapaneseSubFile();
	
	public abstract File getOutputJapaneseTextFile();

	public abstract File getTranslatedSubFile();

	public abstract SubtitleFileType getJapaneseSubFileType();

	public abstract SubtitleFileType getTranslatedSubFileType();
	
	public abstract File getNihongoJtalkHtmlFile();

	public abstract String getTranslatedSubLanguage();

	public abstract File getOutputIdxFile();

	public abstract File getOutputHtmlDirectory();

	public abstract File getCssFile();

	public abstract File getBdnXmlFile();

	public abstract String getWkhtmltoimageFile();

	public abstract int getExactMatchTimeDiff();

	public abstract int getApproxMatchTimeDiff();

	public abstract int getWidth();

	public abstract int getHeight();
	
	String getDefaultFileNamePrefix();
}