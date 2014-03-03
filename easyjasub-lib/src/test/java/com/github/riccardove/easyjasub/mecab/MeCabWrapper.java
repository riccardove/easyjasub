package com.github.riccardove.easyjasub.mecab;

import org.chasen.mecab.MeCab;


public final class MeCabWrapper {

	private static MeCabWrapper instance;

	/**
	 * Loads the native MeCab library
	 */
	public synchronized static MeCabWrapper loadLibrary() {
		if (instance != null) {
			return instance;
		}
		System.loadLibrary("MeCab");
		instance = new MeCabWrapper();
		return instance;
	}

	/**
	 * Loads the native MeCab library
	 * @param path
	 *            full path to libmecab.dll file
	 */
	public synchronized static MeCabWrapper load(String path) {
		if (instance != null) {
			return instance;
		}
		System.load(path);
		instance = new MeCabWrapper();
		return instance;
	}

	private MeCabWrapper() {

	}

	public String getVersion() {
		return MeCab.VERSION;
	}

	/**
	 * The MeCab parsing and output from natto can be customized by using the
	 * following options:
	 * <ul>
	 * <li>rcfile -- resource file</li>
	 * <li>dicdir -- system dicdir</li>
	 * <li>userdic -- user dictionary</li>
	 * <li>lattice-level -- lattice information level (DEPRECATED)</li>
	 * <li>output-format-type -- output format type (wakati, chasen, yomi, etc.)
	 * </li>
	 * <li>all-morphs -- output all morphs (default false)</li>
	 * <li>nbest -- output N best results (integer, default 1), requires lattice
	 * level >= 1</li>
	 * <li>partial -- partial parsing mode (default false)</li>
	 * <li>marginal -- output marginal probability (default false)</li>
	 * <li>max-grouping-size -- maximum grouping size for unknown words
	 * (integer, default 24)</li>
	 * <li>node-format -- user-defined node format</li>
	 * <li>unk-format -- user-defined unknown node format</li>
	 * <li>bos-format -- user-defined beginning-of-sentence format</li>
	 * <li>eos-format -- user-defined end-of-sentence format</li>
	 * <li>eon-format -- user-defined end-of-NBest format</li>
	 * <li>unk-feature -- feature for unknown morpheme</li>
	 * <li>input-buffer-size -- set input buffer size (default 8192)</li>
	 * <li>allocate-sentence -- allocate new memory for input sentence</li>
	 * <li>theta -- temperature parameter theta (float, default 0.75)</li>
	 * <li>cost-factor -- cost factor (integer, default 700)</li>
	 * </ul>
	 * 
	 * @param args
	 * @return
	 */
	public MeCabModel createModel(String args) {
		return new MeCabModel(args);
	}
}
