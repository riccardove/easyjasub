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

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.inputnihongojtalk.NihongoJTalkSubtitleLine;
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
			File bdnFolder, int width) {
		outputStream.println("onWriteImagesStart " + bdnFolder.getAbsolutePath() + " " + wkhtml + " " + width);
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
	public void onWriteIdxFileStart(File file, File bdnFile) {
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

	@Override
	public void onWriteImage(File pngFile, File file) {
		outputStream.println("writing image " + pngFile.getAbsolutePath());
		outputStream.flush();
	}

	@Override
	public void onReadJapaneseSubtitlesIOError(File jaF, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error reading japanese subtitles file " + jaF.getAbsolutePath() + ": " + ex.getLocalizedMessage());
	}

	@Override
	public void onReadJapaneseSubtitlesParseError(File jaF,
			InputTextSubException ex) throws EasyJaSubException {
		throw new EasyJaSubException("Error parsing japanese subtitles file " + jaF.getAbsolutePath() + " content: " + ex.getLocalizedMessage());
	}

	@Override
	public void onInputNihongoJTalkHtmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error reading nihongo.j-talk file " + f.getAbsolutePath() + ": " + ex.getLocalizedMessage());
	}

	@Override
	public void onInputNihongoJTalkHtmlFileParseError(File f, SAXException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error parsing nihongo.j-talk file " + f.getAbsolutePath() + "content: " + ex.getLocalizedMessage());
	}

	@Override
	public void onReadTranslatedSubtitlesIOError(File jaF, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error reading translated subtitles file " + jaF.getAbsolutePath() + ": " + ex.getLocalizedMessage());
	}

	@Override
	public void onReadTranslatedSubtitlesParseError(File jaF,
			InputTextSubException ex) throws EasyJaSubException {
		throw new EasyJaSubException("Error parsing translated subtitles file " + jaF.getAbsolutePath() + " content: " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteCssIOError(File cssFile, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error writing css file " + cssFile.getAbsolutePath() + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteHtmlError(File htmlFolder, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error writing html file on folder " + htmlFolder.getAbsolutePath() + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteImagesWkhtmlError(File bdnFolder, Exception ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error invoking wkhtmltoimage to write files on folder " + bdnFolder.getAbsolutePath() + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteImagesIOError(File bdnFolder, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error writing image files on folder " + bdnFolder.getAbsolutePath() + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteBdnXmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {
		throw new EasyJaSubException("Error writing BDMXML file " + f.getAbsolutePath() + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onWriteOutputJapaneseTextFileIOError(File txtFile,
			IOException ex) throws EasyJaSubException {
		throw new EasyJaSubException("Error writing japanese text file " + txtFile.getAbsolutePath() + " : " + ex.getLocalizedMessage());
	}

	@Override
	public void onTranslatedSubDuplicated(String content, int mSeconds,
			int startTime) {
		outputStream.println("Duplicated translation caption " + content + " starting at " + mSeconds + " at " + startTime);
		outputStream.flush();
	}

	@Override
	public void onInputNihongoJTalkHtmlLine(NihongoJTalkSubtitleLine line) {
		outputStream.println("Line: " + line.toString());
		outputStream.flush();
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
	public void onWriteIdxFileSkipped(File file, File bdnFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadJapaneseSubtitlesSkipped(File jaF) {
		// TODO Auto-generated method stub
		
	}
}
