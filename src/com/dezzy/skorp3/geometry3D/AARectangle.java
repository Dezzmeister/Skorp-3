package com.dezzy.skorp3.geometry3D;

import java.awt.Color;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1280555775726110766L;

	private Plane plane;
	
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
	
	public AARectangle(Vertex _point, int _width, int _height, Plane _plane, Color _color) {
		point = _point;
		width = _width;
		height = _height;
		plane = _plane;
		color = _color;
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
		
		vertices.add(new Vertex(x - (width/2),y,z + (height/2)));
		vertices.add(new Vertex(x + (width/2),y,z + (height/2)));
		vertices.add(new Vertex(x - (width/2),y,z - (height/2)));
		vertices.add(new Vertex(x + (width/2),y,z - (height/2)));
		return Triangle.addTriangles(new Triangle(vertices.get(0),vertices.get(1),vertices.get(2),color),
				 new Triangle(vertices.get(2),vertices.get(3),vertices.get(1),color));
	}
	
	private List<Triangle> decomposeXY() {
		double x = point.x;
		double y = point.y;
		double z = point.z;
		vertices.add(new Vertex(x - (width/2),y + (height/2),z));
		vertices.add(new Vertex(x + (width/2),y + (height/2),z));
		vertices.add(new Vertex(x - (width/2),y - (height/2),z));
		vertices.add(new Vertex(x + (width/2),y - (height/2),z));
		return Triangle.addTriangles(new Triangle(vertices.get(0),vertices.get(1),vertices.get(2),color),
									 new Triangle(vertices.get(2),vertices.get(3),vertices.get(1),color));
	}
	
	private List<Triangle> decomposeYZ() {
		double x = point.x;
		double y = point.y;
		double z = point.z;
		vertices.add(new Vertex(x,y + (width/2),z + (height/2)));
		vertices.add(new Vertex(x,y + (width/2),z - (height/2)));
		vertices.add(new Vertex(x,y - (width/2),z + (height/2)));
		vertices.add(new Vertex(x,y - (width/2),z - (height/2)));
		return Triangle.addTriangles(new Triangle(vertices.get(0),vertices.get(1),vertices.get(2),color),
				 new Triangle(vertices.get(2),vertices.get(3),vertices.get(1),color));
	}
}
