package com.dezzy.skorp3.UI;

import com.dezzy.skorp3.skorp3D.data.VBO;

public class MouseVBOTransformer implements Runnable {
	private volatile MouseData mouse;
	private volatile VBO vbo;
	private volatile boolean enabled = true;
	
	public MouseVBOTransformer(MouseData _mouse, VBO _vbo) {
		mouse = _mouse;
		vbo = _vbo;
	}
	
	@Override
	public void run() {
		while(true) {
			if (mouse.hasUpdated() && enabled) {
				rotateAroundOrigin();
			}
		}
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
	
	public void rotateAroundOrigin() {
		int deltaX = -mouse.dx();
		
		double norm = deltaX/(double)mouse.width();
		double angle = norm*360.0;
		vbo.rotateY(angle);
		
		int deltaY = mouse.dy();
		norm = deltaY/(double)mouse.height();
		angle = norm*360.0;
		vbo.rotateX(angle);
		
		vbo.apply();
	}
}
