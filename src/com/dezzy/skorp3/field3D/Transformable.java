package com.dezzy.skorp3.field3D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.GPU.GPUKernel;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.math3D.datastructures.Stack;

/**
 * Represents an object with a Matrix stack and the capability to transform in 3D space.
 * 
 * @author Dezzmeister
 *
 */
public abstract class Transformable implements Serializable, Transformable3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6355738060061476562L;
	private static final int STACK_MAX_SIZE = 20;
	protected Stack<Matrix4> stack  = new Stack<Matrix4>(Matrix4::collapse,STACK_MAX_SIZE);
	protected List<Vertex> vertices = new ArrayList<Vertex>();
	protected transient boolean updated = false;
	
	public void rotateX(double deg) {
		Matrix4 rot = Matrix4.getXRotationMatrix(deg);
		
		stack.push(rot);
	}
	
	public void rotateY(double deg) {
		Matrix4 rot = Matrix4.getYRotationMatrix(deg);
		
		stack.push(rot);
	}
	
	public void rotateZ(double deg) {
		Matrix4 rot = Matrix4.getZRotationMatrix(deg);
		
		stack.push(rot);
	}
	
	public void translate(double x, double y, double z) {
		Matrix4 trans = Matrix4.getTranslationMatrix(x, y, z);
		
		stack.push(trans);
	}
	
    public void scale(double x, double y, double z) {
		Matrix4 scale = Matrix4.getScalingMatrix(x, y, z);
		
		stack.push(scale);
	}
    
    public Matrix4 computeTransformationMatrix() {
    	Matrix4 u1 =  stack.collapse();
    	stack.clear();
    	return u1;
    }
    
    /**
     * Computes the transformation matrix from the stack and applies the transformation to all
     * vertices using the CPU.
     */
    @Deprecated
    protected void transformAll() {
		Matrix4 u1 = computeTransformationMatrix();
		for (Vertex v : vertices) {
			v = u1.transform(v);
		}
	}
    
    /**
     * Computes the transformation matrix from the stack and applies the transformation to all
     * vertices using the graphics card.
     */
    @Deprecated
    protected void transformAllGPU() {
    	Matrix4 u1 = computeTransformationMatrix();
    	vertices = GPUKernel.transformVertices(u1, vertices);
    }
    
    //TODO work on this
    protected List<Vertex> transformAll(List<Vertex> vertices) {
    	return GPUKernel.transformVertices(computeTransformationMatrix(), vertices);
    }
    
    /**
     * Collapse stack and apply collapsed matrix to vertices.
     * Do not call this method directly, use apply() instead.
     */
    public abstract void applyTransformations();
    
    public abstract List<Triangle> getTransformedTriangles();
    
    /**
     * Update the internal state of the Transformable, signifying that a transformation has been applied and an
     * update to a VBO is needed.
     */
    public void update() {
    	updated = true;
    }
    
    /*
     * If a transformation has been applied, this will change the update status and return true.
     * A VBO can adjust itself accordingly.
     */
    public boolean hasUpdated() {
    	boolean temp = updated;
    	updated = false;
    	return temp;
    }
}
