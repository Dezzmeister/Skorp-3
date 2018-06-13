package com.dezzy.skorp3;

import static com.dezzy.skorp3.Global.frame;
import static com.dezzy.skorp3.Global.pane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.dezzy.skorp3.skorp3D.render.SkorpPanel;
import com.dezzy.skorp3.skorp3D.render.SkorpPanelSupplier;

public class Skorp3 {
	public static void main(String ... args) {
		//launch(SkorpPanel::createStandardTrue3D2);
		launch(SkorpPanel::createStandardRaycast);
	}
	
	public static void launch(SkorpPanelSupplier supplier) {
		SkorpPanel renderPanel = supplier.createPanel(pane, Global.WIDTH, Global.HEIGHT);
		
		pane.add(renderPanel, BorderLayout.CENTER);
		Timer timer = new Timer(0, new AbstractAction() {
			private static final long serialVersionUID = -3111030517304106245L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long beginTime;
				int timeDiff;
				int fps = 1000000 / 60;
				
				beginTime = System.nanoTime();
				
				renderPanel.repaint();
				
				timeDiff = (int) (System.nanoTime() - beginTime);
				int diff = fps - timeDiff;
		        //Global.calculateFrameTimeFactor(timeDiff);
		        if (timeDiff < fps) {
		        	
		        }
			}
		});
		timer.start();
		
		frame.pack();
		frame.setSize(Global.SCREENWIDTH,Global.SCREENHEIGHT);
		//frame.setUndecorated(true);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
