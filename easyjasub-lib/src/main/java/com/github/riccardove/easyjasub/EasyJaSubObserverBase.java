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
 *      http:
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
import java.util.List;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.dictionary.EasyJaSubDictionary;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;

public class EasyJaSubObserverBase implements EasyJaSubObserver {

	@Override
	public void onReadJapaneseSubtitlesStart(File jaF) {
		

	}

	@Override
	public void onReadJapaneseSubtitlesEnd(File jaF) {
		

	}

	@Override
	public void onReadTranslatedSubtitlesStart(File jaF) {
		

	}

	@Override
	public void onReadTranslatedSubtitlesEnd(File jaF) {
		

	}

	@Override
	public void onWriteHtmlStart(File htmlFolder, File cssFileUrl) {
		

	}

	@Override
	public void onWriteHtmlEnd(File htmlFolder) {
		

	}

	@Override
	public void onWriteImagesStart(String wkhtml, File htmlFolder,
			File bdnFolder, int width) {
		

	}

	@Override
	public void onWriteImagesEnd(String wkhtml, File htmlFolder, File bdnFolder) {
		

	}

	@Override
	public void onWriteBdnXmlFileStart(File f) {
		

	}

	@Override
	public void onWriteBdnXmlFileEnd(File f) {
		

	}

	@Override
	public void onWriteIdxFileEnd(File file) {
		

	}

	@Override
	public void onWriteOutputJapaneseTextFileStart(File txtFile) {
		

	}

	@Override
	public void onWriteOutputJapaneseTextFileEnd(File txtFile) {
		

	}

	@Override
	public void onWriteCssStart(File cssFile) {
		

	}

	@Override
	public void onWriteCssEnd(File cssFile) {
		

	}

	@Override
	public void onWriteImage(File pngFile, File file) {
		

	}

	@Override
	public void onReadJapaneseSubtitlesIOError(File jaF, IOException ex)
			throws EasyJaSubException {
		

	}

	@Override
	public void onReadJapaneseSubtitlesParseError(File jaF,
			InputTextSubException ex) throws EasyJaSubException {
		

	}

	@Override
	public void onReadTranslatedSubtitlesIOError(File jaF, IOException ex)
			throws EasyJaSubException {
		

	}

	@Override
	public void onReadTranslatedSubtitlesParseError(File jaF,
			InputTextSubException ex) throws EasyJaSubException {
		

	}

	@Override
	public void onWriteCssIOError(File cssFile, IOException ex)
			throws EasyJaSubException {
		

	}

	@Override
	public void onWriteHtmlError(File htmlFolder, IOException ex)
			throws EasyJaSubException {
		

	}

	@Override
	public void onWriteImagesWkhtmlError(File bdnFolder, Exception ex)
			throws EasyJaSubException {
		

	}

	@Override
	public void onWriteImagesIOError(File bdnFolder, IOException ex)
			throws EasyJaSubException {
		

	}

	@Override
	public void onWriteBdnXmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {
		

	}

	@Override
	public void onWriteOutputJapaneseTextFileIOError(File txtFile,
			IOException ex) throws EasyJaSubException {
		

	}

	@Override
	public void onTranslatedSubDuplicated(String content, int mSeconds,
			int startTime) {
		

	}

	@Override
	public void onWriteOutputJapaneseTextFileSkipped(File txtFile) {
		

	}

	@Override
	public void onReadTranslatedSubtitlesSkipped(File enF) {
		

	}

	@Override
	public void onWriteCssSkipped(File cssFile) {
		

	}

	@Override
	public void onWriteHtmlFile(File file) {
		

	}

	@Override
	public void onWriteHtmlFileSkipped(File file) {
		

	}

	@Override
	public void onWriteBdnXmlFileSkipped(File f) {
		

	}

	@Override
	public void onWriteImageSkipped(File pngFile, File file) {
		

	}

	@Override
	public void onWriteIdxFileStart(File file, File bdnFile) {
		

	}

	@Override
	public void onWriteIdxFileSkipped(File file, File bdnFile) {
		

	}

	@Override
	public void onReadJapaneseSubtitlesSkipped(File jaF) {
		

	}

	@Override
	public void onEncodingWarning(String systemEncoding, String charsetstr) {


	}

	@Override
	public void onWriteXmlFileStart(File f) {


	}

	@Override
	public void onWriteXmlFileEnd(File f) {


	}

	@Override
	public void onWriteXmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {


	}

	@Override
	public void onWriteXmlFileSkipped(File f) {


	}

	@Override
	public void onReadXmlFileStart(File f) {


	}

	@Override
	public void onReadXmlFileEnd(File f) {


	}

	@Override
	public void onReadXmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {


	}

	@Override
	public void onReadXmlFileError(File f, SAXException ex)
			throws EasyJaSubException {


	}

	@Override
	public void onWriteJGlossFileStart(File f) {


	}

	@Override
	public void onWriteJGlossFileEnd(File f) {


	}

	@Override
	public void onWriteJGlossFileIOError(File f, IOException ex) {


	}

	@Override
	public void onWriteJGlossFileSkipped(File f) {


	}

	@Override
	public void onLuceneErrors(List<String> pronunciationErrors) {


	}

	@Override
	public void onLuceneParseStart() {


	}

	@Override
	public void onLuceneParseEnd() {


	}

	@Override
	public void onConvertToHtmlSubtitleListStart(File htmlFolder) {


	}

	@Override
	public void onConvertToHtmlSubtitleListEnd(File htmlFolder) {


	}

	@Override
	public void onConvertToHtmlSubtitleListError(File htmlFolder, IOException ex) {


	}

	@Override
	public void onDictionaryDeserialize(File cacheFile) {


	}

	@Override
	public void onDictionaryDeserialized(File cacheFile,
			EasyJaSubDictionary dictionary) {


	}

	@Override
	public void onDictionaryDeserializeError(File cacheFile, Exception ex) {


	}

	@Override
	public void onDictionaryJMDictParse(File dictionaryFile) {


	}

	@Override
	public void onDictionaryJMDictParsed(File dictionaryFile) {


	}

	@Override
	public void onDictionaryJMDictParseError(File dictionaryFile, Exception ex) {


	}

	@Override
	public void onDictionarySerialize(File cacheFile) {


	}

	@Override
	public void onDictionarySerialized(File cacheFile) {


	}

	@Override
	public void onDictionarySerializeError(File cacheFile, Exception ex) {


	}

	@Override
	public void onWriteImageError(File pngFile, File file)
			throws EasyJaSubException {


	}

	@Override
	public void onSupConvertMessage(String message) {


	}

}
