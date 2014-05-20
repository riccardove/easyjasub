package com.github.riccardove.easyjasub.inputtextsub;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import subtitleFile.Caption;
import subtitleFile.FatalParsingException;
import subtitleFile.FormatASS;
import subtitleFile.FormatSCC;
import subtitleFile.FormatSRT;
import subtitleFile.FormatSTL;
import subtitleFile.FormatTTML;
import subtitleFile.SubtitleFileTimeWrapper;
import subtitleFile.TimedTextFileFormat;
import subtitleFile.TimedTextObject;

import com.github.riccardove.easyjasub.InputSubtitleLine;
import com.github.riccardove.easyjasub.SubtitleFileType;

public class InputTextSubFile {

	private TimedTextFileFormat createFormat(SubtitleFileType inputFormat) {
		switch (inputFormat) {
		case SRT:
			return new FormatSRT();
		case STL:
			return new FormatSTL();
		case SCC:
			return new FormatSCC();
		case TTML:
		case XML:
			return new FormatTTML();
		case ASS:
			return new FormatASS();
		default:
			break;
		}
		throw new IllegalArgumentException("Unrecognized input format: "
				+ inputFormat + " only [SRT,STL,SCC,XML,ASS] are possible");
	}

	public InputTextSubFile(SubtitleFileType inputFormat, String fileName,
			InputStream is) throws InputTextSubException, IOException {
		try {
			tto = createFormat(inputFormat).parseFile(fileName, is);
		} catch (FatalParsingException ex) {
			throw new InputTextSubException(
					"Parse error returned by subtitle read library", ex);
		}
		captions = new ArrayList<InputSubtitleLine>(tto.captions.size());
		for (Caption caption : tto.captions.values()) {
			InputSubtitleLine line = new InputSubtitleLine();
			line.setContent(caption.content);
			line.setStartTime(new SubtitleFileTimeWrapper(caption.start)
					.getMSeconds());
			line.setEndTime(new SubtitleFileTimeWrapper(caption.end)
					.getMSeconds());
			captions.add(line);
		}
	}

	private final TimedTextObject tto;
	private final ArrayList<InputSubtitleLine> captions;

	public String getWarnings() {
		return tto.warnings;
	}

	public String getDescription() {
		return tto.description;
	}

	public String getLanguage() {
		return tto.language;
	}

	public List<InputSubtitleLine> getCaptions() {
		return captions;
	}

	public String getTitle() {
		return tto.title;
	}
}
