package com.dezzy.skorp3.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReceiver implements Runnable {
	private int packetSize = 128;
	private int port;
	private DatagramSocket socket;
	boolean isServer = false;
	
	public UDPReceiver(int _port) {
		port = _port;
	}
	
	public void acceptData() {
		try {
			socket = new DatagramSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UDPManager.receiverRunning = true;
	}
	
	@Override
	public void run() {
		while (true) {
			if (UDPManager.receiverRunning) {
				byte[] incomingPacket = new byte[packetSize];
				DatagramPacket packet = new DatagramPacket(incomingPacket,incomingPacket.length);
				try {
					socket.receive(packet);
					String data = new String(packet.getData());
					String header = (data.indexOf(" ")!=-1 && data.length()>=3) ? data.substring(0,data.indexOf(" ")) : null;
					if (header != null) {
						if (this.isServer) {
							UDPManager.executeServerDirective(header);
						} else {
							UDPManager.executeClientDirective(header);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				if (socket!=null) {
					socket.close();
				}
			}
		}
	}
	
	public void setPacketSize(int size) {
		packetSize = (size < 65508 && size > 0) ? size : packetSize;
	}
	
	public int getPacketSize() {
		return packetSize;
	}
}
