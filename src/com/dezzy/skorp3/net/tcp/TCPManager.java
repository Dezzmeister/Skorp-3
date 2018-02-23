package com.dezzy.skorp3.net.tcp;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dezzy.skorp3.net.DirectiveContainer;
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
 * @see com.dezzy.skorp3.net.DirectiveContainer
 */
public class TCPManager {
	public volatile DirectiveContainer directives;
	private volatile InputStream sendMessage = new ByteArrayInputStream("".getBytes());
	private volatile AtomicBoolean running;
	private TCPServer server;
	private TCPClient client;
	public boolean isServer;
	
	public TCPManager(boolean _isServer) {
		directives = new DirectiveContainer();
		running = new AtomicBoolean(false);
		isServer = _isServer;
		if (isServer) {
			server = new TCPServer(sendMessage,running,directives);
		} else {
			client = new TCPClient(sendMessage,running,directives);
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
	
	public synchronized void send(String message) {
		sendMessage = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
	}
}
