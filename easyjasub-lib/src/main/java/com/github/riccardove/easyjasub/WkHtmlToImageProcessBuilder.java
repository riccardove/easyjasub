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


import java.io.IOException;

class WkHtmlToImageProcessBuilder {
	public WkHtmlToImageProcessBuilder(String command) {
		wkhtmltoimageexe = command;
	}
	
	private final String wkhtmltoimageexe;
	
	public Process start(String htmlFile, String pngFile, int width) throws IOException {
		return new ProcessBuilder(wkhtmltoimageexe,
				"--width", Integer.toString(width), //"--disable-smart-width",
				"--transparent", 
					  "--enable-local-file-access", // see https://sourceforge.net/p/easyjasub/tickets/4/
				htmlFile, pngFile).start();
	}
}
