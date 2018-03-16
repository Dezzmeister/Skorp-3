package com.dezzy.skorp3.field3D;

import com.dezzy.skorp3.game.Triple;

public class Geometric3D extends Entity3D {
	public int height;	//Y axis
	public int width;	//X axis
	public int length;	//Z axis
	
	public Geometric3D(double x, double y, double z)	{
		point = new Triple<Double>(x,y,z);
	}
	
	@Override
	public String encode() {
		// TODO Auto-generated method stub
		return null;
	}

}
