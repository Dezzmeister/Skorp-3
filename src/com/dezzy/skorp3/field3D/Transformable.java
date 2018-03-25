package com.dezzy.skorp3.field3D;

import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.datastructures.Stack;

public class Transformable {
	protected Stack<Matrix4> stack  = new Stack<Matrix4>(Matrix4::collapse);
	
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
    
    public Matrix4 collapse() {
    	return stack.collapse();
    }
}
