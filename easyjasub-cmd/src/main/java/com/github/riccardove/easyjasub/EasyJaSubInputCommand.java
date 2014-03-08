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

	public abstract String getWkHtmlToImageCommand();

	public abstract String getOutputJapaneseTextFileName();

	public abstract String getCssFileName();

	public abstract String getExactMatchTimeDiff();

	public abstract String getApproxMatchTimeDiff();

	public abstract String getHeight();

	public abstract String getWidth();

	public abstract String getCssHiraganaFont();

	public abstract String getCssKanjiFont();

	public abstract String getCssTranslationFont();

	public abstract String getShowRomaji();

	public abstract String getShowTranslation();

	public abstract String getShowKanji();

	public abstract String getShowFurigana();

	public abstract String getShowDictionary();

	public abstract String getSelectLines();

	public abstract String getMeCabCommand();

	public abstract int getVerbose();
}