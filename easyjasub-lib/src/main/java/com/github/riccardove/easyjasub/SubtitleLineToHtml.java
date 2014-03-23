package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.util.List;

import com.github.riccardove.easyjasub.rendersnake.RendersnakeHtmlCanvas;

class SubtitleLineToHtml {

	private final boolean hasWkhtml;
	private final boolean hasFurigana;
	private final boolean hasDictionary;
	private final boolean hasKanji;
	private final boolean hasRomaji;
	private final boolean showTranslation;

	public SubtitleLineToHtml(boolean hasWkhtml, boolean hasFurigana,
			boolean hasRomaji, boolean hasDictionary, boolean hasKanji,
			boolean showTranslation) {
		this.hasWkhtml = hasWkhtml;
		this.hasFurigana = hasFurigana;
		this.hasRomaji = hasRomaji;
		this.hasDictionary = hasDictionary;
		this.hasKanji = hasKanji;
		this.showTranslation = showTranslation;
	}

	public String toHtml(SubtitleLine s, String cssFileRef) throws IOException {
		RendersnakeHtmlCanvas html = new RendersnakeHtmlCanvas(
				EasyJaSubWriter.Newline);
		html.header(cssFileRef, EasyJaSubCharset.CHARSETSTR);

		List<SubtitleItem> items = s.getItems();
		if (items != null) {
			html.newline();
			if (hasWkhtml && canUseRuby()) {
				appendRubyItems(html, items);
			} else {
				appendItems(html, items);
			}
		} else {
			if (s.getSubText() != null) {
				appendSubText(html, s);
			}
		}
		if (showTranslation) {
			appendTranslation(html, s);
		}
		if (items != null) {
			appendComment(html, items);
		}

		html.footer();

		return html.toString();
	}

	private void appendRubyItems(RendersnakeHtmlCanvas html,
			List<SubtitleItem> items) throws IOException {
		html.p();
		html.newline();
		for (SubtitleItem item : items) {
			if (item.getText() != null) {
				appendRuby(html, item);
			}
		}
		html._p();
	}

	private void appendRuby(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		List<SubtitleItem.Inner> elements = item.getElements();
		String text = item.getText();
		if (elements == null) {
			appendText(html, text);
		} else {
			html.ruby(item.getGrammarElement());
			if (hasFurigana) {
				// TODO: appendRubyElements(html, elements);
				appendElements(html, elements);
				html.rt(item.getFurigana());
			} else if (hasRomaji) {
				appendElements(html, elements);
				html.rt(item.getRomaji());
			} else if (hasDictionary) {
				appendElements(html, elements);
				html.rt(item.getDictionary());
			}
			html._ruby();
		}
	}

	private boolean canUseRuby() {
		if (hasKanji) {
			if (hasRomaji) {
				return !hasFurigana && !hasDictionary;
			} else if (hasFurigana) {
				return !hasDictionary;
			}
			return true;
		}
		return false; // TODO
	}

	private void appendComment(RendersnakeHtmlCanvas html,
			List<SubtitleItem> items) throws IOException {
		boolean hasComment = false;
		for (SubtitleItem item : items) {
			String comment = item.getComment();
			if (comment != null) {
				if (!hasComment) {
					hasComment = true;
					html.comment();
				}
				html.write(comment);
				html.newline();
			}
		}
		if (hasComment) {
			html._comment();
		}
	}

	private void appendTranslation(RendersnakeHtmlCanvas html, SubtitleLine line)
			throws IOException {
		String translation = line.getTranslation();
		if (translation != null) {
			html.newline();
			html.p(translation);
			html.newline();
		}
	}

	private void appendSubText(RendersnakeHtmlCanvas html, SubtitleLine line)
			throws IOException {
		html.table("center", line.getSubText());
		html.newline();
	}

	private void appendItems(RendersnakeHtmlCanvas html,
			List<SubtitleItem> items) throws IOException {
		html.table();
		html.newline();
		if (hasFurigana && hasKanji) {
			html.tr("top");
			for (SubtitleItem item : items) {
				html.space();
				renderFurigana(html, item);
				html.newline();
			}
			html._tr();
			html.newline();
		}
		html.tr("center");
		for (SubtitleItem item : items) {
			renderOnCenter(html, item, hasKanji);
			html.newline();
		}
		html._tr();
		html.newline();
		if (hasRomaji) {
			html.tr("bottom");
			for (SubtitleItem item : items) {
				html.write("  ");
				renderRomaji(html, item);
				html.newline();
			}
			html._tr();
			html.newline();
		}
		if (hasDictionary) {
			html.tr("translation");
			for (SubtitleItem item : items) {
				html.write("  ");
				renderDictionary(html, item);
				html.newline();
			}
			html._tr();
			html.newline();
		}
		html._table();
	}

	private void renderFurigana(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		String furigana = item.getFurigana();
		if (furigana == null) {
			html.tdEmpty();
		} else {
			html.td(item.getGrammarElement(), furigana);
		}

	}

	private void renderOnCenter(RendersnakeHtmlCanvas html, SubtitleItem item,
			boolean showKanji) throws IOException {
		html.write("  ");
		List<SubtitleItem.Inner> elements = item.getElements();
		String text = item.getText();
		if (text == null) {
			html.tdEmpty();
		} else if (elements == null) {
			String styleClass = item.getGrammarElement();
			if (styleClass == null) {
				html.td();
			} else {
				html.tdOpen(styleClass);
			}
			appendText(html, text);
			html._td();
		} else if (showKanji) {
			html.tdOpen(item.getGrammarElement());
			appendElements(html, elements);
			html._td();
		} else {
			html.td(item.getGrammarElement(), item.getFurigana());
		}
	}

	private void appendText(RendersnakeHtmlCanvas html, String text)
			throws IOException {
		String trimmedText = text.replace('　', ' ').trim();
		html.write(trimmedText);
		if (trimmedText.length() < text.length()) {
			html.write("&thinsp;");
		}
	}

	private void appendRubyElements(RendersnakeHtmlCanvas html,
			List<SubtitleItem.Inner> elements) throws IOException {
		for (SubtitleItem.Inner element : elements) {
			String kanji = element.getKanji();
			if (kanji != null) {
				html.ruby();
				html.span(kanji);
				html.rt(element.getText());
				html._ruby();
			} else {
				html.write(element.getText());
			}
		}
	}

	private void appendElements(RendersnakeHtmlCanvas html,
			List<SubtitleItem.Inner> elements) throws IOException {
		for (SubtitleItem.Inner element : elements) {
			String kanji = element.getKanji();
			if (kanji != null) {
				html.span(kanji);
			} else {
				html.write(element.getText());
			}
		}
	}

	private void renderRomaji(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		String romaji = item.getRomaji();
		if (romaji == null) {
			html.tdEmpty();
		} else {
			html.td(item.getGrammarElement(), romaji);
		}

	}

	private void renderDictionary(RendersnakeHtmlCanvas html, SubtitleItem item)
			throws IOException {
		String dict = item.getDictionary();
		if (dict != null) {
			html.td(dict);
		} else {
			html.tdEmpty();
		}
	}

}
