package com.dezzy.skorp3.math3D;

import java.util.List;

import com.dezzy.skorp3.GPU.GPUKernel;
import com.dezzy.skorp3.log.Logger;
import com.dezzy.skorp3.math3D.datastructures.Stack;
import com.dezzy.skorp3.skorp3D.true3D2.core.Vector4;

/**
 * Represents a 4x4 matrix. ESSENTIAL for the True3D code.
 * 
 * @author Dezzmeister
 *
 */
public class Matrix4 {
	public static final Matrix4 IDENTITY = new Matrix4(new double[] {
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1
	});
	
	public double[] values = new double[16];
	
	public Matrix4(double[] _values) {
		if (_values.length==16) {
			values = _values;
		} else {
			System.out.println("Matrix4 array should have 16 values!");
			Logger.warn("Matrix4 created with " + _values.length + " values, should have 16!");
		}
	}
	
	public Matrix4 multiply(double scalar) {
		double[] result = new double[16];
		for (int i = 0; i < values.length; i++) {
			result[i] = values[i] * scalar;
		}
		return new Matrix4(result);
	}
	
	public Matrix4 multiply(Matrix4 matrix) {
		double[] result = new double[16];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int i = 0; i < 4; i++) {
                    result[row * 4 + col] +=
                        this.values[row * 4 + i] * matrix.values[i * 4 + col];
                }
            }
        }
        return new Matrix4(result);
	}
	
	public Vertex transform(Vertex in) {
		return new Vertex(
                in.x * values[0] + in.y * values[4] + in.z * values[8] + in.w * values[12],
                in.x * values[1] + in.y * values[5] + in.z * values[9] + in.w * values[13],
                in.x * values[2] + in.y * values[6] + in.z * values[10] + in.w * values[14],
                in.x * values[3] + in.y * values[7] + in.z * values[11] + in.w * values[15]
                );
	}
	
	public Vector4 transform(Vector4 in) {
		return new Vector4(
                in.x * values[0] + in.y * values[4] + in.z * values[8] + in.w * values[12],
                in.x * values[1] + in.y * values[5] + in.z * values[9] + in.w * values[13],
                in.x * values[2] + in.y * values[6] + in.z * values[10] + in.w * values[14],
                in.x * values[3] + in.y * values[7] + in.z * values[11] + in.w * values[15]
                );
	}
	
	//public double determinant
	
	public Vertex[] transform(Vertex ... vertices) {
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = transform(vertices[i]);
		}
		return vertices;
	}
	
	public Vertex[] transformGPU(Vertex ... vertices) {
		return GPUKernel.transformVertices(this, vertices);
	}
	
	/**
	 * A standard collapse method for Matrix4 Stacks. A Stack of Matrix4 should probably
	 * use this method.
	 * 
	 * @param list List of Matrices to be collapsed
	 * @return the Matrix that is the result of successive multiplications on the matrices in the list
	 */
	public static Matrix4 collapse(List<Matrix4> list) {
		Matrix4 result = IDENTITY;
		for (Matrix4 m : list) {
			result = result.multiply(m);
		}
		return result;
	}
	
	public static Matrix4 collapse(Stack<Matrix4> stack) {
		if (stack.size()==0) {
			return IDENTITY;
		} else {
			return stack.pop().multiply(collapse(stack));
		}
	}
	
	public static Matrix4 getXRotationMatrix(double deg) {
		double angle = Math.toRadians(deg);
		return new Matrix4(new double[] {
				1, 0, 0, 0,
				0, Math.cos(angle), -Math.sin(angle), 0,
				0, Math.sin(angle), Math.cos(angle), 0,
				0, 0, 0, 1
		});
	}
	
	public static Matrix4 getYRotationMatrix(double deg) {
		double angle = Math.toRadians(deg);
		
		return new Matrix4(new double[] {
				Math.cos(angle), 0, Math.sin(angle), 0,
				0, 1, 0, 0,
				-Math.sin(angle), 0, Math.cos(angle), 0,
				0, 0, 0, 1
		});
	}
	
	public static Matrix4 getZRotationMatrix(double deg) {
		double angle = Math.toRadians(deg);
		
		return new Matrix4(new double[] {
				Math.cos(angle), -Math.sin(angle), 0, 0,
				Math.sin(angle), Math.cos(angle), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		});
	}
	
	public static Matrix4 getTranslationMatrix(double x, double y, double z) {
		return new Matrix4(new double[] {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				x, y, z, 1
		});
	}
	
	public static Matrix4 getScalingMatrix(double x, double y, double z) {
		return new Matrix4(new double[] {
				x, 0, 0, 0,
				0, y, 0, 0,
				0, 0, z, 0,
				0, 0, 0, 1
		});
	}
	
	public static Matrix4 getPerspectiveMatrix(double fovY, double aspect, double nearZ, double farZ) {
		fovY = Math.toRadians(fovY);
		double f = 1.0/Math.tan(fovY/2.0);
		
		return new Matrix4(new double[] {
				f/aspect, 0, 0, 0,
				0, f, 0, 0,
				0, 0, (nearZ + farZ)/(nearZ - farZ), (2 * farZ * nearZ)/(nearZ - farZ),
				0, 0, -1, 0
		});
	}
	
	public static Matrix4 getOrthographicMatrix(double width, double height, double nearZ, double farZ) {
		return new Matrix4(new double[] {
				1/width, 0, 0, 0,
				0, 1/height, 0, 0,
				0, 0, -(2/(farZ-nearZ)), -((farZ+nearZ)/(farZ-nearZ)),
				0, 0, 0, 1
		});
	}
}
