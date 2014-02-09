package com.github.riccardove.easyjasub;

import java.io.File;
import java.util.Set;

public interface EasyJaSubObserver {

	void onInputNihongoJTalkHtmlFileParseStart(File f);

	void onInputNihongoJTalkHtmlFileParseEnd(File f, Set<String> posset);

	void onReadJapaneseSubtitlesStart(File jaF);

	void onReadJapaneseSubtitlesEnd(File jaF);

	void onInputNihongoJTalkHtmlFileParseHiraganaDivEnd();

	void onInputNihongoJTalkHtmlFileParseTextareaEnd();

	void onInputNihongoJTalkHtmlFileParseTextareaStart();

	void onInputNihongoJTalkHtmlFileParseHiraganaDivStart();

	void onReadTranslatedSubtitlesStart(File jaF);

	void onReadTranslatedSubtitlesEnd(File jaF);

	void onWriteHtmlStart(File htmlFolder);

	void onWriteHtmlEnd(File htmlFolder);

	void onWriteImagesStart(String wkhtml, File htmlFolder, File bdnFolder);

	void onWriteImagesEnd(String wkhtml, File htmlFolder, File bdnFolder);

	void onWriteBdnXmlFileStart(File f);

	void onWriteBdnXmlFileEnd(File f);

	void onWriteIdxFileStart(File file);

	void onWriteIdxFileEnd(File file);

}
