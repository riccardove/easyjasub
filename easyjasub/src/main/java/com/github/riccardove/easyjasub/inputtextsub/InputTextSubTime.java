package com.github.riccardove.easyjasub.inputtextsub;

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
