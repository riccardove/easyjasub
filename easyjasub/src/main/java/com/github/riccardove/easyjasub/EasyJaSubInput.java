package com.github.riccardove.easyjasub;

import java.io.File;
import java.util.Set;

public interface EasyJaSubInput {
	
	public abstract Set<Phases> getPhases();

	public abstract File getVideoFile();

	public abstract File getJapaneseSubFile();
	
	public abstract File getTranslatedSubFile();

	public abstract SubtitleFileType getJapaneseSubFileType();

	public abstract SubtitleFileType getTranslatedSubFileType();
	
	public abstract File getNihongoJtalkHtmlFile();

	public abstract String getTranslatedSubLanguage();

	public abstract File getOutputIdxDirectory();

	public abstract File getOutputHtmlDirectory();

	public abstract File getBdnXmlFile();

	public abstract File getWkhtmltoimageFile();

}