package com.dezzy.skorp3.net.tcp;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.dezzy.skorp3.net.StringActor;

/**
 * TCPManager will control a Client-Server TCP connection using "directives."
 * Directives are just blocks of code that will run on either the Client or the Server when an associated header is received.
 * CLARIFICATION: Server directives will be run BY THE SERVER when a header is received FROM THE CLIENT
 * Directive actions will have one parameter, a String containing the received message (without the header). The code provided as an action
 * will act on the String appropriately, and may return something if need be. 
 * 
 * The InputStream sendMessage is shared across the main thread and the client/server sending thread.
 * Calls to send() should update sendMessage and the sending thread should react appropriately.
 * 
 * One TCPManager should only ever have one TCPServer or TCPClient, never both.
 * 
 * @author Dezzmeister
 * @see StringActor
 *
 */
public class TCPManager {
	//Might not need a StringActor, Consumer<String> MAY work
	static Map<String,StringActor<? extends Object>> clientDirectives = new HashMap<String,StringActor<? extends Object>>();
	static Map<String,StringActor<? extends Object>> serverDirectives = new HashMap<String,StringActor<? extends Object>>();
	static volatile InputStream sendMessage = new ByteArrayInputStream("".getBytes());
	static volatile boolean running = false;
	private TCPServer server;
	private TCPClient client;
	public boolean isServer;
	
	public TCPManager(boolean _isServer) {
		isServer = _isServer;
		if (isServer) {
			server = new TCPServer();
		} else {
			client = new TCPClient();
		}
	}
	
	public void open(int port) {
		if (server != null) {
			server.connect(port);
		} else {
			System.out.println("Use TCPManager.connect instead of TCPManager.open");
		}
	}
	
	public void connect(String ip, int port) {
		if (client != null) {
			client.connect(ip, port);
		} else {
			System.out.println("Use TCPManager.open instead of TCPManager.connect");
		}
	}
	
	public static <T> void addClientDirective(String header, StringActor<T> action) {
		clientDirectives.put(header,action);
	}
	
	public static <T> void addServerDirective(String header, StringActor<T> action) {
		serverDirectives.put(header, action);
	}
	
	public static Object executeClientDirective(String header) {
		try {
			return clientDirectives.get(header).act(header);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Object executeServerDirective(String header) {
		try {
			return serverDirectives.get(header).act(header);
		} catch (Exception e) {
			return null;
		}
	}
	
	public synchronized void send(String message) {
		sendMessage = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
	}
}
