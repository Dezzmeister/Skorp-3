package com.dezzy.skorp3.net.tcp;


import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.InputContainer;

class SendToServer implements Runnable {
	private Socket socket = null;
	private PrintWriter writer = null;
	private AtomicBoolean running;
	private volatile InputContainer input;
	
	public SendToServer(Socket _socket, InputContainer _input, AtomicBoolean _running) {
		socket = _socket;
		input = _input;
		running = _running;
	}
	
	public void run() {
		try {
			if (socket.isConnected()) {
				System.out.println("Client connected to "+socket.getInetAddress()+":"+socket.getPort());
				writer = new PrintWriter(socket.getOutputStream(),true);
				while (true && running.get()) {
					if (input.get() !=null && input.isUpdated()) {
						String message = input.read();
						if (message != null) {
							writer.println(message);
							writer.flush();
					
							if (message.equals("stop")) {
								running.set(false);
								break;
							}
						}
					}
				}
				socket.close();
			}
		} catch (Exception e) {
			running.set(false);
			e.printStackTrace();
		}
	}
}
