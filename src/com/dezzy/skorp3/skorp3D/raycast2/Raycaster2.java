package com.dezzy.skorp3.skorp3D.raycast2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.KeyStroke;

import com.dezzy.skorp3.annotations.urgency.Urgency;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastRenderer;
import com.dezzy.skorp3.skorp3D.raycast2.core.RaycastContainer2;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;

public class Raycaster2 implements RaycastRenderer {
	private RaycastContainer2 container;
	private final int WIDTH;
	private final int HEIGHT;
	private double[] zbuf;
	
	public Raycaster2(RaycastContainer2 _container, int _width, int _height) {
		container = _container;
		WIDTH = _width;
		HEIGHT = _height;
		zbuf = new double[WIDTH];
		resetZBuffer();
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held W"), "moveForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held UP"), "moveForward");
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released W"), "stopMovingForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "stopMovingForward");
	}
	
	private void resetZBuffer() {
		for (int i = 0; i < zbuf.length; i++) {
			zbuf[i] = Double.MAX_VALUE;
		}
	}
	
	@Urgency(3)
	@Override
	public void render() {
		Graphics2D g2 = (Graphics2D) container.g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, container.panel.getWidth(), container.panel.getHeight());
		
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);		
	    
	    //Rotate left/right
	    if (container.mouse.dx() < 0) {
	    	container.camera.rotateLeft(Math.abs(container.mouse.dx()));
	    } else if (container.mouse.dx() > 0) {
	    	container.camera.rotateRight(container.mouse.dx());
	    }
	    
	    //Move
	    int sprintfactor = (container.keys[KeyEvent.VK_SHIFT]) ? 2 : 1;
	    
	    if (!container.keys['W'] && !container.keys[KeyEvent.VK_UP] && !container.keys['S'] && !container.keys[KeyEvent.VK_DOWN]) {
	    	sprintfactor = 0;
	    }
	    /**
	    if (container.keys['W'] || container.keys[KeyEvent.VK_UP]) {
	    	container.camera.moveForward(container.map,sprintfactor);
	    }
	    if (container.keys['S'] || container.keys[KeyEvent.VK_DOWN]) {
	    	container.camera.moveBackward(container.map,sprintfactor);
	    }
	    if (container.keys['A'] || container.keys[KeyEvent.VK_LEFT]) {
	    	container.camera.moveLeft(container.map,sprintfactor);
	    }
	    **/
	    Vector pos = container.camera.pos;
	    Vector dir = container.camera.dir;
	    Vector plane = container.camera.plane;
	    
	    for (int x = 0; x < WIDTH; x++) {
	    	//Map the x value to a range of -1 to 1
	    	double norm = (2 * (x/(double)WIDTH)) - 1;
	    	
	    	
	    	Vector rayendp = new Vector(pos.x+dir.x+(plane.x*norm),pos.y+dir.y+(plane.y*norm));
	    	Wall ray = new Wall(pos,rayendp);
	    	
	    	//TODO Add sectors and use those instead of just testing all the walls.
	    	for (Wall l : container.map.walls) {
	    		Vector hit = rayHitSegment(ray,l);
	    		if (hit != null) {
	    			double distance = Vector.distance(pos, hit);
	    			if (distance < zbuf[x]) {
	    				zbuf[x] = distance;
	    				int lineHeight = (int) (HEIGHT/distance);
	    				int drawStart = (HEIGHT/2) - (lineHeight/2);
	    				int drawEnd = (HEIGHT/2) + (lineHeight/2);
	    				
	    				g2.setColor(l.color);
	    				g2.drawLine(x, drawStart, x, drawEnd);
	    			}
	    		}
	    	}
	    }
	}
	
	public static Vector rayHitSegment(Wall ray, Wall seg) {
		double m1 = ray.slope();
		double b1 = ray.yIntercept();
		
		double m2 = seg.slope();
		double b2 = seg.yIntercept();
		
		if (m1==m2) {
			return null;
		}
		
		double bDiff = b1-b2;
		double mDiff = m2-m1;
		
		double sharedX = (bDiff/mDiff);
		double sharedY = (m1*sharedX)+b1;
		
		Vector p = new Vector(sharedX,sharedY);
		double minX = Math.min(seg.v0.x, seg.v1.x);
		double maxX = Math.max(seg.v0.x, seg.v1.x);
		double minY = Math.min(seg.v0.y, seg.v1.y);
		double maxY = Math.max(seg.v0.y, seg.v1.y);
		//Ensure that the point found lies on the second line
		if (p.x >= minX && p.x <= maxX && p.y >= minY && p.y <= maxY) {
			return p;
		}
		return null;
	}

	@Override
	public boolean shouldRedraw() {
		return container.hasUpdated();
	}

	@Override
	public RaycastContainer2 getGraphicsContainer() {
		return container;
	}

}
