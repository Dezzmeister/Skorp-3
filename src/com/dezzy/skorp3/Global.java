package com.dezzy.skorp3;

import com.dezzy.skorp3.game3D.VBO3D;

/**
 * Global should be used to hold global variables/objects that will be used throughout Skorp.
 * For example, there should always be a main VBO.
 * 
 * @author Dezzmeister
 *
 */
public final class Global {
	public static final VBO3D mainVBO = new VBO3D("main");
	
	
	private Global() {
		
	}
}
