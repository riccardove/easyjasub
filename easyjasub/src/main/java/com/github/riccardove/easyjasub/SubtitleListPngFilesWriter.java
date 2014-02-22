package com.github.riccardove.easyjasub;

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
