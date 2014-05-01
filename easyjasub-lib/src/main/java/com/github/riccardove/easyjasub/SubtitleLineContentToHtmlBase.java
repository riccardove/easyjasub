package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.util.List;

import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

abstract class SubtitleLineContentToHtmlBase {

	public abstract void appendItems(RendersnakeHtmlCanvas html,
			List<SubtitleItem> items) throws IOException;

	protected void appendElements(RendersnakeHtmlCanvas html,
			List<SubtitleItem.Inner> elements) throws IOException {
		for (SubtitleItem.Inner element : elements) {
			String kanji = element.getKanji();
			if (kanji != null) {
				html.spanKanji(kanji);
			} else {
				html.write(element.getText());
			}
		}
	}

	protected void appendText(RendersnakeHtmlCanvas html, String text)
			throws IOException {
		String trimmedText = text.replace('　', ' ').trim();
		html.write(trimmedText);
		if (trimmedText.length() < text.length()) {
			html.write("&thinsp;");
		}
	}

	// TODO: write furigara separately on each kanji section
	private void appendRubyElements(RendersnakeHtmlCanvas html,
			List<SubtitleItem.Inner> elements) throws IOException {
		for (SubtitleItem.Inner element : elements) {
			String kanji = element.getKanji();
			if (kanji != null) {
				html.ruby();
				html.spanKanji(kanji);
				html.rt(element.getText());
				html._ruby();
			} else {
				html.write(element.getText());
			}
		}
	}

}
