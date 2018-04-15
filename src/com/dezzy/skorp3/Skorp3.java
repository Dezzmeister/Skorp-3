package com.dezzy.skorp3;

import static com.dezzy.skorp3.Global.frame;
import static com.dezzy.skorp3.Global.pane;
import static com.dezzy.skorp3.Global.True3D.VBO;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.KeyStroke;

import com.dezzy.skorp3.UI.MouseVBOTransformer;
import com.dezzy.skorp3.math3D.Vertex;
import com.dezzy.skorp3.skorp3D.primitive.Quad;
import com.dezzy.skorp3.skorp3D.raycast.actions.MoveAction;
import com.dezzy.skorp3.skorp3D.raycast.actions.MoveForwardAction;
import com.dezzy.skorp3.skorp3D.render.SkorpPanel;

public class Skorp3 {
	public static void main(String ... args) {
		raycast();
	}
	
	public static void true3D() {		
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
		
		SkorpPanel renderPanel = SkorpPanel.createStandard(pane); 
		
		MouseVBOTransformer transformer = new MouseVBOTransformer(Global.mouseData, Global.True3D.VBO);
		Thread thread = new Thread(transformer);
		thread.start();
		transformer.enable();
		
		pane.add(renderPanel, BorderLayout.CENTER);
		
		frame.setSize(Global.WIDTH,Global.HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void raycast() {		
		SkorpPanel renderPanel = SkorpPanel.createStandardRaycast(pane,Global.WIDTH,Global.HEIGHT);
		
		pane.add(renderPanel, BorderLayout.CENTER);
		//renderPanel.requestFocus();
		
		frame.setSize(Global.WIDTH,Global.HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
