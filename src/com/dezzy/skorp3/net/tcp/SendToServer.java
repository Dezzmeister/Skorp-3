package com.dezzy.skorp3.net.tcp;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

class SendToServer implements Runnable {
	private Socket socket = null;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private AtomicBoolean running;
	private InputStream sentMessage;
	
	public SendToServer(Socket _socket, InputStream _sentMessage, AtomicBoolean _running) {
		socket = _socket;
		sentMessage = _sentMessage;
		running = _running;
	}
	
	public void run() {
		try {
			if (socket.isConnected()) {
				System.out.println("Client connected to "+socket.getInetAddress()+":"+socket.getPort());
				writer = new PrintWriter(socket.getOutputStream(),true);
				while (true && running.get()) {
					if (sentMessage !=null) {
						reader = new BufferedReader(new InputStreamReader(sentMessage));
						String message = null;
						message = reader.readLine();
						if (message != null) {
							writer.println(message);
							writer.flush();
					
							if (message.equals("exit")) {
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
