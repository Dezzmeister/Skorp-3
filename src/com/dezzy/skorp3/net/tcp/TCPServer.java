package com.dezzy.skorp3.net.tcp;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.DirectiveContainer;

public class TCPServer {
	int port = 10221;
	private ServerSocket server = null;
	private Socket clientSocket = null;
	ReceiveFromClient receiver;
	SendToClient sender;
	Thread receiveThread;
	Thread sendThread;
	private InputStream sentMessage;
	private AtomicBoolean running;
	private DirectiveContainer directives;
	
	public TCPServer(InputStream _sentMessage, AtomicBoolean _running, DirectiveContainer _directives) {
		sentMessage = _sentMessage;
		running = _running;
		directives = _directives;
	}
	
	public void connect(int _port) {
		port = _port;
		try {
			running.set(true);
			server = new ServerSocket(port);
			clientSocket = server.accept();
			System.out.println("Connected to "+clientSocket.getInetAddress()+":"+clientSocket.getPort());
			
			receiver = new ReceiveFromClient(clientSocket,running,directives);
			receiveThread = new Thread(receiver);
			receiveThread.start();
			
			sender = new SendToClient(clientSocket,sentMessage,running);
			sendThread = new Thread(sender);
			sendThread.start();
		} catch(Exception e) {
			e.printStackTrace();
			running.set(true);
		}
	}
}
