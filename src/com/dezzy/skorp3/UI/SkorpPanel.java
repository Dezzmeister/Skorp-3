package com.dezzy.skorp3.UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class SkorpPanel extends JPanel implements MouseMotionListener {
	private MouseData mouseData;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	{
		addMouseMotionListener(this);
	}
	
	public SkorpPanel(MouseData data) {
		super();
		mouseData = data;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMouseData(e);
		repaint();
	}
	
	private void updateMouseData(MouseEvent e) {
		mouseData.x(e.getX());
		mouseData.y(e.getY());
	}

}
