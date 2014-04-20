package com.github.riccardove.easyjasub;


public interface EasyJaSubXmlHandler<T extends Enum<?>> {
	void onStartElement(T element);

	void onEndElement(T element, String text);
}
