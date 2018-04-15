package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.KeyStroke;

import com.dezzy.skorp3.skorp3D.raycast.core.Element;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector;

public class Raycaster implements RaycastRenderer {
	private RaycastGraphicsContainer container;
	private int WIDTH, HEIGHT;
	private double aspect = 1;
	private static final Color FLOOR = new Color(89,45,0);
	
	public Raycaster(RaycastGraphicsContainer _container, int width, int height) {
		container = _container;
		WIDTH = width;
		HEIGHT = height;
		aspect = WIDTH/(double)HEIGHT;
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held W"), "moveForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held UP"), "moveForward");
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released W"), "stopMovingForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "stopMovingForward");		
	}
	
	@Override
	public void render() {
		Graphics2D g2 = (Graphics2D) container.g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, container.panel.getWidth(), container.panel.getHeight());
		g2.setColor(FLOOR);
		g2.fillRect(0, HEIGHT/2, WIDTH, HEIGHT/2);
		
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		//Sick-looking floor
		int iterations = 15;
		int startblue = 89;
		int endblue = 46;
		int bluestep = (startblue - endblue)/iterations;
		
		int startgreen = 45;
		int endgreen = 23;
		int greenstep = (startgreen - endgreen)/iterations;
		
		int b = 0;
		for (int i = HEIGHT; i > ((HEIGHT/2) + ((HEIGHT/2)/iterations)); i -= ((HEIGHT/2)/iterations)) {
			//89 45 0
			//to
			//46 23 0
			
			g2.setColor(new Color(startblue - (b * bluestep),startgreen - (b * greenstep),0));
			g2.fillRect(0, i - ((HEIGHT/2)/iterations), WIDTH, (HEIGHT/2)/iterations);
			b++;
		}
		
	    
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
	    
	    if (container.keys['W'] || container.keys[KeyEvent.VK_UP]) {
	    	container.camera.moveForward(container.map,sprintfactor);
	    }
	    if (container.keys['S'] || container.keys[KeyEvent.VK_DOWN]) {
	    	container.camera.moveBackward(container.map,sprintfactor);
	    }
	    
	    Vector pos = container.camera.pos;
	    Vector dir = container.camera.dir;
	    Vector plane = container.camera.plane;
	    
	    for (int x = 0; x < WIDTH; x++) {
	    	Element element = null;
	    	
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
	            element = container.map.get(mapX, mapY);
	        	if (element.id() > 0) {
	        		hit = true;
	        	}
	        }
	          
	        if (!side) {
	        	perpWallDist = (mapX - rposx + (1 - stepX)/2)/rdirx;
	        } else {
	        	perpWallDist = (mapY - rposy + (1 - stepY)/2)/rdiry;
	        }
	        //System.out.println(perpWallDist);
	          
	        int lineHeight = (int)(HEIGHT/perpWallDist);
	          
	        int drawStart = -lineHeight/2 + HEIGHT/2;
	        if (drawStart < 0) {
	        	  drawStart = 0;
	        }
	        int drawEnd = lineHeight/2 + HEIGHT/2;
	        if (drawEnd >= HEIGHT) {
	        	  drawEnd = HEIGHT -1;
	        }
	        
	      //Scale the line based on aspect ratio
	        int yDiff = (lineHeight/2);
	        yDiff *= aspect;
	        //drawStart = (HEIGHT/2) - yDiff;
	        //drawEnd = (HEIGHT/2) + yDiff;
	        //lineHeight = drawEnd - drawStart;
	        
	        //Texturing
	        double wallX;
	        if (side) {
	        	wallX = (pos.x + ((mapY - pos.y + (1 - stepY)/2)/rdiry) * rdirx);
	        } else {
	        	wallX = (pos.y + ((mapX - pos.x + (1 - stepX)/2)/rdirx) * rdiry);
	        }
	        
	        wallX -= Math.floor(wallX);
	        
	        int texX = (int)(wallX * element.frontTexture().SIZE);
	        if((!side && rdirx > 0) || (side && rdiry < 0)) texX = element.frontTexture().SIZE - texX - 1;
	        
	        double alpha = 0;
	        
	        if (perpWallDist > container.camera.fogDistance) {
	        	alpha = 255;
	        } else {
	        	alpha = (perpWallDist/container.camera.fogDistance) * 255;
	        }
	        
	        for (int y = drawStart; y < drawEnd; y++) {
	        	int texY = (((y*2 - HEIGHT + lineHeight) * element.frontTexture().SIZE)/lineHeight)/2;
	        	int color;
	        	if (!side) {
	        		color = element.frontTexture().pixels[(int) (texX + (texY * element.frontTexture().SIZE))];
	        	} else {
	        		color = (element.sideTexture().pixels[(int) (texX + (texY * element.sideTexture().SIZE))]);
	        	}
	        	img.setRGB(x, y, color);
	        }
	        
	        /*
	        Color col = element.color();
	          
	        if (side) {
	              col = new Color(col.getRed()/2,col.getGreen()/2,col.getBlue()/2);
	        }
	        
	        g2.setColor(col);
	        g2.drawLine(x, drawStart, x, drawEnd);
	        */
	    }
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
	
	public void updateDimensions(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		aspect = WIDTH/(double)HEIGHT;
	}
}
