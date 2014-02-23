package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;

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

	void onWriteHtmlStart(File htmlFolder, String cssFileUrl);

	void onWriteHtmlEnd(File htmlFolder);

	void onWriteImagesStart(String wkhtml, File htmlFolder, File bdnFolder, int width);

	void onWriteImagesEnd(String wkhtml, File htmlFolder, File bdnFolder);

	void onWriteBdnXmlFileStart(File f);

	void onWriteBdnXmlFileEnd(File f);

	void onWriteIdxFileStart(File file, File bdnFile);

	void onWriteIdxFileEnd(File file);

	void onWriteOutputJapaneseTextFileStart(File txtFile);

	void onWriteOutputJapaneseTextFileEnd(File txtFile);

	void onWriteCssStart(File cssFile);

	void onWriteCssEnd(File cssFile);

	void onWriteImage(File pngFile, File file);

	void onReadJapaneseSubtitlesIOError(File jaF, IOException ex) throws EasyJaSubException;

	void onReadJapaneseSubtitlesParseError(File jaF, InputTextSubException ex) throws EasyJaSubException;

	void onInputNihongoJTalkHtmlFileIOError(File f, IOException ex) throws EasyJaSubException;

	void onInputNihongoJTalkHtmlFileParseError(File f, SAXException ex) throws EasyJaSubException;

	void onReadTranslatedSubtitlesIOError(File jaF, IOException ex) throws EasyJaSubException;

	void onReadTranslatedSubtitlesParseError(File jaF, InputTextSubException ex) throws EasyJaSubException;

	void onWriteCssIOError(File cssFile, IOException ex) throws EasyJaSubException;

	void onWriteHtmlError(File htmlFolder, IOException ex) throws EasyJaSubException;

	void onWriteImagesWkhtmlError(File bdnFolder, Exception ex) throws EasyJaSubException;

	void onWriteImagesIOError(File bdnFolder, IOException ex) throws EasyJaSubException;

	void onWriteBdnXmlFileIOError(File f, IOException ex) throws EasyJaSubException;

	void onWriteOutputJapaneseTextFileIOError(File txtFile, IOException ex) throws EasyJaSubException ;

	void onTranslatedSubDuplicated(String content, int mSeconds, int startTime);

	void onInputNihongoJTalkHtmlLine(SubtitleLine line);

	void onWriteOutputJapaneseTextFileSkipped(File txtFile);

	void onInputNihongoJTalkHtmlFileParseSkipped(File f);

	void onReadTranslatedSubtitlesSkipped(File enF);

	void onWriteCssSkipped(File cssFile);

	void onWriteHtmlFile(File file);

	void onWriteHtmlFileSkipped(File file);

	void onWriteBdnXmlFileSkipped(File f);

	void onWriteImageSkipped(File pngFile, File file);

	void onWriteIdxFileSkipped(File file, File bdnFile);

	void onReadJapaneseSubtitlesSkipped(File jaF);
}
