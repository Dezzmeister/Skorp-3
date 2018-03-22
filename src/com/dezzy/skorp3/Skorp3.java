package com.dezzy.skorp3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dezzy.skorp3.field3D.AARectPrism;
import com.dezzy.skorp3.game.Physics;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Vertex;

public class Skorp3 {
	public static void main(String ... args) {
		VBO3D vbo = new VBO3D("main");
		//AARectPrism test = (AARectPrism) new AARectPrism(0,0,0,50,50,50,Color.GREEN).addto(vbo);
		//AARectangle test = (AARectangle) new AARectangle(0,0,-100,50,50,Plane.XY,Color.BLUE).addto(vbo);
		AARectPrism toot = (AARectPrism) new AARectPrism(0,0,-200,50,50,50,Color.ORANGE).addto(vbo);
		
		vbo.add(new Triangle(new Vertex(100, 100, 100),
	            new Vertex(-100, -100, -50),
	            new Vertex(-100, 100, -100),
	            Color.YELLOW));
		
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		
		JPanel renderPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5418462503464201094L;

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
