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
		launch(SkorpPanel::createStandardRaycast2);
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
