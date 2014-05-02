package com.github.riccardove.easyjasub.jmdict;

/*
 * #%L
 * easyjasub-lib
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
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.EasyJaSubXmlHandlerAdapter;
import com.github.riccardove.easyjasub.EasyJaSubXmlReader;

public class JMDictParser {

	public void parse(File file, JMDictObserver observer,
			String threeLetterlanguageCode) throws IOException, SAXException {
		createXmlReader(observer, threeLetterlanguageCode).parse(file);
	}

	public void parse(InputStream inputStream, JMDictObserver observer,
			String threeLetterlanguageCode) throws IOException, SAXException {
		createXmlReader(observer, threeLetterlanguageCode).parse(inputStream);
	}

	private EasyJaSubXmlReader createXmlReader(JMDictObserver observer,
			String threeLetterlanguageCode) throws SAXException {
		return new EasyJaSubXmlReader(
				new EasyJaSubXmlHandlerAdapter<JMDictXmlElement>(
						JMDictXmlElement.undef, JMDictXmlElement.values(),
						new JMDictXmlHandler(observer, threeLetterlanguageCode)));
	}
}
