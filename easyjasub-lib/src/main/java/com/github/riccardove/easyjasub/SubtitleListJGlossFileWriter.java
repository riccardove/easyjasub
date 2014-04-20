package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class SubtitleListJGlossFileWriter {
	public void write(SubtitleList s, File file) throws IOException,
			FileNotFoundException {
		f = new EasyJaSubXmlWriter(file);

		groupOpen(SubtitleListJGlossElement.jgloss);
		groupOpen(SubtitleListJGlossElement.head);
		tag(SubtitleListJGlossElement.title, s.getTitle());
		tag(SubtitleListJGlossElement.generator, EasyJaSubProperty.getName()
				+ " " + EasyJaSubProperty.getVersion());
		groupClose(SubtitleListJGlossElement.head);

		groupOpen(SubtitleListJGlossElement.body);
		groupOpen(SubtitleListJGlossElement.div);

		for (SubtitleLine l : s) {
			if (l.getItems() != null) {
				f.printIndent();
				f.tagOpenInline(SubtitleListJGlossElement.p.toString());
				for (SubtitleItem item : l.getItems()) {
					String reading = item.getFurigana();
					boolean romajiReading = false;
					if (reading == null) {
						reading = item.getRomaji();
						romajiReading = true;
					}
					if (reading != null) {
						String attribute = item.getDictionary();
						if (attribute == null && !romajiReading) {
							attribute = item.getRomaji();
						}
						if (attribute == null) {
							f.tagOpenInline(SubtitleListJGlossElement.anno
									.toString());
						} else {
							f.tagWithAttributeOpenInline(
									SubtitleListJGlossElement.anno.toString(),
									"tr", attribute);
						}
						rbase(item.getText(), reading);
						f.tagCloseInline(SubtitleListJGlossElement.anno
								.toString());
					} else {
						f.write(item.getText() + " ");
					}
				}
				f.tagCloseInline(SubtitleListJGlossElement.p.toString());
				f.writeln();
			} else {
				tag(SubtitleListJGlossElement.p, l.getSubText());
			}
		}
		groupClose(SubtitleListJGlossElement.div);
		groupClose(SubtitleListJGlossElement.body);
		groupClose(SubtitleListJGlossElement.jgloss);
		f.close();
	}

	private EasyJaSubXmlWriter f;

	private void groupOpen(SubtitleListJGlossElement name) throws IOException {
		f.groupOpen(name.toString());
	}

	private void groupClose(SubtitleListJGlossElement name) throws IOException {
		f.groupClose(name.toString());
	}

	private void tag(SubtitleListJGlossElement name, String content)
			throws IOException {
		f.tag(name.toString(), content);
	}

	private void rbase(String content, String furigana) throws IOException {
		f.tagWithAttributeInline(SubtitleListJGlossElement.rbase.toString(),
				"re",
				furigana, content);
	}

}
