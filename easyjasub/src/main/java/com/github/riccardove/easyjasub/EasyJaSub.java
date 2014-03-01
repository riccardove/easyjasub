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

public class EasyJaSub {
	
	
	
	
	
	public int run(EasyJaSubInput command, EasyJaSubObserver observer) throws EasyJaSubException {
		String filePrefix = command.getDefaultFileNamePrefix();
		if (filePrefix == null) {
			filePrefix = "easyjasub_";
		}

		String systemEncoding = SystemProperty.getEncoding();
		if (!EasyJaSubCharset.CHARSETSTR.equals(systemEncoding)) {
			observer.onEncodingWarning(systemEncoding,
					EasyJaSubCharset.CHARSETSTR);
		}

//		    s.setWidth(1366);
//		    s.setHeight(768);
//            return "720p (1280x720)";

	    SubtitleList s = new SubtitleList();

	    EasyJaSubLinesSelection selection = null;
	    if (command.getStartLine() > 0 ||
	    	command.getEndLine() > 0) {
	    	selection = new EasyJaSubLinesSelection();
	    	selection.setStartLine(command.getStartLine());
	    	selection.setEndLine(command.getEndLine());
	    }
	    
		readJapaneseSubtitles(command, observer, s, selection);
	    
	    writeOutputJapaneseTextFile(command, observer, s);

	    readTranslatedSubFile(command, observer, s, selection);

		parseNihongoJTalkFile(command, observer, s);

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
	    	
			String b = filePrefix + "_line" +String.format("%04d", line.getIndex());
			line.setHtmlFile(new File(htmlFolder, b + ".html"));
			line.setPngFile(new File(bdnFolder, b + ".png"));
	    }
		
		writeHtmlFiles(command, observer, s, htmlFolder, cssFileUrl);

		writePngFiles(command, observer, s, htmlFolder, bdnFolder);

		writeBdmXmlFile(command, observer, s);

		File idxFile = command.getOutputIdxFile();
		if (idxFile == null) {
			return 0;
		}
		writeIdxFile(command, observer, s, bdnFolder);

/*		    if (phases == null 
				|| phases.contains(Phases.Ifo)) {
			File folder = createFolder(FileName + "_ifo");
			toIdx(bdnFolder, FileName + ".xml", folder, FileName + ".ifo", s.getWidth());
			System.out.println("Ifo");
		}

		File supFolder = createFolder(FileName + "_sup");
		if (phases == null 
				|| phases.contains(Phases.Sup)) {
			toIdx(bdnFolder, FileName + ".xml", supFolder, FileName + ".sup", s.getWidth());
			System.out.println("Sup");
		}

		if (phases == null 
				|| phases.contains(Phases.SupXml)) {
			File bisFolder = createFolder(FileName + "_bis");
			toIdx(supFolder, FileName + ".sup", bisFolder, FileName + ".xml", s.getWidth());
			System.out.println("SupXml");
		}
*/
		return 0;
	}

	private void writeIdxFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s, File bdnFolder)
			throws EasyJaSubException {
		File file = command.getOutputIdxFile();
		File bdnFile = command.getBdnXmlFile();
		if (file != null && bdnFile != null && !file.exists()) {
			mkParentDirs(bdnFile);
			observer.onWriteIdxFileStart(file, bdnFile);
			// TODO create folders for output file
			// TODO output file
			new BDSup2SubWrapper().toIdx(bdnFile, file, command.getWidth());
			observer.onWriteIdxFileEnd(file);
		}
		else {
			observer.onWriteIdxFileSkipped(file, bdnFile);
		}
	}

	private void writeBdmXmlFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File f = command.getBdnXmlFile();
		if (!f.exists()) {
			mkParentDirs(f);
			observer.onWriteBdnXmlFileStart(f);
			try {// TODO fps 
				new SubtitleListBdmXmlFileWriter(command, 23.976, "23.976", "720p").writeBDM(s, f);
				observer.onWriteBdnXmlFileEnd(f);
			}
			catch (IOException ex) {
				observer.onWriteBdnXmlFileIOError(f, ex);
			}
		}
		else {
			observer.onWriteBdnXmlFileSkipped(f);
		}
	}

	private void writePngFiles(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s, File htmlFolder,
			File bdnFolder) throws EasyJaSubException {
		String wkhtml = command.getWkhtmltoimageFile();
		int width = command.getWidth();
		mkDirs(htmlFolder);
		mkDirs(bdnFolder);
		observer.onWriteImagesStart(wkhtml, htmlFolder, bdnFolder, width);
		try {
			if (wkhtml != null) {
			    new SubtitleListPngFilesWriter(wkhtml, width, observer).writeImages(s, htmlFolder, bdnFolder);
			}
			else {
			    new SubtitleListPngFilesJavaWriter(width, observer).writeImages(s, htmlFolder, bdnFolder);
			}
			observer.onWriteImagesEnd(wkhtml, htmlFolder, bdnFolder);
		}
		catch (WkhtmltoimageException ex) {
			observer.onWriteImagesWkhtmlError(bdnFolder, ex);
		}
		catch (InterruptedException ex) {
			observer.onWriteImagesWkhtmlError(bdnFolder, ex);
		}
		catch (IOException ex) {
			observer.onWriteImagesIOError(bdnFolder, ex);
		}
	}

	private void writeHtmlFiles(EasyJaSubInput command,EasyJaSubObserver observer, SubtitleList s,
			File htmlFolder, File cssFile) throws EasyJaSubException {
		observer.onWriteHtmlStart(htmlFolder, cssFile);
		mkDirs(htmlFolder);
		try {
			new SubtitleListHtmlFilesWriter(htmlFolder, cssFile, observer)
					.writeHtmls(s,
					command);
			observer.onWriteHtmlEnd(htmlFolder);
		}
		catch (IOException ex) {
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
			}
		    catch (IOException ex) {
		    	observer.onWriteCssIOError(cssFile, ex);
		    }
		}
		else {
			observer.onWriteCssSkipped(cssFile);
		}
		return cssFile;
	}

	private void readTranslatedSubFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s, EasyJaSubLinesSelection selection)
			throws EasyJaSubException {
		File file = command.getTranslatedSubFile();
		if (file != null) {
		    observer.onReadTranslatedSubtitlesStart(file);
		    try {
			    new SubtitleListTranslatedSubFileReader(
			    		command.getExactMatchTimeDiff(),
			    		command.getApproxMatchTimeDiff()).readTranslationSubtitles(s, file,
			    		command.getTranslatedSubFileType(), observer, selection,
			    		command.showTranslation());
			    observer.onReadTranslatedSubtitlesEnd(file);
		    }
		    catch (IOException ex) {
		    	observer.onReadTranslatedSubtitlesIOError(file, ex);
		    }
		    catch (InputTextSubException ex) {
		    	observer.onReadTranslatedSubtitlesParseError(file, ex);
		    }
		}
		else {
		    observer.onReadTranslatedSubtitlesSkipped(file);
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
			}
			catch (IOException ex) {
				observer.onInputNihongoJTalkHtmlFileIOError(f, ex);
			}
			catch (SAXException ex) {
				observer.onInputNihongoJTalkHtmlFileParseError(f, ex);
			}
		}
		else {
			observer.onInputNihongoJTalkHtmlFileParseSkipped(f);
		}
	}

	private void writeOutputJapaneseTextFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File txtFile = command.getOutputJapaneseTextFile();
		if (txtFile == null || txtFile.exists()) {
			observer.onWriteOutputJapaneseTextFileSkipped(txtFile);
		}
		else {
			mkParentDirs(txtFile);
			observer.onWriteOutputJapaneseTextFileStart(txtFile);
			try {
				new SubtitleListJapaneseTextFileWriter().write(s, txtFile);
			}
			catch (IOException ex) {
				observer.onWriteOutputJapaneseTextFileIOError(txtFile, ex);
			}
			observer.onWriteOutputJapaneseTextFileEnd(txtFile);
		}
	}

	private void mkParentDirs(File file) throws EasyJaSubException {
		File parent = file.getAbsoluteFile().getParentFile();
		if (parent == null || (!parent.exists() && !parent.mkdirs())) {
			throw new EasyJaSubException(
					"Could not create parent directories for "
							+ file.getAbsolutePath());
		}
	}

	private void readJapaneseSubtitles(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s,
			EasyJaSubLinesSelection selection)
			throws EasyJaSubException {
		File jaF = command.getJapaneseSubFile();
		if (jaF == null) {
			observer.onReadJapaneseSubtitlesSkipped(jaF);
		}
		else {
			observer.onReadJapaneseSubtitlesStart(jaF);
			try {
			    new SubtitleListJapaneseSubFileReader().readJapaneseSubtitles(s, jaF, 
			    		command.getJapaneseSubFileType(), observer,
			    		selection);
			    observer.onReadJapaneseSubtitlesEnd(jaF);
			}
			catch (IOException ex) {
				observer.onReadJapaneseSubtitlesIOError(jaF, ex);
			}
			catch (InputTextSubException ex) {
				observer.onReadJapaneseSubtitlesParseError(jaF, ex);
			}
		}
	}
}
