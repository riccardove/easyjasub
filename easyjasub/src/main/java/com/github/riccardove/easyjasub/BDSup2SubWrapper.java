package com.github.riccardove.easyjasub;

import java.io.File;

import bdsup2sub.BDSup2Sub;

class BDSup2SubWrapper {

	public void toIdx(File folderIn, File fileIn, File folderOut, File fileOut, int width) {
//		if (folderIn != null) {
//			fileIn = (new File(folderIn, fileIn)).getAbsolutePath();
//		}
//		if (folderOut != null) {
//			fileOut = (new File(folderOut, fileOut)).getAbsolutePath();
//		}
		BDSup2Sub.main(new String[] {
				"-m", "100",
				"-x", "10",
				"-p", "keep",
				"-T", "24p",
				"-v",
//				"-r", Integer.toString(width),
				"-o", fileOut.getAbsolutePath(), 
				fileIn.getAbsolutePath() });
	}

}
