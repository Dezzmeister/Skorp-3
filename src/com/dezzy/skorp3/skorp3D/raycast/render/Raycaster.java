package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.KeyStroke;

import com.dezzy.skorp3.Global;
import com.dezzy.skorp3.field.Line;
import com.dezzy.skorp3.skorp3D.raycast.core.Element;
import com.dezzy.skorp3.skorp3D.raycast.core.RaycastContainer;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast.image.Sprite;

public class Raycaster implements RaycastRenderer {
	private RaycastGraphicsContainer container;
	private int WIDTH, HEIGHT;
	private double[] zbuf;
	private Sprite[] sprites;
	/**
	 * Meant for static floors (without texturing).
	 */	
	public Raycaster(RaycastGraphicsContainer _container, int width, int height) {
		container = _container;
		WIDTH = width;
		HEIGHT = height;
		zbuf = new double[WIDTH];
		sprites = container.map.getSprites();
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held W"), "moveForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held UP"), "moveForward");
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released W"), "stopMovingForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "stopMovingForward");
	}
	
	public static BufferedImage makeFloor(int width, int height) {
		BufferedImage floor = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = floor.createGraphics();
		
		//Sick-looking floor
		int iterations = 16;
		int startblue = 89;
		int endblue = 45;
		int bluestep = (startblue - endblue)/iterations;
				
		int startgreen = 46;
		int endgreen = 23;
		int greenstep = (startgreen - endgreen)/iterations;
				
		int b = 0;
		for (int i = height; i > ((height/2) + ((height/2)/iterations)); i -= ((height/2)/iterations)) {
			//89 45 0
			//to
			//46 23 0
					
			g2.setColor(new Color(startblue - (b * bluestep),startgreen - (b * greenstep),0));
			g2.fillRect(0, i - ((height/2)/iterations), width, (height/2)/iterations);
			b++;
		}
		
		g2.dispose();
		
		return floor;
	}
	
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
	    
	    if (container.keys['W'] || container.keys[KeyEvent.VK_UP]) {
	    	container.camera.moveForward(container.map,sprintfactor);
	    }
	    if (container.keys['S'] || container.keys[KeyEvent.VK_DOWN]) {
	    	container.camera.moveBackward(container.map,sprintfactor);
	    }
	    if (container.keys['A'] || container.keys[KeyEvent.VK_LEFT]) {
	    	container.camera.moveLeft(container.map,sprintfactor);
	    }
	    
	    Vector2 pos = container.camera.pos;
	    Vector2 dir = container.camera.dir;
	    Vector2 plane = container.camera.plane;
	    
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
	        	if (element != Element.SPACE) {
	        		hit = true;
	        	}
	        }
	        //System.out.println(mapX + " " + rdirx);
	          
	        if (!side) {
	        	perpWallDist = (mapX - rposx + (1 - stepX)/2)/rdirx;
	        } else {
	        	perpWallDist = (mapY - rposy + (1 - stepY)/2)/rdiry;
	        }
	        
	        int lineHeight = (int)(HEIGHT/perpWallDist);
	          
	        int drawStart = -(lineHeight >> 1) + (HEIGHT >> 1);
	        if (drawStart < 0) {
	        	  drawStart = 0;
	        }
	        int drawEnd = (lineHeight >> 1) + (HEIGHT >> 1);
	        if (drawEnd >= HEIGHT) {
	        	  drawEnd = HEIGHT -1;
	        }
	        
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
	        
	        for (int y = drawStart; y < drawEnd; y++) {
	        	int texY = ((((y << 1) - HEIGHT + lineHeight) * element.frontTexture().SIZE)/lineHeight) >> 1;
	        	int color;
	        	if (!side) {
	        		color = element.frontTexture().pixels[(int) (texX + (texY * element.frontTexture().SIZE))];
	        	} else {
	        		color = (element.sideTexture().pixels[(int) (texX + (texY * element.sideTexture().SIZE))]);
	        	}
	        	img.setRGB(x, y, color);
	        }
	        
	        zbuf[x] = perpWallDist;
	        
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
	        
	        double currentDist;
	        
	        if (drawEnd < 0) drawEnd = HEIGHT;
	        
	        Texture floortex = container.map.floorTexture();
	        Texture ceilingtex = container.map.ceilingTexture();
	        
	        for (int y = drawEnd + 1; y < HEIGHT; y++) {
	        	currentDist = HEIGHT/((2.0 * y) - HEIGHT);
	        	
	        	double weight = (currentDist)/(perpWallDist);
	        	
	        	double currentFloorX = weight * floorXWall + (1.0 - weight) * pos.x;
	        	double currentFloorY = weight * floorYWall + (1.0 - weight) * pos.y;
	        	
	        	int floorTexX;
	        	int floorTexY;
	        	floorTexX = (int)(currentFloorX * floortex.SIZE) % floortex.SIZE;
	        	floorTexY = (int)(currentFloorY * floortex.SIZE) % floortex.SIZE;
	        	
	        	int color = (floortex.pixels[floortex.SIZE * floorTexY + floorTexX]);
	        	int ceilColor = (ceilingtex.pixels[ceilingtex.SIZE * floorTexY + floorTexX]);
	        	img.setRGB(x, y, color);
	        	img.setRGB(x, (HEIGHT - y), ceilColor);
	        }
	        
	    }
	    
	    //Draw sprites
	    for (int i = 0; i < sprites.length; i++) {
	    	sprites[i].order = i;
	    	sprites[i].distance = ((pos.x - sprites[i].x) * (pos.x - sprites[i].x) + (pos.y - sprites[i].y) * (pos.y - sprites[i].y));
	    }
	    Arrays.sort(sprites);
	    
	    for (int i = 0; i < sprites.length; i++) {
	    	double spriteX = sprites[sprites[i].order].x - pos.x;
	    	double spriteY = sprites[sprites[i].order].y - pos.y;
	    	
	    	double invDet = 1.0 / (plane.x * dir.y - dir.x * plane.y);
	    	double transformX = invDet * (dir.y * spriteX - dir.x * spriteY);
	    	double transformY = invDet * (-plane.y * spriteX + plane.x * spriteY);
	    	
	    	int spriteScreenX = (int)((WIDTH >> 1) * (1 + transformX / transformY));
	    	
	    	int spriteHeight = Math.abs((int)(HEIGHT / transformY));
	    	int drawStartY = -(spriteHeight >> 1) + (HEIGHT >> 1);
	    	if (drawStartY < 0) {
	    		drawStartY = 0;
	    	}
	    	int drawEndY = (spriteHeight >> 1) + (HEIGHT >> 1);
	    	if (drawEndY >= HEIGHT) {
	    		drawEndY = HEIGHT - 1;
	    	}
	    	
	    	int spriteWidth = Math.abs((int)(HEIGHT / transformY));
	    	int drawStartX = -(spriteWidth >> 1) + spriteScreenX;
	    	if (drawStartX < 0) {
	    		drawStartX = 0;
	    	}
	    	int drawEndX = (spriteWidth >> 1) + spriteScreenX;
	    	if (drawEndX >= WIDTH) {
	    		drawEndX = WIDTH - 1;
	    	}
	    	
	    	
	    	int texWidth = sprites[i].width();
	    	int texHeight = sprites[i].height();
	    	for (int stripe = drawStartX; stripe < drawEndX; stripe++) {
	    		int texX = (int)(((stripe - ((-spriteWidth >> 1) + spriteScreenX)) << 8) * texWidth / spriteWidth) >> 8;
	    		if (transformY > 0 && stripe > 0 && stripe < WIDTH && transformY < zbuf[stripe]) {
	    			for (int y = drawStartY; y < drawEndY; y++) {
	    				int d = (y << 8) - (HEIGHT << 7)  + (spriteHeight << 7);
	    				int texY = ((d * texHeight)/spriteHeight) >> 8;
	    				int	color = sprites[sprites[i].order].pixels[texX + texWidth * texY];
	    				if ((color & ~sprites[i].alpha) != 0) {
	    					img.setRGB(stripe, y, color);
	    				}
	    			}
	    		}
	    	}
	    }
	    
	    //g2.drawImage(floor, 0, 0, null);
	    g2.drawImage(img,  0,  0, Global.SCREENWIDTH, Global.SCREENHEIGHT, null);
	}
	
	public static Vector2 rayHitSegment(Line ray, Line seg) {
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
		
		Vector2 p = new Vector2((float)sharedX,(float)sharedY);
		double minX = Math.min(seg.point.x, seg.endpoint.x);
		double maxX = Math.max(seg.point.x, seg.endpoint.x);
		double minY = Math.min(seg.point.y, seg.endpoint.y);
		double maxY = Math.max(seg.point.y, seg.endpoint.y);
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
	public RaycastContainer getGraphicsContainer() {
		return container;
	}
	
	public void updateDimensions(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
	}

	@Override
	public void updateGraphicsObject(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
