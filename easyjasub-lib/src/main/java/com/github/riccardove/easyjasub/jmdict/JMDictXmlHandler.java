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

import java.util.ArrayList;

import org.xml.sax.Attributes;

import com.github.riccardove.easyjasub.EasyJaSubXmlHandler;

/**
 * Handles SAX XML parser events for JMDict XML file
 */
class JMDictXmlHandler implements EasyJaSubXmlHandler<JMDictXmlElement> {

	private final JMDictObserver observer;
	private final String threeLetterlanguageCode;

	/**
	 * Creates a new handler for XML events
	 * 
	 * @param observer
	 *            The object that receives events
	 * @param threeLetterlanguageCode
	 *            The language code you are interested in, english (null) is
	 *            always accepted
	 */
	public JMDictXmlHandler(JMDictObserver observer,
			String threeLetterlanguageCode) {
		this.observer = observer;
		this.threeLetterlanguageCode = threeLetterlanguageCode;
		senses = new ArrayList<IJMDictSense>();
	}

	private int count;
	private String keb;
	private String reb;
	private String entseq;
	private String lang;
	private boolean discardGloss;
	private final ArrayList<IJMDictSense> senses;
	private JMDictSense sense;

	@Override
	public void onStartElement(JMDictXmlElement element, Attributes attributes) {
		switch (element) {
		case entry: {
			count++;
			break;
		}
		case sense: {
			sense = JMDictSenseLazy.create();
			senses.add(sense);
			break;
		}
		case gloss: {
			lang = attributes.getValue("xml", "lang");
			discardGloss = lang != null
					&& !lang.equals(threeLetterlanguageCode);
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
		case ent_seq: {
			entseq = text;
			break;
		}
		case entry: {
			observer.onEntry(count, entseq, keb, reb, senses);
			senses.clear();
			JMDictSenseLazy.clear();
			entseq = null;
			sense = null;
			keb = null;
			reb = null;
			lang = null;
			break;
		}
		case keb: {
			keb = text;
			break;
		}
		case reb: {
			reb = text;
			break;
		}
		case pos: {
			if (sense != null) {
				sense.addPartOfSpeech(text);
			} else {
				onError("Invalid pos: " + text);
			}
			break;
		}
		case gloss: {
			if (sense != null) {
				if (!discardGloss) {
					sense.addGloss(text, lang);
				}
			} else {
				onError("Invalid sense: " + text);
			}
			discardGloss = false;
			lang = null;
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

	private void onError(String message) {
		observer.onError(count, entseq, message);
	}
}
