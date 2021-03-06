package com.dezzy.skorp3.geometry3D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.GPU.GPUKernel;
import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.game3D.Shape3D;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;

/**
 * The building block of every shape. Renderer3D's barycentricRaster renders Triangles, so every Entity3D must be able to 
 * provide a list of Triangles representing the shape.
 * 
 * @author Dezzmeister
 *
 */
public class Triangle extends Entity3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2882166168334107220L;
	public Color color;
	public Vertex v1;
	public Vertex v2;
	public Vertex v3;

	{
		shape = Shape3D.TRIANGLE;
	}
	
	public Triangle(Vertex _v1, Vertex _v2, Vertex _v3, Color _color) {
		v1 = _v1;
		v2 = _v2;
		v3 = _v3;
		color = _color;
	}

	@Override
	public String encode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void transformAll() {
		Matrix4 u = computeTransformationMatrix();
		v1 = u.transform(v1);
		v2 = u.transform(v2);
		v3 = u.transform(v3);
	}
	
	@Override
	public void transformAllGPU() {
    	Matrix4 u1 = computeTransformationMatrix();
    	List<Vertex> vertices = new ArrayList<Vertex>();
    	vertices.add(v1);
    	vertices.add(v2);
    	vertices.add(v3);
    	vertices = GPUKernel.transformVertices(u1, vertices);
    	v1 = vertices.get(0);
    	v2 = vertices.get(1);
    	v3 = vertices.get(2);
    }
	
	@Override
	public List<Triangle> getTriangles() {
		return addTriangles(this);
	}

	@Override
	public List<Triangle> decompose() {
		List<Triangle> result = new ArrayList<Triangle>();
		result.add(this);
		return result;
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
	
	/**
	 * Checks if all of a triangle's vertices have positive z coordinates.
	 * 
	 * @param triangle Triangle to be checked
	 * @return boolean if all vertices' z values are greater than 0
	 */
	public static boolean checkIfPositiveZ(Triangle triangle) {
		double z1 = triangle.v1.z;
		double z2 = triangle.v2.z;
		double z3 = triangle.v3.z;
		
		return (z1 > 0) && (z2 > 0) && (z3 > 0);
	}

	@Override
	public void applyTransformations() {
		update();
		Matrix4 u1 = stack.collapse();
		v1 = u1.transform(v1);
		v2 = u1.transform(v2);
		v3=  u1.transform(v3);
	}
	
	@Override
	public List<Triangle> getTransformedTriangles() {
		return addTriangles(this);
	}
}
