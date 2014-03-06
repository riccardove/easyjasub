package com.github.riccardove.easyjasub.mecab;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class MeCabProcess {
	public MeCabProcess(String command) throws IOException {
		process = new ProcessBuilder(command).start();
	}

	private final Process process;

	public OutputStream getOutputStream() {
		return process.getOutputStream();
	}

	public InputStream getErrorStream() {
		return process.getErrorStream();
	}

	public InputStream getInputStream() {
		return process.getInputStream();
	}

	public int waitFor() throws InterruptedException {
		return process.waitFor();
	}
}
