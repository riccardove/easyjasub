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
import java.nio.file.Files;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

/**
 * Checks input commands and determines valid program input using some heuristic
 */
class EasyJaSubInputFromCommand implements EasyJaSubInput {

	private static void checkFile(String fileName, File file) throws EasyJaSubException {
		if (!file.exists()) {
			throw new EasyJaSubException("File " + fileName + " does not exist");
		}
		if (!file.isFile()) {
			throw new EasyJaSubException(fileName + " is not a file");
		}
		if (!file.canRead()) {
			throw new EasyJaSubException("File " + fileName + " can not be read");
		}
	}

	private static void checkDirectory(String fileName, File file) throws EasyJaSubException {
		if (!file.isDirectory()) {
			throw new EasyJaSubException(fileName + " is not a directory");
		}
		if (!file.canRead()) {
			throw new EasyJaSubException("Directory " + fileName + " can not be read");
		}
		if (!file.canWrite()) {
			throw new EasyJaSubException("Directory " + fileName + " can not be written");
		}
	}

	private static void checkOutputFile(String fileName, File file)
			throws EasyJaSubException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new EasyJaSubException("Invalid output file, " + fileName + " is a directory");
			}
			return;
		}
		File directory = file;
		do {
			directory = directory.getParentFile();
		}
		while (directory != null && !directory.exists());
		if (directory != null && !directory.canWrite()) {
			throw new EasyJaSubException("Can not write on " + directory.getAbsolutePath() + " to create " + fileName);
		}
	}

	private static String getExtension(File file) {
		return FilenameUtils.getExtension(file.getName()).toUpperCase();
	}
	
	private static File getJapaneseSubFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList) throws EasyJaSubException {
		String fileName = command.getJapaneseSubFileName();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isTextContentType(file)) {
				throw new EasyJaSubException("File " + fileName + " does not seem to be text, type is " + probeContentType(file));
			}
			if (!isSubExtension(getExtension(file))) {
				throw new EasyJaSubException("File " + fileName + " does not have a valid subtitle file extension");
			}
			return file;
		}
		for (File file : defaultFileList) {
			String extension = getExtension(file);
			if (isTextContentType(file) &&
				isSubExtension(extension) &&
				isJaLanguageFromFileName(file)) {
				return file;
			}
		}
		return null;
	}

	private static boolean isJaLanguageFromFileName(File file) {
		String language = getSubtitleLanguageFromFileName(file.getName());
		return "ja".equals(language);
	}
	private static File getOutputBdmFile(EasyJaSubInputCommand command,
			File outputIdxFile,
			DefaultFileList defaultFileList) throws EasyJaSubException {
		String fileName = command.getOutputBdnFileName();
		File file = null;
		File directory = null;
		String fileNameBase = null;
		if (fileName != null) {
			file = new File(fileName);
		}
		else {
			fileNameBase = FilenameUtils.getBaseName(outputIdxFile.getName());
			String directoryName = command.getOutputBdnDirectory();
			if (directoryName != null) {
				directory = new File(directoryName);
			}
			else {
			    directory = new File(outputIdxFile.getParentFile(), fileNameBase + "_bdmxml");
			}
			file = new File(directory, fileNameBase + ".xml");
			fileName = file.getAbsolutePath();
		}
		checkOutputFile(fileName, file);
		return file;
	}
	
	private static File getOutputHtmlDirectory(EasyJaSubInputCommand command,
			File outputBdmFile,
			DefaultFileList defaultFileList) throws EasyJaSubException {
		String directoryName = command.getOutputHtmlDirectory();
		File directory = null;
		if (directoryName != null) {
			directory = new File(directoryName);
		}
		else if (outputBdmFile != null)
		{
			directory = outputBdmFile.getParentFile();
		}
		else {
			directory = new File(defaultFileList.getDefaultFileNamePrefix() + "_html");
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
			File videoFile,
			DefaultFileList defaultFileList) throws EasyJaSubException {
		String fileName = command.getOutputIdxFileName();
		File file = null;
		if (fileName != null) {
			file = new File(fileName);
		}
		if (file == null) {
			if (videoFile == null) {
				file = new File(defaultFileList.getDefaultFileNamePrefix() + ".idx");
			}
			else {
			    file = new File(videoFile.getParentFile(), FilenameUtils.getBaseName(videoFile.getName()) + ".idx");
			}
			fileName = file.getAbsolutePath();
		}
		checkOutputFile(fileName, file);
		return file;
	}
	
	private static File getOutputJapaneseTextFile(EasyJaSubInputCommand command,
			File videoFile,
			File japaneseSubFile,
			DefaultFileList defaultFileList) throws EasyJaSubException {
		String fileName = command.getOutputJapaneseTextFileName();
		File file = null;
		String fileNameBase = null;
		if (fileName != null) {
			file = new File(fileName);
		}
		else {
			fileNameBase = defaultFileList.getDefaultFileNamePrefix() + ".txt";
			if (videoFile != null) {
				file = new File(videoFile.getParentFile(), fileNameBase);
			}
			else if (japaneseSubFile != null) {
				file = new File(japaneseSubFile.getParentFile(), fileNameBase);
			}
			else {
				file = new File(fileNameBase);
			}
			fileName = file.getAbsolutePath();
		}
		checkOutputFile(fileName, file);
		return file;
	}


	private static SubtitleFileType getSubtitleFileType(File file) {
		if (file == null) {
			return SubtitleFileType.Undef;
		}
		return SubtitleFileType.valueOf(getExtension(file));
	}

	

	private static String getSubtitleLanguageFromFileName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index > 3 &&
			fileName.charAt(index-3) == '.') {
			return fileName.substring(index-2, index);
		}
		return null;
	}

	private static File getTranslatedSubFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList,
			File japaneseSubFile) throws EasyJaSubException {
		String fileName = command.getTranslatedSubFileName();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isTextContentType(file)) {
				throw new EasyJaSubException("File " + fileName + " does not seem to be text, type is " + probeContentType(file));
			}
			if (!isSubExtension(getExtension(file))) {
				throw new EasyJaSubException("File " + fileName + " does not have a valid subtitle file extension");
			}
			return file;
		}
		for (File file : defaultFileList) {
			String extension = getExtension(file);
			if (!file.equals(japaneseSubFile) &&
				isTextContentType(file) &&
				isSubExtension(extension)) {
				return file;
			}
		}
		return null;
	}

	private static String getTranslatedSubLanguage(EasyJaSubInputCommand command, File translatedSubFile) {
		String result = command.getTranslatedSubLanguage();
		if (result == null && translatedSubFile != null) {
			result = getSubtitleLanguageFromFileName(translatedSubFile.getName());
		}
		return result;
	}

	private static File getVideoFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList) throws EasyJaSubException {
		String fileName = command.getVideoFileName();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isVideoContentType(file)) {
				throw new EasyJaSubException("File " + fileName + " does not seem to be video, type is " + probeContentType(file));
			}
			return file;
		}
		for (File file : defaultFileList) {
			if (isVideoContentType(file) &&
				isVideoExtension(getExtension(file))) {
				return file;
			}
		}
		return null;
	}

	private static String getWkhtmltoimageFile(EasyJaSubInputCommand command)
	throws EasyJaSubException {
		// TODO: this is very system dependent
		String fileName = command.getWkhtmltoimage();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			return fileName;
		}
		if (CommonsLangSystemUtils.isWindows()) {
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
		}
		else {
			File file = new File("/usr/bin/wkhtmltoimage");
			if (file.exists()) {
				return file.getAbsolutePath();
			}
		}
		return null;
	}

	
	private static boolean isHtmlContentType(File file) {
		String type = probeContentType(file);
		return type == null || "text/html".equals(type);
	}

	private static boolean isCssContentType(File file) {
		String type = probeContentType(file);
		return type == null || "text/css".equals(type) || "text/plain".equals(type);
	}

	private static boolean isSubExtension(String ext) {
		return SubtitleFileType.isValue(ext);
	}

	private static boolean isTextContentType(File file) {
		String type = probeContentType(file);
		return type == null || "text/plain".equals(type);
	}

	private static boolean isVideoContentType(File file) {
		String type = probeContentType(file);
		return type == null || type.startsWith("video/");
	}

	private static boolean isVideoExtension(String ext) {
		return VideoFileType.isValue(ext);
	}

	private static String probeContentType(File file) {
		try {
			return Files.probeContentType(file.toPath());
		}
		catch (IOException ex) {
			return null;
		}
	}
	
	private final File bdmXmlFile;
	
	private final File japaneseSubFile;
	
	private final SubtitleFileType japaneseSubFileType;

	private final File nihongoJtalkHtmlFile;

	private final File outputHtmlDirectory;

	private final File outputIdxFile;

	private final File outputJapaneseTextFile;

	private final Set<Phases> phases;

	private final File translatedSubFile;

	private final File cssFile;

	private final SubtitleFileType translatedSubFileType;
	
	private final String translatedSubLanguage;

	private final File videoFile;
	private final String wkhtmltoimageFile;

	private int exactMatchTimeDiff;
	private int approxMatchTimeDiff;

	public EasyJaSubInputFromCommand(EasyJaSubInputCommand command) throws EasyJaSubException {
		DefaultFileList defaultFileList = new DefaultFileList(command);
		nihongoJtalkHtmlFile = getNihongoJtalkHtmlFile(command, defaultFileList);
		phases = command.getPhases();
		japaneseSubFile = getJapaneseSubFile(command, defaultFileList);
		japaneseSubFileType = getSubtitleFileType(japaneseSubFile);
		translatedSubFile = getTranslatedSubFile(command, defaultFileList, japaneseSubFile);
		translatedSubFileType = getSubtitleFileType(translatedSubFile);
		translatedSubLanguage = getTranslatedSubLanguage(command, translatedSubFile);
		videoFile = getVideoFile(command, defaultFileList);
		outputIdxFile = getOutputIdxFile(command, videoFile, defaultFileList);
		bdmXmlFile = getOutputBdmFile(command, outputIdxFile, defaultFileList);
		outputHtmlDirectory = getOutputHtmlDirectory(command, bdmXmlFile, defaultFileList);
		wkhtmltoimageFile = getWkhtmltoimageFile(command);
		outputJapaneseTextFile = getOutputJapaneseTextFile(command, videoFile,
				japaneseSubFile, defaultFileList);
		cssFile = getCssFile(command, outputHtmlDirectory, defaultFileList);
		exactMatchTimeDiff = getTimeDiff(command.getExactMatchTimeDiff(), 2000);
		approxMatchTimeDiff = getTimeDiff(command.getApproxMatchTimeDiff(), 500);
		defaultFileNamePrefix = defaultFileList.getDefaultFileNamePrefix();
	}

	private final String defaultFileNamePrefix;
	
	public String getDefaultFileNamePrefix() {
		return defaultFileNamePrefix;
	}

	@Override
	public File getBdnXmlFile() {
		return bdmXmlFile;
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
	public File getNihongoJtalkHtmlFile() {
		return nihongoJtalkHtmlFile;
	}
	
	private int getTimeDiff(String timeStr, int defaultValue) {
		if (timeStr == null) {
			return defaultValue;
		}
		return Integer.parseInt(timeStr);
	}

	private static File getNihongoJtalkHtmlFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList) throws EasyJaSubException {
		String fileName = command.getNihongoJtalkHtmlFileName();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isHtmlContentType(file)) {
				throw new EasyJaSubException("File " + fileName + " does not seem to be HTML, type is " + probeContentType(file));
			}
			return file;
		}
		for (File file : defaultFileList) {
			String extension = getExtension(file);
			if (("HTML".equals(extension) || "HTM".equals(extension)) &&
				isHtmlContentType(file)) {
				return file;
			}
		}
		return null;
	}

	private static File getCssFile(EasyJaSubInputCommand command,
			File htmlDirectory,
			DefaultFileList defaultFileList) throws EasyJaSubException {
		String fileName = command.getCssFileName();
		if (fileName != null) {
			File file = new File(fileName);
			if (file.exists()) {
				checkFile(fileName, file);
				if (!isCssContentType(file)) {
					throw new EasyJaSubException("File " + fileName + " does not seem to be CSS, type is " + probeContentType(file));
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
		return new File(htmlDirectory, defaultFileList.getDefaultFileNamePrefix() + ".css");
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
	public Set<Phases> getPhases() {
		return phases;
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
	public String getWkhtmltoimageFile() {
		return wkhtmltoimageFile;
	}

	@Override
	public File getCssFile() {
		return cssFile;
	}
	
	@Override
	public int getExactMatchTimeDiff() {
		return exactMatchTimeDiff;
	}

	@Override
	public int getApproxMatchTimeDiff() {
		return approxMatchTimeDiff;
	}

	@Override
	public int getWidth() {
		return 1280; // TODO
	}

	@Override
	public int getHeight() {
		return 720; // TODO
	}

	@Override
	public String getCssHiraganaFont() {
		// TODO Auto-generated method stub
		return "cinecaption,GT2000-01,arial";
	}

	@Override
	public String getCssKanjiFont() {
		// TODO Auto-generated method stub
		return "GT2000-01,cinecaption,arial";
	}

	@Override
	public String getCssTranslationFont() {
		// TODO Auto-generated method stub
		return "arial";
	}
}
