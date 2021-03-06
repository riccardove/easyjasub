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
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;

import com.github.riccardove.easyjasub.commons.CommonsLangSystemUtils;

/**
 * Checks input commands and determines valid program input using some heuristic
 * 
 * TODO: split into multiple classes
 */
class EasyJaSubInputFromCommand implements EasyJaSubInput, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3080110516084720076L;

	private static void checkDirectory(String fileName, File file)
			throws EasyJaSubException {
		if (!file.isDirectory()) {
			throw new EasyJaSubException(fileName + " is not a directory");
		}
		if (!file.canRead()) {
			throw new EasyJaSubException("Directory " + fileName
					+ " can not be read");
		}
		if (!file.canWrite()) {
			throw new EasyJaSubException("Directory " + fileName
					+ " can not be written");
		}
	}

	private static void checkFile(String fileName, File file)
			throws EasyJaSubException {
		if (!file.exists()) {
			throw new EasyJaSubException("File " + fileName + " does not exist");
		}
		if (!file.isFile()) {
			throw new EasyJaSubException(fileName + " is not a file");
		}
		if (!file.canRead()) {
			throw new EasyJaSubException("File " + fileName
					+ " can not be read");
		}
	}

	private static void checkOutputFile(String fileName, File file)
			throws EasyJaSubException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new EasyJaSubException("Invalid output file, " + fileName
						+ " is a directory");
			}
			return;
		}
		File directory = file.getAbsoluteFile();
		do {
			directory = directory.getParentFile();
		} while (directory != null && !directory.exists());
		if (directory != null && !directory.canWrite()) {
			throw new EasyJaSubException("Can not write on "
					+ directory.getAbsolutePath() + " to create " + fileName);
		}
	}

	private static File getCssFile(EasyJaSubInputCommand command,
			File htmlDirectory, DefaultFileList defaultFileList)
			throws EasyJaSubException {
		String fileName = command.getCssFileName();
		if (isDisabled(fileName)) {
			return null;
		}
		if (fileName != null && !isDefault(fileName)) {
			File file = new File(fileName);
			if (file.exists()) {
				checkFile(fileName, file);
				if (!isCssContentType(file)) {
					throw new EasyJaSubException("File " + fileName
							+ " does not seem to be CSS, type is "
							+ probeContentType(file));
				}
			}
			return file;
		}
		for (File file : defaultFileList) {
			String extension = getExtension(file);
			if (extension == "CSS" && isCssContentType(file)) {
				return file;
			}
		}
		File directory = htmlDirectory;
		if (directory == null) {
			directory = defaultFileList.getDefaultDirectory();
		}
		return new File(directory, defaultFileList.getDefaultFileNamePrefix()
				+ ".css");
	}

	private static int getDimension(String name, String timeStr,
			int defaultValue) throws EasyJaSubException {
		return getInteger(name, timeStr, defaultValue, 400, 3000);
	}

	private static String getExtension(File file) {
		String ext = FilenameUtils.getExtension(file.getName());
		return ext != null ? ext.toUpperCase() : null;
	}

	private static int getInteger(String name, String timeStr,
			int defaultValue, int min, int max) throws EasyJaSubException {
		if (timeStr == null || isDefault(timeStr)) {
			return defaultValue;
		}
		int value = parseInteger("Invalid " + name, timeStr);
		if (value < 0) {
			throw new EasyJaSubException("Negative values are not allowed for "
					+ name);
		}
		if (value < min) {
			throw new EasyJaSubException("Minimum value for " + name + " is "
					+ min + ", " + value + " should be increased");
		}
		if (value > max) {
			throw new EasyJaSubException("Maximum value for " + name + " is "
					+ max + ", " + value + " should be decreased");
		}
		return value;
	}

	private static File getJapaneseSubFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList) throws EasyJaSubException {
		String fileName = command.getJapaneseSubFileName();
		if (isDisabled(fileName)) {
			return null;
		}
		if (fileName != null && !isDefault(fileName)) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isTextContentType(file)) {
				throw new EasyJaSubException("File " + fileName
						+ " does not seem to be text, type is "
						+ probeContentType(file));
			}
			if (!isSubExtension(getExtension(file))) {
				throw new EasyJaSubException("File " + fileName
						+ " does not have a valid subtitle file extension");
			}
			return file;
		}
		for (File file : defaultFileList) {
			String extension = getExtension(file);
			if (isTextContentType(file) && isSubExtension(extension)
					&& isJapaneseLanguageFromFileName(file)) {
				return file;
			}
		}
		return null;
	}

	private static File getOutputBdnFile(EasyJaSubInputCommand command,
			File outputIdxFile, DefaultFileList defaultFileList)
			throws EasyJaSubException {
		String fileName = command.getOutputBdnFileName();
		if (isDisabled(fileName)) {
			return null;
		}
		File file = null;
		File directory = null;
		String fileNameBase = null;
		if (fileName != null && !isDefault(fileName)) {
			file = new File(fileName);
		} else {
			fileNameBase = defaultFileList.getDefaultFileNamePrefix();
			String directoryName = command.getOutputBdnDirectory();
			if (directoryName != null) {
				directory = new File(directoryName);
			} else if (outputIdxFile != null) {
				directory = new File(outputIdxFile.getAbsoluteFile()
						.getParentFile(), fileNameBase + "_easyjasub");
			} else {
				directory = defaultFileList.getDefaultDirectory();
			}
			file = new File(directory, fileNameBase + ".xml");
			fileName = file.getAbsolutePath();
		}
		checkOutputFile(fileName, file);
		return file;
	}

	private static File getOutputHtmlDirectory(EasyJaSubInputCommand command,
			File outputbdnFile, DefaultFileList defaultFileList)
			throws EasyJaSubException {
		String directoryName = command.getOutputHtmlDirectory();
		if (isDisabled(directoryName)) {
			return null;
		}
		File directory = null;
		if (directoryName != null && !isDefault(directoryName)) {
			directory = new File(directoryName);
		} else if (outputbdnFile != null) {
			directory = new File(outputbdnFile.getAbsoluteFile()
					.getParentFile(), "html");
		} else {
			directory = new File(defaultFileList.getDefaultDirectory(),
					defaultFileList.getDefaultFileNamePrefix() + "_html");
		}
		if (directoryName == null) {
			directoryName = directory.getAbsolutePath();
		}
		if (directory.exists()) {
			checkDirectory(directoryName, directory);
		}
		return directory;
	}

	private static File getOutputIdxFile(EasyJaSubInputCommand command,
			File videoFile, DefaultFileList defaultFileList)
			throws EasyJaSubException {
		String fileName = command.getOutputIdxFileName();
		if (isDisabled(fileName)) {
			return null;
		}
		File file = null;
		if (fileName != null && !isDefault(fileName)) {
			file = new File(fileName);
		}
		if (file == null) {
			if (videoFile == null) {
				file = new File(defaultFileList.getDefaultDirectory(),
						defaultFileList.getDefaultFileNamePrefix() + ".idx");
			} else {
				file = new File(videoFile.getAbsoluteFile().getParentFile(),
						FilenameUtils.getBaseName(videoFile.getName()) + ".idx");
			}
			fileName = file.getAbsolutePath();
		}
		checkOutputFile(fileName, file);
		return file;
	}

	private static File getOutputJapaneseTextFile(
			EasyJaSubInputCommand command, File bdnXmlFile,
			DefaultFileList defaultFileList) throws EasyJaSubException {
		String fileName = command.getOutputJapaneseTextFileName();
		if (isDisabled(fileName)) {
			return null;
		}
		File file = null;
		String fileNameBase = null;
		if (fileName != null && !isDefault(fileName)) {
			file = new File(fileName);
		} else {
			fileNameBase = defaultFileList.getDefaultFileNamePrefix() + ".txt";
			File directory = defaultFileList.getDefaultDirectory();
			if (bdnXmlFile != null) {
				directory = bdnXmlFile.getParentFile();
			}
			file = new File(directory, fileNameBase);
			fileName = file.getAbsolutePath();
		}
		checkOutputFile(fileName, file);
		return file;
	}

	private static SubtitleFileType getSubtitleFileType(File file)
			throws EasyJaSubException {
		if (file == null) {
			return SubtitleFileType.Undef;
		}
		String extension = getExtension(file);
		try {
			return SubtitleFileType.valueOf(extension);
		} catch (Throwable ex) {
			throw new EasyJaSubException(
					"Unrecognized subtitle file extension: " + extension);
		}
	}

	private static String getSubtitleLanguageFromFileName(String fileName) {
		return EasyJaSubLanguageCode.getLanguageCodeFromFileName(fileName);
	}

	private static int getTimeDiff(String name, String timeStr, int defaultValue)
			throws EasyJaSubException {
		return getInteger(name, timeStr, defaultValue, 100, 5000);
	}

	private static File getTranslatedSubFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList, File japaneseSubFile,
			SubtitleFileType japaneseSubFileType) throws EasyJaSubException {
		String fileName = command.getTranslatedSubFileName();
		if (isDisabled(fileName)) {
			return null;
		}
		if (fileName != null && !isDefault(fileName)) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isTextContentType(file)) {
				throw new EasyJaSubException("File " + fileName
						+ " does not seem to be text, type is "
						+ probeContentType(file));
			}
			if (!isSubExtension(getExtension(file))) {
				throw new EasyJaSubException("File " + fileName
						+ " does not have a valid subtitle file extension");
			}
			return file;
		}
		if (japaneseSubFileType != SubtitleFileType.TXT) {
			// by default, no translation when japanese is in text format
			for (File file : defaultFileList) {
				String extension = getExtension(file);
				if (!file.equals(japaneseSubFile) && isTextContentType(file)
						&& isSubExtension(extension)) {
					return file;
				}
			}
		}
		return null;
	}

	private static String getTranslatedSubLanguage(
			EasyJaSubInputCommand command, File translatedSubFile) {
		String result = command.getTranslatedSubLanguage();
		if (result == null && translatedSubFile != null) {
			result = getSubtitleLanguageFromFileName(translatedSubFile
					.getName());
		}
		return result;
	}

	private static File getVideoFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList) throws EasyJaSubException {
		String fileName = command.getVideoFileName();
		if (isDisabled(fileName)) {
			return null;
		}
		if (fileName != null && !isDefault(fileName)) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isVideoContentType(file)) {
				throw new EasyJaSubException("File " + fileName
						+ " does not seem to be video, type is "
						+ probeContentType(file));
			}
			return file;
		}
		for (File file : defaultFileList) {
			if (isVideoContentType(file)
					&& isVideoExtension(getExtension(file))) {
				return file;
			}
		}
		return null;
	}

	private static String getWkhtmltoimageFile(EasyJaSubInputCommand command)
			throws EasyJaSubException {
		String fileName = command.getWkHtmlToImageCommand();
		if (isDisabled(fileName)) {
			return null;
		}
		if (fileName != null && !isDefault(fileName)) {
			File file = new File(fileName);
			checkFile(fileName, file);
			return fileName;
		}
		if (CommonsLangSystemUtils.isWindows()) {
			// gets the file from default installation folder in Windows
			String programFiles = SystemEnv.getWindowsProgramFiles32();
			if (programFiles == null) {
				programFiles = SystemEnv.getWindowsProgramFiles();
			}
			File directory = new File(programFiles, "wkhtmltopdf");
			if (directory.exists()) {
				File file = new File(directory, "wkhtmltoimage.exe");
				if (file.exists()) {
					fileName = file.getAbsolutePath();
					checkFile(fileName, file);
					return fileName;
				}
			}
		} else {
			// guesses the installation folder on Unix
			// TODO: this is very system dependent
			File file = new File("/usr/bin/wkhtmltoimage");
			if (file.exists()) {
				return file.getAbsolutePath();
			}
		}
		throw new EasyJaSubException(
				"Could not find the wkhtmltoimage program, use the -wk option, if you want to try the internal renderer use the \"-wk disabled\" option");
	}

	private static boolean isCssContentType(File file) {
		String type = probeContentType(file);
		return type == null || "text/css".equals(type)
				|| "text/plain".equals(type);
	}

	private static boolean isDefault(String name) {
		return "default".equals(name);
	}

	private static boolean isDisabled(String name) {
		return "disabled".equals(name);
	}

	private static boolean isEnabled(String name) {
		return "enabled".equals(name);
	}

	private static boolean isJapaneseLanguageFromFileName(File file) {
		return EasyJaSubLanguageCode.isJapaneseLanguageFromFileName(file
				.getName());
	}

	private static boolean isSubExtension(String ext) {
		return SubtitleFileType.isValue(ext);
	}

	// TODO: check the file type of all supported subtitles formats
	private static boolean isTextContentType(File file) {
		return true;
		// String type = probeContentType(file);
		// return type == null || type.startsWith("text/");
	}

	private static boolean isVideoContentType(File file) {
		String type = probeContentType(file);
		return type == null || type.startsWith("video/");
	}

	private static boolean isVideoExtension(String ext) {
		return VideoFileType.isValue(ext);
	}

	private static int parseInteger(String message, String timeStr)
			throws EasyJaSubException {
		try {
			return Integer.parseInt(timeStr);
		} catch (Exception ex) {
			throw new EasyJaSubException(message + ": " + timeStr
					+ " is not a number or a valid value");
		}
	}

	private static String probeContentType(File file) {
		try {
			return Files.probeContentType(file.toPath());
		} catch (IOException ex) {
			return null;
		}
	}

	private final int approxMatchTimeDiff;
	private final File bdnXmlFile;
	private final File cssFile;
	private final String cssHiraganaFont;
	private final String cssKanjiFont;
	private final String cssTranslationFont;
	private final String defaultFileNamePrefix;
	private final int exactMatchTimeDiff;
	private final int height;
	private final File japaneseSubFile;
	private final SubtitleFileType japaneseSubFileType;
	private final File outputHtmlDirectory;
	private final File outputIdxFile;
	private final File outputJapaneseTextFile;
	private final File translatedSubFile;
	private final SubtitleFileType translatedSubFileType;
	private final String translatedSubLanguage;
	private final File videoFile;
	private final int width;
	private final String wkhtmltoimageFile;
	private final boolean showTranslation;
	private final boolean showFurigana;
	private final boolean showDictionary;
	private final boolean showRomaji;
	private final boolean showKanji;
	private final File xmlFile;
	private final File jglossFile;
	private int startLine;
	private int endLine;
	private final boolean isSingleLine;
	private final File htmlFile;

	private static File abs(File file) {
		return file != null ? file.getAbsoluteFile() : null;
	}

	public EasyJaSubInputFromCommand(EasyJaSubInputCommand command)
			throws EasyJaSubException {

		EasyJaSubCmdHomeDir homeDir = getHomeDir(command.getHomeDirectoryName());

		DefaultFileList defaultFileList = new DefaultFileList(command);
		japaneseSubFile = abs(getJapaneseSubFile(command, defaultFileList));
		japaneseSubFileType = getSubtitleFileType(japaneseSubFile);
		translatedSubFile = abs(getTranslatedSubFile(command, defaultFileList,
				japaneseSubFile, japaneseSubFileType));
		translatedSubFileType = getSubtitleFileType(translatedSubFile);
		translatedSubLanguage = getTranslatedSubLanguage(command,
				translatedSubFile);
		videoFile = abs(getVideoFile(command, defaultFileList));
		outputIdxFile = abs(getOutputIdxFile(command, videoFile,
				defaultFileList));
		bdnXmlFile = abs(getOutputBdnFile(command, outputIdxFile,
				defaultFileList));
		outputHtmlDirectory = abs(getOutputHtmlDirectory(command, bdnXmlFile,
				defaultFileList));
		wkhtmltoimageFile = getWkhtmltoimageFile(command);
		outputJapaneseTextFile = abs(getOutputJapaneseTextFile(command,
				bdnXmlFile, defaultFileList));
		cssFile = abs(getCssFile(command, outputHtmlDirectory, defaultFileList));
		exactMatchTimeDiff = getTimeDiff("exact match time",
				command.getExactMatchTimeDiff(), 2000);
		approxMatchTimeDiff = getTimeDiff("approximate match time",
				command.getApproxMatchTimeDiff(), 500);
		height = getDimension("height", command.getHeight(), 720);
		width = getDimension("width", command.getWidth(), 1280);
		defaultFileNamePrefix = defaultFileList.getDefaultFileNamePrefix();
		cssHiraganaFont = getFont(command.getCssHiraganaFont(), "arial");
		cssKanjiFont = getFont(command.getCssKanjiFont(), "arial");
		cssTranslationFont = getFont(command.getCssTranslationFont(), "arial");
		jmDictFile = abs(getJMDictFile(homeDir, command.getJMDictFileName()));
		showTranslation = getShowTranslation(command.getShowTranslation(),
				translatedSubFile);
		showKanji = getShowKanji(command.getShowKanji());
		showFurigana = getShowFurigana(command.getShowFurigana(), showKanji);
		showDictionary = getShowDictionary(command.getShowDictionary(),
				jmDictFile);
		showRomaji = getShowRomaji(command.getShowRomaji(), showFurigana);
		xmlFile = abs(getXmlFile(defaultFileList, outputJapaneseTextFile,
				bdnXmlFile));
		jglossFile = abs(getJGlossFile(defaultFileList, bdnXmlFile));
		isSingleLine = getSingleLine();
		getSelectLines(command.getSelectLines());
		dictionaryCacheFile = abs(getDictionaryCacheFile(homeDir));
		htmlFile = abs(getHtmlFile(defaultFileList, bdnXmlFile));
		url = getUrl(command.getUrl());
	}

	private final URL url;

	private static URL getUrl(String urlStr) throws EasyJaSubException {
		if (urlStr != null) {
			try {
				return new URL(urlStr);
			} catch (Exception ex) {
				throw new EasyJaSubException("Invalid URL: "
						+ ex.getLocalizedMessage());
			}
		}
		return null;
	}

	public URL getUrl() {
		return url;
	}

	private static File getHtmlFile(DefaultFileList defaultFileList,
			File bdnXmlFile) {
		File dir = null;
		if (bdnXmlFile != null) {
			dir = bdnXmlFile.getParentFile();
		} else {
			dir = defaultFileList.getDefaultDirectory();
		}
		return new File(dir, defaultFileList.getDefaultFileNamePrefix()
				+ ".html");
	}

	private EasyJaSubCmdHomeDir getHomeDir(String directoryName)
			throws EasyJaSubException {
		File directory = null;
		if (directoryName != null && !isDefault(directoryName)
				&& !isEnabled(directoryName)) {
			if (isDisabled(directoryName)) {
				return null;
			}
			directory = new File(directoryName);
			if (directory.exists()) {
				if (!directory.isDirectory()) {
					throw new EasyJaSubException("Home directory "
							+ directoryName + " is not a directory");
				}
				if (!directory.canRead()) {
					throw new EasyJaSubException("Home directory "
							+ directoryName + " can not be read");
				}
				if (!directory.canWrite()) {
					throw new EasyJaSubException("Home directory "
							+ directoryName + " can not be written");
				}
			}
		}
		EasyJaSubCmdHomeDir homeDir = new EasyJaSubCmdHomeDir();
		homeDir.init(directory);
		return homeDir;
	}

	private static File getDictionaryCacheFile(EasyJaSubCmdHomeDir homeDir) {
		// TODO allow options
		return homeDir.getDictionaryFile();
	}

	private static File getJMDictFile(EasyJaSubCmdHomeDir homeDir,
			String fileName) throws EasyJaSubException {
		if (fileName != null && !isDefault(fileName) && !isEnabled(fileName)) {
			if (isDisabled(fileName)) {
				return null;
			}
			File file = new File(fileName);
			if (!file.exists() || !file.isFile()) {
				throw new EasyJaSubException("Can not find JMdict file "
						+ fileName);
			}
			if (!file.canRead()) {
				throw new EasyJaSubException("Can not read JMdict file "
						+ fileName);
			}
			return file;
		}
		if (homeDir != null) {
			File file = homeDir.getJMDictFile();
			if (file != null && file.isFile() && file.canRead()) {
				return file;
			}
		}
		return null;
	}

	private final File jmDictFile;
	private final File dictionaryCacheFile;

	private boolean getSingleLine() {
		if (wkhtmltoimageFile != null) {
			// ruby tag is not supported by Java-only HTML renderer
			if (showKanji) {
				if (showRomaji) {
					return !showFurigana && !showDictionary;
				} else if (showFurigana) {
					return !showDictionary;
				}
				return true;
			} else {
				if (showRomaji) {
					return !showFurigana || !showDictionary;
				}
				return true;
			}
		}
		return false; // TODO
	}

	private File getXmlFile(DefaultFileList defaultFileList,
			File outputJapaneseTextFile, File bdnXmlFile) {
		// TODO select a file
		File directory = null;
		if (bdnXmlFile != null) {
			directory = bdnXmlFile.getParentFile();
		} else {
			directory = defaultFileList.getDefaultDirectory();
		}
		return new File(directory, defaultFileList.getDefaultFileNamePrefix()
				+ ".easyjasub");
	}

	private static File getJGlossFile(DefaultFileList defaultFileList,
			File bdnXmlFile) {
		// TODO select a file
		File directory = defaultFileList.getDefaultDirectory();
		if (bdnXmlFile != null) {
			directory = bdnXmlFile.getParentFile();
		}
		return new File(directory, defaultFileList.getDefaultFileNamePrefix()
				+ ".jgloss");
	}

	private void getSelectLines(String selectLines) throws EasyJaSubException {
		startLine = 0;
		endLine = 0;
		if (selectLines == null || isDefault(selectLines)
				|| isDisabled(selectLines)) {
			return;
		}
		selectLines = selectLines.trim();
		int separator = selectLines.indexOf('-');
		if (separator < 0) {
			try {
				endLine = Integer.parseInt(selectLines);
			} catch (Exception ex) {
				throw new EasyJaSubException("Invalid lines selection: "
						+ selectLines);
			}
		} else if (separator == 0) {
			try {
				endLine = Integer.parseInt(selectLines.substring(1));
			} catch (Exception ex) {
				throw new EasyJaSubException("Invalid lines selection: "
						+ selectLines);
			}
		} else if (separator == selectLines.length() - 1) {
			try {
				startLine = Integer.parseInt(selectLines.substring(0,
						selectLines.length() - 1));
			} catch (Exception ex) {
				throw new EasyJaSubException("Invalid lines selection: "
						+ selectLines);
			}
		} else {
			try {
				startLine = Integer.parseInt(selectLines
						.substring(0, separator));
			} catch (Exception ex) {
				throw new EasyJaSubException("Invalid lines selection: "
						+ selectLines);
			}
			try {
				endLine = Integer.parseInt(selectLines.substring(separator + 1,
						selectLines.length() - separator - 1));
			} catch (Exception ex) {
				throw new EasyJaSubException("Invalid lines selection: "
						+ selectLines);
			}
		}
		if (endLine < 0) {
			throw new EasyJaSubException(
					"Invalid end line in lines selection: " + selectLines);
		}
		if (startLine < 0 || startLine > endLine) {
			throw new EasyJaSubException(
					"Invalid start line in lines selection: " + selectLines);
		}
	}

	private static boolean getShowTranslation(String showTranslation,
			File translatedSubFile) throws EasyJaSubException {
		if (showTranslation == null || isDefault(showTranslation)) {
			return translatedSubFile != null;
		}
		if (isDisabled(showTranslation)) {
			return false;
		}
		if (isEnabled(showTranslation)) {
			if (translatedSubFile == null) {
				throw new EasyJaSubException(
						"Can not display translation without translated subs");
			}
			return true;
		}
		throw new EasyJaSubException("Invalid setting for translation: "
				+ showTranslation);
	}

	private static boolean getShowRomaji(String showRomaji, boolean showFurigana)
			throws EasyJaSubException {
		if (showRomaji == null || isDefault(showRomaji)) {
			return !showFurigana;
		}
		if (isDisabled(showRomaji)) {
			return false;
		}
		if (isEnabled(showRomaji)) {
			return true;
		}
		throw new EasyJaSubException("Invalid setting for romaji: "
				+ showRomaji);
	}

	private static boolean getShowKanji(String show) throws EasyJaSubException {
		if (show == null || isDefault(show)) {
			return true;
		}
		if (isDisabled(show)) {
			return false;
		}
		if (isEnabled(show)) {
			return true;
		}
		throw new EasyJaSubException("Invalid setting for kanji: " + show);
	}

	private static boolean getShowFurigana(String show, boolean showKanji)
			throws EasyJaSubException {
		if (show == null || isDefault(show)) {
			return true;
		}
		if (isDisabled(show)) {
			return false;
		}
		if (isEnabled(show)) {
			return true;
		}
		throw new EasyJaSubException("Invalid setting for furigana: " + show);
	}

	private static boolean getShowDictionary(String show, File jmDictFile)
			throws EasyJaSubException {
		if (show == null || isDefault(show)) {
			return jmDictFile != null;
		}
		if (isDisabled(show)) {
			return false;
		}
		if (isEnabled(show)) {
			if (jmDictFile == null) {
				throw new EasyJaSubException(
						"Can not display dictionary without JMdict file");
			}
			return true;
		}
		throw new EasyJaSubException("Invalid setting for dictionary: " + show);
	}

	@Override
	public int getApproxMatchTimeDiff() {
		return approxMatchTimeDiff;
	}

	@Override
	public File getBdnXmlFile() {
		return bdnXmlFile;
	}

	@Override
	public File getCssFile() {
		return cssFile;
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
	public String getDefaultFileNamePrefix() {
		return defaultFileNamePrefix;
	}

	@Override
	public int getExactMatchTimeDiff() {
		return exactMatchTimeDiff;
	}

	private String getFont(String fontStr, String defaultFontStr) {
		return fontStr != null && !isDefault(fontStr) ? fontStr
				: defaultFontStr;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public File getJapaneseSubFile() {
		return japaneseSubFile;
	}

	@Override
	public SubtitleFileType getJapaneseSubFileType() {
		return japaneseSubFileType;
	}

	@Override
	public File getOutputHtmlDirectory() {
		return outputHtmlDirectory;
	}

	@Override
	public File getOutputIdxFile() {
		return outputIdxFile;
	}

	@Override
	public File getOutputJapaneseTextFile() {
		return outputJapaneseTextFile;
	}

	@Override
	public File getTranslatedSubFile() {
		return translatedSubFile;
	}

	@Override
	public SubtitleFileType getTranslatedSubFileType() {
		return translatedSubFileType;
	}

	@Override
	public String getTranslatedSubLanguage() {
		return translatedSubLanguage;
	}

	@Override
	public File getVideoFile() {
		return videoFile;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public String getWkHtmlToImageCommand() {
		return wkhtmltoimageFile;
	}

	@Override
	public boolean showTranslation() {
		return showTranslation;
	}

	@Override
	public boolean showRomaji() {
		return showRomaji;
	}

	@Override
	public boolean showFurigana() {
		return showFurigana;
	}

	@Override
	public boolean showDictionary() {
		return showDictionary;
	}

	@Override
	public boolean showKanji() {
		return showKanji;
	}

	@Override
	public int getStartLine() {
		return startLine;
	}

	@Override
	public int getEndLine() {
		return endLine;
	}

	@Override
	public File getXmlFile() {
		return xmlFile;
	}

	@Override
	public boolean isSingleLine() {
		return isSingleLine;
	}

	@Override
	public File getJGlossFile() {
		return jglossFile;
	}

	@Override
	public File getJMDictFile() {
		return jmDictFile;
	}

	@Override
	public File getDictionaryCacheFile() {
		return null; // TODO: serialization seems slower than reading original
						// file; dictionaryCacheFile;
	}

	@Override
	public File getHtmlFile() {
		return htmlFile;
	}
}
