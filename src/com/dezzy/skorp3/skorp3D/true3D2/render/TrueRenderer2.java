package com.dezzy.skorp3.skorp3D.true3D2.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.skorp3D.render.Renderer;
import com.dezzy.skorp3.skorp3D.true3D2.core.Mesh;
import com.dezzy.skorp3.skorp3D.true3D2.core.MeshList;
import com.dezzy.skorp3.skorp3D.true3D2.core.Vector4;

public class TrueRenderer2 implements Renderer {
	private volatile Graphics g;
	private volatile Mouse mouse;
	private Data3D data3D;
	private JPanel panel;
	private Camera camera;
	private MeshList meshes;
	private final int WIDTH;
	private final int HEIGHT;
	
	public TrueRenderer2(int _width, int _height, Mouse _mouse, Data3D _data3D, JPanel _panel, Camera _camera, MeshList _meshes) {
		WIDTH = _width;
		HEIGHT = _height;
		mouse = _mouse;
		data3D = _data3D;
		panel = _panel;
		camera = _camera;
		
		meshes = _meshes;
	}
	
	@Override
	public void render() {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, WIDTH, HEIGHT);
		
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Matrix4 transform = Matrix4.IDENTITY;
		
		Matrix4 rotateX = Matrix4.getXRotationMatrix(30);
		Matrix4 rotateY = Matrix4.getYRotationMatrix(40);
		
		transform = transform.multiply(rotateX).multiply(rotateY);
		
		for (Mesh m : meshes) {
			for (int i = 0; i < m.vertices.length-2; i += 3) {
				Vector4 v1 = m.vertices[i];
				Vector4 v2 = m.vertices[i + 1];
				Vector4 v3 = m.vertices[i + 2];
				
				v1 = transform.transform(v1);
				v2 = transform.transform(v2);
				v3 = transform.transform(v3);
				
				v1.x/=v1.z;
				v1.y/=v1.z;
				
				v2.x/=v2.z;
				v2.y/=v2.z;
				
				v3.x/=v3.z;
				v3.y/=v3.z;
				
				g2.setColor(Color.YELLOW);
				int x1 = (int) v1.x;
				int y1 = (int) v1.y;
				
				int x2 = (int) v2.x;
				int y2 = (int) v2.y;
				
				int x3 = (int) v3.x;
				int y3 = (int) v3.y;
				
				g2.drawLine(x1, y1, x2, y2);
				g2.drawLine(x2, y2, x3, y3);
				g2.drawLine(x3, y3, x1, y1);
			}
		}
	}

	@Override
	public boolean shouldRedraw() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateGraphicsObject(Graphics _g) {
		g = _g;		
	}
}
