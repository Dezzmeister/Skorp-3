package com.dezzy.skorp3;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import com.dezzy.skorp3.skorp3D.primitive.Triangle;
import com.dezzy.skorp3.skorp3D.render.SkorpPanel;

public class Skorp3 {
	public static void main(String ... args) {
		/*
		mainVBO.add(new AARectangle(0,50,-200,50,50,Plane.XZ,Color.MAGENTA));
		mainVBO.add(new AARectangle(-200,0,200,100,40,Plane.XY,Color.GREEN));
		mainVBO.add(new AARectangle(0,-1000,-2000,1000,1000,Plane.XZ,Color.RED));
		
		mainVBO.rotateX(60);
		mainVBO.scale(2,1,3);
		mainVBO.applyTransformations();
		
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		
		SkorpPanel renderPanel = SkorpPanel.create();
		*/
		
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		Triangle tralala = new Triangle(0, 100, 200,
			    						30, 400, 120,
			    						122, 144, 900);
		Global.VBO.add(tralala);
		tralala.scale(100,100,100);
		tralala.apply();
		
		SkorpPanel renderPanel = SkorpPanel.createStandard();
		
		
		pane.add(renderPanel, BorderLayout.CENTER);
		
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
