package com.dezzy.skorp3.file;

import static org.junit.jupiter.api.Assertions.*;
import static com.dezzy.skorp3.file.Configs.globalVars;
import static com.dezzy.skorp3.file.Configs.globalKeys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
class ConfigTest {

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void test() {
		assertEquals(globalVars.get("name"),"Dezzy");
		
	}

}
