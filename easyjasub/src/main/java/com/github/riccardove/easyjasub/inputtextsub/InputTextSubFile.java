package com.github.riccardove.easyjasub.inputtextsub;

import java.io.InputStream;
import java.util.ArrayList;

import com.github.riccardove.easyjasub.SubtitleFileType;

import subtitleFile.*;

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
		}
		throw new IllegalArgumentException("Unrecognized input format: "+inputFormat+" only [SRT,STL,SCC,XML,ASS] are possible");
	}
	
	public InputTextSubFile(SubtitleFileType inputFormat, String fileName, InputStream is) throws Exception {
		tto = createFormat(inputFormat).parseFile(fileName, is);
		captions = new ArrayList<InputTextSubCaption>(tto.captions.size()); 
		for (Caption caption : tto.captions.values()) {
			captions.add(new InputTextSubCaption(caption));
		}
	}
	
	private final TimedTextObject tto;
	private final ArrayList<InputTextSubCaption> captions;

	public String getWarnings() {
		return tto.warnings;
	}
	
	public String getDescription() {
		return tto.description;
	}
	
	public String getLanguage() {
		return tto.language;
	}
	
	public Iterable<InputTextSubCaption> getCaptions() {
		return captions;
	}

	public String getTitle() {
		return tto.title;
	}
}
