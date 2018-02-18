package com.dezzy.skorp3.net;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TCPTest {
	TCPManager manager;

	@BeforeEach
	void setUp() throws Exception {
		manager = new TCPManager();
	}

	@Test
	void testTCPManager() {
		manager.<Integer>addClientDirective("header",s->5);
		assertEquals(manager.executeClientDirective("header"),5);
	}

	@Test
	void testAddClientDirective() {
		
	}

	@Test
	void testAddServerDirective() {
		
	}

	@Test
	void testExecuteClientDirective() {

	}

	@Test
	void testExecuteServerDirective() {
		
	}

}
