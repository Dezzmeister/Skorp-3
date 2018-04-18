package com.dezzy.skorp3.skorp3D.render.gpu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.KeyStroke;

import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastRenderer;

public class GPURaycaster implements RaycastRenderer {
	private final int WIDTH;
	private final int HEIGHT;
	private RaycastGraphicsContainer container;
	private final int[][] ids;
	private final int[][][][] textures;
	private final int[] floortexture;
    private final int[] ceilingtexture;
    private final int floortexSize;
    private final int ceiltexSize;
    private final int[][][] texSizeMap;
	
	public GPURaycaster(RaycastGraphicsContainer _container, int _width, int _height) {
		container = _container;
		WIDTH = _width;
		HEIGHT = _height;
		ids = container.map.getIDArray();
		textures = container.map.getTextureArrays();
		floortexture = container.map.floorTexture().pixels;
		ceilingtexture = container.map.ceilingTexture().pixels;
		floortexSize = (int) Math.sqrt(floortexture.length);
		ceiltexSize = (int) Math.sqrt(ceilingtexture.length);
		texSizeMap = container.map.getTextureSizeMap();
		
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
		
		//BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		int[] img = new int[WIDTH * HEIGHT];
	    
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
	    
	    double[] pos = {container.camera.pos.x,container.camera.pos.y};
	    double[] dir = {container.camera.dir.x,container.camera.dir.y};
	    double[] plane = {container.camera.plane.x,container.camera.plane.y};
	    
	    for (int x = 0; x < WIDTH; x++) {
	    	
	    	double camX = 2 * x/(double)WIDTH - 1.0;
	        double rposx = pos[0];
	        double rposy = pos[1];
	        double rdirx = dir[0] + plane[0] * camX;
	        double rdiry = dir[1] + plane[1] * camX;
	        
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
	        	
	        	if (ids[mapX][mapY] > 0) {
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
	        
	        //Texturing
	        double wallX;
	        if (side) {
	        	wallX = (pos[0] + ((mapY - pos[1] + (1 - stepY)/2)/rdiry) * rdirx);
	        } else {
	        	wallX = (pos[1] + ((mapX - pos[0] + (1 - stepX)/2)/rdirx) * rdiry);
	        }
	        
	        wallX -= Math.floor(wallX);
	        
	        int[] fronttex = textures[mapX][mapY][0];

	        int[] sidetex = textures[mapX][mapY][1];
	        
	        int frontSize = texSizeMap[mapX][mapY][0];
	        int sideSize = texSizeMap[mapX][mapY][1];
	        
	        int texX = (int)(wallX * frontSize);
	        if((!side && rdirx > 0) || (side && rdiry < 0)) texX = frontSize - texX - 1;
	        
	        for (int y = drawStart; y < drawEnd; y++) {
	        	int texY = (((y*2 - HEIGHT + lineHeight) * frontSize)/lineHeight)/2;
	        	int color;
	        	if (!side) {
	        		color = fronttex[(int) (texX + (texY * frontSize))];
	        	} else {
	        		color = (sidetex[(int) (texX + (texY * sideSize))]);
	        	}
	        	img[x + y * WIDTH] = color;
	        }
	        
	        //Floor casting
	        double floorXWall;
	        double floorYWall;
	        
	        if (!side && rdirx > 0) {
	        	floorXWall = mapX;
	        	floorYWall = mapY + wallX;
	        } else if (!side && rdirx < 0) {
	        	floorXWall = mapX + 1.0;
	        	floorYWall = mapY + wallX;
	        } else if(side && rdiry > 0) {
	        	floorXWall = mapX + wallX;
	        	floorYWall = mapY;
	        } else {
	        	floorXWall = mapX + wallX;
	        	floorYWall = mapY + 1.0;
	        }
	        
	        double distWall;
	        double distPlayer;
	        double currentDist;
	        
	        distWall = perpWallDist;
	        distPlayer = 0.0;
	        
	        if (drawEnd < 0) drawEnd = HEIGHT;
	        
	        for (int y = drawEnd + 1; y < HEIGHT; y++) {
	        	currentDist = HEIGHT/((2.0 * y) - HEIGHT);
	        	
	        	double weight = (currentDist - distPlayer)/(distWall - distPlayer);
	        	
	        	double currentFloorX = weight * floorXWall + (1.0 - weight) * pos[0];
	        	double currentFloorY = weight * floorYWall + (1.0 - weight) * pos[1];
	        	
	        	int floorTexX;
	        	int floorTexY;
	        	floorTexX = (int)(currentFloorX * floortexSize) % floortexSize;
	        	floorTexY = (int)(currentFloorY * floortexSize) % floortexSize;
	        	
	        	int color = (floortexture[floortexSize * floorTexY + floorTexX]);
	        	int ceilColor = (ceilingtexture[ceiltexSize * floorTexY + floorTexX]);
	        	img[x + y * WIDTH] = color;
	        	img[x + ((HEIGHT-y) * WIDTH)] = ceilColor;
	        }
	        
	    }
	    //g2.drawImage(floor, 0, 0, null);
	    BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	    image.setRGB(0, 0, WIDTH, HEIGHT, img, 0, WIDTH);
	    
	    g2.drawImage(image,  0,  0, null);
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
