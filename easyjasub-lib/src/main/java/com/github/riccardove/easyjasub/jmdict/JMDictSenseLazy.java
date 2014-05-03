package com.github.riccardove.easyjasub.jmdict;

/*
 * #%L
 * easyjasub-lib
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
