package com.github.riccardove.easyjasub;

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


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

	public T deserializeFromStream(InputStream stream) throws IOException,
			ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(stream);
		T obj = readObject(in);
		in.close();
		return obj;
	}

	public T deserializeFromFile(File file) throws ClassNotFoundException,
			IOException {
		InputStream stream = new FileInputStream(file);
		T obj = deserializeFromStream(stream);
		stream.close();
		return obj;
	}

	public void serializeToStream(OutputStream stream, T obj) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(stream);
		writeObject(obj, out);
		out.close();
	}

	public void serializeToFile(File file, T obj) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		serializeToStream(stream, obj);
		stream.close();
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
