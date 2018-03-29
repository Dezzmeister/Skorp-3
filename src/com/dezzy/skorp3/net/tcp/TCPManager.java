package com.dezzy.skorp3.net.tcp;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import com.dezzy.skorp3.net.DirectiveContainer;
import com.dezzy.skorp3.net.End;
import com.dezzy.skorp3.net.InputContainer;
import com.dezzy.skorp3.net.StringActor;

/**
 * TCPManager will control a Client-Server TCP connection using "directives."
 * Directives are just blocks of code that will run on either the Client or the Server when an associated header is received.
 * CLARIFICATION: Server directives will be run BY THE SERVER when a header is received FROM THE CLIENT
 * Directive actions will have one parameter, a String containing the received message (without the header). The code provided as an action
 * will act on the String appropriately, and may return something if need be. 
 * 
 * The InputContainer input contains an InputStream which is updated whenever send() is called. SendToClient and SendToServer
 * both have pointers (references) to this InputContainer, so when InputStream changes they can react appropriately and send a message.
 * 
 * One TCPManager should only ever have one TCPServer or TCPClient, never both.
 * 
 * @author Dezzmeister
 * @see StringActor
 * @see com.dezzy.skorp3.net.DirectiveContainer
 */
public class TCPManager {
	private volatile DirectiveContainer directives;
	private volatile InputContainer input;
	private volatile AtomicBoolean running;
	private TCPServer server;
	private TCPClient client;
	private boolean isServer;
	
	public TCPManager(End end) {
		directives = new DirectiveContainer();
		input = new InputContainer();
		running = new AtomicBoolean(false);
		if (end == End.SERVER) {
			isServer = true;
			server = new TCPServer(input,running,directives);
		} else {
			isServer = false;
			client = new TCPClient(input,running,directives);
		}
	}
	
	public void open(int port) {
		if (isServer) {
			server.connect(port);
		} else {
			System.out.println("Use TCPManager.connect instead of TCPManager.open");
		}
	}
	
	public void connect(String ip, int port) {
		if (!isServer) {
			client.connect(ip, port);
		} else {
			System.out.println("Use TCPManager.open instead of TCPManager.connect");
		}
	}
	
	public synchronized void send(String message) {
		input.set(message);
	}
	
	public synchronized void addDirective(String header, Consumer<String> action) {
		if (isServer) {
			directives.addConstantServerDirective(header, action);
		} else {
			directives.addConstantClientDirective(header, action);
		}
	}
	
	public boolean isServer() {
		return isServer;
	}
}
