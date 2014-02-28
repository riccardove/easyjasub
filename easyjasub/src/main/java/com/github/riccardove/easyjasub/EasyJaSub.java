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
import java.util.*;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.inputnihongojtalk.InputNihongoJTalkHtmlFile;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;

public class EasyJaSub {
	
	
	
	
	
	public int run(EasyJaSubInput command, EasyJaSubObserver observer) throws EasyJaSubException {
		String filePrefix = command.getDefaultFileNamePrefix();
		if (filePrefix == null) {
			filePrefix = "easyjasub_";
		}
//		    s.setWidth(1366);
//		    s.setHeight(768);
//            return "720p (1280x720)";

	    SubtitleList s = new SubtitleList();

		readJapaneseSubtitles(command, observer, s);
	    
	    writeOutputJapaneseTextFile(command, observer, s);

	    readTranslatedSubFile(command, observer, s);

		parseNihongoJTalkFile(command, observer, s);


		File htmlFolder = createFolder(command.getOutputHtmlDirectory());
		File bdnFolder = createFolder(command.getBdnXmlFile().getParentFile());

	    int index = 0;
	    for (SubtitleLine line : s) {
	    	line.setIndex(++index);
	    	
			String b = filePrefix + "_line" +String.format("%04d", line.getIndex());
			line.setHtmlFile(new File(htmlFolder, b + ".html"));
			line.setPngFile(new File(bdnFolder, b + ".png"));
	    }


		String cssFileUrl = createCssFile(command, observer);
		
		writeHtmlFiles(observer, s, htmlFolder, cssFileUrl);


		writePngFiles(command, observer, s, htmlFolder, bdnFolder);

		writeBdmXmlFile(command, observer, s);

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
			EasyJaSubObserver observer, SubtitleList s, File bdnFolder) {
		File file = command.getOutputIdxFile();
		File bdnFile = command.getBdnXmlFile();
		if (file != null && bdnFile != null && !file.exists()) {
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

	private void writeHtmlFiles(EasyJaSubObserver observer, SubtitleList s,
			File htmlFolder, String cssFileUrl) throws EasyJaSubException {
		observer.onWriteHtmlStart(htmlFolder, cssFileUrl);
		try {
			new SubtitleListHtmlFilesWriter(htmlFolder, cssFileUrl, observer).writeHtmls(s);
			observer.onWriteHtmlEnd(htmlFolder);
		}
		catch (IOException ex) {
			observer.onWriteHtmlError(htmlFolder, ex);
		}
	}

	private String createCssFile(EasyJaSubInput command,
			EasyJaSubObserver observer) throws EasyJaSubException {
		File cssFile = command.getCssFile();
		if (cssFile != null && !cssFile.exists()) {
			observer.onWriteCssStart(cssFile);
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
		String cssFileUrl = cssFile != null ? cssFile.toURI().toString() : "default.css";
		return cssFileUrl;
	}

	private void readTranslatedSubFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File enF = command.getTranslatedSubFile();
		if (enF != null) {
		    observer.onReadTranslatedSubtitlesStart(enF);
		    try {
			    new SubtitleListTranslatedSubFileReader(
			    		command.getExactMatchTimeDiff(),
			    		command.getApproxMatchTimeDiff()).readTranslationSubtitles(s, enF,
			    		command.getTranslatedSubFileType(), observer);
			    observer.onReadTranslatedSubtitlesEnd(enF);
		    }
		    catch (IOException ex) {
		    	observer.onReadTranslatedSubtitlesIOError(enF, ex);
		    }
		    catch (InputTextSubException ex) {
		    	observer.onReadTranslatedSubtitlesParseError(enF, ex);
		    }
		}
		else {
		    observer.onReadTranslatedSubtitlesSkipped(enF);
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

	private void readJapaneseSubtitles(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File jaF = command.getJapaneseSubFile();
		if (jaF == null) {
			observer.onReadJapaneseSubtitlesSkipped(jaF);
		}
		else {
			observer.onReadJapaneseSubtitlesStart(jaF);
			try {
			    new SubtitleListJapaneseSubFileReader().readJapaneseSubtitles(s, jaF, 
			    		command.getJapaneseSubFileType(), observer);
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

	private static File createFolder(File bdnFolder) {
		if (!bdnFolder.exists()) {
			bdnFolder.mkdir();
		}
		return bdnFolder;
	}
}
