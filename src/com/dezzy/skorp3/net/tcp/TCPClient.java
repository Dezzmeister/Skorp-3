package com.dezzy.skorp3.net.tcp;

import java.net.Socket;

public class TCPClient {
	String ip = "";
	int port = 10221;
	Socket socket;
	SendToServer sender;
	ReceiveFromServer receiver;
	Thread sendThread;
	Thread receiveThread;
	
	public void connect(String _ip, int _port) {
		ip = _ip;
		port = _port;
		try {
			socket = new Socket(ip,port);
			
			sender = new SendToServer(socket);
			sendThread = new Thread(sender);
			sendThread.start();
			
			receiver = new ReceiveFromServer(socket);
			receiveThread = new Thread(receiver);
			receiveThread.start();
			TCPManager.running = true;
		} catch (Exception e) {
			e.printStackTrace();
			TCPManager.running = false;
		}
	}
}
