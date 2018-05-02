package com.dezzy.skorp3.skorp3D.raycast2.gpu;

import com.aparapi.Kernel;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast2.core.Linetype;
import com.dezzy.skorp3.skorp3D.raycast2.core.RenderUtils;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;

public class RaycastTask extends Kernel {
	private int WIDTH;
	private int HEIGHT;
	private int[] img;
	Linetype vec1 = new Wall(5,5,4,6);
	Linetype vec2 = new Wall(4,4,5,8);
	
	public RaycastTask(int _width, int _height) {
		WIDTH = _width;
		HEIGHT = _height;
	}
	
	@Override
	public void run() {
		float vfloat = 5.5f;
	}

}
