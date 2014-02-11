package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.PrintWriter;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

class EasyJaSubConsole implements EasyJaSubObserver {

	private PrintWriter outputStream;
	private PrintWriter errorStream;

	public EasyJaSubConsole(PrintWriter outputStream, PrintWriter errorStream) {
		this.outputStream = outputStream;
		this.errorStream = errorStream;
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseStart(File file) {
		outputStream.println("onInputNihongoJTalkHtmlFileParseStart " + file.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseEnd(File file, Set<String> posset) {
		outputStream.println("onInputNihongoJTalkHtmlFileParseEnd " + file.getAbsolutePath() + " " 
				+ StringUtils.join(posset, ","));
		outputStream.flush();
	}

	@Override
	public void onReadJapaneseSubtitlesStart(File file) {
		outputStream.println("onReadJapaneseSubtitlesStart " + file.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onReadJapaneseSubtitlesEnd(File file) {
		outputStream.println("onReadJapaneseSubtitlesEnd " + file.getAbsolutePath());
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
	public void onReadTranslatedSubtitlesStart(File file) {
		outputStream.println("onReadTranslatedSubtitlesStart " + file.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onReadTranslatedSubtitlesEnd(File file) {
		outputStream.println("onReadTranslatedSubtitlesEnd " + file.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteHtmlStart(File directory, String cssFileUrl) {
		outputStream.println("onWriteHtmlStart " + directory.getAbsolutePath() + " " + cssFileUrl);
		outputStream.flush();
	}

	@Override
	public void onWriteHtmlEnd(File directory) {
		outputStream.println("onWriteHtmlEnd " + directory.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteImagesStart(String wkhtml, File htmlFolder,
			File bdnFolder) {
		outputStream.println("onWriteImagesStart " + bdnFolder.getAbsolutePath() + " " + wkhtml);
		outputStream.flush();
	}

	@Override
	public void onWriteImagesEnd(String wkhtml, File htmlFolder, File bdnFolder) {
		outputStream.println("onWriteImagesEnd " + bdnFolder.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteBdnXmlFileStart(File file) {
		outputStream.println("onWriteBdnXmlFileStart " + file.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteBdnXmlFileEnd(File file) {
		outputStream.println("onWriteBdnXmlFileEnd " + file.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteIdxFileStart(File file) {
		outputStream.println("onWriteIdxFileStart " + file.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteIdxFileEnd(File file) {
		outputStream.println("onWriteIdxFileEnd " + file.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteOutputJapaneseTextFileStart(File txtFile) {
		outputStream.println("onWriteOutputJapaneseTextFileStart " + txtFile.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteOutputJapaneseTextFileEnd(File txtFile) {
		outputStream.println("onWriteOutputJapaneseTextFileEnd " + txtFile.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteCssStart(File cssFile) {
		outputStream.println("onWriteCssStart " + cssFile.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onWriteCssEnd(File cssFile) {
		outputStream.println("onWriteCssStart " + cssFile.getAbsolutePath());
		outputStream.flush();
	}
}
