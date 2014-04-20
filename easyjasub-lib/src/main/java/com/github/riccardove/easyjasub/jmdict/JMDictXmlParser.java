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

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.EasyJaSubXmlHandler;
import com.github.riccardove.easyjasub.EasyJaSubXmlHandlerAdapter;
import com.github.riccardove.easyjasub.EasyJaSubXmlReader;

// TODO
public class JMDictXmlParser implements EasyJaSubXmlHandler<JMDictXmlElement> {

	public JMDictXmlParser() {

	}

	public void read(File file) throws IOException, SAXException {
		new EasyJaSubXmlReader(
				new EasyJaSubXmlHandlerAdapter<JMDictXmlElement>(
						JMDictXmlElement.undef, JMDictXmlElement.values(), this))
				.parse(file);
	}

	@Override
	public void onStartElement(JMDictXmlElement element) {
		switch (element) {
		case gloss: {

			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	public void onEndElement(JMDictXmlElement element, String text) {
		switch (element) {
		case gloss: {

			break;
		}
		default:
			break;
		}
	}
	/*
	 * <entry> <ent_seq>2172220</ent_seq> <k_ele> <keb>鳳梨</keb> </k_ele> <r_ele>
	 * <reb>ほうり</reb> </r_ele> <info> <audit> <upd_date>2007-05-30</upd_date>
	 * <upd_detl>Entry created</upd_detl> </audit> </info> <sense>
	 * <pos>&n;</pos> <xref>パイナップル</xref> <misc>&obsc;</misc>
	 * <gloss>pineapple</gloss> </sense> </entry>
	 */
}
