package com.dezzy.skorp3.skorp3D.raycast2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.KeyStroke;

import com.dezzy.skorp3.Global;
import com.dezzy.skorp3.annotations.urgency.Urgency;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastRenderer;
import com.dezzy.skorp3.skorp3D.raycast2.core.RaycastContainer2;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;

/**
 * The chief difference between this raycaster and the first is that this does not use digital differential analysis
 * like the first one does. The map is not represented as a grid of elements; it is represented as 
 * a collection of lines (sectors coming soon). Ray-line segment intersection math is used instead of DDA. This may be slower, but it 
 * gives much more flexibility. For example, this new raycaster allows for non-orthogonal walls and walls
 * of varying heights. However, using this new system makes other tasks more difficult, such as collision detection.
 * 
 * @author Dezzmeister
 *
 */
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
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released W"), "stopMovingForward");
		
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("held UP"), "moveForward");
		container.panel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "stopMovingForward");
	}
	
	//TODO optimize with predefined array and System.arraycopy()? (if need be)
	@Urgency(4)
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
	    
	    if (container.keys['W'] || container.keys[KeyEvent.VK_UP]) {
	    	container.camera.moveForward(sprintfactor);
	    }
	    
	    if (container.keys['S'] || container.keys[KeyEvent.VK_DOWN]) {
	    	container.camera.moveBackward(sprintfactor);
	    }
	    
	    if (container.keys['A'] || container.keys[KeyEvent.VK_LEFT]) {
	    	container.camera.moveLeft(sprintfactor);
	    }
	    
	    if (container.keys['D'] || container.keys[KeyEvent.VK_RIGHT]) {
	    	container.camera.moveRight(sprintfactor);
	    }
	    
	    Vector pos = container.camera.pos;
	    Vector dir = container.camera.dir;
	    Vector plane = container.camera.plane;
	    
	    for (int x = 0; x < WIDTH; x++) {
	    	//Map the x value to a range of -1 to 1
	    	double norm = (2 * (x/(double)WIDTH)) - 1;
	    	
	    	//The direction of the ray
	    	Vector rayendp = new Vector(pos.x+dir.x+(plane.x*norm),pos.y+dir.y+(plane.y*norm));
	    	
	    	//The correct wall
	    	Wall perpWall = new Wall(pos.x,pos.y,pos.x+dir.x,pos.y+dir.y);
	    	
	    	//TODO Add sectors and use those instead of just testing all the walls.
	    	for (Wall l : container.map.walls) {
	    		Vector hit = rayHitSegment(pos,rayendp,l);
	    		Wall ray = new Wall(pos,hit);
	    		if (hit != null) {
	    			double distance = Vector.distance(pos, hit);
	    			distance *= Math.cos(Wall.angleBetweenLines(perpWall, ray));
	    			
	    			if (distance < zbuf[x]) {
	    				zbuf[x] = distance;
	    				int lineHeight = (int) (HEIGHT/distance);
	    				
	    				int drawStart = (HEIGHT/2) - (lineHeight/2);
	    				drawStart = (int)clamp(drawStart,0,HEIGHT-1);
	    				
	    				int drawEnd = (HEIGHT/2) + (lineHeight/2);
	    				drawEnd = (int)clamp(drawEnd,0,HEIGHT-1);
	    				
	    				for (int y = drawStart; y <= drawEnd; y++) {
	    					img.setRGB(x, y, l.color);
	    				}
	    			}
	    		}
	    	}
	    }
	    resetZBuffer();
	    g2.drawImage(img,  0,  0, Global.SCREENWIDTH, Global.SCREENHEIGHT, null);
	}
	
	public static double clamp(double val, double minVal, double maxVal) {
		if (val < minVal) {
			return minVal;
		}
		if (val > maxVal) {
			return maxVal;
		}
		return val;
	}
	
	public static Vector rayHitSegment(Vector rayStart, Vector rayDirection, Wall segment) {
		Vector r0 = rayStart;
		Vector r1 = rayDirection;
		Vector a = segment.v0;
		Vector b = segment.v1;
		
		Vector s1,s2;
		s1 = new Vector(r1.x-r0.x,r1.y-r0.y);
		s2 = new Vector(b.x-a.x,b.y-a.y);
		  
		double s,t;
		s = (-s1.y * (r0.x - a.x) + s1.x * (r0.y - a.y)) / (-s2.x * s1.y + s1.x * s2.y);
		t = (s2.x * (r0.y - a.y) - s2.y * (r0.x - a.x)) / (-s2.x * s1.y + s1.x * s2.y);
		  
		if (s >= 0 && s <= 1 && t >= 0) {
		  return new Vector(r0.x + (t * s1.x), r0.y + (t * s1.y));
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
