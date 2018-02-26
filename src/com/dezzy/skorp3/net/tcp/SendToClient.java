package com.dezzy.skorp3.net.tcp;


import java.io.BufferedReader;
import java.io.InputStreamReader;
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
				if (input.get() !=null) {
					String message = null;
					BufferedReader reader = new BufferedReader(new InputStreamReader(input.get()));
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