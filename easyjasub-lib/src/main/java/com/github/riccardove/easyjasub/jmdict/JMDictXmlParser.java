package com.github.riccardove.easyjasub.jmdict;

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
