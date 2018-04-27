package com.dezzy.skorp3.skorp3D.true3D2.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.skorp3D.render.Renderer;
import com.dezzy.skorp3.skorp3D.true3D2.core.Triangle;

public class TrueRenderer2 implements Renderer {
	private volatile Graphics g;
	private volatile Mouse mouse;
	private Data3D data3D;
	private JPanel panel;
	private Camera camera;
	private List<Triangle> triangles;
	private final int WIDTH;
	private final int HEIGHT;
	
	public TrueRenderer2(int _width, int _height, Mouse _mouse, Data3D _data3D, JPanel _panel, Camera _camera) {
		WIDTH = _width;
		HEIGHT = _height;
		mouse = _mouse;
		data3D = _data3D;
		panel = _panel;
		camera = _camera;
		
		triangles = new ArrayList<Triangle>();
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
		
		for (Triangle t : triangles) {
			Vertex v1 = t.v1;
			Vertex v2 = t.v2;
			Vertex v3 = t.v3;
			
			v1 = transform.transform(v1);
			v2 = transform.transform(v2);
			v3 = transform.transform(v3);
			
			
		}
	}

	@Override
	public boolean shouldRedraw() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateGraphicsObject(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
