package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.PrintWriter;
import java.util.Set;

class EasyJaSubConsole implements EasyJaSubObserver {

	private PrintWriter outputStream;
	private PrintWriter errorStream;

	public EasyJaSubConsole(PrintWriter outputStream, PrintWriter errorStream) {
		this.outputStream = outputStream;
		this.errorStream = errorStream;
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseStart(File f) {
		outputStream.println("onInputNihongoJTalkHtmlFileParseStart");
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseEnd(File f, Set<String> posset) {
		outputStream.println("onInputNihongoJTalkHtmlFileParseEnd");
		outputStream.flush();
	}

	@Override
	public void onReadJapaneseSubtitlesStart(File jaF) {
		outputStream.println("onReadJapaneseSubtitlesStart");
		outputStream.flush();
	}

	@Override
	public void onReadJapaneseSubtitlesEnd(File jaF) {
		outputStream.println("onReadJapaneseSubtitlesEnd");
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseHiraganaDivEnd() {
		outputStream.println("onInputNihongoJTalkHtmlFileParseHiraganaDivEnd");
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseTextareaEnd() {
		outputStream.println("onInputNihongoJTalkHtmlFileParseTextareaEnd");
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseTextareaStart() {
		outputStream.println("onInputNihongoJTalkHtmlFileParseTextareaStart");
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseHiraganaDivStart() {
		outputStream.println("onInputNihongoJTalkHtmlFileParseHiraganaDivStart");
		outputStream.flush();
	}

	@Override
	public void onReadTranslatedSubtitlesStart(File jaF) {
		outputStream.println("onReadTranslatedSubtitlesStart");
		outputStream.flush();
	}

	@Override
	public void onReadTranslatedSubtitlesEnd(File jaF) {
		outputStream.println("onReadTranslatedSubtitlesEnd");
		outputStream.flush();
	}

	@Override
	public void onWriteHtmlStart(File htmlFolder) {
		outputStream.println("onWriteHtmlStart");
		outputStream.flush();
	}

	@Override
	public void onWriteHtmlEnd(File htmlFolder) {
		outputStream.println("onWriteHtmlEnd");
		outputStream.flush();
	}

	@Override
	public void onWriteImagesStart(String wkhtml, File htmlFolder,
			File bdnFolder) {
		outputStream.println("onWriteImagesStart");
		outputStream.flush();
	}

	@Override
	public void onWriteImagesEnd(String wkhtml, File htmlFolder, File bdnFolder) {
		outputStream.println("onWriteImagesEnd");
		outputStream.flush();
	}

	@Override
	public void onWriteBdnXmlFileStart(File f) {
		outputStream.println("onWriteBdnXmlFileStart");
		outputStream.flush();
	}

	@Override
	public void onWriteBdnXmlFileEnd(File f) {
		outputStream.println("onWriteBdnXmlFileEnd");
		outputStream.flush();
	}

	@Override
	public void onWriteIdxFileStart(File file) {
		outputStream.println("onWriteIdxFileStart");
		outputStream.flush();
	}

	@Override
	public void onWriteIdxFileEnd(File file) {
		outputStream.println("onWriteIdxFileEnd");
		outputStream.flush();
	}
}
