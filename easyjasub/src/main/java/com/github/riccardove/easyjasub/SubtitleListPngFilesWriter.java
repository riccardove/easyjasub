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
import java.util.LinkedList;

class SubtitleListPngFilesWriter {
	
	private final EasyJaSubObserver observer;
	private final int width;

	public SubtitleListPngFilesWriter(String command, int width,
			EasyJaSubObserver observer) {
		this.width = width;
		this.observer = observer;
		wkhtmltoimageexe = new WkHtmlToImageProcessBuilder(command);
	}
	
	private final WkHtmlToImageProcessBuilder wkhtmltoimageexe;
	
	public void writeImages(SubtitleList s, File htmlFolder, File pngFolder) throws IOException, InterruptedException, WkhtmltoimageException 
	{
		int result  = 0;
		LinkedList<Process> processes = new LinkedList<Process>();
		boolean first = true;
		for (SubtitleLine l : s) {
			
			File file = new File(htmlFolder, l.getHtmlFile());
		
			File pngFile = new File(pngFolder, l.getPngFile());
			if (pngFile.exists()) {
				observer.onWriteImageSkipped(pngFile, file);
			}
			else {
				observer.onWriteImage(pngFile, file);
				Process p = wkhtmltoimageexe.start(file.getAbsolutePath(), pngFile.getAbsolutePath(), width);
				processes.add(p);
				if (first || processes.size() > 10) {
					first = false;
					do {
						result = processes.removeFirst().waitFor();
					}
					while (result == 0 && processes.size() > 3);
				}
				if (result != 0) {
					throw new WkhtmltoimageException("Error invoking wkhtmltoimage command, it returned " + result);
				}
			}
		}
		while (result == 0 && processes.size() > 0)
		{
			result = processes.removeFirst().waitFor();
		}
		if (result != 0) {
			throw new WkhtmltoimageException("Error invoking wkhtmltoimage command, it returned " + result);
		}
	}

}
