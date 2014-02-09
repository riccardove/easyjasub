package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

class EasyJaSubInputFromCommand implements EasyJaSubInput {
	public EasyJaSubInputFromCommand(EasyJaSubInputCommand command) throws Exception {
		Iterable<File> defaultFileList = new DefaultFileList(command);
		nihongoJtalkHtmlFile = getNihongoJtalkHtmlFile(command, defaultFileList);
		phases = command.getPhases();
		japaneseSubFile = getJapaneseSubFile(command, defaultFileList);
		japaneseSubFileType = getSubtitleFileType(japaneseSubFile);
		translatedSubFile = getTranslatedSubFile(command, defaultFileList);
		translatedSubFileType = getSubtitleFileType(japaneseSubFile);
		translatedSubLanguage = command.getTranslatedSubLanguage();
		if (translatedSubLanguage == null) {
			translatedSubLanguage = getSubtitleLanguageFromFileName(translatedSubFile.getName());
		}
		videoFile = getVideoFile(command, defaultFileList);
//		String bdmXmlDirectoryName = command.getOutputBdnDirectory();
//		File bdmXmlDirectory = bdmXmlDirectoryName == null ? 
//				new File(videoFile.getParent(), videoFile.getName() + "_bdm") 
//		: File();
//			
//		}
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


	private File getVideoFile(EasyJaSubInputCommand command,
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
		throw new Exception("Could not find any japanese sub file");
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

	private File getJapaneseSubFile(EasyJaSubInputCommand command,
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
	
	private String getSubtitleLanguageFromFileName(String fileName) {
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

	private File getTranslatedSubFile(EasyJaSubInputCommand command,
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
	public File getOutputIdxDirectory() {
		return outputIdxDirectory;
	}

	@Override
	public File getOutputHtmlDirectory() {
		return outputHtmlDirectory;
	}

	@Override
	public File getWkhtmltoimageFile() {
		return wkhtmltoimageFile;
	}

	private File translatedSubFile;
	private File nihongoJtalkHtmlFile;
	private String translatedSubLanguage;
	private File outputIdxDirectory;
	private File outputHtmlDirectory;
	private File wkhtmltoimageFile;

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
