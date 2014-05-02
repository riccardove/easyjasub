package com.github.riccardove.easyjasub;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Utility class to serialize objects
 * 
 * @param <T> A serializable type
 */
public class EasyJaSubSerialize<T extends Serializable> {

	public T deserialize(InputStream stream) throws IOException,
			ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(stream);
		T trie = readObject(in);
		in.close();
		return trie;
	}

	public void serialize(OutputStream stream, T obj) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(stream);
		writeObject(obj, out);
		out.close();
	}

	@SuppressWarnings("unchecked")
	private T readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		return ((T) in.readObject());
	}

	private void writeObject(T obj, ObjectOutputStream out) throws IOException {
		out.writeObject(obj);
	}
}
