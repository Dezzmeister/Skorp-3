package com.dezzy.skorp3.net.tcp;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.DirectiveContainer;
import com.dezzy.skorp3.net.InputContainer;

public class TCPClient {
	private String ip = "";
	private int port = 10221;
	private Socket socket;
	private SendToServer sender;
	private ReceiveFromServer receiver;
	private Thread sendThread;
	private Thread receiveThread;
	private volatile InputContainer input;
	private AtomicBoolean running;
	private DirectiveContainer directives;
	
	public TCPClient(InputContainer _input, AtomicBoolean _running, DirectiveContainer _directives) {
		input = _input;
		running = _running;
		directives = _directives;
	}
	
	public void connect(String _ip, int _port) {
		ip = _ip;
		port = _port;
		try {
			running.set(true);
			socket = new Socket(ip,port);
			
			sender = new SendToServer(socket,input,running);
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
