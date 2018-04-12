package com.dezzy.skorp3.UI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.dezzy.skorp3.Global;
import com.dezzy.skorp3.game.Physics;

/**
 * A wrapper for JPanel that incorporates 3D rendering methods from Renderer3D and MouseListeners.
 * SkorpPanel is abstract because paintComponent should be defined for every SkorpPanel.
 * 
 * @author Dezzmeister
 *
 */
//TODO add support for mouse clicks
@Deprecated
public abstract class SkorpPanel extends JPanel implements MouseMotionListener {
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
	
	/**
	 * Creates a SkorpPanel using Global objects
	 * 
	 * @return new SkorpPanel
	 */
	public static SkorpPanel create() {
		SkorpPanel panel = new SkorpPanel(Global.mouseData) {
			
			private static final long serialVersionUID = -2463995051870675710L;
			
			{
				Physics.renderer3D.initializeBarycentricRenderer(Global.True3D.renderList, this, Global.mouseData, Global.True3D.data3D);
			}

			@Override
			public void paintComponent(Graphics g) {
				Physics.renderer3D.barycentricRaster(g);
			}
		};
		
		return panel;
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
