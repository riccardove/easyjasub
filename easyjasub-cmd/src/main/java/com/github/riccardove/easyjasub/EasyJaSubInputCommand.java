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

public interface EasyJaSubInputCommand {

	boolean isHelp();

	String getVideoFileName();

	String getJapaneseSubFileName();

	String getTranslatedSubFileName();

	String getTranslatedSubLanguage();

	String getOutputIdxFileName();

	String getOutputIdxDirectory();

	String getOutputHtmlDirectory();

	String getOutputBdnDirectory();

	String getOutputBdnFileName();

	String getWkHtmlToImageCommand();

	String getOutputJapaneseTextFileName();

	String getCssFileName();

	String getExactMatchTimeDiff();

	String getApproxMatchTimeDiff();

	String getHeight();

	String getWidth();

	String getCssHiraganaFont();

	String getCssKanjiFont();

	String getCssTranslationFont();

	String getShowRomaji();

	String getShowTranslation();

	String getShowKanji();

	String getShowFurigana();

	String getShowDictionary();

	String getSelectLines();

	int getVerbose();

	String getHomeDirectoryName();

	String getJMDictFileName();
}