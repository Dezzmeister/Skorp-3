package com.dezzy.skorp3;

import static com.dezzy.skorp3.Global.VBO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

import com.dezzy.skorp3.UI.MouseVBOTransformer;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.skorp3D.primitive.Quad;
import com.dezzy.skorp3.skorp3D.primitive.Triangle;
import com.dezzy.skorp3.skorp3D.render.SkorpPanel;

public class Skorp3 {
	public static void main(String ... args) {
		
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		
		/*
		VBO.add(new Triangle(new Vertex(100, 100, 100),
	            new Vertex(-100, -100, 100),
	            new Vertex(-100, 100, -100),
	            Color.WHITE));
		VBO.add(new Triangle(new Vertex(100, 100, 100),
	            new Vertex(-100, -100, 100),
	            new Vertex(100, -100, -100),
	            Color.RED));
		VBO.add(new Triangle(new Vertex(-100, 100, -100),
	            new Vertex(100, -100, -100),
	            new Vertex(100, 100, 100),
	            Color.GREEN));
		VBO.add(new Triangle(new Vertex(-100, 100, -100),
	            new Vertex(100, -100, -100),
	            new Vertex(-100, -100, 100),
	            Color.BLUE));
		*/
		VBO.add(new Quad(new Vertex(100,100,-100),
						 new Vertex(150,100,-100),
						 new Vertex(150,150,-100),
						 new Vertex(100,150,-50),
						 Color.ORANGE));
		
		VBO.add(new Quad(new Vertex(100,100,-400),
				 new Vertex(200,100,-400),
				 new Vertex(200,200,-400),
				 new Vertex(100,200,-400),
				 Color.ORANGE));
		
		VBO.apply();
		
		SkorpPanel renderPanel = SkorpPanel.createStandard(); 
		
		MouseVBOTransformer transformer = new MouseVBOTransformer(Global.mouseData, Global.VBO);
		Thread thread = new Thread(transformer);
		thread.start();
		transformer.enable();
		
		pane.add(renderPanel, BorderLayout.CENTER);
		
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
