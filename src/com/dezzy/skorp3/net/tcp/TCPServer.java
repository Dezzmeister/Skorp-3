package com.dezzy.skorp3.net.tcp;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.DirectiveContainer;
import com.dezzy.skorp3.net.InputContainer;

public class TCPServer {
	int port = 10221;
	private ServerSocket server = null;
	private Socket clientSocket = null;
	ReceiveFromClient receiver;
	SendToClient sender;
	Thread receiveThread;
	Thread sendThread;
	private volatile InputContainer input;
	private AtomicBoolean running;
	private DirectiveContainer directives;
	
	public TCPServer(InputContainer _input, AtomicBoolean _running, DirectiveContainer _directives) {
		input = _input;
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
			receiveThread = new Thread(receiver,"Skorp TCP Receiver");
			receiveThread.start();
			
			sender = new SendToClient(clientSocket,input,running);
			sendThread = new Thread(sender, "Skorp TCP Sender");
			sendThread.start();
		} catch(Exception e) {
			e.printStackTrace();
			running.set(true);
		}
	}
}
