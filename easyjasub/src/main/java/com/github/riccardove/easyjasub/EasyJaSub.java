package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.xml.sax.SAXException;

import bdsup2sub.BDSup2Sub;

import com.github.riccardove.easyjasub.inputnihongojtalk.InputNihongoJTalkHtmlFile;

public class EasyJaSub {
	
	private enum Phases {
		All,
		Html,
		Png,
		Bdm,
		Sup,
		Idx, SupXml, Htmls, Ifo,
	};
	
	private static final Pattern WashPattern = Pattern.compile("[^\\p{L}\\p{Nd}]");
	private static final Pattern WashPattern2 = Pattern.compile("__+");
	
	public static int run(String[] args) throws Exception {
		HashSet<Phases> phases = null;
		if (args.length == 0) {
			System.err.println("File name without extension as first argument");
			return -2;
		}
		String fileName = args[0];
		if (args.length > 1) {
			phases = new HashSet<EasyJaSub.Phases>();
			for (int i = 1; i<args.length; ++i) {
				Phases phase = Phases.valueOf(args[i]);
				phases.add(phase);
			}
		}
		return run(phases, fileName);
	}

	private static int run(HashSet<Phases> phases, String fileName) throws Exception {
		String washedFileName = WashPattern.matcher(fileName).replaceAll("_");
		washedFileName = WashPattern2.matcher(washedFileName).replaceAll("_");
		SubtitleList s = new SubtitleList(washedFileName);
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
			System.out.println("parseJhtml");
		    InputNihongoJTalkHtmlFile.parse(new File(fileName + ".htm"), s);
		    
			System.out.println("readJapaneseSubtitles");
		    new SubtitleListJapaneseSubFileReader().readJapaneseSubtitles(s, fileName +".ass", new File(fileName +".ass"));
		    
			System.out.println("readEnglishSubtitles");
		    new SubtitleListTranslatedSubFileReader().readEnglishSubtitles(s, fileName + ".en.ass", new File(fileName +".en.ass"));
//			for (String pos : RedSubtitleLineItem.PosSet) {
//				System.out.println(pos);
//			}
		}

		File htmlFolder = createFolder(washedFileName + "_html");

		if (phases == null 
				|| phases.contains(Phases.Htmls)) {
			System.out.println("writeHtmls");
			new SubtitleListHtmlFilesWriter(htmlFolder).writeHtmls(s);;
		}

		File bdnFolder = createFolder(washedFileName + "_bdn");

		int result = 0;
		if (phases == null 
				|| phases.contains(Phases.Png)) {
			System.out.println("writeImages");
		    result = new SubtitleListPngFilesWriter().writeImages(s, htmlFolder, bdnFolder);
		}

		if (phases == null || phases.contains(Phases.Bdm)) {
			System.out.println("writeBDM");
			new SubtitleListBdmXmlFileWriter().writeBDM(s, bdnFolder, fileName + ".xml");
			System.out.println("BDN file created");
		}

		if (phases == null 
				|| phases.contains(Phases.Idx)) {
			System.out.println("writeBDM");
			
			File folder = createFolder(washedFileName + "_idx");
			// TODO Core.timestampsStr = s.getIdxTimestamps();
			
			new BDSup2SubWrapper().toIdx(bdnFolder, fileName + ".xml", folder, fileName + ".idx", s.getWidth());
			System.out.println("Idx");
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

	private static File createFolder(String name) {
		File bdnFolder = new File(name);
		if (!bdnFolder.exists()) {
			bdnFolder.mkdir();
		}
		return bdnFolder;
	}
}
