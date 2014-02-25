package com.github.riccardove.easyjasub.inputnihongojtalk;

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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.github.riccardove.easyjasub.EasyJaSubObserver;

public class InputNihongoJTalkHtmlFile {

	private static final String TAGSOUP_PARSER = "org.ccil.cowan.tagsoup.Parser";

	public void parse(File file, NihongoJTalkSubtitleList s, EasyJaSubObserver observer)
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
