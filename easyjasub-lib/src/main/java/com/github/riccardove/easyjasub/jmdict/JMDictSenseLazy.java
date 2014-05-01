package com.github.riccardove.easyjasub.jmdict;

import java.util.ArrayList;

final class JMDictSenseLazy {

	static {
		instances = new ArrayList<JMDictSense>();
	}

	private static final ArrayList<JMDictSense> instances;
	private static int index;

	public static JMDictSense create() {
		if (index >= instances.size()) {
			instances.add(new JMDictSense());
		}
		return instances.get(index++);
	}

	public static void clear() {
		index = 0;
	}
}
