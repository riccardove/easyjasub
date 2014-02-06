package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;


class SubtitleListBdmXmlFileWriter {
	public SubtitleListBdmXmlFileWriter() 
	{
		ir = getImageReader();
	}
	
	private final ImageReader ir;
	
	public void writeBDM(SubtitleList s, File folder, String fileName) throws IOException, FileNotFoundException {
		f = new FileWriter(new File(folder, fileName));

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
			ir.setInput(is);
			int width = ir.getWidth(0);
			int height = ir.getHeight(0);
			ir.setInput(null);
			is.close();

			String inTC = l.getInTC();
			String outTC = l.getOutTC();

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

	private ImageReader getImageReader() {
		Iterator<ImageReader> ir = ImageIO.getImageReadersBySuffix("png");
		if (!ir.hasNext()) {
			throw new NullPointerException("Can not read png files");
		}
		return ir.next();
	}

	private FileWriter f;
	
	private void writeln(String line) throws IOException
	{
		f.write(line + "\r\n");
	}
}
