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

import com.github.riccardove.easyjasub.dictionary.EasyJaSubDictionary;

/**
 * Input arguments for EasyJaSub
 */
public interface EasyJaSubInput {
	/**
	 * The video file associated with subtitles
	 * 
	 * @return
	 */
	public abstract File getVideoFile();

	/**
	 * File for Japanese subtitles
	 * 
	 * @return
	 */
	public abstract File getJapaneseSubFile();

	/**
	 * A text transcript of Japanese subtitles is written to this file
	 * 
	 * @return
	 */
	public abstract File getOutputJapaneseTextFile();

	/**
	 * File for translated subtitles
	 * 
	 * @return
	 */
	public abstract File getTranslatedSubFile();

	/**
	 * The format of Japanese subtitles file
	 * 
	 * @return
	 */
	public abstract SubtitleFileType getJapaneseSubFileType();

	/**
	 * The format of translated subtitles file
	 * 
	 * @return
	 */
	public abstract SubtitleFileType getTranslatedSubFileType();

	/**
	 * The language of translated subtitles
	 * 
	 * @return
	 */
	public abstract String getTranslatedSubLanguage();

	/**
	 * The file where to write subtitles converted in IDX+SUB format
	 * 
	 * @return
	 */
	public abstract File getOutputIdxFile();

	/**
	 * An XML file to save the analysis of subtitles, used as an input file if
	 * exists
	 * 
	 * @return
	 */
	public abstract File getXmlFile();

	/**
	 * Directory where to write generated HTML files for each line
	 * 
	 * @return
	 */
	public abstract File getOutputHtmlDirectory();

	/**
	 * CSS file used to style the HTML files
	 * 
	 * @return
	 */
	public abstract File getCssFile();

	/**
	 * Output BDN/XML file created from subtitles
	 * 
	 * @return
	 */
	public abstract File getBdnXmlFile();

	/**
	 * Command to run wkhtmltoimage
	 * 
	 * @return
	 */
	public abstract String getWkHtmlToImageCommand();

	/**
	 * Difference in milliseconds used to detect if two subtitle lines are the
	 * same
	 * 
	 * @return
	 */
	public abstract int getExactMatchTimeDiff();

	/**
	 * Difference in milliseconds used to detect if a subtitle line is part of
	 * an other
	 * 
	 * @return
	 */
	public abstract int getApproxMatchTimeDiff();

	/**
	 * Width of generated subtitles pictures
	 * 
	 * @return
	 */
	public abstract int getWidth();

	/**
	 * Height of generated subtitles pictures
	 * 
	 * @return
	 */
	public abstract int getHeight();

	/**
	 * Prefix to use for generated files
	 * 
	 * @return
	 */
	public abstract String getDefaultFileNamePrefix();

	/**
	 * Font to use in CSS for Hiragana
	 * 
	 * @return
	 */
	public abstract String getCssHiraganaFont();

	/**
	 * Font to use in CSS for Kanji
	 * 
	 * @return
	 */
	public abstract String getCssKanjiFont();

	/**
	 * Font to use in CSS for translated text
	 * 
	 * @return
	 */
	public abstract String getCssTranslationFont();

	/**
	 * If you want to show translated text in subtitles
	 * 
	 * @return
	 */
	public abstract boolean showTranslation();

	/**
	 * If you want to view romaji in subtitles
	 * 
	 * @return
	 */
	public abstract boolean showRomaji();

	/**
	 * If you want to view furigana in subtitles
	 * 
	 * @return
	 */
	public abstract boolean showFurigana();

	/**
	 * If you want to display the dictionary translation for each word
	 * 
	 * @return
	 */
	public abstract boolean showDictionary();

	/**
	 * If you want to display Kanji
	 * 
	 * @return
	 */
	public abstract boolean showKanji();

	/**
	 * Index of the first line to read from Japanese subtitles, to select a
	 * subset of subtitles
	 * 
	 * @return
	 */
	public abstract int getStartLine();

	/**
	 * Index of the last line to read from Japanese subtitles, to select a
	 * subset of subtitles
	 * 
	 * @return
	 */
	public abstract int getEndLine();

	/**
	 * If you want to display subtitles into a single line and avoid
	 * word-wrapping
	 * 
	 * @return
	 */
	public abstract boolean isSingleLine();

	/**
	 * File where to write the transcript of subtitles in .jgloss format
	 * 
	 * @return
	 */
	public abstract File getJGlossFile();

	/**
	 * Dictionary class
	 * 
	 * @return
	 */
	EasyJaSubDictionary getDictionary();

	File getJMDictFile();

	File getDictionaryCacheFile();
}