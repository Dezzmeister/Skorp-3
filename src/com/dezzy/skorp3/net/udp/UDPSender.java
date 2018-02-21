package com.dezzy.skorp3.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPSender implements Runnable {
	private DatagramSocket socket;
	private InetAddress address;

	private int port;
	
	public UDPSender(String ip, int _port) throws UnknownHostException {
		port = _port;
		address = InetAddress.getByName(ip);
	}
	
	public void acceptData() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		UDPManager.senderRunning = true;
	}

	@Override
	public void run() {
		while (true) {
			if (UDPManager.senderRunning) {
				if (UDPManager.message != null) {
					byte[] message = UDPManager.message.getBytes();
					DatagramPacket packet = new DatagramPacket(message,message.length,address,port);
					try {
						socket.send(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (socket!=null) {
					socket.close();
				}
			}
		}
	}
}
