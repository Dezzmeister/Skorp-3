package com.dezzy.skorp3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dezzy.skorp3.field3D.RectPrism;
import com.dezzy.skorp3.game.Physics;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Vertex;

public class Skorp3 {
	public static void main(String ... args) {
		Skorp3 skorp = new Skorp3();
		VBO3D vbo = new VBO3D("main");
		RectPrism test = (RectPrism) new RectPrism(250,250,100,Color.GREEN).addto(vbo);
		
		vbo.add(new Triangle(new Vertex(100, 100, 100),
	            new Vertex(-100, -100, -50),
	            new Vertex(-100, 100, -100),
	            Color.GREEN));
		
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		
		JPanel renderPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Physics.barycentricRaster(vbo, g, this);
			}
		};
		
		pane.add(renderPanel, BorderLayout.CENTER);
		
		frame.setSize(600,600);
		frame.setVisible(true);	
	}
}
