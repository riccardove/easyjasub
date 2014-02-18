package com.github.riccardove.easyjasub.inputnihongojtalk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.github.riccardove.easyjasub.EasyJaSubObserver;
import com.github.riccardove.easyjasub.SubtitleList;

public class InputNihongoJTalkHtmlFile {

	private static final String TAGSOUP_PARSER = "org.ccil.cowan.tagsoup.Parser";

	public void parse(File file, SubtitleList s, EasyJaSubObserver observer)
			throws IOException, SAXException
	{
	    XMLReader saxParser = XMLReaderFactory.createXMLReader(TAGSOUP_PARSER);

	    InputNihongoJTalkHtmlHandler handler = new InputNihongoJTalkHtmlHandler(s, observer);

	    saxParser.setContentHandler(handler);
		saxParser.setEntityResolver(handler);
		
		BufferedReader br = new BufferedReader(new FileReader(file));

		saxParser.parse(new InputSource(br));
		
		br.close();
	}
}
