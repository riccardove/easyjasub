package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public final class CommonsIOUtils {

	private CommonsIOUtils() {
	}

	public static String streamToString(InputStream is) throws IOException {
		return IOUtils.toString(is, EasyJaSubCharset.CHARSET);
	}
}
