package com.github.riccardove.easyjasub;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;
import static org.rendersnake.HtmlAttributesFactory.class_;

public class TextSubtitleLineItem extends SubtitleLineItem {
	protected String text;
	
	public TextSubtitleLineItem(SubtitleLine subtitleLine, String text) {
		super(subtitleLine);
		if (text == null) {
			throw new NullPointerException("Invalid text");
		}
		this.text = text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public void renderOn(HtmlCanvas html) throws IOException {
		html.write(text);
	}

	public void renderOnCenter(HtmlCanvas html) throws IOException {
		renderText(null, html);
	}
	
	protected void renderText(String styleClass, HtmlCanvas html) throws IOException {
		String trimmedText = text.replace('　', ' ').trim();
		if (styleClass == null) {
			html.td();
		}
		else {
			html.td(class_(styleClass));
		}
		html.write(trimmedText, false);
		if (trimmedText.length() < text.length()) {
			html.write("&thinsp;", false);
		}
		html._td();
	}
}
