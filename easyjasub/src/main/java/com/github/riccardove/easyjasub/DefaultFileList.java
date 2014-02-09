package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

class DefaultFileList implements Iterable<File> {
	private EasyJaSubInputCommand command;

	public DefaultFileList(EasyJaSubInputCommand command) {
		this.command = command;
		
	}
	
	private List<File> list;
	
	private static String getDefaultFileNamePrefix(EasyJaSubInputCommand command) {
		String fileName = command.getVideoFileName();
		if (fileName == null) {
			fileName = command.getJapaneseSubFileName();
		}
		if (fileName == null) {
			fileName = command.getTranslatedSubFileName();
		}
		if (fileName == null) {
			fileName = command.getNihongoJtalkHtmlFileName();
		}
		if (fileName != null) {
			return FilenameUtils.getBaseName(fileName);
		}
		return null;
	}
	
	private static List<File> getDefaultDirectories(EasyJaSubInputCommand command) {
		ArrayList<File> result = new ArrayList<File>();
		result.add(new File(SystemProperty.getUserDir()));
		for (String fileName : new String[] {
			command.getVideoFileName(),
			command.getJapaneseSubFileName(),
			command.getTranslatedSubFileName(),
			command.getNihongoJtalkHtmlFileName()
		}) {
			addParentDirectoryIfDistinct(result, fileName);
		}
		return result;
	}

	private static void addParentDirectoryIfDistinct(ArrayList<File> result,
			String fileName) {
		if (fileName != null) {
			File file = new File(fileName);
			if (file.exists() && file.isFile()) {
				File directory = file.getParentFile();
				if (file.isDirectory()) {
					addIfDistinct(result, directory);
				}
			}
		}
	}

	private static void addIfDistinct(ArrayList<File> result, File directory) {
		for (File defaultDirectory : result) {
			if (defaultDirectory.equals(directory)) {
				return;
			}
		}
		result.add(directory);
	}

	private static List<File> getDefaultFiles(List<File> directories, final String fileBaseName) {
		ArrayList<File> result = new ArrayList<File>();
		final int fileBaseNameLength = fileBaseName.length();
		for (File directory : directories) {
			for (File file : directory.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.length() >= fileBaseNameLength + 3 &&
							name.startsWith(fileBaseName) &&
							name.charAt(fileBaseNameLength) == '.';
				}
				
			})) {
				if (file.isFile() && file.canRead()) {
					result.add(file);
				}
			};
		}
		return result;
	}

	@Override
	public Iterator<File> iterator() {
		if (list == null) {
			String defaultFileNamePrefix = getDefaultFileNamePrefix(command);
			if (defaultFileNamePrefix == null) {
				list = new ArrayList<File>();
			}
			else {
				List<File> defaultDirectories = getDefaultDirectories(command);
				list = getDefaultFiles(defaultDirectories, defaultFileNamePrefix);
			}
		}
		return list.iterator();
	}

}
