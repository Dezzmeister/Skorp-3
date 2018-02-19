package com.dezzy.skorp3.net.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

class ReceiveFromClient implements Runnable {
	Socket clientSocket = null;
	BufferedReader reader = null;
	
	public ReceiveFromClient(Socket _clientSocket) {
		clientSocket = _clientSocket;
	}
	
	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String message;
			while (true && TCPManager.running) {
				while ((message = reader.readLine())!=null) {
					String header = message.substring(0,message.indexOf(" "));
					TCPManager.executeServerDirective(header);
					System.out.println(message);
					if (message.equals("exit")) {
						break;
					}
				}
				clientSocket.close();
			}
		} catch (Exception e) {
			TCPManager.running = false;
			e.printStackTrace();
		}
	}
}
