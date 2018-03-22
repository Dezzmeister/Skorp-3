package com.dezzy.skorp3.geometry3D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.math3D.Vertex;

/**
 * Axis-Aligned rectangle. 
 * When determining whether width or length is X, Y, or Z, remember that if there is a Z dimension, it will always be length.
 * If not, then Y is length and X is width. X is always width if it is present.
 * 
 * @author Dezzmeister
 *
 */
public class AARectangle extends Entity3D {
	public Plane plane;
	/**
	 * X or Y
	 */
	public int width = 0;
	/**
	 * Y or Z
	 */
	public int height = 0;
	
	{
		shape = Shape3D.RECTANGLE;
	}
	
	public AARectangle(double x, double y, double z, Color _color) {
		super(x, y, z);
		color = _color;
	}
	
	public AARectangle(double x, double y, double z, int _width, int _height, Plane _plane, Color _color) {
		super(x,y,z);
		width = _width;
		height = _height;
		plane = _plane;
		color = _color;
	}
	
	@Override
	public String encode() {
		
		return null;
	}

	@Override
	public List<Triangle> decompose() {
		
		switch (plane) {
		case XZ:
			return decomposeXZ();
		case XY:
			return decomposeXY();
		case YZ:
		default:
			return decomposeYZ();
		}
	}
	
	private List<Triangle> decomposeXZ() {
		double x = point.x;
		double y = point.y;
		double z = point.z;
		Vertex v1 = new Vertex(x - (width/2),y,z + (height/2));
		Vertex v2 = new Vertex(x + (width/2),y,z + (height/2));
		Vertex v3 = new Vertex(x - (width/2),y,z - (height/2));
		Vertex v4 = new Vertex(x + (width/2),y,z - (height/2));
		Triangle t1 = new Triangle(v1,v2,v3,color);
		Triangle t2 = new Triangle(v3,v4,v2,color);
		return addTriangles(t1,t2);
	}
	
	private List<Triangle> decomposeXY() {
		double x = point.x;
		double y = point.y;
		double z = point.z;
		Vertex v1 = new Vertex(x - (width/2),y + (height/2),z);
		Vertex v2 = new Vertex(x + (width/2),y + (height/2),z);
		Vertex v3 = new Vertex(x - (width/2),y - (height/2),z);
		Vertex v4 = new Vertex(x + (width/2),y - (height/2),z);
		Triangle t1 = new Triangle(v1,v2,v3,color);
		Triangle t2 = new Triangle(v3,v4,v2,color);
		return addTriangles(t1,t2);
	}
	
	private List<Triangle> decomposeYZ() {
		double x = point.x;
		double y = point.y;
		double z = point.z;
		Vertex v1 = new Vertex(x,y + (width/2),z + (height/2));
		Vertex v2 = new Vertex(x,y + (width/2),z - (height/2));
		Vertex v3 = new Vertex(x,y - (width/2),z + (height/2));
		Vertex v4 = new Vertex(x,y - (width/2),z - (height/2));
		Triangle t1 = new Triangle(v1,v2,v3,color);
		Triangle t2 = new Triangle(v3,v4,v2,color);
		return addTriangles(t1,t2);
	}
	
	public static List<Triangle> addTriangles(Triangle ... triangles) {
		List<Triangle> result = new ArrayList<Triangle>();
		for (Triangle t : triangles) {
			result.add(t);
		}
		return result;
	}
	
	@SafeVarargs
	public static List<Triangle> addTriangles(List<Triangle> ... lists) {
		List<Triangle> result = new ArrayList<Triangle>();
		
		for (List<Triangle> l : lists) {
			result.addAll(l);
		}
		
		return result;
	}
}
