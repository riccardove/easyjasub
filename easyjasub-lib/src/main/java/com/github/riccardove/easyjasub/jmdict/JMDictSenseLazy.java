package com.github.riccardove.easyjasub.jmdict;

import java.util.ArrayList;

/**
 * Recycle JMDictSense objects
 */
final class JMDictSenseLazy {

	static {
		instances = new ArrayList<JMDictSense>();
	}

	private static final ArrayList<JMDictSense> instances;
	private static int index;

	/**
	 * Returns a JMDictSense object, a new one of an existing one
	 */
	public static JMDictSense create() {
		if (index >= instances.size()) {
			instances.add(new JMDictSense());
		}
		return instances.get(index++);
	}

	/**
	 * Reset all created JMDictSense object so that they can be reused
	 */
	public static void clear() {
		for (int i = 0; i < index; ++i) {
			instances.get(i).clear();
		}
		index = 0;
	}
}
