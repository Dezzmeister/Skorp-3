package com.dezzy.skorp3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.UI.SkorpPanel;
import com.dezzy.skorp3.game.Physics;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.geometry3D.AARectangle;
import com.dezzy.skorp3.geometry3D.Plane;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Vertex;

public class Skorp3 {
	public static MouseData mouse = new MouseData();
	
	public static void main(String ... args) {
		VBO3D vbo = new VBO3D("main");
		//AARectPrism test = (AARectPrism) new AARectPrism(0,0,0,50,50,50,Color.GREEN).addto(vbo);
		//AARectangle test = (AARectangle) new AARectangle(0,0,-100,50,50,Plane.XY,Color.BLUE).addto(vbo);
		
		/*vbo.add(new Triangle(new Vertex(100, 100, 100),
	            new Vertex(-100, -100, -50),
	            new Vertex(-100, 100, -100),
	            Color.YELLOW));
	            */
		vbo.add(new AARectangle(0,50,-200,50,50,Plane.XY,Color.MAGENTA));
		vbo.add(new AARectangle(0,-10000,-500,5000,5000,Plane.XZ,Color.RED));
		Triangle important = new Triangle(new Vertex(100, 100, 100),
	            new Vertex(-100, -100, -50),
	            new Vertex(-100, 100, -100),
	            Color.YELLOW);
		vbo.add(important);
		//vbo.add(new AARectPrism(-500,2000,-2000,500,500,500,Color.GREEN));
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = frame.getContentPane();
		
		SkorpPanel renderPanel = new SkorpPanel(mouse) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -33201335959975219L;

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
