package com.dezzy.skorp3.math3D;

import com.dezzy.skorp3.log.Logger;

public class Matrix3 {
	public static final Matrix3 IDENTITY = new Matrix3(new double[] {
			1, 0, 0,
			0, 1, 0,
			0, 0, 1
	});
	
	public double[] values = new double[9];
	
	public Matrix3(double[] _values) {
		if (_values.length==9) {
			values = _values;
		} else {
			System.out.println("Matrix3 array should have 9 values!");
			Logger.warn("Matrix3 created with " + _values.length + " values, should have 9!");
		}
	}
	
	public Matrix3 multiply(double scalar) {
		double[] result = new double[9];
		for (int i = 0; i < values.length; i++) {
			result[i] = values[i] * scalar;
		}
		return new Matrix3(result);
	}
	
	public Matrix3 multiply(Matrix4 matrix) {
		double[] result = new double[9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    result[row * 3 + col] +=
                        this.values[row * 3 + i] * matrix.values[i * 3 + col];
                }
            }
        }
        return new Matrix3(result);
	}
	
	public double determinant() {
		double a = values[0]*((values[4]*values[8])-(values[5]*values[7]));
		double b = values[1]*((values[3]*values[8])-(values[5]*values[6]));
		double c = values[2]*((values[3]*values[7])-(values[4]*values[6]));
		
		return a-b+c;
	}
}
