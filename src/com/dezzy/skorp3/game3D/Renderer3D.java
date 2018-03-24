package com.dezzy.skorp3.game3D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import com.dezzy.skorp3.Skorp3;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;

public class Renderer3D {
	private final static int zBufferLimit = 0;
	public boolean perspectiveMode = true;
	
	public static double map(double value, double min1, double max1, double min2, double max2) {
		double range1 = max1-min1;
		double norm = value/range1;
		double range2 = max2-min2;
		double mapped = range2*norm;
		return (mapped + min2);
	}
	
	public void barycentricRaster(VBO3D vbo, Graphics g, JPanel panel) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,  0, panel.getWidth(), panel.getHeight());
			
		List<Triangle> triangles = vbo.getVBO();
		int windowWidth = panel.getWidth();
		int windowHeight = panel.getHeight();
		
		double heading = Math.toRadians(0); //Y
		double pitch = Math.toRadians(0); //X
		double roll = Math.toRadians(0); //Z
		
		Matrix4 headingTransform;
		Matrix4 pitchTransform;
		Matrix4 rollTransform;
		Matrix4 panOutTransform;
		
		Matrix4 transform;
		
		headingTransform = new Matrix4(new double[] {
				Math.cos(heading), 0, -Math.sin(heading), 0,
				0, 1, 0, 0,
				Math.sin(heading), 0, Math.cos(heading), 0,
				0, 0, 0, 1
			});
		pitchTransform = new Matrix4(new double[] {
				1, 0, 0, 0,
				0, Math.cos(pitch), Math.sin(pitch), 0,
				0, -Math.sin(pitch), Math.cos(pitch), 0,
				0, 0, 0, 1
         	});
		rollTransform = new Matrix4(new double[] {
				Math.cos(roll), -Math.sin(roll), 0, 0,
				Math.sin(roll), Math.cos(roll), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
           	});

		panOutTransform = new Matrix4(new double[] {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, -400, 1
           	});
			
		transform = headingTransform.multiply(pitchTransform).multiply(rollTransform).multiply(panOutTransform);
		
		double fovAngle = Math.toRadians(60);

        double fov = Math.tan(fovAngle / 2) * 170;
        
        BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        double[] zBuffer = new double[(img.getWidth()+zBufferLimit) * (img.getHeight()+zBufferLimit)];
		for (int q = 0; q < zBuffer.length; q++) {
		    zBuffer[q] = Double.NEGATIVE_INFINITY;
		}
		
		for (Triangle t : triangles) {
			Vertex v1 = transform.transform(t.v1);
			Vertex v2 = transform.transform(t.v2);
			Vertex v3 = transform.transform(t.v3);
			
			Vertex ab = new Vertex(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z, v2.w - v1.w);

            Vertex ac = new Vertex(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z, v3.w - v1.w);
			Vertex norm = new Vertex(
				ab.y * ac.z - ab.z * ac.y,
				ab.z * ac.x - ab.x * ac.z,
				ab.x * ac.y - ab.y * ac.x,
				1
			);
			
			double normalLength = Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
			
			norm.x /= normalLength;
			norm.y /= normalLength;
			norm.z /= normalLength;
			
			double angleCos = Math.abs(norm.z);
			
			//Perspective
			
			if (perspectiveMode) {
				
				v1.x = (v1.x / (-v1.z) * fov);
				v1.y = (v1.y / (-v1.z) * fov);
				v2.x = (v2.x / (-v2.z) * fov);
				v2.y = (v2.y / (-v2.z) * fov);
				v3.x = (v3.x / (-v3.z) * fov);
				v3.y = (v3.y / (-v3.z) * fov);
			}
			
			//Translate to the center of the window
			v1.x += windowWidth / 2;
			v1.y += windowHeight /2;
			v2.x += windowWidth / 2;
			v2.y += windowHeight / 2;
			v3.x += windowWidth / 2;
			v3.y += windowHeight / 2;
			
			//Rasterization using barycentric coordinates
			
			int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
			int maxX = (int) Math.min(windowWidth - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
			
			int minY = (int) Math.max(0,  Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
			int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));
			
			double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);
			
			for (int y = minY; y <= maxY; y++) {
				for (int x = minX; x <= maxX; x++) {
					
					double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
					
					double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
					
					double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;
					
					
					
					if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
						double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
						int zIndex = y * img.getWidth() + x;
						if (zBuffer[zIndex] < depth) {
							try { 
								img.setRGB(x, y, getShade(t.color, angleCos).getRGB());
							} catch (Exception e) {
								
							}
							zBuffer[zIndex] = depth;
						}
					}
				}
			}
		}
		
		g2.drawImage(img,  0,  0, null);
	}
	
	private static Color getShade(Color color, double shade) {
		double redLinear = Math.pow(color.getRed(), 2.4) * shade;
		double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
		double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;
		
		int red = (int) Math.pow(redLinear,  1/2.4);
		int green = (int) Math.pow(greenLinear, 1/2.4);
		int blue = (int) Math.pow(blueLinear, 1/2.4);
		
		return new Color(red, green, blue);
	}
}
