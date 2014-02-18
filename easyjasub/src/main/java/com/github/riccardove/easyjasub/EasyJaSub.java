package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.xml.sax.SAXException;
import com.github.riccardove.easyjasub.inputnihongojtalk.InputNihongoJTalkHtmlFile;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;

public class EasyJaSub {
	
	
	
	
	
	public int run(EasyJaSubInput command, EasyJaSubObserver observer) throws EasyJaSubException {
		Set<Phases> phases = command.getPhases();
		SubtitleList s = new SubtitleList(command.getVideoFile().getName());
//		    s.setWidth(1366);
//		    s.setHeight(768);
//            return "720p (1280x720)";

		s.setWidth(1280);
		s.setHeight(720);
		
		if (phases == null 
				|| phases.contains(Phases.Html)
				|| phases.contains(Phases.Idx)
				|| phases.contains(Phases.Bdm)
				|| phases.contains(Phases.Png)) {
			readJapaneseSubtitles(command, observer, s);
		    
		    writeOutputJapaneseTextFile(command, observer, s);

			parseNihongoJTalkFile(command, observer, s);

		    readTranslatedSubFile(command, observer, s);
		}

		File htmlFolder = createFolder(command.getOutputHtmlDirectory());

		if (phases == null 
				|| phases.contains(Phases.Htmls)) {
			String cssFileUrl = createCssFile(command, observer);
			
			writeHtmlFiles(observer, s, htmlFolder, cssFileUrl);
		}

		File bdnFolder = createFolder(command.getBdnXmlFile().getParentFile());

		if (phases == null 
				|| phases.contains(Phases.Png)) {
			writePngFiles(command, observer, s, htmlFolder, bdnFolder);
		}

		if (phases == null || phases.contains(Phases.Bdm)) {
			writeBdmXmlFile(command, observer, s);
		}

		if (phases == null 
				|| phases.contains(Phases.Idx)) {
			writeIdxFile(command, observer, s, bdnFolder);
		}

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
		File folder = createFolder(file.getParentFile());
		// TODO Core.timestampsStr = s.getIdxTimestamps();
		observer.onWriteIdxFileStart(file);
		// TODO output file
		new BDSup2SubWrapper().toIdx(bdnFolder, command.getBdnXmlFile(), folder, file, s.getWidth());
		observer.onWriteIdxFileEnd(file);
	}

	private void writeBdmXmlFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File f = command.getBdnXmlFile();
		observer.onWriteBdnXmlFileStart(f);
		try {
			new SubtitleListBdmXmlFileWriter().writeBDM(s, f);
			observer.onWriteBdnXmlFileEnd(f);
		}
		catch (IOException ex) {
			observer.onWriteBdnXmlFileIOError(f, ex);
		}
	}

	private void writePngFiles(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s, File htmlFolder,
			File bdnFolder) throws EasyJaSubException {
		String wkhtml = command.getWkhtmltoimageFile();
		int width = s.getWidth();
		observer.onWriteImagesStart(wkhtml, htmlFolder, bdnFolder, width);
		try {
			if (wkhtml != null) {
			    new SubtitleListPngFilesWriter(wkhtml).writeImages(s, htmlFolder, bdnFolder, width, observer);
			}
			else {
			    new SubtitleListPngFilesJavaWriter().writeImages(s, htmlFolder, bdnFolder, width, observer);
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
			new SubtitleListHtmlFilesWriter(htmlFolder, cssFileUrl).writeHtmls(s);
			observer.onWriteHtmlEnd(htmlFolder);
		}
		catch (IOException ex) {
			observer.onWriteHtmlError(htmlFolder, ex);
		}
	}

	private String createCssFile(EasyJaSubInput command,
			EasyJaSubObserver observer) throws EasyJaSubException {
		File cssFile = command.getCssFile();
		if (!cssFile.exists()) {
			observer.onWriteCssStart(cssFile);
			try {
				new SubtitleListCssFileWriter(cssFile).write();
				observer.onWriteCssEnd(cssFile);
			}
		    catch (IOException ex) {
		    	observer.onWriteCssIOError(cssFile, ex);
		    }
		}
		String cssFileUrl = cssFile.toURI().toString();
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
	}

	private void parseNihongoJTalkFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File f = command.getNihongoJtalkHtmlFile();
		if (f != null) {
			observer.onInputNihongoJTalkHtmlFileParseStart(f);
			try {
			    new InputNihongoJTalkHtmlFile().parse(f, s, observer);
				observer.onInputNihongoJTalkHtmlFileParseEnd(f, RedSubtitleLineItem.PosSet);
			}
			catch (IOException ex) {
				observer.onInputNihongoJTalkHtmlFileIOError(f, ex);
			}
			catch (SAXException ex) {
				observer.onInputNihongoJTalkHtmlFileParseError(f, ex);
			}
		}
	}

	private void writeOutputJapaneseTextFile(EasyJaSubInput command,
			EasyJaSubObserver observer, SubtitleList s)
			throws EasyJaSubException {
		File txtFile = command.getOutputJapaneseTextFile();
		if (txtFile != null) {
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

	private static File createFolder(File bdnFolder) {
		if (!bdnFolder.exists()) {
			bdnFolder.mkdir();
		}
		return bdnFolder;
	}
}
