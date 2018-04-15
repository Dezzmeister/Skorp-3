package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import com.dezzy.skorp3.skorp3D.raycast.actions.KeyHub;
import com.dezzy.skorp3.skorp3D.raycast.actions.MoveAction;
import com.dezzy.skorp3.skorp3D.raycast.actions.MoveForwardAction;
import com.dezzy.skorp3.skorp3D.raycast.actions.MoveForwardReleasedAction;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector;

public class Raycaster implements RaycastRenderer {
	private RaycastGraphicsContainer container;
	private MoveAction forwardMover;
	private MoveAction forwardStopper;
	private KeyHub hub = new KeyHub();
	private int WIDTH, HEIGHT;
	private double aspect = 1;
	
	public Raycaster(RaycastGraphicsContainer _container, int width, int height) {
		container = _container;
		WIDTH = width;
		HEIGHT = height;
		aspect = WIDTH/(double)HEIGHT;
		
		forwardMover = new MoveForwardAction(container,hub);
		forwardStopper = new MoveForwardReleasedAction(container,hub);
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held W"), "moveForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held UP"), "moveForward");
		//container.panel.getActionMap().put("moveForward", forwardMover);
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released W"), "stopMovingForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "stopMovingForward");
		//container.panel.getActionMap().put("stopMovingForward", forwardStopper);
		
	}
	
	@Override
	public void render() {
		Graphics2D g2 = (Graphics2D) container.g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, container.panel.getWidth(), container.panel.getHeight());
	    
	    //Rotate left/right
	    if (container.mouse.dx() < 0) {
	    	container.camera.rotateLeft(Math.abs(container.mouse.dx()));
	    } else if (container.mouse.dx() > 0) {
	    	container.camera.rotateRight(container.mouse.dx());
	    }

	    if (container.keys['W'] || container.keys[KeyEvent.VK_UP]) {
	    	container.camera.moveForward(container.map,1);
	    }
	    
	    Vector pos = container.camera.pos;
	    Vector dir = container.camera.dir;
	    Vector plane = container.camera.plane;
	    
	    for (int x = 0; x < WIDTH; x++) {
	    	double camX = 2 * x/(double)WIDTH - 1.0;
	        double rposx = pos.x;
	        double rposy = pos.y;
	        double rdirx = dir.x + plane.x * camX;
	        double rdiry = dir.y + plane.y * camX;
	        
	        int mapX = (int)rposx;
	        int mapY = (int)rposy;
	        
	        double sideDistX;
	        double sideDistY;
	        
	        double deltaDistX = Math.sqrt(1 + (rdiry * rdiry)/(rdirx * rdirx));
	        double deltaDistY = Math.sqrt(1 + (rdirx * rdirx)/(rdiry * rdiry));
	        double perpWallDist;
	        
	        int stepX;
	        int stepY;
	        
	        boolean hit = false;
	        boolean side = false;
	        
	        if (rdirx < 0) {
	            stepX = -1;
	            sideDistX = (rposx - mapX) * deltaDistX;
	        } else {
	        	stepX = 1;
	        	sideDistX = (mapX + 1.0 - rposx) * deltaDistX;
	        }
	          
	        if (rdiry < 0) {
	        	stepY = -1;
	        	sideDistY = (rposy - mapY) * deltaDistY;
	        } else {
	        	stepY = 1;
	        	sideDistY = (mapY + 1.0 - rposy) * deltaDistY;
	        }
	          
	        //DDA
	        while (!hit) {
	        	if (sideDistX < sideDistY) {
	        	    sideDistX += deltaDistX;
	        		mapX += stepX;
	        		side = false;
	        	} else {
	        		sideDistY += deltaDistY;
	        		mapY += stepY;
	        		side = true;
	        	}
	            
	        	if (container.map.get(mapX,mapY).id() > 0) {
	        		hit = true;
	        	}
	        }
	          
	        if (!side) {
	        	perpWallDist = (mapX - rposx + (1 - stepX)/2)/rdirx;
	        } else {
	        	perpWallDist = (mapY - rposy + (1 - stepY)/2)/rdiry;
	        }
	          
	        int lineHeight = (int)(HEIGHT/perpWallDist);
	          
	        int drawStart = -lineHeight/2 + HEIGHT/2;
	        if (drawStart < 0) {
	        	  drawStart = 0;
	        }
	        int drawEnd = lineHeight/2 + HEIGHT/2;
	        if (drawEnd >= HEIGHT) {
	        	  drawEnd = HEIGHT -1;
	        }
	          
	        Color col = container.map.get(mapX, mapY).color();
	          
	        if (side) {
	              col = new Color(col.getRed()/2,col.getGreen()/2,col.getBlue()/2);
	        }
	        
	        //Scale the line based on aspect ratio
	        int yDiff = (lineHeight/2);
	        yDiff *= aspect;
	        drawStart = (HEIGHT/2) - yDiff;
	        drawEnd = (HEIGHT/2) + yDiff;
	        
	        g2.setColor(col);
	        g2.drawLine(x, drawStart, x, drawEnd);
	    }
	}

	@Override
	public boolean shouldRedraw() {
		return container.hasUpdated();
	}

	@Override
	public RaycastGraphicsContainer getGraphicsContainer() {
		return container;
	}
	
	public void updateDimensions(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		aspect = WIDTH/(double)HEIGHT;
	}
}
