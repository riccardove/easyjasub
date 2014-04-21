package com.github.riccardove.easyjasub.lucene;

public class LuceneToken {
	private String pronunciation;
	private String reading;
	private String partOfSpeech;
	private String inflectionForm;
	private String inflectionType;
	private String baseForm;
	private int startOffset;
	private int endOffset;

	public String getPronunciation() {
		return pronunciation;
	}

	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public String getInflectionType() {
		return inflectionType;
	}

	public void setInflectionType(String inflectionType) {
		this.inflectionType = inflectionType;
	}

	public String getInflectionForm() {
		return inflectionForm;
	}

	public void setInflectionForm(String inflectionForm) {
		this.inflectionForm = inflectionForm;
	}

	public String getBaseForm() {
		return baseForm;
	}

	public void setBaseForm(String baseForm) {
		this.baseForm = baseForm;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}
}