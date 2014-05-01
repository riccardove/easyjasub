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

import com.github.riccardove.easyjasub.commandline.CommandLineContent;
import com.github.riccardove.easyjasub.commandline.CommandLineOptionList;

class EasyJaSubCommandLine implements EasyJaSubInputCommand {
	private static final String HELP = "h";
	private static final String VI = "vi";
	private static final String JA = "ja";
	private static final String TR = "tr";
	private static final String NJ = "nj";
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
	private static final String MECAB = "mc";

	public EasyJaSubCommandLine() {
		list = new CommandLineOptionList();
		addOption(EasyJaSubInputOption.video, VI, "video", "file");
		addOption(EasyJaSubInputOption.japanesesub, JA, "japanese-sub", "file");
		addOption(EasyJaSubInputOption.translatedsub, TR, "translated-sub",
				"file");
		addOption(EasyJaSubInputOption.nihongojtalk, NJ, "nihongo-jtalk",
				"file");
		addOption(EasyJaSubInputOption.translation, TRANSLATION, "translation",
				"enabled|disabled");
		addOption(EasyJaSubInputOption.romaji, ROMAJI, "romaji",
				"enabled|disabled");
		addOption(EasyJaSubInputOption.dictionary, DICTIONARY, "dictionary",
				"enabled|disabled");
		addOption(EasyJaSubInputOption.furigana, FURIGANA, "furigana",
				"enabled|disabled");
		addOption(EasyJaSubInputOption.kanji, KANJI, "kanji",
				"enabled|disabled");
		addOption(EasyJaSubInputOption.trsublang, TRL, "tr-sub-lang",
				"language");
		addOption(EasyJaSubInputOption.outputtext, TX, "output-text", "file");
		addOption(EasyJaSubInputOption.cssstyle, CSS, "css-style", "file");
		addOption(EasyJaSubInputOption.outputidx, IDX, "output-idx", "file");
		addOption(EasyJaSubInputOption.outputhtml, HTML, "output-html",
				"directory");
		addOption(EasyJaSubInputOption.outputbdmxml, BDN, "output-bdmxml",
				"directory");
		addOption(EasyJaSubInputOption.mecab, MECAB, "mecab", "command");
		addOption(EasyJaSubInputOption.wkhtmltoimage, WK, "wkhtmltoimage",
				"command");
		addOption(EasyJaSubInputOption.height, HEIGHT, "height", "pixels");
		addOption(EasyJaSubInputOption.width, WIDTH, "width", "pixels");
		addOption(EasyJaSubInputOption.matchdiff, MATCHTIME, "match-diff",
				"milliseconds");
		addOption(EasyJaSubInputOption.approxdiff, APPROXTIME, "approx-diff",
				"milliseconds");
		addOption(EasyJaSubInputOption.selectlines, SELECT, "select-lines",
				"n-m");
		addOption(EasyJaSubInputOption.quiet, QUIET, "quiet");
		addOption(EasyJaSubInputOption.verbose, VERBOSE, "verbose");
		addOption(EasyJaSubInputOption.help, HELP, "help");
	}

	private void addOption(EasyJaSubInputOption optionIndex, String shortName,
			String longName) {
		list.addOption(shortName, longName,
				EasyJaSubProperty.getOptionDescription(optionIndex));
	}

	private void addOption(EasyJaSubInputOption optionIndex, String shortName,
			String longName, String argumentDescription) {
		list.addOption(shortName, longName,
				EasyJaSubProperty.getOptionDescription(optionIndex),
				argumentDescription);
	}

	private final CommandLineOptionList list;
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
	public String getNihongoJtalkHtmlFileName() {
		return nihongoJtalkHtmlFileName;
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
	private String nihongoJtalkHtmlFileName;
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
	private String mecab;
	private int verbose;

	@Override
	public String getOutputIdxDirectory() {
		return outputIdxDirectory;
	}

	@Override
	public String getOutputBdnFileName() {
		return outputBdnFileName;
	}

	public boolean parse(String[] args) {
		try {
			CommandLineContent cm = list.parse(args);
			String d = HELP;
			isHelp = cm.hasOption(d);
			videoFileName = cm.getOptionValue(VI);
			japaneseSubFileName = cm.getOptionValue(JA);
			translatedSubFileName = cm.getOptionValue(TR);
			nihongoJtalkHtmlFileName = cm.getOptionValue(NJ);
			translatedSubLanguage = cm.getOptionValue(TRL);
			outputIdxFileName = cm.getOptionValue(IDX);
			outputHtmlDirectory = cm.getOptionValue(HELP);
			outputBdnDirectory = cm.getOptionValue(BDN);
			wkhtmltoimage = cm.getOptionValue(WK);
			outputJapaneseTextFileName = cm.getOptionValue(TX);
			cssFileName = cm.getOptionValue(CSS);
			exactMatchTimeDiff = cm.getOptionValue(MATCHTIME);
			approxMatchTimeDiff = cm.getOptionValue(APPROXTIME);
			width = cm.getOptionValue(WIDTH);
			height = cm.getOptionValue(HEIGHT);
			showDictionary = cm.getOptionValue(DICTIONARY);
			showFurigana = cm.getOptionValue(FURIGANA);
			showRomaji = cm.getOptionValue(ROMAJI);
			showKanji = cm.getOptionValue(KANJI);
			showTranslation = cm.getOptionValue(TRANSLATION);
			selectLines = cm.getOptionValue(SELECT);
			mecab = cm.getOptionValue(MECAB);
			if (cm.hasOption(VERBOSE)) {
				verbose = 1;
			} else if (cm.hasOption(QUIET)) {
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
	public String getMeCabCommand() {
		return mecab;
	}

	@Override
	public int getVerbose() {
		return verbose;
	}

}
