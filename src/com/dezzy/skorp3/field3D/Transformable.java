package com.dezzy.skorp3.field3D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dezzy.skorp3.GPU.GPUKernel;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.math3D.datastructures.Stack;

public abstract class Transformable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6355738060061476562L;
	private static final int STACK_MAX_SIZE = 20;
	protected Stack<Matrix4> stack  = new Stack<Matrix4>(Matrix4::collapse,STACK_MAX_SIZE);
	protected List<Vertex> vertices = new ArrayList<Vertex>();
	protected boolean updated = false;
	
	public void rotateX(double deg) {
		double angle = Math.toRadians(deg);
		
		Matrix4 rot = new Matrix4(new double[] {
				1, 0, 0, 0,
				0, Math.cos(angle), -Math.sin(angle), 0,
				0, Math.sin(angle), Math.cos(angle), 0,
				0, 0, 0, 1
		});
		
		stack.push(rot);
	}
	
	public void rotateY(double deg) {
		double angle = Math.toRadians(deg);
		
		Matrix4 rot = new Matrix4(new double[] {
				Math.cos(angle), 0, Math.sin(angle), 0,
				0, 1, 0, 0,
				-Math.sin(angle), 0, Math.cos(angle), 0,
				0, 0, 0, 1
		});
		
		stack.push(rot);
	}
	
	public void rotateZ(double deg) {
		double angle = Math.toRadians(deg);
		
		Matrix4 rot = new Matrix4(new double[] {
				Math.cos(angle), -Math.sin(angle), 0, 0,
				Math.sin(angle), Math.cos(angle), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		});
		
		stack.push(rot);
	}
	
	public void translate(double x, double y, double z) {
		Matrix4 trans = new Matrix4(new double[] {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				x, y, z, 1
		});
		
		stack.push(trans);
	}
	
    public void scale(double x, double y, double z) {
		Matrix4 scale = new Matrix4(new double[] {
				x, 0, 0, 0,
				0, y, 0, 0,
				0, 0, z, 0,
				0, 0, 0, 1
		});
		
		stack.push(scale);
	}
    
    public Matrix4 computeTransformationMatrix() {
    	return stack.collapse();
    }
    
    /**
     * Computes the transformation matrix from the stack and applies the transformation to all
     * vertices using the CPU.
     */
    public void transformAll() {
		Matrix4 u1 = computeTransformationMatrix();
		for (Vertex v : vertices) {
			v = u1.transform(v);
		}
	}
    
    /**
     * Computes the transformation matrix from the stack and applies the transformation to all
     * vertices using the graphics card.
     */
    public void transformAllGPU() {
    	Matrix4 u1 = computeTransformationMatrix();
    	vertices = GPUKernel.transformVertices(u1, vertices);
    }
    
    //TODO work on this
    protected List<Vertex> transformAll(List<Vertex> vertices) {
    	return GPUKernel.transformVertices(computeTransformationMatrix(), vertices);
    }
    
    /**
     * Update the internal state of the Transformable, signifying that a transformation has been applied and an
     * update to a VBO is needed.
     */
    protected void update() {
    	updated = true;
    }
    
    /*
     * If a transformation has been applied, this will change the update status and return true.
     * A VBO can adjust itself accordingly.
     */
    public boolean needsUpdate() {
    	boolean temp = updated;
    	updated = false;
    	return temp;
    }
}
