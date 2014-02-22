package com.github.riccardove.easyjasub;

import java.util.Set;

public interface EasyJaSubInputCommand {

	public abstract boolean isHelp();

	public abstract String getVideoFileName();

	public abstract String getJapaneseSubFileName();

	public abstract String getTranslatedSubFileName();

	public abstract String getNihongoJtalkHtmlFileName();

	public abstract String getTranslatedSubLanguage();

	public abstract String getOutputIdxFileName();

	public abstract String getOutputIdxDirectory();

	public abstract String getOutputHtmlDirectory();

	public abstract String getOutputBdnDirectory();

	public abstract String getOutputBdnFileName();

	public abstract String getWkhtmltoimage();

	public abstract Set<Phases> getPhases();

	public abstract String getOutputJapaneseTextFileName();

	public abstract String getCssFileName();

	public abstract String getExactMatchTimeDiff();

	public abstract String getApproxMatchTimeDiff();

	int getHeight();

	int getWidth();

}