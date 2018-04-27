package com.dezzy.skorp3.skorp3D.true3D2.render;

import com.dezzy.skorp3.math3D.Vertex;

public class Camera {
	public Vertex pos;
	public Vertex dir;
	
	public Camera(Vertex _pos, Vertex _dir) {
		pos = _pos;
		dir = _dir;
	}
}
