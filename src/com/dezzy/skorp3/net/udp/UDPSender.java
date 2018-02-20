package com.dezzy.skorp3.net.udp;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSender implements Runnable {
	private DatagramSocket socket;
	private InetAddress address;
	
	private int packetSize = 128;
	
	public UDPSender(String ip) {
		try {
			address = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String message) {
		byte[] messageBytes = message.getBytes();
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
