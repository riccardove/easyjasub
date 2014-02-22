package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;

public class FakeEasyJaSubObserver implements EasyJaSubObserver {

	@Override
	public void onInputNihongoJTalkHtmlFileParseStart(File f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseEnd(File f, Set<String> posset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadJapaneseSubtitlesStart(File jaF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadJapaneseSubtitlesEnd(File jaF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseHiraganaDivEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseTextareaEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseTextareaStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseHiraganaDivStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadTranslatedSubtitlesStart(File jaF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadTranslatedSubtitlesEnd(File jaF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteHtmlStart(File htmlFolder, String cssFileUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteHtmlEnd(File htmlFolder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteImagesStart(String wkhtml, File htmlFolder,
			File bdnFolder, int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteImagesEnd(String wkhtml, File htmlFolder, File bdnFolder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteBdnXmlFileStart(File f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteBdnXmlFileEnd(File f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteIdxFileEnd(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteOutputJapaneseTextFileStart(File txtFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteOutputJapaneseTextFileEnd(File txtFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteCssStart(File cssFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteCssEnd(File cssFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteImage(File pngFile, File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadJapaneseSubtitlesIOError(File jaF, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadJapaneseSubtitlesParseError(File jaF,
			InputTextSubException ex) throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseError(File f, SAXException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadTranslatedSubtitlesIOError(File jaF, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadTranslatedSubtitlesParseError(File jaF,
			InputTextSubException ex) throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteCssIOError(File cssFile, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteHtmlError(File htmlFolder, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteImagesWkhtmlError(File bdnFolder, Exception ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteImagesIOError(File bdnFolder, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteBdnXmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteOutputJapaneseTextFileIOError(File txtFile,
			IOException ex) throws EasyJaSubException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTranslatedSubDuplicated(String content, int mSeconds,
			int startTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlLine(SubtitleLine line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteOutputJapaneseTextFileSkipped(File txtFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseSkipped(File f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadTranslatedSubtitlesSkipped(File enF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteCssSkipped(File cssFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteHtmlFile(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteHtmlFileSkipped(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteBdnXmlFileSkipped(File f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteImageSkipped(File pngFile, File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteIdxFileStart(File file, File bdnFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteIdxFileSkipped(File file, File bdnFile) {
		// TODO Auto-generated method stub
		
	}

}
