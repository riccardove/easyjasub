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
			File jaF = command.getJapaneseSubFile();
		    observer.onReadJapaneseSubtitlesStart(jaF);
		    try {
			    new SubtitleListJapaneseSubFileReader().readJapaneseSubtitles(s, jaF, 
			    		command.getJapaneseSubFileType());
			    observer.onReadJapaneseSubtitlesEnd(jaF);
		    }
		    catch (IOException ex) {
		    	observer.onReadJapaneseSubtitlesIOError(jaF, ex);
		    }
		    catch (InputTextSubException ex) {
		    	observer.onReadJapaneseSubtitlesParseError(jaF, ex);
		    }
		    
		    File txtFile = command.getOutputJapaneseTextFile();
		    if (txtFile != null) {
		    	observer.onWriteOutputJapaneseTextFileStart(txtFile);
		    	
		    	observer.onWriteOutputJapaneseTextFileEnd(txtFile);
		    }

			File f = command.getNihongoJtalkHtmlFile();
			if (f != null) {
				observer.onInputNihongoJTalkHtmlFileParseStart(f);
				try {
				    InputNihongoJTalkHtmlFile.parse(f, s, observer);
					observer.onInputNihongoJTalkHtmlFileParseEnd(f, RedSubtitleLineItem.PosSet);
				}
				catch (IOException ex) {
					observer.onInputNihongoJTalkHtmlFileIOError(f, ex);
				}
				catch (SAXException ex) {
					observer.onInputNihongoJTalkHtmlFileParseError(f, ex);
				}
			}

		    File enF = command.getTranslatedSubFile();
		    if (enF != null) {
			    observer.onReadTranslatedSubtitlesStart(enF);
			    try {
				    new SubtitleListTranslatedSubFileReader().readEnglishSubtitles(s, enF,
				    		command.getTranslatedSubFileType());
				    observer.onReadTranslatedSubtitlesEnd(enF);
			    }
			    catch (IOException ex) {
			    	observer.onReadTranslatedSubtitlesIOError(jaF, ex);
			    }
			    catch (InputTextSubException ex) {
			    	observer.onReadTranslatedSubtitlesParseError(jaF, ex);
			    }
		    }
		}

		File htmlFolder = createFolder(command.getOutputHtmlDirectory());

		if (phases == null 
				|| phases.contains(Phases.Htmls)) {
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
			observer.onWriteHtmlStart(htmlFolder, cssFileUrl);
			try {
				new SubtitleListHtmlFilesWriter(htmlFolder, cssFileUrl).writeHtmls(s);
				observer.onWriteHtmlEnd(htmlFolder);
			}
			catch (IOException ex) {
				observer.onWriteHtmlError(htmlFolder, ex);
			}
		}

		File bdnFolder = createFolder(command.getBdnXmlFile().getParentFile());

		int result = 0;
		if (phases == null 
				|| phases.contains(Phases.Png)) {
			String wkhtml = command.getWkhtmltoimageFile();
			int width = s.getWidth();
			observer.onWriteImagesStart(wkhtml, htmlFolder, bdnFolder, width);
			try {
				if (wkhtml != null) {
				    result = new SubtitleListPngFilesWriter(wkhtml).writeImages(s, htmlFolder, bdnFolder, width, observer);
				}
				else {
				    result = new SubtitleListPngFilesJavaWriter().writeImages(s, htmlFolder, bdnFolder, width, observer);
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

		if (phases == null || phases.contains(Phases.Bdm)) {
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

		if (phases == null 
				|| phases.contains(Phases.Idx)) {
			File file = command.getOutputIdxFile();
			File folder = createFolder(file.getParentFile());
			// TODO Core.timestampsStr = s.getIdxTimestamps();
			observer.onWriteIdxFileStart(file);
			// TODO output file
			new BDSup2SubWrapper().toIdx(bdnFolder, command.getBdnXmlFile(), folder, file, s.getWidth());
			observer.onWriteIdxFileEnd(file);
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
		return result;
	}

	private static File createFolder(File bdnFolder) {
		if (!bdnFolder.exists()) {
			bdnFolder.mkdir();
		}
		return bdnFolder;
	}
}
