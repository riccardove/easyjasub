package com.github.riccardove.easyjasub.inputtextsub;

import java.io.InputStream;
import java.util.ArrayList;

import subtitleFile.*;

public class InputTextSubFile {

	public InputTextSubFile(String inputFormat, String fileName, InputStream is) throws Exception {
		TimedTextObject tto;
		TimedTextFileFormat ttff;

		if ("SRT".equalsIgnoreCase(inputFormat)){
			ttff = new FormatSRT();
		} else if ("STL".equalsIgnoreCase(inputFormat)){
			ttff = new FormatSTL();
		} else if ("SCC".equalsIgnoreCase(inputFormat)){
			ttff = new FormatSCC();
		} else if ("XML".equalsIgnoreCase(inputFormat)){
			ttff = new FormatTTML();
		} else if ("ASS".equalsIgnoreCase(inputFormat)){
			ttff = new FormatASS();
		} else {
			throw new IllegalArgumentException("Unrecognized input format: "+inputFormat+" only [SRT,STL,SCC,XML,ASS] are possible");
		}
		tto = ttff.parseFile(fileName, is);
		captions = new ArrayList<InputTextSubCaption>(tto.captions.size()); 
		for (Caption caption : tto.captions.values()) {
			captions.add(new InputTextSubCaption(caption));
		}
		title = tto.title;
	}
	
	private final ArrayList<InputTextSubCaption> captions;
	public final String title;
	
	public Iterable<InputTextSubCaption> getCaptions() {
		return captions;
	}
}
