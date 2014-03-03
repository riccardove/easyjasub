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


import java.io.File;

/**
 * Input arguments for EasyJaSub
 */
public interface EasyJaSubInput {

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
	
	public abstract String getDefaultFileNamePrefix();

	public abstract String getCssHiraganaFont();

	public abstract String getCssKanjiFont();

	public abstract String getCssTranslationFont();
	
	public abstract boolean showTranslation();
	
	public abstract boolean showRomaji();

	public abstract boolean showFurigana();

	public abstract boolean showDictionary();

	public abstract boolean showKanji();
	
	public abstract int getStartLine();

	public abstract int getEndLine();
}