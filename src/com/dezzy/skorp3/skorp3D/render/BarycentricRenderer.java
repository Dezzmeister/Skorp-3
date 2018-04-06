package com.dezzy.skorp3.skorp3D.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;
import com.dezzy.skorp3.skorp3D.primitive.Triangle;

public class BarycentricRenderer implements Renderer {
	private GraphicsContainer container;
	
	public BarycentricRenderer(GraphicsContainer _container) {
		container = _container;
	}
	
	@Override
	public synchronized void render() {
		Triangle[] triangles = container.collapse();
		if (container.g != null) {
		Graphics2D g2 = (Graphics2D) container.g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,  0, container.panel.getWidth(), container.panel.getHeight());
		
		double heading = Math.toRadians(0);
		double pitch = Math.toRadians(0);
		double roll = Math.toRadians(0);
		
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
		
		 double fovAngle = Math.toRadians(container.data3D.fovAngle);
	     double fov = Math.tan(fovAngle / 2) * 170;
	     
	     BufferedImage img = new BufferedImage(container.panel.getWidth(), container.panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
	        
		double[] zBuffer = new double[(img.getWidth()+container.data3D.zBufferLimit()) * (img.getHeight()+container.data3D.zBufferLimit())];
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
			
			if (container.data3D.perspectiveMode) {
				
				v1.x = (v1.x / (-v1.z) * fov);
				v1.y = (v1.y / (-v1.z) * fov);
				v2.x = (v2.x / (-v2.z) * fov);
				v2.y = (v2.y / (-v2.z) * fov);
				v3.x = (v3.x / (-v3.z) * fov);
				v3.y = (v3.y / (-v3.z) * fov);
			}
			
			//Translate to the center of the window
			v1.x += container.panel.getWidth() / 2;
			v1.y += container.panel.getHeight() /2;
			v2.x += container.panel.getWidth() / 2;
			v2.y += container.panel.getHeight() / 2;
			v3.x += container.panel.getWidth() / 2;
			v3.y += container.panel.getHeight() / 2;
			
			//Rasterization using barycentric coordinates
			
			int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
			int maxX = (int) Math.min(container.panel.getWidth() - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
			
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
						if (zIndex < zBuffer.length && zBuffer[zIndex] < depth) {
							try { //Works for now, but should really be fixed soon
								img.setRGB(x, y, getShade(t.texture(), angleCos).getRGB());
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
		
	}
	
	public static Color getShade(Color color, double shade) {
		double redLinear = Math.pow(color.getRed(), 2.4) * shade;
		double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
		double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;
		
		int red = (int) Math.pow(redLinear,  1/2.4);
		int green = (int) Math.pow(greenLinear, 1/2.4);
		int blue = (int) Math.pow(blueLinear, 1/2.4);
		
		return new Color(red, green, blue);
	}

	@Override
	public synchronized boolean shouldRedraw() {
		return container.hasUpdated();
	}

	@Override
	public synchronized GraphicsContainer getGraphicsContainer() {
		return container;
	}

}
