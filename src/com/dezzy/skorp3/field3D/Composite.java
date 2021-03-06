package com.dezzy.skorp3.field3D;

import java.util.List;

import com.dezzy.skorp3.annotations.urgency.Urgency;
import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.geometry3D.Triangle;

public class Composite extends Geometric3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1554972797781794548L;
	private VBO3D vbo = new VBO3D("Composite VBO");
	
	{
		shape = Shape3D.COMPOSITE;
	}
	
	public Composite(double x, double y, double z) {
		super(x, y, z);
	}
	
	public void add(Entity3D shape) {
		vbo.add(shape);
	}
	
	@Override
	public List<Triangle> getTriangles() {
		return vbo.getVBO();
	}

	@Override
	public List<Triangle> decompose() {
		return vbo.getVBO();
	}

	@Override
	public String encode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyTransformations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Urgency(1)
	public List<Triangle> getTransformedTriangles() {
		// TODO Auto-generated method stub
		return null;
	}
}
