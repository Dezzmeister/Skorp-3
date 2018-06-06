package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.KeyStroke;

import com.dezzy.skorp3.Global;
import com.dezzy.skorp3.log.Logger;
import com.dezzy.skorp3.skorp3D.raycast.core.Element;
import com.dezzy.skorp3.skorp3D.raycast.core.RaycastContainer;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast.image.Sprite;
import com.dezzy.skorp3.skorp3D.raycast2.core.Linetype;
import com.dezzy.skorp3.skorp3D.raycast2.core.RenderUtils;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;

public class Raycaster implements RaycastRenderer {
	private RaycastGraphicsContainer container;
	private int WIDTH, HEIGHT;
	private double[] zbuf;
	private Sprite[] sprites;
	private Vector2 pos;
	private Vector2 dir;
	private Vector2 plane;
	private BufferedImage img;
	private Graphics2D g2;
	
	/**
	 * The number of threads that will be working to render the image.
	 */
	private int rendererCount = 10;
	private ThreadRenderer[] renderers;
	private ThreadPoolExecutor executor;
	private LatchRef latchref;
	private Wall perpWall = new Wall();
	private float[] fisheyeLUT;
	/**
	 * Meant for static floors (without texturing).
	 */	
	public Raycaster(RaycastGraphicsContainer _container, int width, int height) {
		container = _container;
		WIDTH = width;
		HEIGHT = height;
		zbuf = new double[WIDTH];
		sprites = container.map.getSprites();
		
		createThreadPoolRenderers();
		populateFisheyeLUT();
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held W"), "moveForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held UP"), "moveForward");
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released W"), "stopMovingForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "stopMovingForward");
	}
	
	private void stopAndExit() {
		if (executor != null) {
			executor.shutdownNow();
		}
		System.exit(0);
	}
	
	private void createThreadPoolRenderers() {
		if (rendererCount > WIDTH) {
			String error = "It is impossible to have more thread renderers than stripes on the screen!";
			System.err.println(error);
			Logger.warn(error);
			stopAndExit();
		}
		
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(rendererCount);
		Logger.log(executor.getCorePoolSize() + " threads created for thread renderers.");
		Logger.log("Maximum threads allowed: " + executor.getMaximumPoolSize());
		
		//In case the executor could not make all the threads we wanted
		rendererCount = executor.getCorePoolSize();
		
		renderers = new ThreadRenderer[rendererCount];
		latchref = new LatchRef();
		
		int interval = (WIDTH - (WIDTH % rendererCount))/rendererCount;
		
		int step = 0;
		
		while (step+interval < WIDTH) {
			int i = step/interval;
			
			renderers[i] = new ThreadRenderer(step,step+interval,latchref,i);
			step += interval;
		}

		renderers[renderers.length-1] = new ThreadRenderer(step,WIDTH,latchref,rendererCount-1);
		
		Logger.log(rendererCount + " thread renderers created.");
	}
	
	private void renderAndBlock() {
		latchref.latch = new CountDownLatch(rendererCount);
		for (int i = 0; i < renderers.length; i++) {
			executor.execute(renderers[i]);
		}
		
		try {
			latchref.latch.await();
		} catch (Exception e) {
			e.printStackTrace(Logger.log);
			e.printStackTrace();
		}
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
		g2 = (Graphics2D) container.g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, container.panel.getWidth(), container.panel.getHeight());
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);		
	    
	    //Rotate left/right
	    if (container.mouse.dx() < 0) {
	    	container.camera.rotateLeft(Math.abs(container.mouse.dx()));
	    } else if (container.mouse.dx() > 0) {
	    	container.camera.rotateRight(container.mouse.dx());
	    }
	    
	    //Move
	    int sprintfactor = (container.keys[KeyEvent.VK_SHIFT]) ? 2 : 1;
	    
	    if (container.keys['W'] || container.keys[KeyEvent.VK_UP]) {
	    	container.camera.moveForward(container.map,sprintfactor);
	    }
	    if (container.keys['S'] || container.keys[KeyEvent.VK_DOWN]) {
	    	container.camera.moveBackward(container.map,sprintfactor);
	    }
	    if (container.keys['A'] || container.keys[KeyEvent.VK_LEFT]) {
	    	container.camera.moveLeft(container.map,sprintfactor);
	    }
	    if (container.keys['D'] || container.keys[KeyEvent.VK_RIGHT]) {
	    	container.camera.moveRight(container.map,sprintfactor);
	    }
	    
	    pos = container.camera.pos;
	    dir = container.camera.dir;
	    plane = container.camera.plane;
	    //System.out.println("DIR: "+dir);
	    //System.out.println("POS: "+pos);
	    
	    //renderFrom(0,WIDTH);
	    renderAndBlock();
	    renderSprites();
	    
	    //g2.drawImage(floor, 0, 0, null);
	    g2.drawImage(img,  0,  0, Global.SCREENWIDTH, Global.SCREENHEIGHT, null);
	}
	
	public void renderSprites() {
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
	}
	
	public void renderFrom(int startX, int endX) {
		
    	for (int x = startX; x < endX; x++) {
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
    }
	
	private class LatchRef {
		public CountDownLatch latch;
	}
	
	private class ThreadRenderer implements Runnable {
		int startX;
		int endX;
		private LatchRef latch;
		private int id;
		
		public ThreadRenderer(int _startX, int _endX, LatchRef _latch, int _id) {
			startX = _startX;
			endX = _endX;
			latch = _latch;
			id = _id;
		}
		
		@Override
		public void run() {
			this.renderFrom(startX,endX);
			latch.latch.countDown();
		}
		
		private void renderFrom(int startX, int endX) {
			
	    	for (int x = startX; x < endX; x++) {
		    	Element element = null;
		    	
		    	double camX = 2 * x/(double)WIDTH - 1.0;
		    	
		        double rdirx = dir.x + plane.x * camX;
		        double rdiry = dir.y + plane.y * camX;
		        
		        int mapX = (int)pos.x;
		        int mapY = (int)pos.y;
		        
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
		            sideDistX = (pos.x - mapX) * deltaDistX;
		        } else {
		        	stepX = 1;
		        	sideDistX = (mapX + 1.0 - pos.x) * deltaDistX;
		        }
		          
		        if (rdiry < 0) {
		        	stepY = -1;
		        	sideDistY = (pos.y - mapY) * deltaDistY;
		        } else {
		        	stepY = 1;
		        	sideDistY = (mapY + 1.0 - pos.y) * deltaDistY;
		        }
		        Vector2 currentLoc;
		        Vector2 customHit = null;
		        Wall hitWall = null;
		        //DDA
		        dda:  while (!hit) {
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
		        		if (element.isCustom()) {
		        			currentLoc = new Vector2(mapX,mapY);
		        			Vector2 rayDirection = new Vector2((float)rdirx + pos.x,(float)rdiry + pos.y);
		        			for (Wall l : element.lines) {
		        				Wall testing = new Wall(l.v0().add(currentLoc),l.v1().add(currentLoc));
		        				customHit = RenderUtils.rayHitSegment(pos, rayDirection, testing);
		        				if (customHit != null) {
		        					testing.texture = l.texture;
		        					hitWall = testing;
		        					break dda;
		        				}
		        			}
		        		} else {
		        			hit = true;
		        		}
		        	}
		        }
		        //System.out.println(mapX + " " + rdirx);
		        if (customHit == null) {
		        	if (!side) {
		        		perpWallDist = ((mapX - pos.x + (1 - stepX)/2))/rdirx;
		        	} else {
		        		perpWallDist = ((mapY - pos.y + (1 - stepY)/2))/rdiry;
		        	}
		        } else {
		        	//perpWallDist = Vector2.distance(pos, customHit);
		        	if (!side) {
		        		perpWallDist = ((customHit.x - pos.x + (1 - Math.abs(stepX))/2))/rdirx;
		        	} else {
		        		perpWallDist = ((customHit.y - pos.y + (1 - Math.abs(stepY))/2))/rdiry;
		        	}
		        	
		        }
		        
		        int lineHeight = (int)(HEIGHT/perpWallDist);
		          
		        int drawStart = -(lineHeight >> 1) + (HEIGHT >> 1);
		        int trueDrawStart = drawStart;
		        if (drawStart < 0) {
		        	  drawStart = 0;
		        }
		        int drawEnd = (lineHeight + HEIGHT) >> 1;
		        if (drawEnd >= HEIGHT) {
		        	  drawEnd = HEIGHT -1;
		        }
		        
		        //Texturing
		        double wallX;
		        if (customHit == null) {
		        	if (side) {
		        		wallX = (pos.x + ((mapY - pos.y + (1 - stepY)/2)/rdiry) * rdirx);
		        	} else {
		        		wallX = (pos.y + ((mapX - pos.x + (1 - stepX)/2)/rdirx) * rdiry);
		        	}
		        } else {
		        	if (side) {
		        		wallX = (pos.x + ((customHit.y - pos.y + (1 - Math.abs(stepY))/2)/rdiry) * rdirx);
		        	} else {
		        		wallX = (pos.y + ((customHit.x - pos.x + (1 - Math.abs(stepX))/2)/rdirx) * rdiry);
		        	}
		        }
		        
		        wallX -= Math.floor(wallX);
		        
		        int texX;
		        
		        if (customHit == null) {
		        	texX = (int)(wallX * element.frontTexture().SIZE);
		        } else {
		        	texX = (int)(element.frontTexture().SIZE * hitWall.getNorm(customHit));
		        }
		        if((!side && rdirx > 0) || (side && rdiry < 0)) texX = element.frontTexture().SIZE - texX - 1;
		        
		        for (int y = drawStart; y < drawEnd; y++) {
		        	int texY;
		        	if (customHit == null) {
		        		texY = ((((y << 1) - HEIGHT + lineHeight) * element.frontTexture().SIZE)/lineHeight) >> 1;
		        	} else {
		        		texY = (int) (((y - trueDrawStart)/(float)lineHeight) * element.frontTexture().SIZE);
		        	}
		        	int color;
		        	if (!side && (texX + (texY * element.frontTexture().SIZE)) < element.frontTexture().pixels.length && (texX + (texY * element.frontTexture().SIZE)) >= 0) {
		        		color = element.frontTexture().pixels[(int) (texX + (texY * element.frontTexture().SIZE))];
		        	} else if ((texX + (texY * element.sideTexture().SIZE)) < element.sideTexture().pixels.length && (texX + (texY * element.sideTexture().SIZE)) >= 0){
		        		color = (element.sideTexture().pixels[(int) (texX + (texY * element.sideTexture().SIZE))]);
		        	} else {
		        		color = 0;
		        	}
		        	img.setRGB(x, y, color);
		        }
		        img.setRGB(x, drawStart, 0xFFFF0000);
		        img.setRGB(x, drawEnd, 0xFFFF0000);
		        
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
		        	//int color = 0xFF323232;
		        	int ceilColor = (ceilingtex.pixels[ceilingtex.SIZE * floorTexY + floorTexX]);
		        	//int ceilColor = 0xFF505050;
		        	img.setRGB(x, y, color);
		        	img.setRGB(x, (HEIGHT - y), ceilColor);
		        }
		        
		    }
	    }
	}
	
	private void populateFisheyeLUT() {
		fisheyeLUT = new float[WIDTH];
		pos = container.camera.pos;
	    dir = container.camera.dir;
	    plane = container.camera.plane;
	    perpWall = new Wall(pos.x,pos.y,pos.x+dir.x,pos.y+dir.y);
	    
		for (int x = 0; x < WIDTH; x++) {
			float norm = (2 * (x/(float)WIDTH)) - 1;
			Vector2 rayendp = new Vector2(pos.x+dir.x+(plane.x*norm),pos.y+dir.y+(plane.y*norm));
			Wall ray = new Wall(pos,rayendp);
			
			fisheyeLUT[x] = (float) Math.cos(RenderUtils.angleBetweenLines(perpWall, ray));
		}
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
