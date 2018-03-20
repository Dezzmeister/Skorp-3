package com.dezzy.skorp3.game3D;

import com.dezzy.skorp3.math3D.Vertex;

public class Camera {
	private Vertex center;
	private Vertex facing;
	
	public Camera(Vertex _center, Vertex _facing) {
		center = _center;
		facing = _facing;
	}
	
	public Vertex center() {
		return center;
	}
	
	public Vertex facing() {
		return facing;
	}
}
