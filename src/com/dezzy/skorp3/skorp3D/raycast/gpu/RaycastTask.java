package com.dezzy.skorp3.skorp3D.raycast.gpu;

import java.awt.image.BufferedImage;

import com.aparapi.Kernel;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;

public class RaycastTask extends Kernel {
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
    private final double[] pos = new double[2];
    private final double[] dir = new double[2];
    private final double[] plane = new double[2];
    private final int[] img;
	
	public RaycastTask(RaycastGraphicsContainer _container, int _width, int _height) {
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
		img = new int[WIDTH * HEIGHT];
	}
	
	@Override
	public void run() {
		int x = getGlobalId();
		
		double camX = 2 * x/(double)WIDTH - 1.0;
        double rposx = pos[0];
        double rposy = pos[1];
        double rdirx = dir[0] + plane[0] * camX;
        double rdiry = dir[1] + plane[1] * camX;
        
        int mapX = (int)rposx;
        int mapY = (int)rposy;
        
        double sideDistX = 0;
        double sideDistY = 0;
        
        double deltaDistX = Math.sqrt(1 + (rdiry * rdiry)/(rdirx * rdirx));
        double deltaDistY = Math.sqrt(1 + (rdirx * rdirx)/(rdiry * rdiry));
        double perpWallDist = 0;
        
        int stepX = 0;
        int stepY = 0;
        
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
            
        	if (ids[mapX][mapY] != 0) {
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
        double wallX = 0;
        if (side) {
        	wallX = (pos[0] + ((mapY - pos[1] + (1 - stepY)/2)/rdiry) * rdirx);
        } else {
        	wallX = (pos[1] + ((mapX - pos[0] + (1 - stepX)/2)/rdirx) * rdiry);
        }
        
        wallX -= Math.floor(wallX);
        
        int[] frontTexture = textures[mapX][mapY][0];
        int[] sideTexture = textures[mapX][mapY][1];
        int SIZE = texSizeMap[mapX][mapY][0];
        
        int texX = (int)(wallX * SIZE);
        if((!side && rdirx > 0) || (side && rdiry < 0)) texX = SIZE - texX - 1;
        
        for (int y = drawStart; y < drawEnd; y++) {
        	int texY = ((((y << 1) - HEIGHT + lineHeight) * SIZE)/lineHeight) >> 1;
        	int color = 0;
        	if (!side) {
        		color = frontTexture[(int) (texX + (texY * SIZE))];
        	} else {
        		color = (sideTexture[(int) (texX + (texY * SIZE))]);
        	}
        	img[x + y * WIDTH] = color;
        }
        
        //Floor casting
        double floorXWall = 0;
        double floorYWall = 0;
        
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

        double currentDist = 0;
        
        if (drawEnd < 0) drawEnd = HEIGHT;
        
        for (int y = drawEnd + 1; y < HEIGHT; y++) {
        	currentDist = HEIGHT/((2.0 * y) - HEIGHT);
        	
        	double weight = (currentDist)/(perpWallDist);
        	
        	double currentFloorX = weight * floorXWall + (1.0 - weight) * pos[0];
        	double currentFloorY = weight * floorYWall + (1.0 - weight) * pos[1];
        	
        	int floorTexX = (int)(currentFloorX * floortexSize) % floortexSize;
        	int floorTexY = (int)(currentFloorY * floortexSize) % floortexSize;
        	
        	int color = (floortexture[floortexSize * floorTexY + floorTexX]);
        	int ceilColor = (ceilingtexture[ceiltexSize * floorTexY + floorTexX]);

        	img[x + y * WIDTH] = color;
        	img[x + ((HEIGHT - y) * WIDTH)] = ceilColor;
        }	
	}
	
	public void updateCameraVectors() {
		pos[0] = container.camera.pos.x;
	    pos[1] = container.camera.pos.y;
	    
	    dir[0] = container.camera.dir.x;
	    dir[1] = container.camera.dir.y;
	    
	    plane[0] = container.camera.plane.x;
	    plane[1] = container.camera.plane.y;
	}
	
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	    image.setRGB(0, 0, WIDTH, HEIGHT, img, 0, WIDTH);
	    return image;
	}
}
