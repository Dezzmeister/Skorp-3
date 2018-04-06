package com.dezzy.skorp3.skorp3D.geometry;

import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.datastructures.Stack;
import com.dezzy.skorp3.skorp3D.graphic.Texture;
import com.dezzy.skorp3.skorp3D.primitive.Transformable;
import com.dezzy.skorp3.skorp3D.primitive.Triangle;

@SuppressWarnings("unused")
public abstract class Entity extends Transformable {
	/**
	 * Center x-value of this Entity.
	 */
	private int x;
	/**
	 * Center y-value of this Entity.
	 */
	private int y;
	/**
	 * Center z-value of this Entity.
	 */
	private int z;
	
	private boolean updated = true;
	
	public int triangleCount;
	
	private Texture tex;
	
	protected Stack<Matrix4> stack = new Stack<Matrix4>(Matrix4::collapse, 20);
	
	@Override
	public void rotateX(double deg) {
		stack.push(Matrix4.getXRotationMatrix(deg));
	}
	
	@Override
	public void rotateY(double deg) {
		stack.push(Matrix4.getYRotationMatrix(deg));
	}
	
	@Override
	public void rotateZ(double deg) {
		stack.push(Matrix4.getZRotationMatrix(deg));
	}
	
	@Override
	public void translate(double x, double y, double z) {
		stack.push(Matrix4.getTranslationMatrix(x,y,z));
	}
	
	@Override
	public void scale(double x, double y, double z) {
		stack.push(Matrix4.getScalingMatrix(x, y, z));
	}
	
	@Override
	public void update() {
		updated = true;
	}
	
	@Override
	public boolean hasUpdated() {
		if (updated) {
			updated = false;
			return true;
		}
		return false;
	}
	
	public Texture texture() {
		return tex;
	}
	
	public void setTexture(Texture _texture) {
		tex = _texture;
	}
	
	protected Matrix4 getTransformationMatrix() {
		return stack.collapse();
	}
	
	/**
	 * Returns an array of Triangles representing the shape.
	 * These Triangles will not have been updated since the last call to <code>apply()</code>.
	 * 
	 * @return an array of Triangles since last call to <code>apply()</code>
	 * @see Transformable#apply()
	 */
	public abstract Triangle[] getTriangles();
}
