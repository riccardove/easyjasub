package com.github.riccardove.easyjasub.commandline;

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

import com.github.riccardove.easyjasub.EasyJaSubInputCommand;

public class EasyJaSubCommandLine implements EasyJaSubInputCommand {
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
		list.addOption(VI, "video",
				"Sets file as reference for video and audio", "file");
		list.addOption(
				JA,
				"japanese-sub",
				"Read japanese subtitles from file. "
						+ "If not specified files with the same name of video file and of supported text subtitle type are searched.",
				"file");
		list.addOption(
				TR,
				"translated-sub",
				"Read translated subtitles from file. "
						+ "If not specified files with the same name of video file and of supported text subtitle type are searched.",
				"file");
		list.addOption(
				NJ,
				"nihongo-jtalk",
				"Read text analysys produced with http://nihongo.j-talk.com from file. "
						+ "If not specified HTML files with the same name of video file are searched.",
				"file");

		list.addOption(TRANSLATION, "translation",
				"Displays translated subtitles", "enabled|disabled");
		list.addOption(ROMAJI, "romaji", "Shows romaji text in subtitles",
				"enabled|disabled");
		list.addOption(DICTIONARY, "dictionary",
				"Shows dictionary text in subtitles", "enabled|disabled");
		list.addOption(FURIGANA, "furigana",
				"Shows furigana text in subtitles", "enabled|disabled");
		list.addOption(KANJI, "kanji", "Shows kanji text in subtitles",
				"enabled|disabled");

		list.addOption(
				TRL,
				"tr-sub-lang",
				"Sets language (two letter ISO code) as language used for translation. "
						+ "By default reads it from the translated subtitle file.",
				"language");
		list.addOption(TX, "output-text",
				"File name for text file of japanese subtitles", "file");
		list.addOption(CSS, "css-style",
				"File name for CSS file used to style subtitles", "file");
		list.addOption(IDX, "output-idx",
				"File name for IDX file of IDX/SUB subtitles", "file");
		list.addOption(HTML, "output-html",
				"Writes HTML intermediate files in directory", "directory");
		list.addOption(BDN, "output-bdmxml",
				"Writes BDN/XML intermediate files in directory", "directory");
		list.addOption(MECAB, "mecab", "Command to execute MeCab program",
				"command");
		list.addOption(WK, "wkhtmltoimage",
				"Command to execute wkhtmltoimage program", "command");
		list.addOption(HEIGHT, "height",
				"Height of the generated subtitles pictures", "pixels");
		list.addOption(WIDTH, "width",
				"Width of the generated subtitles pictures", "pixels");
		list.addOption(
				MATCHTIME,
				"match-diff",
				"Amount of milliseconds of difference in time to consider two subtitle lines the same",
				"milliseconds");
		list.addOption(
				APPROXTIME,
				"approx-diff",
				"Amount of milliseconds of difference in time to consider two subtitle lines approximately the same",
				"milliseconds");
		list.addOption(
				SELECT,
				"select-lines",
				"Select a subset of subtitle lines from japanese subtitles, useful for sampling",
				"n-m");
		list.addOption(QUIET, "quiet", "Suppresses output messages");
		list.addOption(VERBOSE, "verbose", "More verbose output messages");
		list.addOption(HELP, "help", "Displays help");
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
			isHelp = cm.hasOption(HELP);
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

	public void printHelp(PrintWriter stream, String usage) {
		list.printHelp(stream, usage, null, null);
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
