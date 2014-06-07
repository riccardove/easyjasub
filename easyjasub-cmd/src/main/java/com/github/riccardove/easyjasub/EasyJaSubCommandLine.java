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

import java.io.PrintWriter;

class EasyJaSubCommandLine implements EasyJaSubInputCommand {
	private static final String HELP = "h";
	private static final String VI = "vi";
	private static final String JA = "ja";
	private static final String TR = "tr";
	private static final String TRL = "trl";
	private static final String IDX = "oidx";
	private static final String HTML = "ohtml";
	private static final String BDN = "obdn";
	private static final String WK = "wk";
	private static final String TX = "txt";
	private static final String CSS = "css";
	private static final String MATCHTIME = "mt";
	private static final String APPROXTIME = "at";
	private static final String HEIGHT = "he";
	private static final String WIDTH = "wi";

	private static final String ROMAJI = "r";
	private static final String TRANSLATION = "t";
	private static final String DICTIONARY = "d";
	private static final String KANJI = "k";
	private static final String FURIGANA = "f";
	private static final String SELECT = "s";

	private static final String VERBOSE = "v";
	private static final String QUIET = "q";

	public EasyJaSubCommandLine() {
		list = new EasyJaSubCommandLineOptionList();
		list.addOption(EasyJaSubInputOption.video, VI, "video", "file");
		list.addOption(EasyJaSubInputOption.japanesesub, JA, "japanese-sub",
				"file");
		list.addOption(EasyJaSubInputOption.translatedsub, TR,
				"translated-sub", "file");
		list.addOption(EasyJaSubInputOption.translation, TRANSLATION,
				"translation", "enabled|disabled");
		list.addOption(EasyJaSubInputOption.romaji, ROMAJI, "romaji",
				"enabled|disabled");
		list.addOption(EasyJaSubInputOption.dictionary, DICTIONARY,
				"dictionary", "enabled|disabled");
		list.addOption(EasyJaSubInputOption.furigana, FURIGANA, "furigana",
				"enabled|disabled");
		list.addOption(EasyJaSubInputOption.kanji, KANJI, "kanji",
				"enabled|disabled");
		list.addOption(EasyJaSubInputOption.trsublang, TRL, "tr-sub-lang",
				"language");
		list.addOption(EasyJaSubInputOption.outputtext, TX, "output-text",
				"file");
		list.addOption(EasyJaSubInputOption.cssstyle, CSS, "css-style", "file");
		list.addOption(EasyJaSubInputOption.outputidx, IDX, "output-idx",
				"file");
		list.addOption(EasyJaSubInputOption.outputhtml, HTML, "output-html",
				"directory");
		list.addOption(EasyJaSubInputOption.outputbdmxml, BDN, "output-bdmxml",
				"directory");
		list.addOption(EasyJaSubInputOption.home, "ho", "home", "directory");
		list.addOption(EasyJaSubInputOption.jmdict, "jmd", "jmdict", "file");
		list.addOption(EasyJaSubInputOption.wkhtmltoimage, WK, "wkhtmltoimage",
				"command");
		list.addOption(EasyJaSubInputOption.height, HEIGHT, "height", "pixels");
		list.addOption(EasyJaSubInputOption.width, WIDTH, "width", "pixels");
		list.addOption(EasyJaSubInputOption.matchdiff, MATCHTIME, "match-diff",
				"milliseconds");
		list.addOption(EasyJaSubInputOption.approxdiff, APPROXTIME,
				"approx-diff", "milliseconds");
		list.addOption(EasyJaSubInputOption.selectlines, SELECT,
				"select-lines", "n-m");
		list.addOption(EasyJaSubInputOption.font, "fn", "font", "name");
		list.addOption(EasyJaSubInputOption.remoteUrl, "url", "remote-url",
				"URL");
		list.addOption(EasyJaSubInputOption.quiet, QUIET, "quiet");
		list.addOption(EasyJaSubInputOption.verbose, VERBOSE, "verbose");
		list.addOption(EasyJaSubInputOption.help, HELP, "help");
	}

	private final EasyJaSubCommandLineOptionList list;
	private String message;
	private boolean isHelp;

	public String getMessage() {
		return message;
	}

	@Override
	public boolean isHelp() {
		return isHelp;
	}

	@Override
	public String getVideoFileName() {
		return videoFileName;
	}

	@Override
	public String getJapaneseSubFileName() {
		return japaneseSubFileName;
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
	public String getOutputIdxFileName() {
		return outputIdxFileName;
	}

	@Override
	public String getOutputHtmlDirectory() {
		return outputHtmlDirectory;
	}

	@Override
	public String getOutputBdnDirectory() {
		return outputBdnDirectory;
	}

	@Override
	public String getWkHtmlToImageCommand() {
		return wkhtmltoimage;
	}

	private String videoFileName;
	private String japaneseSubFileName;
	private String translatedSubFileName;
	private String translatedSubLanguage;
	private String outputIdxFileName;
	private String outputHtmlDirectory;
	private String outputBdnDirectory;
	private String wkhtmltoimage;
	private String outputJapaneseTextFileName;
	private String cssFileName;
	private String exactMatchTimeDiff;
	private String approxMatchTimeDiff;
	private String height;
	private String width;
	private String cssHiraganaFont;
	private String cssKanjiFont;
	private String cssTranslationFont;
	private String outputIdxDirectory;
	private String outputBdnFileName;
	private String showTranslation;
	private String showFurigana;
	private String showDictionary;
	private String showRomaji;
	private String showKanji;
	private String selectLines;
	private String home;
	private String jmdict;
	private String url;
	private int verbose;

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getOutputIdxDirectory() {
		return outputIdxDirectory;
	}

	@Override
	public String getOutputBdnFileName() {
		return outputBdnFileName;
	}

	@Override
	public String getHomeDirectoryName() {
		return home;
	}

	@Override
	public String getJMDictFileName() {
		return jmdict;
	}

	public boolean parse(String[] args) {
		try {
			list.parse(args);
			isHelp = list.hasOption(EasyJaSubInputOption.help);
			videoFileName = list.getOptionValue(EasyJaSubInputOption.video);
			japaneseSubFileName = list
					.getOptionValue(EasyJaSubInputOption.japanesesub);
			translatedSubFileName = list
					.getOptionValue(EasyJaSubInputOption.translatedsub);
			translatedSubLanguage = list
					.getOptionValue(EasyJaSubInputOption.trsublang);
			outputIdxFileName = list
					.getOptionValue(EasyJaSubInputOption.outputidx);
			outputHtmlDirectory = list
					.getOptionValue(EasyJaSubInputOption.outputhtml);
			outputBdnDirectory = list
					.getOptionValue(EasyJaSubInputOption.outputbdmxml);
			wkhtmltoimage = list
					.getOptionValue(EasyJaSubInputOption.wkhtmltoimage);
			outputJapaneseTextFileName = list
					.getOptionValue(EasyJaSubInputOption.outputtext);
			cssFileName = list.getOptionValue(EasyJaSubInputOption.cssstyle);
			exactMatchTimeDiff = list
					.getOptionValue(EasyJaSubInputOption.matchdiff);
			approxMatchTimeDiff = list
					.getOptionValue(EasyJaSubInputOption.approxdiff);
			width = list.getOptionValue(EasyJaSubInputOption.width);
			height = list.getOptionValue(EasyJaSubInputOption.height);
			showDictionary = list
					.getOptionValue(EasyJaSubInputOption.dictionary);
			showFurigana = list.getOptionValue(EasyJaSubInputOption.furigana);
			showRomaji = list.getOptionValue(EasyJaSubInputOption.romaji);
			showKanji = list.getOptionValue(EasyJaSubInputOption.kanji);
			showTranslation = list
					.getOptionValue(EasyJaSubInputOption.translation);
			selectLines = list.getOptionValue(EasyJaSubInputOption.selectlines);
			home = list.getOptionValue(EasyJaSubInputOption.home);
			jmdict = list.getOptionValue(EasyJaSubInputOption.jmdict);
			cssHiraganaFont = cssKanjiFont = list
					.getOptionValue(EasyJaSubInputOption.font);
			url = list.getOptionValue(EasyJaSubInputOption.remoteUrl);
			if (list.hasOption(EasyJaSubInputOption.verbose)) {
				verbose = 1;
			} else if (list.hasOption(EasyJaSubInputOption.quiet)) {
				verbose = -1;
			}
		} catch (Exception ex) {
			message = ex.getMessage();
		}
		if (!isHelp && message != null) {
			return false;
		}
		return true;
	}

	public void printHelp(PrintWriter stream, String usage, String header,
			String footer) {
		list.printHelp(stream, usage, header, footer);
	}

	@Override
	public String getSelectLines() {
		return selectLines;
	}

	@Override
	public String getOutputJapaneseTextFileName() {
		return outputJapaneseTextFileName;
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
	public String getApproxMatchTimeDiff() {
		return approxMatchTimeDiff;
	}

	@Override
	public String getHeight() {
		return height;
	}

	@Override
	public String getWidth() {
		return width;
	}

	@Override
	public String getCssHiraganaFont() {
		return cssHiraganaFont;
	}

	@Override
	public String getCssKanjiFont() {
		return cssKanjiFont;
	}

	@Override
	public String getCssTranslationFont() {
		return cssTranslationFont;
	}

	@Override
	public String getShowRomaji() {
		return showRomaji;
	}

	@Override
	public String getShowTranslation() {
		return showTranslation;
	}

	@Override
	public String getShowKanji() {
		return showKanji;
	}

	@Override
	public String getShowFurigana() {
		return showFurigana;
	}

	@Override
	public String getShowDictionary() {
		return showDictionary;
	}

	@Override
	public int getVerbose() {
		return verbose;
	}

}
