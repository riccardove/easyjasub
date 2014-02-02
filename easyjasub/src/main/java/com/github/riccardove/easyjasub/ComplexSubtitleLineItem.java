package com.github.riccardove.easyjasub;

import static org.rendersnake.HtmlAttributesFactory.class_;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;

public class ComplexSubtitleLineItem extends DictSubtitleLineItem {

	public ComplexSubtitleLineItem(SubtitleLine subtitleLine, String text, String furigana,
			String dictionaryInfo, 
			String pos, String romaji)  
	{
		super(subtitleLine, text, dictionaryInfo, pos, romaji);
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
		html.td(class_(posc)).content(furigana);
	}
	
	public void renderOnCenter(HtmlCanvas html) throws IOException {
		html.td(class_(posc));
	
		String littleChars = null;
		for (int i = 0; i < text.length(); i++){
		    char c = text.charAt(i);        
		    if (!JapaneseChar.isLittle(c)) {
		    	if (littleChars == null) {
		    		littleChars = Character.toString(c);
		    	}
		    	else {
		    		littleChars +=  c;
		    	}
		    }
		    else {
		    	if (littleChars!=null) {
		    		html.span(class_("kk")).content(littleChars);
		    		littleChars = null;
		    	}
		    	html.write(c);
		    }
		}		
    	if (littleChars!=null) {
    		html.span(class_("kk")).content(littleChars);
    		littleChars = null;
    	}

		html._td();
	}
	
	public void renderOn(HtmlCanvas html) throws IOException {
		
		String kanji = text;
		
		html.table().tr().td(class_(pos)).content(furigana)._tr()
		.tr().td(class_(pos));
		
		html.content(kanji)._tr()
		.tr().td(class_("r")).content(romaji)._tr()._table();
		//html.ruby().write(text).rt().content(furigana)._ruby();
	}

}
