package com.dezzy.skorp3.file;

public class Configs {
	public static Config<String> globalVars = new Config<>("config/config.txt",String.class);
	public static Config<Character> globalKeys = new Config<>("config/keyconfig.txt",Character.class);
	
	
	
}
