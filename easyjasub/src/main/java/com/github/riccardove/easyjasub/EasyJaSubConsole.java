package com.github.riccardove.easyjasub;

/*
 * #%L
 * easyjasub
 * %%
 * Copyright (C) 2014 Riccardo Vestrini
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;

class EasyJaSubConsole implements EasyJaSubObserver {

	private final PrintWriter outputStream;
	private final PrintWriter errorStream;

	public EasyJaSubConsole(PrintWriter outputStream, PrintWriter errorStream) {
		this.outputStream = outputStream;
		this.errorStream = errorStream;
	}

	private void println(String text) {
		outputStream.println(text);
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseStart(File file) {
		println("onInputNihongoJTalkHtmlFileParseStart " + toString(file));
		flushOutput();
	}

	private void flushOutput() {
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseEnd(File file, Set<String> posset) {
		println("onInputNihongoJTalkHtmlFileParseEnd " + toString(file) + " "
				+ CommonsLangStringUtils.join(posset, ","));
		flushOutput();
	}

	@Override
	public void onReadJapaneseSubtitlesStart(File file) {
		println("onReadJapaneseSubtitlesStart " + toString(file));
		flushOutput();
	}

	@Override
	public void onReadJapaneseSubtitlesEnd(File file) {
		println("onReadJapaneseSubtitlesEnd " + toString(file));
		flushOutput();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseHiraganaDivEnd() {
		println("onInputNihongoJTalkHtmlFileParseHiraganaDivEnd");
		flushOutput();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseTextareaEnd() {
		println("onInputNihongoJTalkHtmlFileParseTextareaEnd");
		flushOutput();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseTextareaStart() {
		println("onInputNihongoJTalkHtmlFileParseTextareaStart");
		flushOutput();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseHiraganaDivStart() {
		println("onInputNihongoJTalkHtmlFileParseHiraganaDivStart");
		flushOutput();
	}

	@Override
	public void onReadTranslatedSubtitlesStart(File file) {
		println("onReadTranslatedSubtitlesStart " + toString(file));
		flushOutput();
	}

	@Override
	public void onReadTranslatedSubtitlesEnd(File file) {
		println("onReadTranslatedSubtitlesEnd " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteHtmlStart(File directory, String cssFileUrl) {
		println("onWriteHtmlStart " + toString(directory) + " " + cssFileUrl);
		flushOutput();
	}

	@Override
	public void onWriteHtmlEnd(File directory) {
		println("onWriteHtmlEnd " + toString(directory));
		flushOutput();
	}

	@Override
	public void onWriteImagesStart(String wkhtml, File htmlFolder,
			File bdnFolder, int width) {
		println("onWriteImagesStart " + toString(bdnFolder) + " " + wkhtml
				+ " " + width);
		flushOutput();
	}

	@Override
	public void onWriteImagesEnd(String wkhtml, File htmlFolder, File bdnFolder) {
		println("onWriteImagesEnd " + toString(bdnFolder));
		flushOutput();
	}

	@Override
	public void onWriteBdnXmlFileStart(File file) {
		println("onWriteBdnXmlFileStart " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteBdnXmlFileEnd(File file) {
		println("onWriteBdnXmlFileEnd " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteIdxFileStart(File file, File bdnFile) {
		println("onWriteIdxFileStart " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteIdxFileEnd(File file) {
		println("onWriteIdxFileEnd " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteOutputJapaneseTextFileStart(File file) {
		println("onWriteOutputJapaneseTextFileStart " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteOutputJapaneseTextFileEnd(File file) {
		println("onWriteOutputJapaneseTextFileEnd " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteCssStart(File file) {
		println("onWriteCssStart " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteCssEnd(File file) {
		println("onWriteCssEnd " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteImage(File pngFile, File file) {
		println("writing image " + toString(pngFile) + " " + toString(file));
		flushOutput();
	}

	@Override
	public void onReadJapaneseSubtitlesIOError(File file, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error reading japanese subtitles file " + toString(file) + ": " + ex.getLocalizedMessage());
	}

	@Override
	public void onReadJapaneseSubtitlesParseError(File file,
			InputTextSubException ex) throws EasyJaSubException {
		throw new EasyJaSubException("Error parsing japanese subtitles file " + toString(file) + " content: " + ex.getLocalizedMessage());
	}

	@Override
	public void onInputNihongoJTalkHtmlFileIOError(File file, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error reading nihongo.j-talk file " + toString(file) + ": " + ex.getLocalizedMessage());
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseError(File file, SAXException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error parsing nihongo.j-talk file " + toString(file) + "content: " + ex.getLocalizedMessage());
	}

	@Override
	public void onReadTranslatedSubtitlesIOError(File file, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error reading translated subtitles file " + toString(file) + ": " + ex.getLocalizedMessage());
	}

	@Override
	public void onReadTranslatedSubtitlesParseError(File file,
			InputTextSubException ex) throws EasyJaSubException {
		throw new EasyJaSubException("Error parsing translated subtitles file " + toString(file) + " content: " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteCssIOError(File file, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error writing css file " + toString(file) + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteHtmlError(File htmlFolder, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error writing html file on folder " + toString(htmlFolder) + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteImagesWkhtmlError(File bdnFolder, Exception ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error invoking wkhtmltoimage to write files on folder " + toString(bdnFolder) + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteImagesIOError(File bdnFolder, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error writing image files on folder " + toString(bdnFolder) + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteBdnXmlFileIOError(File file, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error writing BDMXML file " + toString(file) + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteOutputJapaneseTextFileIOError(File file,
			IOException ex) throws EasyJaSubException {
		throw new EasyJaSubException("Error writing japanese text file " + toString(file) + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onTranslatedSubDuplicated(String content, int mSeconds,
			int startTime) {
		println("Duplicated translation caption " + content + " starting at "
				+ mSeconds + " at " + startTime);
		flushOutput();
	}

	@Override
	public void onInputNihongoJTalkHtmlLine(String line) {
		println("Line: " + toString(line));
		flushOutput();
	}

	@Override
	public void onWriteOutputJapaneseTextFileSkipped(File file) {
		println("onWriteOutputJapaneseTextFileSkipped " + toString(file));
		flushOutput();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseSkipped(File file) {
		println("onInputNihongoJTalkHtmlFileParseSkipped " + toString(file));
		flushOutput();
	}

	@Override
	public void onReadTranslatedSubtitlesSkipped(File file) {
		println("onReadTranslatedSubtitlesSkipped " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteCssSkipped(File file) {
		println("onWriteCssSkipped " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteHtmlFile(File file) {
		println("onWriteHtmlFile " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteHtmlFileSkipped(File file) {
		println("onWriteHtmlFileSkipped " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteBdnXmlFileSkipped(File file) {
		println("onWriteBdnXmlFileSkipped " + toString(file));
		flushOutput();
	}

	@Override
	public void onWriteImageSkipped(File pngFile, File file) {
		println("onWriteImageSkipped " + toString(pngFile) + " "
				+ toString(file));
		flushOutput();
	}

	@Override
	public void onWriteIdxFileSkipped(File file, File bdnFile) {
		println("onWriteIdxFileSkipped " + toString(file) + " "
				+ toString(bdnFile));
		flushOutput();
	}

	@Override
	public void onReadJapaneseSubtitlesSkipped(File file) {
		println("onReadJapaneseSubtitlesSkipped " + toString(file));
		flushOutput();
	}
	
	private static String toString(File file) {
		return file != null ? file.getAbsolutePath() : "<null>";
	}
	
	private static String toString(String text) {
		return text != null ? text : "<null>";
	}

	@Override
	public void onInputNihongoJTalkHtmlLineParseSkipped(List<Integer> nLines,
			List<Integer> subLines) {
		println("onInputNihongoJTalkHtmlLineParseSkipped "
				+ CommonsLangStringUtils.join(nLines, ",") + " -- "
				+ CommonsLangStringUtils.join(subLines, ","));
		flushOutput();
	}
}
