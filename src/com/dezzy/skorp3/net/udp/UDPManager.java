package com.dezzy.skorp3.net.udp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class UDPManager {
	private static Map<String,Consumer<String>> serverDirectives = new HashMap<String,Consumer<String>>();
	private static Map<String,Consumer<String>> clientDirectives = new HashMap<String,Consumer<String>>();
	
	public static volatile String message = "";
	public static volatile boolean senderRunning = false;
	public static volatile boolean receiverRunning = false;
	public static volatile boolean isServer = false;
	
	public UDPManager(boolean _isServer) {
		isServer = _isServer;
		
	}
	
	public void send(String msg) {
		message = msg;
	}
	
	public static void addClientDirective(String header, Consumer<String> action) {
		clientDirectives.put(header,action);
	}
	
	public static void addServerDirective(String header, Consumer<String> action) {
		serverDirectives.put(header, action);
	}
	
	public static void executeClientDirective(String header) {
		try {
			clientDirectives.get(header).accept(header);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void executeServerDirective(String header) {
		try {
			serverDirectives.get(header).accept(header);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
