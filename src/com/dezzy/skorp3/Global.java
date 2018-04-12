package com.dezzy.skorp3;

import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.annotations.Processors;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.game3D.VBO3DList;
import com.dezzy.skorp3.skorp3D.data.VBO;
import com.dezzy.skorp3.skorp3D.data.VBOList;
import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;

/**
 * Global should be used to hold global variables/objects that will be used throughout Skorp.
 * For example, there should always be a main VBO.
 * 
 * @author Dezzmeister
 *
 */
public final class Global {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	
	public static final VBO3D mainVBO = new VBO3D("main");
	public static final MouseData mouseData = new MouseData(WIDTH,HEIGHT);
	
	static {
		Processors.activate();
		True3D.renderList.add(mainVBO);
		
		True3D.VBOLIST.add(True3D.VBO);
	}
	
	private Global() {

	}
	
	public static class True3D {
		public static final VBO3DList renderList = new VBO3DList();
		public static final Data3D data3D = new Data3D(false,70);
		
		public static final VBO VBO = new VBO();
		public static final VBOList VBOLIST = new VBOList();
	}
	
	public static class Raycast {
		public static WorldMap mainMap;
	}
}
