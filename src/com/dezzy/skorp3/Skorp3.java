package com.dezzy.skorp3;

import static com.dezzy.skorp3.Global.frame;
import static com.dezzy.skorp3.Global.pane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.dezzy.skorp3.math3D.Matrix3;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.skorp3D.render.SkorpPanel;
import com.dezzy.skorp3.skorp3D.render.SkorpPanelSupplier;

public class Skorp3 {
	public static void main(String ... args) {
		Matrix3 test = new Matrix3(new double[] {
				6, 1, 1,
				4, -2, 5,
				2, 8, 7
		});
		
		System.out.println(test.determinant());
		
		Matrix4 test2 = new Matrix4(new double[] {
				9, 2, 4, -9,
				8, 4, 1, -3,
				-7, -8, 3, 4,
				4, -6, 1, 4
		});
		
		System.out.println(test2.inverse());
		System.out.println();
		System.out.println(test2.multiply(test2.inverse()));
		
		//launch(SkorpPanel::createStandardTrue3D2);
	}
	
	public static void launch(SkorpPanelSupplier supplier) {
		SkorpPanel renderPanel = supplier.createPanel(pane, Global.WIDTH, Global.HEIGHT);
		
		pane.add(renderPanel, BorderLayout.CENTER);
		Timer timer = new Timer(0, new AbstractAction() {
			private static final long serialVersionUID = -3111030517304106245L;

			@Override
			public void actionPerformed(ActionEvent e) {
				long beginTime;
				long timeDiff;
				int sleepTime;
				int fps = 1000 / 60;
				
				beginTime = System.nanoTime() / 1000000;
				
				renderPanel.repaint();
				
				timeDiff = System.nanoTime() / 1000000 - beginTime;

		        sleepTime = fps - (int) (timeDiff);
		        
		        if (sleepTime > 0) {
		        	((Timer)e.getSource()).setDelay(sleepTime);
		        }
			}
		});
		timer.start();
		
		frame.pack();
		frame.setSize(Global.SCREENWIDTH,Global.SCREENHEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
