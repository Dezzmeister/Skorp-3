package com.dezzy.skorp3.net.tcp;


import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.InputContainer;

class SendToClient implements Runnable {
	private PrintWriter writer;
	private Socket clientSocket = null;
	private volatile InputContainer input;
	private AtomicBoolean running;
	
	public SendToClient(Socket _clientSocket, InputContainer _input, AtomicBoolean _running) {
		clientSocket = _clientSocket;
		input = _input;
		running = _running;
	}
	
	public void run() {
		try {
			writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			while (true && running.get()) {
				if (input.get() !=null && input.isUpdated()) {
					String message = input.read();
					if (message!=null) {
						writer.println(message);
						writer.flush();
						
						if (message.equals("stop")) {
							running.set(false);
							break;
						}
					}
				}
			}			
		} catch (Exception e) {
			running.set(false);
			e.printStackTrace();
		}
	}
}