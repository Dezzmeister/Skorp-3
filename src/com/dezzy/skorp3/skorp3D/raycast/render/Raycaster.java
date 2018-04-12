package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Raycaster implements RaycastRenderer {
	private RaycastGraphicsContainer container;
	
	
	@Override
	public void render() {
		Graphics2D g2 = (Graphics2D) container.g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,  0, container.panel.getWidth(), container.panel.getHeight());
	     
	    BufferedImage img = new BufferedImage(container.panel.getWidth(), container.panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    
	    g2.drawImage(img,  0,  0, null);
	}

	@Override
	public boolean shouldRedraw() {
		return container.hasUpdated();
	}

	@Override
	public RaycastGraphicsContainer getGraphicsContainer() {
		return container;
	}
	
}
