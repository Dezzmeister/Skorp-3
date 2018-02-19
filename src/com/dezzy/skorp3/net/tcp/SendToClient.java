package com.dezzy.skorp3.net.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class SendToClient implements Runnable {
	PrintWriter writer;
	Socket clientSocket = null;
	
	public SendToClient(Socket _clientSocket) {
		clientSocket = _clientSocket;
	}
	
	public void run() {
		try {
			writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			while (true && TCPManager.running) {
				String message = null;
				BufferedReader input = new BufferedReader(new InputStreamReader(TCPManager.sendMessage));
				message = input.readLine();
				writer.println(message);
				writer.flush();
				//change to accept method input
			}			
		} catch (Exception e) {
			TCPManager.running = false;
			e.printStackTrace();
		}
	}
}
