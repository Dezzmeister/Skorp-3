package com.dezzy.skorp3.skorp3D.true3D2.core;

import com.dezzy.skorp3.math3D.Vertex;

public class Mesh {
	public Vertex[] vertices;
	public Vertex pos;
	public Vertex rotation;
	
	public Mesh(Vertex[] _vertices) {
		vertices = _vertices;
	}
	
	public Mesh(int size) {
		vertices = new Vertex[size];
	}
	
	public Mesh set(int index, int x, int y, int z) {
		vertices[index] = new Vertex(x,y,z);
		return this;
	}
}
