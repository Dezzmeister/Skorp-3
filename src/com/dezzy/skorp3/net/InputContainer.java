package com.dezzy.skorp3.net;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputContainer {
	private volatile InputStream input;
	
	public InputContainer() {
		input = new ByteArrayInputStream("".getBytes());
	}
	
	public synchronized InputStream get() {
		return input;
	}
	
	public synchronized void set(String message) {
		input = new ByteArrayInputStream(message.getBytes());
	}
}
