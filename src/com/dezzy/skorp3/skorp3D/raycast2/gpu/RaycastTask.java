package com.dezzy.skorp3.skorp3D.raycast2.gpu;

import com.aparapi.Kernel;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;

public class RaycastTask extends Kernel {
	private int WIDTH;
	private int HEIGHT;
	private int[] img;
	Vector2 vec = new Vector2(5,5);
	Vector2 vec2 = new Vector2(4,4);
	
	public RaycastTask(int _width, int _height) {
		WIDTH = _width;
		HEIGHT = _height;
	}
	
	@Override
	public void run() {
		
	}

}
