package com.github.riccardove.easyjasub.mecab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.github.riccardove.easyjasub.EasyJaSubCharset;

/**
 * Runs the MeCab process, set the process input text and retrieve the output as
 * a list of lines
 */
class MeCabRunner {

	public MeCabRunner(String command) throws IOException {
		process = new MeCabProcess(command);
		lines = new ArrayList<String>();

		textReaderThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					BufferedReader r = new BufferedReader(
							new InputStreamReader(process.getInputStream(),
									EasyJaSubCharset.CHARSET));
					String line;
					do {
						line = r.readLine();
						if (line != null) {
							lines.add(line);
						}
					} while (line != null);
				} catch (Exception e) {
					lines.clear();
				}
			}
		});
		textReaderThread.start();
		textWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(),
				EasyJaSubCharset.CHARSET));
	}

	private final Thread textReaderThread;
	private final Writer textWriter;
	private final MeCabProcess process;
	private final ArrayList<String> lines;

	/**
	 * Adds text to the input
	 */
	public void append(String text) throws IOException {
		textWriter.append(text);
	}

	/**
	 * Closes the input stream, waits for process stop and for reader stop
	 */
	public void close() throws IOException, InterruptedException {
		textWriter.close();
		process.waitFor();
		textReaderThread.join();
	}

	/**
	 * Retrieves the list of lines outputted by the process
	 */
	public Iterable<String> lines() {
		return lines;
	}
}
