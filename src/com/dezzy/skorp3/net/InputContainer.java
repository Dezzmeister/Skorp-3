package com.dezzy.skorp3.net;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class for communication between sender threads and the main thread.
 * The InputStream is updated by the main thread, and a sending thread sees this update and sends the message.
 * The AtomicBoolean "updated" prevents a sender thread from spamming the same message several times.
 * Updated is changed to false when the InputStream is read.
 * 
 * @author Dezzmeister
 *
 */
public class InputContainer {
	private volatile InputStream input;
	private volatile AtomicBoolean updated;
	
	public InputContainer() {
		input = new ByteArrayInputStream("".getBytes());
		updated = new AtomicBoolean(false);
	}
	
	public synchronized InputStream get() {
		return input;
	}
	
	public synchronized void set(String message) {
		input = new ByteArrayInputStream(message.getBytes());
		updated.set(true);
	}
	
	public synchronized String read() throws IOException {
		updated.set(false);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String message = null;
		message = reader.readLine();
		return message;
	}
	
	public boolean isUpdated() {
		return updated.get();
	}
}
