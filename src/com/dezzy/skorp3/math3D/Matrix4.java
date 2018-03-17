package com.dezzy.skorp3.math3D;

/**
 * Represents a 4x4 matrix.
 * 
 * @author Dezzmeister
 *
 */
public class Matrix4 {
	public double[] values = new double[16];
	
	public Matrix4(double[] _values) {
		if (_values.length==16) {
			values = _values;
		} else {
			System.out.println("Matrix4 array should have 16 values!");
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
}
