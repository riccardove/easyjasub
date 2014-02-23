package com.github.riccardove.easyjasub.inputtextsub;

/*
 * #%L
 * easyjasub
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


import bdsup2sub.core.Framerate;
import subtitleFile.SubtitleFileTimeWrapper;
import subtitleFile.Time;

/**
 * Timestamp of a caption in input text subtitles
 */
public class InputTextSubTime  implements Comparable<InputTextSubTime> {

	protected InputTextSubTime(Time time) 
	{
		SubtitleFileTimeWrapper wrapper = new SubtitleFileTimeWrapper(time);
		this.mseconds = wrapper.getMSeconds();
		bdmTimeStr = wrapper.getTime("hh:mm:ss:ff/" + Framerate.FPS_23_976.getValue());
		str = wrapper.getTime("hh:mm:ss,ms");
	}
	
	private final int mseconds;
	private final String bdmTimeStr;
	private final String str;
	
	public String getBDMTime() {
		return bdmTimeStr;
	}
	
	public int compareTo(InputTextSubTime o) {
		return Integer.compare(mseconds, o.mseconds);
	}
	
	@Override
	public String toString() {
		return str;
	}

	public int getMSeconds() {
		return mseconds;
	}
}
