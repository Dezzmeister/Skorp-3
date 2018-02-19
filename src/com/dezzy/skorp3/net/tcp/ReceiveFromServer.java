package com.dezzy.skorp3.net.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

class ReceiveFromServer implements Runnable {
	Socket socket = null;
	BufferedReader reader = null;
	
	public ReceiveFromServer(Socket _socket) {
		socket = _socket;
	}
	
	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String data = null;
			while ((data = reader.readLine())!=null) {
				//handle it
				System.out.println(data);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
