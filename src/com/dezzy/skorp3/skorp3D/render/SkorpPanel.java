package com.dezzy.skorp3.skorp3D.render;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.dezzy.skorp3.Global;
import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.annotations.urgency.Urgency;
import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaytraceGraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaytraceRenderer;

public abstract class SkorpPanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = 118236244530832266L;
	
	private MouseData mouseData;
	
	{
		addMouseMotionListener(this);
	}
	
	public SkorpPanel(MouseData data) {
		mouseData = data;
	}
	
	/**
	 * Creates a True3D SkorpPanel using Global objects.
	 * 
	 * @return new SkorpPanel
	 */
	@Urgency(4)
	public static SkorpPanel createStandard() {
		SkorpPanel panel = new SkorpPanel(Global.mouseData) {
			
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
				renderer.render(); //TODO: Get this on its own Thread
			}
		};
		
		return panel;
	}
	
	/**
	 * Creates a Raytrace SkorpPanel using Global Objects.
	 * 
	 * @return new SkorpPanel
	 */
	@Urgency(4)
	public static SkorpPanel createStandardRaycast() {
		SkorpPanel panel = new SkorpPanel(Global.mouseData) {
			private static final long serialVersionUID = -7595491200924341805L;
			private final RaytraceGraphicsContainer container = new RaytraceGraphicsContainer();
			private RaytraceRenderer renderer;
			
			{
				container.setPanel(this)
						 .setMouseData(Global.mouseData)
						 .setWorldMap(Global.Raycast.mainMap);
				renderer = Renderers.createAndStartRaytracer(container);
			}
			
			@Override
			public void paintComponent(Graphics g) {
				container.setGraphics(g);
				renderer.render(); //TODO: Get this on its own Thread
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
