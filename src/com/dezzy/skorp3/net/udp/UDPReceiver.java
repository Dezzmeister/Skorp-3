package com.dezzy.skorp3.net.udp;

import java.net.DatagramSocket;

public class UDPReceiver implements Runnable {
	private int packetSize = 128;
	DatagramSocket socket;
	
	public UDPReceiver(int port) {
		
	}
	
	@Override
	public void run() {
				
	}
	
	public void setPacketSize(int size) {
		packetSize = (size < 65508 && size > 0) ? size : packetSize;
	}
	
	public int getPacketSize() {
		return packetSize;
	}
}
