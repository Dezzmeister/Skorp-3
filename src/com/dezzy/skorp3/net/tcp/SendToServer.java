package com.dezzy.skorp3.net.tcp;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class SendToServer implements Runnable {
	Socket socket = null;
	PrintWriter writer = null;
	BufferedReader reader = null;
	
	public SendToServer(Socket _socket) {
		socket = _socket;
	}
	
	public void run() {
		try {
			if (socket.isConnected()) {
				System.out.println("Client connected to "+socket.getInetAddress()+":"+socket.getPort());
				writer = new PrintWriter(socket.getOutputStream(),true);
				while (true && TCPManager.running) {
					if (TCPManager.sendMessage !=null) {
						reader = new BufferedReader(new InputStreamReader(TCPManager.sendMessage));
						String message = null;
						message = reader.readLine();
						if (message != null) {
							writer.println(message);
							writer.flush();
					
							if (message.equals("exit")) {
								TCPManager.running = false;
								break;
							}
						}
					}
				}
				socket.close();
			}
		} catch (Exception e) {
			TCPManager.running = false;
			e.printStackTrace();
		}
	}
}
