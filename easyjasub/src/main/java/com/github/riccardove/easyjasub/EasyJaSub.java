package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

import javax.jws.Oneway;

import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

import bdsup2sub.BDSup2Sub;

import com.github.riccardove.easyjasub.inputnihongojtalk.InputNihongoJTalkHtmlFile;

public class EasyJaSub {
	
	private static final Pattern WashPattern = Pattern.compile("[^\\p{L}\\p{Nd}]");
	private static final Pattern WashPattern2 = Pattern.compile("__+");
	
	
	
	
	public int run(EasyJaSubInput command, EasyJaSubObserver observer) throws Exception {
		
		
		
//		command.getNihongoJtalkHtmlFileName()
		
		Set<Phases> phases = command.getPhases();
//		String fileName = command.getVideoFileName();
//		String washedFileName = WashPattern.matcher(fileName).replaceAll("_");
//		washedFileName = WashPattern2.matcher(washedFileName).replaceAll("_");
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
			File f = command.getNihongoJtalkHtmlFile();
			observer.onInputNihongoJTalkHtmlFileParseStart(f);
		    InputNihongoJTalkHtmlFile.parse(f, s, observer);
			observer.onInputNihongoJTalkHtmlFileParseEnd(f, RedSubtitleLineItem.PosSet);
		    
			File jaF = command.getJapaneseSubFile();
		    observer.onReadJapaneseSubtitlesStart(jaF);
		    new SubtitleListJapaneseSubFileReader().readJapaneseSubtitles(s, jaF);
		    observer.onReadJapaneseSubtitlesEnd(jaF);
		    
		    File enF = command.getTranslatedSubFile();
		    observer.onReadTranslatedSubtitlesStart(enF);
		    new SubtitleListTranslatedSubFileReader().readEnglishSubtitles(s, enF);
		    observer.onReadTranslatedSubtitlesEnd(enF);
		}

		File htmlFolder = createFolder(command.getOutputHtmlDirectory());

		if (phases == null 
				|| phases.contains(Phases.Htmls)) {
			observer.onWriteHtmlStart(htmlFolder);
			new SubtitleListHtmlFilesWriter(htmlFolder).writeHtmls(s);
			observer.onWriteHtmlEnd(htmlFolder);
		}

		File bdnFolder = createFolder(command.getBdnXmlFile().getParentFile());

		int result = 0;
		if (phases == null 
				|| phases.contains(Phases.Png)) {
			String wkhtml = command.getWkhtmltoimageFile();
			observer.onWriteImagesStart(wkhtml, htmlFolder, bdnFolder);
		    result = new SubtitleListPngFilesWriter(wkhtml).writeImages(s, htmlFolder, bdnFolder);
			observer.onWriteImagesEnd(wkhtml, htmlFolder, bdnFolder);
		}

		if (phases == null || phases.contains(Phases.Bdm)) {
			File f = command.getBdnXmlFile();
			observer.onWriteBdnXmlFileStart(f);
			new SubtitleListBdmXmlFileWriter().writeBDM(s, f);
			observer.onWriteBdnXmlFileEnd(f);
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
