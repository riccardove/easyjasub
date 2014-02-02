package com.github.riccardove.easyjasub;

import static org.rendersnake.HtmlAttributesFactory.class_;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;

public class FuriSubtitleLineItem extends RedSubtitleLineItem {

	public FuriSubtitleLineItem(SubtitleLine subtitleLine, String text, String furigana,
			String pos, String romaji)  
	{
		super(subtitleLine, text, pos, romaji);
		if (furigana == null) {
			throw new NullPointerException("Invalid furigana for text " + text);
		}
		this.furigana = furigana;
	}
	
	private String furigana; 
	
	@Override
	public String toString() {
		return super.toString() + "(" + furigana + ")";
	}
	
	public void renderOnTop(HtmlCanvas html) throws IOException {
		html.td(class_(pos)).content(furigana);
	}

}
