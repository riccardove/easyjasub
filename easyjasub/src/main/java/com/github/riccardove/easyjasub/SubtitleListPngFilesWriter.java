package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

class SubtitleListPngFilesWriter {
	
	public SubtitleListPngFilesWriter(String command) {
		wkhtmltoimageexe = new WkHtmlToImageProcessBuilder(command);
	}
	
	private final WkHtmlToImageProcessBuilder wkhtmltoimageexe;
	
	public int writeImages(SubtitleList s, File htmlFolder, File pngFolder, int width,
			EasyJaSubObserver observer) throws IOException, InterruptedException, WkhtmltoimageException 
	{
		int result  = 0;
		LinkedList<Process> processes = new LinkedList<Process>();
		boolean first = true;
		for (SubtitleLine l : s) {
			
			File file = new File(htmlFolder, l.getHtmlFile());
		
			File pngFile = new File(pngFolder, l.getPngFile());
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
		do {
			result = processes.removeFirst().waitFor();
		}
		while (result == 0 && processes.size() > 0);
		return result;
	}

}
