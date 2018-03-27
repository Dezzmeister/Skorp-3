package com.dezzy.skorp3;

import static com.dezzy.skorp3.Global.mainVBO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

import com.dezzy.skorp3.UI.SkorpPanel;
import com.dezzy.skorp3.geometry3D.AARectangle;
import com.dezzy.skorp3.geometry3D.Plane;

public class Skorp3 {
	
	public static void main(String ... args) {
		//AARectPrism test = (AARectPrism) new AARectPrism(0,0,0,50,50,50,Color.GREEN).addto(vbo);
		//AARectangle test = (AARectangle) new AARectangle(0,0,-100,50,50,Plane.XY,Color.BLUE).addto(vbo);
		
		/*vbo.add(new Triangle(new Vertex(100, 100, 100),
	            new Vertex(-100, -100, -50),
	            new Vertex(-100, 100, -100),
	            Color.YELLOW));
	            */
		mainVBO.add(new AARectangle(0,50,-200,50,50,Plane.XZ,Color.MAGENTA));
		mainVBO.add(new AARectangle(-200,0,200,100,40,Plane.XY,Color.GREEN));
		//vbo.add(new AARectPrism(-500,2000,-2000,500,500,500,Color.GREEN));
		
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		
		SkorpPanel renderPanel = SkorpPanel.create();
		
		pane.add(renderPanel, BorderLayout.CENTER);
		
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
