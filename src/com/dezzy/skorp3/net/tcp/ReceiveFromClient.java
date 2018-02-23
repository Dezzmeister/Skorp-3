package com.dezzy.skorp3.net.tcp;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.DirectiveContainer;

class ReceiveFromClient implements Runnable {
	private Socket clientSocket = null;
	private BufferedReader reader = null;
	private AtomicBoolean running;
	private DirectiveContainer directives;
	
	public ReceiveFromClient(Socket _clientSocket, AtomicBoolean _running, DirectiveContainer _directives) {
		clientSocket = _clientSocket;
		running = _running;
		directives = _directives;
	}
	
	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String message;
			while (true && running.get()) {
				while ((message = reader.readLine())!=null) {
					String header = message.substring(0,message.indexOf(" ")!=-1?message.indexOf(" "):message.length());
					directives.executeServerDirective(header);
					System.out.println(message);
					if (message.equals("exit")) {
						break;
					}
				}
				clientSocket.close();
			}
		} catch (Exception e) {
			running.set(false);
			e.printStackTrace();
		}
	}
}
