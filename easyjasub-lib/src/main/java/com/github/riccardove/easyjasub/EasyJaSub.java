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

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.inputnihongojtalk.InputNihongoJTalkHtmlFile;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;

/**
 * Main code, executes actions
 */
public class EasyJaSub {

	public int run(EasyJaSubInput command, EasyJaSubObserver observer)
			throws EasyJaSubException {
		String filePrefix = command.getDefaultFileNamePrefix();
		if (filePrefix == null) {
			filePrefix = "easyjasub_";
		}

		String systemEncoding = SystemProperty.getEncoding();
		if (!EasyJaSubCharset.CHARSETSTR.equals(systemEncoding)) {
			observer.onEncodingWarning(systemEncoding,
					EasyJaSubCharset.CHARSETSTR);
		}

		// s.setWidth(1366);
		// s.setHeight(768);
		// return "720p (1280x720)";

		SubtitleList s = new SubtitleList();

		if (command.getXmlFile() != null && command.getXmlFile().exists()) {
			readInputXmlFile(s, command, observer);
		} else {
			EasyJaSubLinesSelection selection = null;
			if (command.getStartLine() > 0 || command.getEndLine() > 0) {
				selection = new EasyJaSubLinesSelection();
				selection.setStartLine(command.getStartLine());
				selection.setEndLine(command.getEndLine());
			}

			readJapaneseSubtitles(command, observer, s, selection);

			writeOutputJapaneseTextFile(command, observer, s);

			readTranslatedSubFile(command, observer, s, selection);

			if (command.getNihongoJtalkHtmlFile() != null) {
				parseNihongoJTalkFile(command, observer, s);
			} else if (command.getMeCabCommand() != null) {
				runMeCab(command, observer, s);
			} else {
				luceneAnalyze(command, observer, s);
			}

			if (command.getXmlFile() != null) {
				writeOutputXmlFile(s, command, observer);
			}
		}

		if (command.getJGlossFile() != null) {
			writeJGlossFile(command, observer, s);
		}

		// TODO: check that actions skipped do not impact other actions

		File htmlFolder = command.getOutputHtmlDirectory();
		if (htmlFolder == null) {
			return 0;
		}

		File cssFileUrl = createCssFile(command, observer);

		File bdnFile = command.getBdnXmlFile();
		if (bdnFile == null) {
			// TODO: render images
			return 0;
		}

		File bdnFolder = bdnFile.getAbsoluteFile().getParentFile();

		int index = 0;
		for (SubtitleLine line : s) {
			line.setIndex(++index);
		}

		PictureSubtitleList ps = toPictureSubtitleList(s, observer, command,
				filePrefix, htmlFolder, bdnFolder, cssFileUrl);

		writeHtmlFiles(command, observer, ps, htmlFolder, cssFileUrl);

		writePngFiles(command, observer, ps, htmlFolder, bdnFolder);

		writeBdnXmlFile(command, observer, ps);

		File idxFile = command.getOutputIdxFile();
		if (idxFile == null) {
			return 0;
		}
		observer.onWriteIdxFileStart(idxFile, bdnFile);
		return 0;
	}

	private PictureSubtitleList toPictureSubtitleList(SubtitleList s,
			EasyJaSubObserver observer, EasyJaSubInput command,
			String filePrefix, File htmlFolder, File bdnFolder, File cssFileUrl)
			throws EasyJaSubException {
		observer.onConvertToHtmlSubtitleListStart(htmlFolder);
		PictureSubtitleList result = null;
		try {
			result = new SubtitleListToPictureSubtitleList(htmlFolder,
					cssFileUrl).writeHtmls(filePrefix, s, command, htmlFolder,
					bdnFolder);
			observer.onConvertToHtmlSubtitleListEnd(htmlFolder);
		} catch (IOException ex) {
			observer.onConvertToHtmlSubtitleListError(htmlFolder, ex);
		}
		return result;
	}

	private void readInputXmlFile(SubtitleList s, EasyJaSubInput command,
			EasyJaSubObserver observer) throws EasyJaSubException {
		File f = command.getXmlFile();
		observer.onReadXmlFileStart(f);
		try {
			new SubtitleListXmlFileReader(s).read(f);
			observer.onReadXmlFileEnd(f);
		} catch (IOException ex) {
			observer.onReadXmlFileIOError(f, ex);
		} catch (SAXException ex) {
			observer.onReadXmlFileError(f, ex);
		}
	}

	private void writeOutputXmlFile(SubtitleList s, EasyJaSubInput command,
			EasyJaSubObserver observer) throws EasyJaSubException {
		File f = command.getXmlFile();
		if (f != null) {
			mkParentDirs(f);
			observer.onWriteXmlFileStart(f);
			try {
				new SubtitleListXmlFileWriter().write(s, f);
				observer.onWriteXmlFileEnd(f);
			} catch (IOException ex) {
				observer.onWriteXmlFileIOError(f, ex);
			}
		} else {
			observer.onWriteXmlFileSkipped(f);
		}
	}

	private void writeBdnXmlFile(EasyJaSubInput command,
			EasyJaSubObserver observer, PictureSubtitleList s)
			throws EasyJaSubException {
		File f = command.getBdnXmlFile();
		if (!f.exists()) {
			mkParentDirs(f);
			observer.onWriteBdnXmlFileStart(f);
			try {// TODO fps
				new SubtitleListBdnXmlFileWriter(command, 23.976, "23.976",
						"720p").write(s, f);
				observer.onWriteBdnXmlFileEnd(f);
			} catch (IOException ex) {
				observer.onWriteBdnXmlFileIOError(f, ex);
			}
		} else {
			observer.onWriteBdnXmlFileSkipped(f);
		}
	}

	private void writeJGlossFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File f = command.getJGlossFile();
		if (!f.exists()) {
			mkParentDirs(f);
			observer.onWriteJGlossFileStart(f);
			try {
				new SubtitleListJGlossFileWriter().write(s, f);
				observer.onWriteJGlossFileEnd(f);
			} catch (IOException ex) {
				observer.onWriteJGlossFileIOError(f, ex);
			}
		} else {
			observer.onWriteJGlossFileSkipped(f);
		}
	}

	private void writePngFiles(EasyJaSubInput command,
			EasyJaSubObserver observer, PictureSubtitleList s, File htmlFolder,
			File bdnFolder) throws EasyJaSubException {
		String wkhtml = command.getWkHtmlToImageCommand();
		int width = command.getWidth();
		mkDirs(htmlFolder);
		mkDirs(bdnFolder);
		observer.onWriteImagesStart(wkhtml, htmlFolder, bdnFolder, width);
		try {
			if (wkhtml != null) {
				new SubtitleListPngFilesWriter(wkhtml, width, observer)
						.writeImages(s, htmlFolder, bdnFolder);
			} else {
				new SubtitleListPngFilesJavaWriter(width, observer)
						.writeImages(s, htmlFolder, bdnFolder);
			}
			observer.onWriteImagesEnd(wkhtml, htmlFolder, bdnFolder);
		} catch (WkhtmltoimageException ex) {
			observer.onWriteImagesWkhtmlError(bdnFolder, ex);
		} catch (InterruptedException ex) {
			observer.onWriteImagesWkhtmlError(bdnFolder, ex);
		} catch (IOException ex) {
			observer.onWriteImagesIOError(bdnFolder, ex);
		}
	}

	private void writeHtmlFiles(EasyJaSubInput command,
			EasyJaSubObserver observer, PictureSubtitleList s, File htmlFolder,
			File cssFile) throws EasyJaSubException {
		observer.onWriteHtmlStart(htmlFolder, cssFile);
		mkDirs(htmlFolder);
		try {
			new SubtitleListHtmlFilesWriter(observer).writeHtmls(s, command);
			observer.onWriteHtmlEnd(htmlFolder);
		} catch (IOException ex) {
			observer.onWriteHtmlError(htmlFolder, ex);
		}
	}

	private void mkDirs(File directory) throws EasyJaSubException {
		if (!directory.exists() && !directory.mkdirs()) {
			throw new EasyJaSubException("Could not create directory "
					+ directory.getAbsolutePath());
		}
	}

	private File createCssFile(EasyJaSubInput command,
			EasyJaSubObserver observer) throws EasyJaSubException {
		File cssFile = command.getCssFile();
		if (cssFile != null && !cssFile.exists()) {
			observer.onWriteCssStart(cssFile);
			mkParentDirs(cssFile);
			try {
				new SubtitleListCssFileWriter(cssFile, command).write();
				observer.onWriteCssEnd(cssFile);
			} catch (IOException ex) {
				observer.onWriteCssIOError(cssFile, ex);
			}
		} else {
			observer.onWriteCssSkipped(cssFile);
		}
		return cssFile;
	}

	private void readTranslatedSubFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s,
			EasyJaSubLinesSelection selection) throws EasyJaSubException {
		File file = command.getTranslatedSubFile();
		if (file != null) {
			observer.onReadTranslatedSubtitlesStart(file);
			try {
				new SubtitleListTranslatedSubFileReader(
						command.getExactMatchTimeDiff(),
						command.getApproxMatchTimeDiff())
						.readTranslationSubtitles(s, file,
								command.getTranslatedSubFileType(), observer,
								selection, command.showTranslation());
				observer.onReadTranslatedSubtitlesEnd(file);
			} catch (IOException ex) {
				observer.onReadTranslatedSubtitlesIOError(file, ex);
			} catch (InputTextSubException ex) {
				observer.onReadTranslatedSubtitlesParseError(file, ex);
			}
		} else {
			observer.onReadTranslatedSubtitlesSkipped(file);
		}
	}

	private void luceneAnalyze(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		observer.onLuceneParseStart();
		new SubtitleListLuceneAnalyzer(observer).run(s);
		observer.onLuceneParseEnd();
	}

	private void runMeCab(EasyJaSubInput command, EasyJaSubObserver observer,
			SubtitleList s) throws EasyJaSubException {
		String meCabCommand = command.getMeCabCommand();
		if (meCabCommand != null) {
			observer.onMeCabRunStart(meCabCommand);
			new InputMeCab(observer, meCabCommand).run(s,
					command.getMeCabFile());
			observer.onMeCabRunEnd(meCabCommand);
		} else {
			observer.onMeCabRunSkipped(meCabCommand);
		}
	}

	private void parseNihongoJTalkFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File f = command.getNihongoJtalkHtmlFile();
		if (f != null) {
			observer.onInputNihongoJTalkHtmlFileParseStart(f);
			try {
				new InputNihongoJTalkHtmlFile().parse(f, s, observer);
				observer.onInputNihongoJTalkHtmlFileParseEnd(f, null);
			} catch (IOException ex) {
				observer.onInputNihongoJTalkHtmlFileIOError(f, ex);
			} catch (SAXException ex) {
				observer.onInputNihongoJTalkHtmlFileParseError(f, ex);
			}
		} else {
			observer.onInputNihongoJTalkHtmlFileParseSkipped(f);
		}
	}

	private void writeOutputJapaneseTextFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File txtFile = command.getOutputJapaneseTextFile();
		if (txtFile == null || txtFile.exists()) {
			observer.onWriteOutputJapaneseTextFileSkipped(txtFile);
		} else {
			mkParentDirs(txtFile);
			observer.onWriteOutputJapaneseTextFileStart(txtFile);
			try {
				new SubtitleListJapaneseTextFileWriter().write(s, txtFile);
			} catch (IOException ex) {
				observer.onWriteOutputJapaneseTextFileIOError(txtFile, ex);
			}
			observer.onWriteOutputJapaneseTextFileEnd(txtFile);
		}
	}

	private void mkParentDirs(File file) throws EasyJaSubException {
		EasyJaSubWriter.mkParentDirs(file);
	}

	private void readJapaneseSubtitles(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s,
			EasyJaSubLinesSelection selection) throws EasyJaSubException {
		File jaF = command.getJapaneseSubFile();
		if (jaF == null) {
			observer.onReadJapaneseSubtitlesSkipped(jaF);
			throw new EasyJaSubException(
					"You must specify a valid Japanese subtitles file");
		} else {
			observer.onReadJapaneseSubtitlesStart(jaF);
			try {
				new SubtitleListJapaneseSubFileReader().readJapaneseSubtitles(
						s, jaF, command.getJapaneseSubFileType(), observer,
						selection);
				observer.onReadJapaneseSubtitlesEnd(jaF);
			} catch (IOException ex) {
				observer.onReadJapaneseSubtitlesIOError(jaF, ex);
			} catch (InputTextSubException ex) {
				observer.onReadJapaneseSubtitlesParseError(jaF, ex);
			}
		}
	}
}
