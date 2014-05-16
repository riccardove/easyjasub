package com.github.riccardove.easyjasub;

import java.io.File;

class EasyJaSubCssFile {

	private final File file;

	public EasyJaSubCssFile(File file) {
		this.file = file;
	}

	public String toRelativeURIStr(File directory) {
		if (file == null) {
			return "default.css";
		}
		if (directory == null) {
			return file.toURI().toString();
		}
		return directory.toURI().relativize(file.toURI()).toString();
	}

}
