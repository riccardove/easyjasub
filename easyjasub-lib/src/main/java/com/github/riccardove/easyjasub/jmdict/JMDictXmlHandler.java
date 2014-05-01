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

import com.github.riccardove.easyjasub.EasyJaSubXmlHandler;
import com.github.riccardove.easyjasub.PartOfSpeech;

// TODO
public class JMDictXmlHandler implements EasyJaSubXmlHandler<JMDictXmlElement> {

	private final JMDictObserver observer;

	public JMDictXmlHandler(JMDictObserver observer) {
		this.observer = observer;
		senses = new ArrayList<JMDictSense>();
	}

	private int count;
	private String keb;
	private String reb;
	private String entseq;
	private final ArrayList<JMDictSense> senses;
	private JMDictSense sense;

	@Override
	public void onStartElement(JMDictXmlElement element) {
		switch (element) {
		case entry: {
			count++;
			break;
		}
		case sense: {
			sense = new JMDictSense();
			senses.add(sense);
			break;
		}
		default: {
			break;
		}
		}
	}

	private static PartOfSpeech convertTextToPartOfSpeech(String text) {
		return PartOfSpeech.undef; // TODO
	}

	@Override
	public void onEndElement(JMDictXmlElement element, String text) {
		switch (element) {
		case ent_seq: {
			entseq = text;
			break;
		}
		case entry: {
			if (senses.size() > 1) {
				observer.onEntry(count, entseq, keb, reb, senses);
			} else {
				observer.onEntry(count, entseq, keb, reb, sense);
			}
			senses.clear();
			entseq = null;
			sense = null;
			keb = null;
			reb = null;
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
				PartOfSpeech pos = convertTextToPartOfSpeech(text);
				if (pos != PartOfSpeech.undef) {
					sense.addPartOfSpeech(pos);
				} else {
					// TODO onError("Unknown pos: " + text);
				}
			} else {
				onError("Invalid pos: " + text);
			}
			break;
		}
		case gloss: {
			if (sense != null) {
				sense.addGloss(text);
			} else {
				onError("Invalid sense: " + text);
			}
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
