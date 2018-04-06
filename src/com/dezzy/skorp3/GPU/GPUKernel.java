package com.dezzy.skorp3.GPU;

import java.util.ArrayList;
import java.util.List;

import com.aparapi.Kernel;
import com.aparapi.Range;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;

/**
 * A class containing operations that can/probably should be performed on the graphics card. For example, 
 * <code>transformVertices()</code> uses the graphics card to apply a Matrix transformation to a list of vertices. The Matrix should be computed beforehand
 * as a combination of several smaller transformations.
 * 
 * @author Dezzmeister
 * @see Matrix4
 * @see Vertex
 *
 */
public final class GPUKernel {
	
	private GPUKernel() {
		
	}
	
	public static List<Vertex> transformVertices(Matrix4 transformMatrix, List<Vertex> vertexList) {
		final double[] m = transformMatrix.values;
		final double[] x = new double[vertexList.size()];
		final double[] y = new double[vertexList.size()];
		final double[] z = new double[vertexList.size()];
		final double[] w = new double[vertexList.size()];
		
		for (int i = 0; i < vertexList.size(); i++) {
			Vertex v = vertexList.get(i);
			x[i] = v.x;
			y[i] = v.y;
			z[i] = v.z;
			w[i] = v.w;
		}
		
		final double[] outX = new double[x.length];
		final double[] outY = new double[y.length];
		final double[] outZ = new double[z.length];
		final double[] outW = new double[w.length];
		
		Kernel kernel = new Kernel() {
			protected @PrivateMemorySpace(32) short[] buffer = new short[32]; 
			
			@Override
			public void run() {
				for (int i = 0; i < m.length; i++) {
					outX[i] = (x[i] * m[0]) + (y[i] * m[4]) + (z[i] * m[8]) + (w[i] * m[12]);
					outY[i] = (x[i] * m[1]) + (y[i] * m[5]) + (z[i] * m[9]) + (w[i] * m[13]);
					outZ[i] = (x[i] * m[2]) + (y[i] * m[6]) + (z[i] * m[10]) + (w[i] * m[14]);
					outW[i] = (x[i] * m[3]) + (y[i] * m[7]) + (z[i] * m[11]) + (w[i] * m[15]);
				}
			}
		};
		
		Range range = Range.create(1);
		kernel.execute(range); //Iterate 1 time
		kernel.dispose(); //Dispose of old values clogging GPU memory
		
		List<Vertex> result = new ArrayList<Vertex>();
		for (int i = 0; i < x.length; i++) {
			result.add(new Vertex(outX[i],outY[i],outZ[i],outW[i]));
		}
		
		return result;
	}
	
	public static Vertex[] transformVertices(Matrix4 transformMatrix, Vertex[] vertices) {
		final double[] m = transformMatrix.values;
		final double[] x = new double[vertices.length];
		final double[] y = new double[vertices.length];
		final double[] z = new double[vertices.length];
		final double[] w = new double[vertices.length];
		
		for (int i = 0; i < vertices.length; i++) {
			Vertex v = vertices[i];
			x[i] = v.x;
			y[i] = v.y;
			z[i] = v.z;
			w[i] = v.w;
		}
		
		final double[] outX = new double[x.length];
		final double[] outY = new double[y.length];
		final double[] outZ = new double[z.length];
		final double[] outW = new double[w.length];
		
		Kernel kernel = new Kernel() {
			protected @PrivateMemorySpace(32) short[] buffer = new short[32]; 
			
			@Override
			public void run() {
				for (int i = 0; i < m.length; i++) {
					outX[i] = (x[i] * m[0]) + (y[i] * m[4]) + (z[i] * m[8]) + (w[i] * m[12]);
					outY[i] = (x[i] * m[1]) + (y[i] * m[5]) + (z[i] * m[9]) + (w[i] * m[13]);
					outZ[i] = (x[i] * m[2]) + (y[i] * m[6]) + (z[i] * m[10]) + (w[i] * m[14]);
					outW[i] = (x[i] * m[3]) + (y[i] * m[7]) + (z[i] * m[11]) + (w[i] * m[15]);
				}
			}
		};
		
		Range range = Range.create(1);
		kernel.execute(range); //Iterate 1 time
		kernel.dispose(); //Dispose of old values clogging GPU memory
		
		Vertex[] result = new Vertex[vertices.length];
		for (int i = 0; i < x.length; i++) {
			result[i] = new Vertex(outX[i],outY[i],outZ[i],outW[i]);
		}
		
		return result;
	}
}
