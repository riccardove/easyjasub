package com.github.riccardove.easyjasub;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

class SubtitleListPngFilesWriter {
	
	public SubtitleListPngFilesWriter() {
		wkhtmltoimageexe = "C:\\Program Files (x86)\\wkhtmltopdf\\wkhtmltoimage.exe";
	}
	
	private final String wkhtmltoimageexe;
	
	public int writeImages(SubtitleList s, File htmlFolder, File pngFolder) throws IOException, InterruptedException 
	{
		int result  = 0;
		LinkedList<Process> processes = new LinkedList<Process>(); 
		for (SubtitleLine l : s) {
			
			File file = new File(htmlFolder, l.getHtmlFile());
		
			File pngFile = new File(pngFolder, l.getPngFile());
			Process p = toImage(file.getAbsolutePath(), pngFile.getAbsolutePath(), s.getWidth());
			processes.add(p);
			if (processes.size() > 10 || l.getIndex() == 1) {
				do {
					result = processes.removeFirst().waitFor();
				}
				while (result == 0 && processes.size() > 3);
			}
			if (result > 0) {
				break;
			}
			System.out.print('*');
			if (l.getIndex() % 80 == 0) {
				System.out.println();
			}
			System.out.flush();
		}
		do {
			result = processes.removeFirst().waitFor();
		}
		while (result == 0 && processes.size() > 0);
		System.out.println();
		return result;
	}

	private Process toImage(String htmlFile, String pngFile, int width) throws IOException {
		return new ProcessBuilder(wkhtmltoimageexe,
				"--width", Integer.toString(width), //"--disable-smart-width",
				"--transparent",
				htmlFile, pngFile).start();
	}

}
