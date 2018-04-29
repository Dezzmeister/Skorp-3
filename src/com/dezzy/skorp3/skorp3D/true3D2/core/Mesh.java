package com.dezzy.skorp3.skorp3D.true3D2.core;

import com.dezzy.skorp3.math3D.Vertex;

public class Mesh {
	public Vector4[] vertices;
	public Vertex pos;
	public Vertex rotation;
	
	public Mesh(Vector4[] _vertices) {
		vertices = _vertices;
	}
	
	public Mesh(int size) {
		vertices = new Vector4[size];
	}
	
	public Mesh set(int index, int x, int y, int z) {
		vertices[index] = new Vector4(x,y,z);
		return this;
	}
}
