package com.dezzy.skorp3.file;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to load config files and store their contents in global HashMaps. 
 * At the moment, it only loads a config file containing global variables.
 * 
 * @author Dezzmeister
 *
 */
//TODO Add methods that will reset the config file if it has been corrupted.
public class Config {
	public static Map<String, String> globalVars = new HashMap<String,String>();
	public static Map<String, Character> globalKeys = new HashMap<String,Character>();
	
	static {
		loadFiles();
	}
	
	public static void loadFiles() {
		readConfig("config/config.txt");
		readKeyConfig("config/keyconfig.txt");
	}
	
	public static void readConfig(String path) {
		Load.load(path).forEach(Config::formatAndStoreConfigEntry);
	}
	
	public static void readKeyConfig(String path) {
		Load.load(path).forEach(Config::formatAndStoreKeyConfigEntry);
	}
	
	/**
	 * Takes a string like "valName=value" (given from the config file) and formats it, 
	 * then adds it to globalVars like (valName,value).
	 * 
	 * @param entry String to be formatted
	 */
	public static void formatAndStoreConfigEntry(String entry) {
		String entryName = entry.substring(0,entry.indexOf("="));
		String entryValue = entry.substring(entry.indexOf("=")+1);
		globalVars.put(entryName, entryValue);
	}
	
	public static void formatAndStoreKeyConfigEntry(String entry) {
		String entryName = entry.substring(0,entry.indexOf("="));
		char entryValue = entry.charAt(entry.indexOf("=")+1);
		globalKeys.put(entryName, entryValue);
	}
}
