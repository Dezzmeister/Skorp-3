package com.dezzy.skorp3.skorp3D.render;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.dezzy.skorp3.Global;
import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastRenderer;

public abstract class SkorpPanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = 118236244530832266L;
	
	private Mouse mouseData;
	private Container pane;
	
	{
		addMouseMotionListener(this);
	}
	
	public SkorpPanel(Mouse data, Container _pane) {
		mouseData = data;
		pane = _pane;
	}
	
	/**
	 * Creates a SkorpPanel using Global objects.
	 * 
	 * @return new SkorpPanel
	 */
	public static SkorpPanel createStandard(Container pane) {
		SkorpPanel panel = new SkorpPanel(Global.mouseData, pane) {
			
			private static final long serialVersionUID = -2463995051870675710L;
			private final GraphicsContainer container = new GraphicsContainer();
			private TrueRenderer renderer;
			
			{
				container.setPanel(this)
						 .setData3D(Global.True3D.data3D)
						 .setVBOList(Global.True3D.VBOLIST)
						 .setMouseData(Global.mouseData);
				renderer = Renderers.createAndStartBarycentricRenderer(container);
			}

			@Override
			public void paintComponent(Graphics g) {
				container.setGraphics(g);
				renderer.render();
			}
		};
		
		return panel;
	}
	
	public static SkorpPanel createStandardRaycast(Container pane) {
		SkorpPanel panel = new SkorpPanel(Global.mouseData, pane) {
			private static final long serialVersionUID = -7595491200924341805L;
			private final RaycastGraphicsContainer container = new RaycastGraphicsContainer();
			private RaycastRenderer renderer;
			
			{
				container.setMouseData(Global.mouseData)
						 .setPanel(this)
						 .setWorldMap(Global.Raycast.mainMap)
						 .setCamera(Global.Raycast.camera);
				
				renderer = Renderers.createAndStartRaycaster(container);
			}
			
			public void paintComponent(Graphics g) {
				container.setGraphics(g);
				renderer.render();
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
		mouseData.x((int) e.getX());
		mouseData.y((int) e.getY());
	}
}
