package com.dezzy.skorp3.skorp3D.true3D2.core;

import java.awt.Color;

import com.dezzy.skorp3.math3D.Vertex;

/**
 * Another Triangle class for another renderer.
 * 
 * @author Dezzmeister
 *
 */
public class Triangle {
	public Vertex v1;
	public Vertex v2;
	public Vertex v3;
	public Color color;
	
	public Triangle(Vertex _v1, Vertex _v2, Vertex _v3, Color _color) {
		v1 = _v1;
		v2 = _v2;
		v3 = _v3;
		color = _color;
	}
}
