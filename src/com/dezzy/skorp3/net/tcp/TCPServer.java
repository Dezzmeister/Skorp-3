package com.dezzy.skorp3.net.tcp;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	int port = 10221;
	private ServerSocket server = null;
	private Socket clientSocket = null;
	ReceiveFromClient receiver;
	SendToClient sender;
	Thread receiveThread;
	Thread sendThread;
	
	public TCPServer() {
		
	}
	
	public void connect(int _port) {
		port = _port;
		try {
			server = new ServerSocket(port);
			clientSocket = server.accept();
			System.out.println("Connected to "+clientSocket.getInetAddress()+":"+clientSocket.getPort());
			
			receiver = new ReceiveFromClient(clientSocket);
			receiveThread = new Thread(receiver);
			receiveThread.start();
			
			sender = new SendToClient(clientSocket);
			sendThread = new Thread(sender);
			sendThread.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
