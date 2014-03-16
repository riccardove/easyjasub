package com.github.riccardove.easyjasub.mecab;

/*
 * #%L
 * easyjasub-lib
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
import java.util.ArrayList;
import java.util.List;

import com.github.riccardove.easyjasub.EasyJaSubException;
import com.github.riccardove.easyjasub.EasyJaSubReader;
import com.github.riccardove.easyjasub.EasyJaSubWriter;

/**
 * Runs the MeCab process, set the process input text and retrieve the output as
 * a list of lines
 */
public class MeCabRunner {

	private static final class ReaderRunnable implements Runnable {
		private final EasyJaSubReader reader;
		private final List<String> lines;
		private IOException exception;
		private final EasyJaSubWriter outputWriter;

		public ReaderRunnable(EasyJaSubReader reader, List<String> lines,
				EasyJaSubWriter outputWriter) {
			this.reader = reader;
			this.lines = lines;
			this.outputWriter = outputWriter;
		}

		@Override
		public void run() {
			try {
				String line;
				do {
					line = reader.readLine();
					if (line != null) {
						lines.add(line);
						if (outputWriter != null) {
							outputWriter.println(line);
							if (MeCabProcess.isLineSeparator(line)) {
								outputWriter.flush();
							}
						}
					}
				} while (line != null);
			} catch (IOException e) {
				exception = e;
			}
		}

		public IOException getException() {
			return exception;
		}

	}

	private static final int OUTPUT_LINES_MULTIPLIER = 15;

	public MeCabRunner(String command, int expectedLinesCount,
			File meCabOutputFile) throws IOException {
		process = new MeCabProcess(command);
		lines = new ArrayList<String>(expectedLinesCount
				* OUTPUT_LINES_MULTIPLIER);
		errorLines = new ArrayList<String>();

		textReader = new EasyJaSubReader(process.getInputStream());
		errorReader = new EasyJaSubReader(process.getErrorStream());
		textWriter = new EasyJaSubWriter(process.getOutputStream());

		if (meCabOutputFile != null) {
			outputWriter = new EasyJaSubWriter(meCabOutputFile);
		} else {
			outputWriter = null;
		}

		textReaderRunnable = new ReaderRunnable(textReader, lines, outputWriter);
		errorReaderRunnable = new ReaderRunnable(errorReader, lines,
				outputWriter);
		textReaderThread = new Thread(textReaderRunnable);
		errorReaderThread = new Thread(errorReaderRunnable);
	}

	private final EasyJaSubWriter outputWriter;
	private final EasyJaSubReader textReader;
	private final EasyJaSubReader errorReader;
	private final Thread textReaderThread;
	private final Thread errorReaderThread;
	private final ReaderRunnable textReaderRunnable;
	private final ReaderRunnable errorReaderRunnable;
	private final EasyJaSubWriter textWriter;
	private final MeCabProcess process;
	private final ArrayList<String> lines;
	private final ArrayList<String> errorLines;

	public void start() {
		textReaderThread.start();
		errorReaderThread.start();
	}

	/**
	 * Adds text to the input
	 */
	public void append(String text) throws IOException {
		textWriter.println(text);
	}

	/**
	 * Closes the input stream, waits for process stop and for reader stop
	 */
	public void close() throws IOException, InterruptedException,
			EasyJaSubException {
		textWriter.close();
		int result = process.waitFor();
		textReaderThread.join();
		errorReaderThread.join();
		textReader.close();
		errorReader.close();
		if (outputWriter != null) {
			outputWriter.close();
		}
		IOException errorException = errorReaderRunnable.getException();
		if (errorException != null) {
			throw new EasyJaSubException(
					"Reading MeCab error messages returned an error: "
							+ errorException.getMessage(), errorException);
		}
		IOException textException = textReaderRunnable.getException();
		if (textException != null) {
			throw new EasyJaSubException(
					"Reading MeCab output returned an error: "
							+ textException.getMessage(), textException);
		}
		if (result != 0) {
			throw new EasyJaSubException("MeCab returned an error: " + result);
		}
	}

	/**
	 * Retrieves the list of lines outputted by the process
	 */
	public List<String> getOutputLines() {
		return lines;
	}

	/**
	 * Retrieves the list of lines outputted by the process in the standard
	 * error
	 */
	public List<String> getErrorLines() {
		return errorLines;
	}
}
