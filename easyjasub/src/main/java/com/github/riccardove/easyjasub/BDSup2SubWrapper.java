package com.github.riccardove.easyjasub;

import java.io.File;

import bdsup2sub.BDSup2Sub;

class BDSup2SubWrapper {

	public void toIdx(File folderIn, String fileIn, File folderOut, String fileOut, int width) {
//		if (width>1080) {
//			width = 1080;
//		}
		if (folderIn != null) {
			fileIn = (new File(folderIn, fileIn)).getAbsolutePath();
		}
		if (folderOut != null) {
			fileOut = (new File(folderOut, fileOut)).getAbsolutePath();
		}
		BDSup2Sub.main(new String[] {
				"-m", "100",
				"-x", "10",
				"-p", "keep",
				"-T", "24p",
				"-v",
//				"-r", Integer.toString(width),
				"-o", fileOut, 
				fileIn });
	}

}
