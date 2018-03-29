package com.dezzy.skorp3;

import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.game3D.VBO3DList;

/**
 * Global should be used to hold global variables/objects that will be used throughout Skorp.
 * For example, there should always be a main VBO.
 * 
 * @author Dezzmeister
 *
 */
public final class Global {
	public static final VBO3D mainVBO = new VBO3D("main");
	public static final MouseData mouseData = new MouseData();
	public static final VBO3DList renderList = new VBO3DList();
	public static final Data3D data3D = new Data3D(true,90);
	
	static {
		renderList.add(mainVBO);
	}
	
	private Global() {
		
	}
}
