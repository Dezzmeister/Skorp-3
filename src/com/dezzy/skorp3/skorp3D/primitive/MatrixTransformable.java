package com.dezzy.skorp3.skorp3D.primitive;

import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.datastructures.Stack;

public abstract class MatrixTransformable extends Transformable {
	protected Stack<Matrix4> stack = new Stack<Matrix4>(Matrix4::collapse,20);
	
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
		stack.push(Matrix4.getTranslationMatrix(x, y, z));
	}

	@Override
	public void scale(double x, double y, double z) {
		stack.push(Matrix4.getScalingMatrix(x, y, z));
	}
	
	public void customTransform(Matrix4 custom) {
		stack.push(custom);
	}
	
	public abstract void forceTransform(Matrix4 custom);
	
	/**
	 * Recursively calculates a final transformation matrix from previous calls
	 * to transformation methods.
	 * 
	 * @return collapsed transformation <code>Matrix4</code>
	 */
	protected Matrix4 getTransformationMatrix() {
		return stack.collapse();
	}
}
