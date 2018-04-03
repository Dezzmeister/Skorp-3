package com.dezzy.skorp3.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class Load {
	
	private Load() {
		
	}

	public static Stream<String> load(String path) {
		try {
			return Files.newBufferedReader(Paths.get(path)).lines();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
