package com.dezzy.skorp3.net.tcp;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

class SendToClient implements Runnable {
	private PrintWriter writer;
	private Socket clientSocket = null;
	private volatile InputStream sentMessage;
	private AtomicBoolean running;
	
	public SendToClient(Socket _clientSocket, InputStream _sentMessage, AtomicBoolean _running) {
		clientSocket = _clientSocket;
		sentMessage = _sentMessage;
		running = _running;
	}
	
	public void run() {
		try {
			writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			while (true && running.get()) {
				if (sentMessage !=null) {
					String message = null;
					BufferedReader reader = new BufferedReader(new InputStreamReader(sentMessage));
					message = reader.readLine();
					System.out.println(message); //NULL!!!
					writer.println(message);
					writer.flush();
					//change to accept method input
				}
			}			
		} catch (Exception e) {
			running.set(false);
			e.printStackTrace();
		}
	}
}