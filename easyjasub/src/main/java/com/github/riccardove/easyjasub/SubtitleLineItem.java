package com.github.riccardove.easyjasub;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

public abstract class SubtitleLineItem  implements Renderable {

	protected final SubtitleLine subtitleLine;

	protected SubtitleLineItem(SubtitleLine subtitleLine) {
		this.subtitleLine = subtitleLine;
		
	}
	
	protected int index;

	public void renderOnTop(HtmlCanvas html) throws IOException {
		html.td()._td();
	}
	public void renderOnCenter(HtmlCanvas html) throws IOException {
		html.td()._td();
	}
	public void renderOnBottom(HtmlCanvas html) throws IOException {
		html.td()._td();
	}

	public void renderOnLast(HtmlCanvas html) throws IOException {
		html.td()._td();
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getComment() {
		return null;
	}

}
