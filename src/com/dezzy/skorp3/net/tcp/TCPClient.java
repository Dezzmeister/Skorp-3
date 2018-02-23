package com.dezzy.skorp3.net.tcp;

import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.DirectiveContainer;

public class TCPClient {
	private String ip = "";
	private int port = 10221;
	private Socket socket;
	private SendToServer sender;
	private ReceiveFromServer receiver;
	private Thread sendThread;
	private Thread receiveThread;
	private InputStream sentMessage;
	private AtomicBoolean running;
	private DirectiveContainer directives;
	
	public TCPClient(InputStream _sentMessage, AtomicBoolean _running, DirectiveContainer _directives) {
		sentMessage = _sentMessage;
		running = _running;
		directives = _directives;
	}
	
	public void connect(String _ip, int _port) {
		ip = _ip;
		port = _port;
		try {
			running.set(true);
			socket = new Socket(ip,port);
			
			sender = new SendToServer(socket,sentMessage,running);
			sendThread = new Thread(sender);
			sendThread.start();
			
			receiver = new ReceiveFromServer(socket,running,directives);
			receiveThread = new Thread(receiver);
			receiveThread.start();
		} catch (Exception e) {
			e.printStackTrace();
			running.set(true);
		}
	}
}
