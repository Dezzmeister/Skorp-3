package com.dezzy.skorp3.field3D;

import java.awt.Color;
import java.util.List;

import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.geometry3D.AARectangle;
import com.dezzy.skorp3.geometry3D.Plane;
import com.dezzy.skorp3.geometry3D.Triangle;

public class AARectPrism extends Geometric3D {
	
	{
		shape = Shape3D.RECTPRISM;
	}
	public AARectPrism(double x, double y, double z, Color _color) {
		super(x, y, z);
		color = _color;
	}
	
	public AARectPrism(double x, double y, double z, int width, int height, int length, Color _color) {
		super(x,y,z,width,height,length);
		color = _color;
	}

	@Override
	public List<Triangle> decompose() {
		double x = point.x;
		double y = point.y;
		double z = point.z;
		
		AARectangle front = new AARectangle(x,y,z + (length/2),width,height,Plane.XY,color);
		AARectangle back = new AARectangle(x,y,z - (length/2),width,height,Plane.XY,color);
		
		AARectangle bottom = new AARectangle(x,y + (height/2),z,width,length,Plane.XZ,color);
		AARectangle top = new AARectangle(x,y - (height/2),z,width,length,Plane.XZ,color);
		
		AARectangle left = new AARectangle(x - (width/2),y,z,height,length,Plane.YZ,color);
		AARectangle right = new AARectangle(x + (width/2),y,z,height,length,Plane.YZ,color);
		
		return Triangle.addTriangles(front.decompose(), back.decompose(),
									    bottom.decompose(), top.decompose(),
									    left.decompose(), right.decompose());
	}

	@Override
	public String encode() {
		// TODO Auto-generated method stub
		return null;
	}
}
