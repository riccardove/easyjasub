package com.github.riccardove.easyjasub;

import static org.rendersnake.HtmlAttributesFactory.charset;
import static org.rendersnake.HtmlAttributesFactory.lang;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.FileImageInputStream;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;
import org.xml.sax.SAXException;

import bdsup2sub.BDSup2Sub;

import com.github.riccardove.easyjasub.inputnihongojtalk.InputNihongoJTalkHtmlFile;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubCaption;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubFile;
import com.sun.imageio.plugins.png.PNGImageReaderSpi;

public class EasyJaSub {
		
	private static final String CssFileName = 
			"file:///C:/Users/riccardo/workspace/jsubs/default.css";
	
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
		    parseJhtml(new File(fileName + ".htm"), s);
		    
			System.out.println("readJapaneseSubtitles");
		    readJapaneseSubtitles(s, fileName +".ass", new File(fileName +".ass"));
		    
			System.out.println("readEnglishSubtitles");
		    readEnglishSubtitles(s, fileName + ".en.ass", new File(fileName +".en.ass"), true);
			for (String pos : RedSubtitleLineItem.PosSet) {
				System.out.println(pos);
			}
		}

		File htmlFolder = createFolder(washedFileName + "_html");

		if (phases == null 
				|| phases.contains(Phases.Htmls)) {
			System.out.println("writeHtmls");
			writeHtmls(s, htmlFolder);
		}

		File bdnFolder = createFolder(washedFileName + "_bdn");

		int result = 0;
		if (phases == null 
				|| phases.contains(Phases.Png)) {
			System.out.println("writeImages");
		    result = writeImages(s, htmlFolder, bdnFolder);
		}

		if (phases == null || phases.contains(Phases.Bdm)) {
			System.out.println("writeBDM");
			writeBDM(s, bdnFolder, fileName + ".xml");
			System.out.println("BDN file created");
		}

		if (phases == null 
				|| phases.contains(Phases.Idx)) {
			System.out.println("writeBDM");
			
			File folder = createFolder(washedFileName + "_idx");
			// TODO Core.timestampsStr = s.getIdxTimestamps();
			
			toIdx(bdnFolder, fileName + ".xml", folder, fileName + ".idx", s.getWidth());
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

	private static void toIdx(File folderIn, String fileIn, File folderOut, String fileOut, int width) {
//		if (width>1080) {
//			width = 1080;
//		}
		if (folderIn != null) {
			fileIn = (new File(folderIn, fileIn)).getAbsolutePath();
		}
		if (folderOut != null) {
			fileOut = (new File(folderOut, fileOut)).getAbsolutePath();
		}
		BDSup2Sub.main(new String[] {
				"-m", "100",
				"-x", "10",
				"-p", "keep",
				"-T", "24p",
				"-v",
//				"-r", Integer.toString(width),
				"-o", fileOut, 
				fileIn });
	}


	private static void writeHtmls(SubtitleList s, File htmlFolder) throws IOException{
		for (SubtitleLine l : s) {
			String htmlStr = toHtml(l, CssFileName);
			
			File file = new File(htmlFolder, l.getHtmlFile());
			toFile(htmlStr, file);
		}
	}

	private static int writeImages(SubtitleList s, File htmlFolder, File pngFolder) throws IOException,
			InterruptedException {
		int result  = 0;
		LinkedList<Process> processes = new LinkedList<Process>(); 
		for (SubtitleLine l : s) {
			
			File file = new File(htmlFolder, l.getHtmlFile());

			File pngFile = new File(pngFolder, l.getPngFile());
			Process p = toImage(file.getAbsolutePath(), pngFile.getAbsolutePath(), s.getWidth());
			processes.add(p);
			if (processes.size() > 10 || l.getIndex() == 1) {
				do {
					result = processes.removeFirst().waitFor();
				}
				while (result == 0 && processes.size() > 3);
			}
			if (result > 0) {
				break;
			}
			System.out.print('*');
			if (l.getIndex() % 80 == 0) {
				System.out.println();
			}
			System.out.flush();
		}
		do {
			result = processes.removeFirst().waitFor();
		}
		while (result == 0 && processes.size() > 0);
		System.out.println();
		return result;
	}

	private static void readJapaneseSubtitles(SubtitleList s, String fileName, File file) throws Exception {
		InputTextSubFile subs = parseAssFile(fileName, file);
		s.setTitle(subs.title);
		Iterator<InputTextSubCaption> ci = subs.getCaptions().iterator(); 
		Iterator<SubtitleLine> si = s.iterator();
		while (ci.hasNext() && si.hasNext()) {
			si.next().setCaption(ci.next());
		}
	}

	private static void readEnglishSubtitles(SubtitleList s, String fileName, File file, boolean add) throws Exception {
		InputTextSubFile subs = parseAssFile(fileName, file);
		s.setEnglishTitle(subs.title);
		if (add) {
			for (InputTextSubCaption enCaption : subs.getCaptions()) {
				boolean added = false;
				for (SubtitleLine jaLine : s) {
					if (jaLine.isJa() &&
						jaLine.compatibleWith(enCaption)) {
						jaLine.addEnglish(enCaption.getContent());
						added = true;
					}
					else if (added) {
						break;
					}
				}
				if (!added) {
					for (SubtitleLine jaLine : s) {
						if (jaLine.isJa() &&
							jaLine.approxCompatibleWith(enCaption)) {
							jaLine.addEnglish(enCaption.getContent());
							if (!added) {
								added = true;
							}
							else {
								System.out.println("Duplicated english caption " + enCaption.getContent() + " starting at " + enCaption.getStart() + " at " + jaLine.getIdxStartTime());
							}
						}
						else if (added) {
							break;
						}
					}
				}
				if (!added) {
					s.insertEnglish(enCaption);
				}
			}
			String lastEnglish = null;
			for (SubtitleLine jaLine : s) {
				if (jaLine.isJa())
				{
					if (!jaLine.isEnglish()) {
						if (lastEnglish != null) {
							jaLine.addEnglish(lastEnglish);
						}
					}
					else {
						lastEnglish = jaLine.getEnglish();
					}
				}
				else {
					lastEnglish = null;
				}
			}
		}
	}

	private static InputTextSubFile parseAssFile(String fileName, File file) throws IOException,
			Exception {
		return new InputTextSubFile("ASS", fileName, new FileInputStream(file));
	}

	
	@SuppressWarnings("restriction")
	private static void writeBDM(SubtitleList s, File folder, String fileName) throws IOException, FileNotFoundException {
		PNGImageReaderSpi pngSpi = IIORegistry.getDefaultInstance().getServiceProviderByClass(PNGImageReaderSpi.class);
		f = new BufferedWriter(new FileWriter(new File(folder, fileName)));

		writeln("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writeln("<BDN Version=\"0.93\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"BD-03-006-0093b BDN File Format.xsd\">");
		writeln("<Description>");
		writeln("  <Name Title=\"" + s.getTitle() + "\" Content=\"\"/>");
		writeln("  <Language Code=\"jpn\" />");
		writeln("  <Format VideoFormat=\"720p\" FrameRate=\"23.976\" DropFrame=\"False\" />");
		String firstTC = s.first().getInTC();
		String lastTC = s.last().getOutTC();
		writeln("  <Events Type=\"Graphic\" FirstEventInTC=\"" + firstTC + "\" LastEventOutTC=\"" + lastTC + "\"");
// ContentInTC=\"" + firstTC + "\" ContentOutTC=\"" + lastTC + "\"
		writeln("    NumberofEvents=\"" + s.size() + "\" />");
		writeln("</Description>");
		writeln("<Events>");
		
		int videoWidth = s.getWidth();
		int videoHeight = s.getHeight();
		for (SubtitleLine l : s) {
			File imageFile = new File(folder, l.getPngFile());
			if (!imageFile.canRead()) {
				throw new IOException("Can not read file " + imageFile.getAbsolutePath());
			}
			FileImageInputStream is = new FileImageInputStream(imageFile);

			ImageReader ir = pngSpi.createReaderInstance();
			ir.setInput(is);
			int width = ir.getWidth(0);
			int height = ir.getHeight(0);
			String inTC = l.getInTC();
			String outTC = l.getOutTC();
			
			ir.dispose();
			is.close();
			
			int x = (videoWidth - width) / 2;
			int y = (videoHeight - height - 20);
			
			writeln("  <Event InTC=\""+ inTC + "\" OutTC=\""+ outTC + "\" Forced=\"False\">");
			writeln("    <Graphic Width=\"" + width + "\" Height=\""+ height 
					+ "\" X=\"" + x + "\" Y=\"" + y + "\">" + l.getPngFile() + "</Graphic>");
			writeln("  </Event>");
		}
		writeln("</Events>");
		writeln("</BDN>");
		f.close();
		
	}

	private static Process toImage(String htmlFile, String pngFile, int width) throws IOException {
		return new ProcessBuilder("C:\\Program Files (x86)\\wkhtmltopdf\\wkhtmltoimage.exe",
				"--width", Integer.toString(width), //"--disable-smart-width",
				"--transparent",
				htmlFile, pngFile).start();
	}

	private static BufferedWriter f;
	
	private static void writeln(String line) throws IOException
	{
		f.write(line + "\r\n");
	}

	private static void parseJhtml(File file, SubtitleList s)
			throws IOException, SAXException
	{
		InputNihongoJTalkHtmlFile.parse(file, s);
	}
	
	private static String toHtml(Renderable s, String cssFileRef) throws IOException 
	{
		HtmlCanvas html = new HtmlCanvas();
		html.write("<!DOCTYPE html>", false)
		.html(lang("ja"))
		.head()
		.meta(charset("utf-8"))
		.write("<link href=\"" + cssFileRef + "\" rel=\"stylesheet\" type=\"text/css\" />", false)
		._head().body();
		s.renderOn(html);
		html._body()._html();

		return html.toHtml();
	}
	
	private static void toFile(String text, File fileName) throws IOException
	{
		BufferedWriter fw = new BufferedWriter(new FileWriter(fileName));
		fw.write(text);
		fw.close();
	}
}
