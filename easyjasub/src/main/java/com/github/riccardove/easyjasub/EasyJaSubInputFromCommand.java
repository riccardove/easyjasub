package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

/**
 * Checks input commands and determines valid program input using some heuristic
 */
class EasyJaSubInputFromCommand implements EasyJaSubInput {
	public EasyJaSubInputFromCommand(EasyJaSubInputCommand command) throws Exception {
		DefaultFileList defaultFileList = new DefaultFileList(command);
		nihongoJtalkHtmlFile = getNihongoJtalkHtmlFile(command, defaultFileList);
		phases = command.getPhases();
		japaneseSubFile = getJapaneseSubFile(command, defaultFileList);
		japaneseSubFileType = getSubtitleFileType(japaneseSubFile);
		translatedSubFile = getTranslatedSubFile(command, defaultFileList, japaneseSubFile);
		translatedSubFileType = getSubtitleFileType(japaneseSubFile);
		translatedSubLanguage = command.getTranslatedSubLanguage();
		if (translatedSubLanguage == null) {
			translatedSubLanguage = getSubtitleLanguageFromFileName(translatedSubFile.getName());
		}
		videoFile = getVideoFile(command, defaultFileList);
		outputIdxFile = getOutputIdxFile(command, videoFile, defaultFileList);
		bdmXmlFile = getOutputBdmFile(command, outputIdxFile, defaultFileList);
		outputHtmlDirectory = getOutputHtmlDirectory(command, bdmXmlFile, defaultFileList);
		wkhtmltoimageFile = getWkhtmltoimageFile(command);
		// TODO: ignore error depending on selected phases
	}

	private static SubtitleFileType getSubtitleFileType(File file) {
		return SubtitleFileType.valueOf(getExtension(file));
	}
	
	private Set<Phases> phases;
	private File videoFile;
	private File japaneseSubFile;

	@Override
	public Set<Phases> getPhases() {
		return phases;
	}
	
	@Override
	public File getVideoFile() {
		return videoFile;
	}


	private static File getVideoFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList) throws Exception {
		String fileName = command.getVideoFileName();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isVideoContentType(file)) {
				throw new Exception("File " + fileName + " does not seem to be video");
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

	

	private static File getOutputIdxFile(EasyJaSubInputCommand command,
			File videoFile,
			DefaultFileList defaultFileList) throws Exception {
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

	private static File getOutputHtmlDirectory(EasyJaSubInputCommand command,
			File outputBdmFile,
			DefaultFileList defaultFileList) throws Exception {
		String directoryName = command.getOutputHtmlDirectory();
		File directory = null;
		if (directoryName != null) {
			directory = new File(directoryName);
		}
		else if (outputBdmFile != null)
		{
			directory = outputBdmFile;
		}
		else {
			directory = new File(defaultFileList.getDefaultFileNamePrefix() + "_html");
		}
		// TODO check and accept this file used as input file
		return directory;
	}

	private static File getOutputBdmFile(EasyJaSubInputCommand command,
			File outputIdxFile,
			DefaultFileList defaultFileList) throws Exception {
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
		checkOutputFile(fileName, file); // TODO accept this file used as input file
		return file;
	}

	private static void checkOutputFile(String fileName, File file)
			throws Exception {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new Exception("Invalid output file, " + fileName + " is a directory");
			}
			throw new Exception("Output file " + fileName + " already exists");
		}
		File directory = null;
		do {
			directory = file.getParentFile();
		}
		while (!directory.exists());
		if (!directory.canWrite()) {
			throw new Exception("Can not write on " + directory.getAbsolutePath() + " to create " + fileName);
		}
	}

	
	private SubtitleFileType japaneseSubFileType;
	private SubtitleFileType translatedSubFileType;

	public SubtitleFileType getJapaneseSubFileType() {
		return japaneseSubFileType;
	}

	public SubtitleFileType getTranslatedSubFileType() {
		return translatedSubFileType;
	}

	@Override
	public File getJapaneseSubFile() {
		return japaneseSubFile;
	}

	private static File getJapaneseSubFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList) throws Exception {
		String fileName = command.getJapaneseSubFileName();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isTextContentType(file)) {
				throw new Exception("File " + fileName + " does not seem to be text");
			}
			if (!isSubExtension(getExtension(file))) {
				throw new Exception("File " + fileName + " does not have a valid subtitle file extension");
			}
			return file;
		}
		for (File file : defaultFileList) {
			String extension = getExtension(file);
			if (isTextContentType(file) &&
				isSubExtension(extension) &&
				getSubtitleLanguageFromFileName(file.getName()) == "ja") {
				return file;
			}
		}
		throw new Exception("Could not find any japanese sub file");
	}
	
	private static String getSubtitleLanguageFromFileName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index > 3 &&
			fileName.charAt(index-3) == '.') {
			return fileName.substring(index-2, index);
		}
		return null;
	}
	
	private static boolean isSubExtension(String ext) {
		return SubtitleFileType.isValue(ext);
	}
	
	private static boolean isVideoExtension(String ext) {
		return VideoFileType.isValue(ext);
	}

	@Override
	public File getTranslatedSubFile() {
		return translatedSubFile;
	}

	private static File getTranslatedSubFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList,
			File japaneseSubFile) throws Exception {
		String fileName = command.getJapaneseSubFileName();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isTextContentType(file)) {
				throw new Exception("File " + fileName + " does not seem to be text");
			}
			if (!isSubExtension(getExtension(file))) {
				throw new Exception("File " + fileName + " does not have a valid subtitle file extension");
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
		throw new Exception("Could not find any japanese sub file");
	}

	@Override
	public File getNihongoJtalkHtmlFile() {
		return nihongoJtalkHtmlFile;
	}

	@Override
	public String getTranslatedSubLanguage() {
		return translatedSubLanguage;
	}

	@Override
	public File getOutputIdxFile() {
		return outputIdxFile;
	}

	@Override
	public File getOutputHtmlDirectory() {
		return outputHtmlDirectory;
	}

	@Override
	public String getWkhtmltoimageFile() {
		return wkhtmltoimageFile;
	}
	
	private static String getWkhtmltoimageFile(EasyJaSubInputCommand command)
	throws Exception {
		// TODO: this is very system dependant
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
			if (!directory.exists()) {
				throw new Exception("Could not find directory " + directory.getAbsolutePath());
			}
			File file = new File(directory, "wkhtmltoimage.exe");
			fileName = file.getAbsolutePath();
			checkFile(fileName, file);
			return fileName;
		}
		return "/usr/bin/wkhtmltoimage";
	}

	private File translatedSubFile;
	private File nihongoJtalkHtmlFile;
	private String translatedSubLanguage;
	private File outputIdxFile;
	private File outputHtmlDirectory;
	private String wkhtmltoimageFile;

	private File getNihongoJtalkHtmlFile(EasyJaSubInputCommand command,
			Iterable<File> defaultFileList) throws Exception {
		String fileName = command.getNihongoJtalkHtmlFileName();
		if (fileName != null) {
			File file = new File(fileName);
			checkFile(fileName, file);
			if (!isHtmlContentType(file)) {
				throw new Exception("File " + fileName + " does not seem to be HTML");
			}
			return file;
		}
		for (File file : defaultFileList) {
			String extension = getExtension(file);
			

			if ((extension == "html" ||
				extension == "htm") &&
				isTextContentType(file)) {
				return file;
			}
		}
		throw new Exception("Could not find any HTML file");
	}

	private static String getExtension(File file) {
		return FilenameUtils.getExtension(file.getName()).toLowerCase();
	}

	private static void checkFile(String fileName, File file) throws Exception {
		if (!file.exists()) {
			throw new Exception("File " + fileName + " does not exist");
		}
		if (!file.isFile()) {
			throw new Exception(fileName + " is not a file");
		}
		if (!file.canRead()) {
			throw new Exception("File " + fileName + " can not be read");
		}
	}

	private static boolean isVideoContentType(File file) {
		String type = probeContentType(file);
		return type != null && type.startsWith("video/");
	}

	private static boolean isTextContentType(File file) {
		return "text/plain".equals(probeContentType(file));
	}

	private static boolean isHtmlContentType(File file) {
		return "text/html".equals(probeContentType(file));
	}
	
	private static String probeContentType(File file) {
		try {
			return Files.probeContentType(file.toPath());
		}
		catch (IOException ex) {
			return null;
		}
	}

	private File bdmXmlFile;
	
	@Override
	public File getBdnXmlFile() {
		return bdmXmlFile;
	}

}
