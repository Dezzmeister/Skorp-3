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
		}
	}
	
	public Matrix4 multiply(double scalar) {
		for (int i = 0; i < values.length; i++) {
			values[i] *= scalar;
		}
		return this;
	}
}
