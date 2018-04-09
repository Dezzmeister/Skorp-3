package com.dezzy.skorp3.skorp3D.geometry;

import java.awt.Color;

import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.skorp3D.primitive.MatrixTransformable;
import com.dezzy.skorp3.skorp3D.primitive.Transformable;
import com.dezzy.skorp3.skorp3D.primitive.Triangle;

@SuppressWarnings("unused")
public abstract class Entity extends MatrixTransformable {
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
	
	/**
	 * Internal state of the Entity. Do not modify directly, use <code>update()</code>.
	 */
	private boolean updated = true;
	
	/**
	 * The number of triangles in the <code>Entity</code>. Should be maintained by the <code>Entity</code> itself.
	 */
	public int triangleCount;
	
	private Color tex;
	
	protected Entity(Color _tex) {
		tex = _tex;
	}
	
	protected Entity() {
		
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
	
	public Color texture() {
		return tex;
	}
	
	public void setTexture(Color _texture) {
		tex = _texture;
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
