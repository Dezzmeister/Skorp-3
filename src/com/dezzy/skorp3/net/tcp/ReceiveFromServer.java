package com.dezzy.skorp3.net.tcp;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.DirectiveContainer;

class ReceiveFromServer implements Runnable {
	private Socket socket = null;
	private BufferedReader reader = null;
	private AtomicBoolean running;
	private DirectiveContainer directives;
	
	public ReceiveFromServer(Socket _socket, AtomicBoolean _running, DirectiveContainer _directives) {
		socket = _socket;
		running = _running;
		directives = _directives;
	}
	
	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String data = null;
			while ((data = reader.readLine())!=null && running.get()) {
				if (data!=null && data.indexOf("null")==-1) {
					String header = data.substring(0,data.indexOf(" ")!=-1?data.indexOf(" "):data.length());
					directives.findAndExecuteClientDirective(header,data);
					System.out.println(data);
				}
			}
		} catch(Exception e) {
			running.set(false);
			e.printStackTrace();
		}
	}
}
