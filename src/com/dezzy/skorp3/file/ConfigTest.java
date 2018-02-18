package com.dezzy.skorp3.file;

import static org.junit.jupiter.api.Assertions.*;
import static com.dezzy.skorp3.file.Config.globalVars;
import static com.dezzy.skorp3.file.Config.globalKeys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigTest {

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void test() {
		assertEquals(globalVars.get("name"),"Dezzy");
		assertEquals(globalVars.get("testVariable"),"Sauce Boss");
		assertEquals(globalVars.size(),2);
		
		assertTrue(globalKeys.get("randomcontrol")=='t');
	}

}
