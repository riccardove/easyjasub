package com.github.riccardove.easyjasub.commons;

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


import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.github.riccardove.easyjasub.EasyJaSubCharset;

public final class CommonsIOUtils {

	private CommonsIOUtils() {
	}

	public static String streamToString(InputStream is) throws IOException {
		return IOUtils.toString(is, EasyJaSubCharset.CHARSET);
	}
}
