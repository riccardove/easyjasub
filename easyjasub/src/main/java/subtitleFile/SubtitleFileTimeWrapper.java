package subtitleFile;

public class SubtitleFileTimeWrapper {

	public SubtitleFileTimeWrapper(Time time) 
	{
		this.time = time;
	}
	
	private final Time time;
	
	public String getTime(String format) {
		return time.getTime(format);
	}
	
	public int getMSeconds() {
		return time.mseconds;
	}
}
