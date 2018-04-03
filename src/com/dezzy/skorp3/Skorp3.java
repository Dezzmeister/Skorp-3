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
		mainVBO.add(new AARectangle(0,50,-200,50,50,Plane.XZ,Color.MAGENTA));
		mainVBO.add(new AARectangle(-200,0,200,100,40,Plane.XY,Color.GREEN));
		mainVBO.add(new AARectangle(0,-1000,-2000,1000,1000,Plane.XZ,Color.RED));
		
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		
		SkorpPanel renderPanel = SkorpPanel.create();
		
		pane.add(renderPanel, BorderLayout.CENTER);
		
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
