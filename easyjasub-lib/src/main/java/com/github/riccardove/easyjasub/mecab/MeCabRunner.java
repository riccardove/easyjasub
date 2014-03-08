package com.github.riccardove.easyjasub.mecab;

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
class MeCabRunner {

	private static final class ReaderRunnable implements Runnable {
		private final EasyJaSubReader reader;
		private final List<String> lines;
		private IOException exception;

		public ReaderRunnable(EasyJaSubReader reader,
				List<String> lines) {
			this.reader = reader;
			this.lines = lines;
		}

		@Override
		public void run() {
			try {
				String line;
				do {
					line = reader.readLine();
					if (line != null) {
						lines.add(line);
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

	public MeCabRunner(String command, int expectedLinesCount)
			throws IOException {
		process = new MeCabProcess(command);
		lines = new ArrayList<String>(expectedLinesCount
				* OUTPUT_LINES_MULTIPLIER);
		errorLines = new ArrayList<String>();

		textReader = new EasyJaSubReader(process.getInputStream());
		errorReader = new EasyJaSubReader(process.getErrorStream());
		textWriter = new EasyJaSubWriter(process.getOutputStream());

		textReaderRunnable = new ReaderRunnable(textReader, lines);
		errorReaderRunnable = new ReaderRunnable(errorReader, lines);
		textReaderThread = new Thread(textReaderRunnable);
		errorReaderThread = new Thread(errorReaderRunnable);
	}

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
