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
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class DefaultFileList implements Iterable<File> {

	public DefaultFileList(EasyJaSubInputCommand command) {
		defaultFileNamePrefix = getDefaultFileNamePrefix(command);
		defaultDirectories = getDefaultDirectories(command);
		if (defaultDirectories.size() > 1) {
			defaultDirectory = defaultDirectories.get(1);
		} else {
			defaultDirectory = defaultDirectories.get(0);
		}
	}

	private final List<File> defaultDirectories;
	private List<File> list;
	private final String defaultFileNamePrefix;
	private final File defaultDirectory;

	public String getDefaultFileNamePrefix() {
		return defaultFileNamePrefix;
	}

	public File getDefaultDirectory() {
		return defaultDirectory;
	}

	private static String getDefaultFileNamePrefix(EasyJaSubInputCommand command) {
		String fileName = command.getVideoFileName();
		if (fileName == null) {
			fileName = command.getJapaneseSubFileName();
		}
		if (fileName == null) {
			fileName = command.getTranslatedSubFileName();
		} else {
			if (fileName.contains(".ja.")) {
				fileName = fileName.replace(".ja.", ".");
			}
		}
		if (fileName == null) {
			fileName = command.getNihongoJtalkHtmlFileName();
		} else {
			if (fileName.contains(".en.")) {
				fileName = fileName.replace(".en.", ".");
			}
		}
		if (fileName != null) {
			int pathSeparatorIndex = fileName.lastIndexOf(File.separatorChar);
			if (pathSeparatorIndex >= 0) {
				fileName = fileName.substring(pathSeparatorIndex + 1);
			}
			int extensionSeparatorIndex = fileName.lastIndexOf('.');
			if (extensionSeparatorIndex > 0) {
				fileName = fileName.substring(0, extensionSeparatorIndex);
			}
		}
		return fileName;
	}

	private static List<File> getDefaultDirectories(
			EasyJaSubInputCommand command) {
		ArrayList<File> result = new ArrayList<File>();
		result.add(getUserDir());
		addDirectoryIfDistinct(result, command.getOutputIdxDirectory());
		for (String fileName : new String[] { command.getOutputIdxFileName(),
				command.getVideoFileName(), command.getJapaneseSubFileName(),
				command.getTranslatedSubFileName(),
				command.getNihongoJtalkHtmlFileName() }) {
			addParentDirectoryIfDistinct(result, fileName);
		}
		return result;
	}

	private static File getUserDir() {
		return new File(SystemProperty.getUserDir());
	}

	private static void addParentDirectoryIfDistinct(ArrayList<File> result,
			String fileName) {
		if (fileName != null) {
			File file = new File(fileName);
			if (file.exists() && file.isFile()) {
				File directory = file.getAbsoluteFile().getParentFile();
				if (directory != null && directory.isDirectory()) {
					addIfDistinct(result, directory);
				}
			}
		}
	}

	private static void addDirectoryIfDistinct(ArrayList<File> result,
			String dirName) {
		if (dirName != null) {
			File directory = new File(dirName);
			if (directory != null && directory.isDirectory()) {
				addIfDistinct(result, directory.getAbsoluteFile());
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

	private static List<File> getDefaultFiles(List<File> directories,
			final String fileBaseName) {
		ArrayList<File> result = new ArrayList<File>();
		final int fileBaseNameLength = fileBaseName.length();
		for (File directory : directories) {
			for (File file : directory.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.length() >= fileBaseNameLength + 3
							&& name.startsWith(fileBaseName)
							&& name.charAt(fileBaseNameLength) == '.';
				}

			})) {
				if (file.isFile() && file.canRead()) {
					result.add(file);
				}
			}
		}
		return result;
	}

	@Override
	public Iterator<File> iterator() {
		if (list == null) {
			if (defaultFileNamePrefix == null) {
				list = Arrays.asList(getUserDir());
			} else {
				list = getDefaultFiles(defaultDirectories,
						defaultFileNamePrefix);
			}
		}
		return list.iterator();
	}

	@Override
	public String toString() {
		return getDefaultFilesStr(list);
	}

	private static String getDefaultFilesStr(Iterable<File> defaultFileList) {
		StringBuffer result = new StringBuffer();
		for (File file : defaultFileList) {
			result.append(file.getAbsolutePath());
			result.append(SystemProperty.getLineSeparator());
		}
		return result.toString();
	}

}
