package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.rendersnake.HtmlCanvas;

import static org.rendersnake.HtmlAttributesFactory.class_;


public class RedSubtitleLineItem  extends TextSubtitleLineItem {

	public RedSubtitleLineItem(SubtitleLine subtitleLine, String text, 
			String pos, String romaji)  
	{
		super(subtitleLine, text);
		if (pos == null) {
			throw new NullPointerException("Invalid pos for text " + text);
		}
		if (romaji == null) {
			throw new NullPointerException("Invalid romaji for text " + text);
		}
		this.pos= pos;
		this.romaji= romaji;
		posc = PosRegex.matcher(pos).replaceAll("").replace(" ", "");
		PosSet.add(posc);
		gpos = Enum.valueOf(Grammar.class, posc);
	}
	
	private static final Pattern PosRegex = Pattern.compile("[\\.()-]");
	public static final HashSet<String> PosSet = new HashSet<String>();
	protected final Grammar gpos;
	protected String pos;
	protected String romaji;
	protected String posc;

	public void renderOn(HtmlCanvas html) throws IOException {
		html.span(class_(posc)).write(text)._span();
	}
	
	public void renderOnBottom(HtmlCanvas html) throws IOException {
		html.td(class_(posc)).content(romaji);
	}
	
	@Override
	public void renderOnCenter(HtmlCanvas html) throws IOException {
		renderText(posc, html);
	}	
	
	@Override
	public String toString() {
		return super.toString() + "{" + pos + "}(" + romaji + ")";
	}
}
