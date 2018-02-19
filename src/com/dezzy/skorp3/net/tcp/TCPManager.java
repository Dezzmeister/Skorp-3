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
 * TCPManager should only ever have one TCPServer or TCPClient, never both.
 * 
 * @author Dezzmeister
 * @see StringActor
 *
 */
public class TCPManager {
	//Might not need a StringActor, Consumer<String> MAY work
	static Map<String,StringActor<?>> clientDirectives = new HashMap<String,StringActor<?>>();
	static Map<String,StringActor<?>> serverDirectives = new HashMap<String,StringActor<?>>();
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
		}
	}
	
	public void connect(String ip, int port) {
		if (client != null) {
			client.connect(ip, port);
		}
	}
	
	public static <T> void addClientDirective(String header, StringActor<T> action) {
		clientDirectives.put(header,action);
	}
	
	public static <T> void addServerDirective(String header, StringActor<T> action) {
		serverDirectives.put(header, action);
	}
	
	public static Object executeClientDirective(String header) {
		return clientDirectives.get(header).act(header);
	}
	
	public static Object executeServerDirective(String header) {
		return serverDirectives.get(header).act(header);
	}
	
	public synchronized void send(String message) {
		sendMessage = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
	}
}
