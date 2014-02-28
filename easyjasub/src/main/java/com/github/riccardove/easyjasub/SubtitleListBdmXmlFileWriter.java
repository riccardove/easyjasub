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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;


class SubtitleListBdmXmlFileWriter {
	private final EasyJaSubInput command;
	private final double fps;
	private final String fpsStr;
	private String videoFormat;

	public SubtitleListBdmXmlFileWriter(EasyJaSubInput command,
			double fps, String fpsStr, String videoFormat) throws EasyJaSubException
	{
		this.command = command;
		this.fps = fps;
		this.fpsStr = fpsStr;
		this.videoFormat = videoFormat;
		ir = getImageReader();
	}
	
	private static final String LineSeparator = SystemProperty.getLineSeparator();
	private final ImageReader ir;
	
	public void writeBDM(SubtitleList s, File file) throws IOException, FileNotFoundException {
		f = new FileWriter(file);

		writeln("<?xml version=\"1.0\" encoding=\"" + EasyJaSubCharset.CHARSETSTR + "\"?>");
        writeln("<BDN Version=\"0.93\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"BD-03-006-0093b BDN File Format.xsd\">");
		writeln("<Description>");
		writeln("  <Name Title=\"" + s.getTitle() + "\" Content=\"\"/>");
		writeln("  <Language Code=\"jpn\" />");
		writeln("  <Format VideoFormat=\"" + videoFormat + "\" FrameRate=\"" + fpsStr + "\" DropFrame=\"False\" />");
		String firstTC = getTimeStamp(s.first().getStartTime());
		String lastTC = getTimeStamp(s.last().getEndTime());
		writeln("  <Events Type=\"Graphic\" FirstEventInTC=\"" + firstTC + "\" LastEventOutTC=\"" + lastTC + "\"");
// ContentInTC=\"" + firstTC + "\" ContentOutTC=\"" + lastTC + "\"
		writeln("    NumberofEvents=\"" + s.size() + "\" />");
		writeln("</Description>");
		writeln("<Events>");

		int videoWidth = command.getWidth();
		int videoHeight = command.getHeight();
		for (SubtitleLine l : s) {
			File imageFile = l.getPngFile();
			if (!imageFile.canRead()) {
				throw new IOException("Can not read file " + imageFile.getAbsolutePath());
			}
			FileImageInputStream is = new FileImageInputStream(imageFile);
			ir.setInput(is);
			int width = ir.getWidth(0);
			int height = ir.getHeight(0);
			ir.setInput(null);
			is.close();

			String inTC = getTimeStamp(l.getStartTime());
			String outTC = getTimeStamp(l.getEndTime());

			int x = (videoWidth - width) / 2;
			int y = (videoHeight - height - 20);
			String pngFileName = l.getPngFile().getName();
			
			writeln("  <Event InTC=\""+ inTC + "\" OutTC=\""+ outTC + "\" Forced=\"False\">");
			writeln("    <Graphic Width=\"" + width + "\" Height=\""+ height 
					+ "\" X=\"" + x + "\" Y=\"" + y + "\">" + pngFileName + "</Graphic>");
			writeln("  </Event>");
		}
		writeln("</Events>");
		writeln("</BDN>");
		f.close();
	}

	private String getTimeStamp(int mseconds) {
		StringBuffer time = new StringBuffer(20);
		int h, m, s, f;
		String aux;
		//now we concatenate time
		h =  mseconds/3600000;
		aux = String.valueOf(h);
		if (aux.length()==1) time.append('0');
		time.append(aux);
		time.append(':');
		m = (mseconds/60000)%60;
		aux = String.valueOf(m);
		if (aux.length()==1) time.append('0');
		time.append(aux);
		time.append(':');
		s = (mseconds/1000)%60;
		aux = String.valueOf(s);
		if (aux.length()==1) time.append('0');
		time.append(aux);
		time.append(':');
		f = (mseconds%1000)*(int)fps/1000;
		aux = String.valueOf(f);
		if (aux.length()==1) time.append('0');
		time.append(aux);
		return time.toString();
	}
	
	private ImageReader getImageReader() throws EasyJaSubException {
		Iterator<ImageReader> ir = ImageIO.getImageReadersBySuffix("png");
		if (!ir.hasNext()) {
			throw new EasyJaSubException("Can not read png files");
		}
		return ir.next();
	}

	private FileWriter f;
	
	private void writeln(String line) throws IOException
	{
		f.write(line);
		f.write(LineSeparator);
	}
}
