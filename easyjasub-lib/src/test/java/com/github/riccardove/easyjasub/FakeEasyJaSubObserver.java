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
import java.util.List;

import org.xml.sax.SAXException;

import com.github.riccardove.easyjasub.dictionary.EasyJaSubDictionary;
import com.github.riccardove.easyjasub.inputtextsub.InputTextSubException;

public class FakeEasyJaSubObserver implements EasyJaSubObserver {

	@Override
	public void onReadJapaneseSubtitlesStart(File jaF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadJapaneseSubtitlesEnd(File jaF) {
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
	public void onWriteHtmlStart(File htmlFolder, File cssFileUrl) {
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
	public void onWriteOutputJapaneseTextFileSkipped(File txtFile) {
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

	@Override
	public void onReadJapaneseSubtitlesSkipped(File jaF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEncodingWarning(String systemEncoding, String charsetstr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteXmlFileStart(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteXmlFileEnd(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteXmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteXmlFileSkipped(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReadXmlFileStart(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReadXmlFileEnd(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReadXmlFileIOError(File f, IOException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReadXmlFileError(File f, SAXException ex)
			throws EasyJaSubException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteJGlossFileStart(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteJGlossFileEnd(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteJGlossFileIOError(File f, IOException ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteJGlossFileSkipped(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLuceneErrors(List<String> pronunciationErrors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLuceneParseStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLuceneParseEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConvertToHtmlSubtitleListStart(File htmlFolder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConvertToHtmlSubtitleListEnd(File htmlFolder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConvertToHtmlSubtitleListError(File htmlFolder, IOException ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionaryDeserialize(File cacheFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionaryDeserialized(File cacheFile,
			EasyJaSubDictionary dictionary) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionaryDeserializeError(File cacheFile, Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionaryJMDictParse(File dictionaryFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionaryJMDictParsed(File dictionaryFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionaryJMDictParseError(File dictionaryFile, Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionarySerialize(File cacheFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionarySerialized(File cacheFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDictionarySerializeError(File cacheFile, Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteImageError(File pngFile, File file)
			throws EasyJaSubException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSupConvertMessage(String message) {
		// TODO Auto-generated method stub

	}

}
