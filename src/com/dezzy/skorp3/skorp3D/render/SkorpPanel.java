package com.dezzy.skorp3.skorp3D.render;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.dezzy.skorp3.Global;
import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.skorp3D.data.GraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastRenderer;

public abstract class SkorpPanel extends JPanel implements MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 118236244530832266L;
	
	private Mouse mouseData;
	@SuppressWarnings("unused")
	private Container pane;
	protected boolean[] keys;
	
	{
		addMouseMotionListener(this);
		addKeyListener(this);
		//requestFocusInWindow();
	}
	
	public SkorpPanel(Mouse data, Container _pane, boolean[] _keys) {
		mouseData = data;
		pane = _pane;
		keys = _keys;
	}
	
	/**
	 * Creates a SkorpPanel using Global objects.
	 * 
	 * @return new SkorpPanel
	 */
	public static SkorpPanel createStandard(Container pane) {
		SkorpPanel panel = new SkorpPanel(Global.mouseData, pane, new boolean[256]) {
			
			private static final long serialVersionUID = -2463995051870675710L;
			private TrueRenderer renderer;
			private GraphicsContainer container = new GraphicsContainer();
			
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
	
	public static SkorpPanel createStandardRaycast(Container pane, int width, int height) {
		SkorpPanel panel = new SkorpPanel(Global.mouseData, pane, new boolean[256]) {
			private static final long serialVersionUID = -7595491200924341805L;
			private RaycastRenderer renderer;
			private RaycastGraphicsContainer container = new RaycastGraphicsContainer();
			
			{
				container.setMouseData(Global.mouseData)
						 .setPanel(this)
						 .setWorldMap(Global.Raycast.mainMap)
						 .setCamera(Global.Raycast.camera)
						 .setContainer(pane)
						 .setKeys(keys);
				
				renderer = Renderers.createAndStartRaycaster(container, width, height);
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
	}
	
	private void updateMouseData(MouseEvent e) {
		mouseData.x((int) e.getX());
		mouseData.y((int) e.getY());
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() < 256) {
			keys[arg0.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() < 256) {
			keys[arg0.getKeyCode()] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
