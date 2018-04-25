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
import com.dezzy.skorp3.skorp3D.raycast.render.Raycaster;
import com.dezzy.skorp3.skorp3D.raycast2.core.RaycastContainer2;
import com.dezzy.skorp3.skorp3D.raycast2.core.RenderUtils;
import com.dezzy.skorp3.skorp3D.raycast2.core.Sector;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;

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
	//TODO consider using a 2D z-buffer to allow for transparent sections in walls that are not limited to vertical stripes
	private double[] zbuf;
	private BufferedImage floor;
	private Sector currentSector;
	private BufferedImage img;
	private Graphics2D g2;
	
	public Raycaster2(RaycastContainer2 _container, int _width, int _height) {
		container = _container;
		WIDTH = _width;
		HEIGHT = _height;
		zbuf = new double[WIDTH];
		resetZBuffer();
		floor = Raycaster.makeFloor(WIDTH, HEIGHT);
		for (int i = 0; i < container.map.sectors.length; i++) {
			boolean inSector = RenderUtils.vectorInSector(container.camera.pos, container.map.sectors[i]);
			if (inSector) {
				currentSector = container.map.sectors[i];
				break;
			}
		}
		
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
	
	@Override
	public void render() {
		preRender();	
		handleRotation();
		handleMovement();
		renderSector(currentSector);
		postRender();
	}
	
	public void renderSector(Sector sector) {	    
	    Vector pos = container.camera.pos;
	    Vector dir = container.camera.dir;
	    Vector plane = container.camera.plane;
	    
	    //The correct wall (distance not affected by fisheye), perpendicular from center of screen
    	Wall perpWall = new Wall(pos.x,pos.y,pos.x+dir.x,pos.y+dir.y);
	    
	    for (int x = 0; x < WIDTH; x++) {
	    	//Map the x value to a range of -1 to 1
	    	double norm = (2 * (x/(double)WIDTH)) - 1;
	    	
	    	//The direction of the ray
	    	Vector rayendp = new Vector(pos.x+dir.x+(plane.x*norm),pos.y+dir.y+(plane.y*norm));
	    	
	    	//TODO Add sectors and use those instead of just testing all the walls.
	    	for (int i = 0; i < sector.walls.length; i++) {
	    		Wall l = sector.walls[i];
	    		
	    		Vector hit = RenderUtils.rayHitSegment(pos,rayendp,l);
	    		
	    		if (hit != null) {
	    			Wall ray = new Wall(pos,hit);
	    			
	    			double distance = Vector.distance(pos, hit);
	    			
	    			//TODO Change this!!! No cosine!!
	    			distance *= Math.cos(Wall.angleBetweenLines(perpWall, ray));
	    			
	    			boolean makeTransparent = false;
	    			if (distance < zbuf[x]) {
	    				int lineHeight = (int) (HEIGHT/distance);
	    				
	    				int trueDrawStart = (int)((HEIGHT >> 1) - (lineHeight >> 1)*sector.ceilScale);
	    				int drawStart = (int)clamp(trueDrawStart,0,HEIGHT-1);
	    				
	    				int trueDrawEnd = (int)((HEIGHT >> 1) + (lineHeight >> 1)*sector.floorScale);
	    				int drawEnd = (int)clamp(trueDrawEnd,0,HEIGHT-1);
	    				
	    				double wallNorm = l.getNorm(hit);
	    				
	    				int texX = (int) ((wallNorm * l.texture.width) * l.xTiles) % l.texture.width;
	    				makeTransparent = (l.texture.pixels[texX]==Texture2.ALPHA);
	    				
	    				for (int y = drawStart; y < drawEnd; y++) {
	    					double normY = (y-trueDrawStart)/(double)lineHeight;
	    					int texY = (int) ((normY*(l.texture.height)) * l.yTiles) % l.texture.height;
	    					
	    					int color = l.texture.pixels[texX + (texY * l.texture.width)];
	    			
	    					if (!makeTransparent) {
	    						img.setRGB(x, y, color);
	    					}
	    				}
	    				if (!makeTransparent) {
	    					zbuf[x] = distance;
	    				}
	    				
	    				for (int y = 0; y < drawStart; y++) {
	    					img.setRGB(x,y,0xFF0000FF);
	    				}
	    			}
	    		}
	    	}
	    }
	}
	
	/**
	 * Set the Graphics2D object and create/reset the BufferedImage.
	 */
	public void preRender() {
		g2 = (Graphics2D) container.g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, container.panel.getWidth(), container.panel.getHeight());
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);	
	}
	
	public void handleMovement() {
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
	}
	
	public void handleRotation() {
		if (container.mouse.dx() < 0) {
	    	container.camera.rotateLeft(Math.abs(container.mouse.dx()));
	    } else if (container.mouse.dx() > 0) {
	    	container.camera.rotateRight(container.mouse.dx());
	    }
	}
	
	/**
	 * After all visible sectors have been rendered, update the z-buffer and obtain the final image.
	 */
	public void postRender() {
		resetZBuffer();
	    g2.drawImage(img, 0, 0, Global.SCREENWIDTH, Global.SCREENHEIGHT, null);
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

	@Override
	public boolean shouldRedraw() {
		return container.hasUpdated();
	}

	@Override
	public RaycastContainer2 getGraphicsContainer() {
		return container;
	}

}
