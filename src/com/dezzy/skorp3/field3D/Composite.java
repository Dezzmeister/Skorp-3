package com.dezzy.skorp3.field3D;

import java.util.List;

import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.math3D.Vertex;

public class Composite extends Geometric3D {
	private VBO3D vbo = new VBO3D("vbo");
	
	{
		shape = Shape3D.MODEL;
	}
	
	public Composite(double x, double y, double z) {
		super(x, y, z);
	}
	
	public void add(Entity3D shape) {
		vbo.add(shape);
	}

	@Override
	public List<Vertex> decompose() {
		// TODO Auto-generated method stub
		return null;
	}
}
