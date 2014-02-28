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

	@Override
	public void onInputNihongoJTalkHtmlFileParseStart(File file) {
		outputStream.println("onInputNihongoJTalkHtmlFileParseStart " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseEnd(File file, Set<String> posset) {
		outputStream.println("onInputNihongoJTalkHtmlFileParseEnd " + toString(file) + " " 
				+ CommonsLangStringUtils.join(posset, ","));
		outputStream.flush();
	}

	@Override
	public void onReadJapaneseSubtitlesStart(File file) {
		outputStream.println("onReadJapaneseSubtitlesStart " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onReadJapaneseSubtitlesEnd(File file) {
		outputStream.println("onReadJapaneseSubtitlesEnd " + toString(file));
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
		outputStream.println("onReadTranslatedSubtitlesStart " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onReadTranslatedSubtitlesEnd(File file) {
		outputStream.println("onReadTranslatedSubtitlesEnd " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteHtmlStart(File directory, String cssFileUrl) {
		outputStream.println("onWriteHtmlStart " + toString(directory) + " " + cssFileUrl);
		outputStream.flush();
	}

	@Override
	public void onWriteHtmlEnd(File directory) {
		outputStream.println("onWriteHtmlEnd " + toString(directory));
		outputStream.flush();
	}

	@Override
	public void onWriteImagesStart(String wkhtml, File htmlFolder,
			File bdnFolder, int width) {
		outputStream.println("onWriteImagesStart " + toString(bdnFolder) + " " + wkhtml + " " + width);
		outputStream.flush();
	}

	@Override
	public void onWriteImagesEnd(String wkhtml, File htmlFolder, File bdnFolder) {
		outputStream.println("onWriteImagesEnd " + toString(bdnFolder));
		outputStream.flush();
	}

	@Override
	public void onWriteBdnXmlFileStart(File file) {
		outputStream.println("onWriteBdnXmlFileStart " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteBdnXmlFileEnd(File file) {
		outputStream.println("onWriteBdnXmlFileEnd " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteIdxFileStart(File file, File bdnFile) {
		outputStream.println("onWriteIdxFileStart " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteIdxFileEnd(File file) {
		outputStream.println("onWriteIdxFileEnd " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteOutputJapaneseTextFileStart(File file) {
		outputStream.println("onWriteOutputJapaneseTextFileStart " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteOutputJapaneseTextFileEnd(File file) {
		outputStream.println("onWriteOutputJapaneseTextFileEnd " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteCssStart(File file) {
		outputStream.println("onWriteCssStart " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteCssEnd(File file) {
		outputStream.println("onWriteCssEnd " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteImage(File pngFile, File file) {
		outputStream.println("writing image " + toString(pngFile) + " " + toString(file));
		outputStream.flush();
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
		outputStream.println("Duplicated translation caption " + content + " starting at " + mSeconds + " at " + startTime);
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlLine(String line) {
		outputStream.println("Line: " + line.toString());
		outputStream.flush();
	}

	@Override
	public void onWriteOutputJapaneseTextFileSkipped(File file) {
		outputStream.println("onWriteOutputJapaneseTextFileSkipped " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseSkipped(File file) {
		outputStream.println("onInputNihongoJTalkHtmlFileParseSkipped " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onReadTranslatedSubtitlesSkipped(File file) {
		outputStream.println("onReadTranslatedSubtitlesSkipped " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteCssSkipped(File file) {
		outputStream.println("onWriteCssSkipped " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteHtmlFile(File file) {
		outputStream.println("onWriteHtmlFile " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteHtmlFileSkipped(File file) {
		outputStream.println("onWriteHtmlFileSkipped " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteBdnXmlFileSkipped(File file) {
		outputStream.println("onWriteBdnXmlFileSkipped " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteImageSkipped(File pngFile, File file) {
		outputStream.println("onWriteImageSkipped " + toString(pngFile) + " " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onWriteIdxFileSkipped(File file, File bdnFile) {
		outputStream.println("onWriteIdxFileSkipped " + toString(file)+ " " + toString(bdnFile));
		outputStream.flush();
	}

	@Override
	public void onReadJapaneseSubtitlesSkipped(File file) {
		outputStream.println("onReadJapaneseSubtitlesSkipped " + toString(file));
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlLineParseSkipped(int index) {
		outputStream.println("onReadJapaneseSubtitlesSkipped " + index);
		outputStream.flush();
	}
	
	private static String toString(File file) {
		return file != null ? toString(file) : "<null>";
	}
}
