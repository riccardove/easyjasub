package com.github.riccardove.easyjasub;

import java.io.File;

import bdsup2sub.BDSup2Sub;

class BDSup2SubWrapper {

	public void toIdx(File fileIn, File fileOut, int width) {
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
