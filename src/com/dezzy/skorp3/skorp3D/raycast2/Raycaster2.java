package com.dezzy.skorp3.skorp3D.raycast2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.dezzy.skorp3.Global;
import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.annotations.urgency.Urgency;
import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast.render.Camera;
import com.dezzy.skorp3.skorp3D.raycast.render.Raycaster;
import com.dezzy.skorp3.skorp3D.raycast2.core.RaycastMap;
import com.dezzy.skorp3.skorp3D.raycast2.core.RenderUtils;
import com.dezzy.skorp3.skorp3D.raycast2.core.Sector;
import com.dezzy.skorp3.skorp3D.raycast2.core.Wall;
import com.dezzy.skorp3.skorp3D.raycast2.image.Texture2;
import com.dezzy.skorp3.skorp3D.render.Renderer;

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
public class Raycaster2 implements Renderer {
	private volatile Graphics g;
	private volatile RaycastMap map;
	private volatile Mouse mouse;
	private volatile JPanel panel;
	private volatile Camera camera;
	private volatile boolean[] keys;
	
	private final int WIDTH;
	private final int HEIGHT;
	/**
	 * A 2D z-buffer, used instead of a 1D z-buffer to support transparent sections in walls
	 */
	private float[] zbuf2;
	/**
	 * The purpose of this z-buffer is to speed up resetting by using System.arraycopy() instead of a for loop.
	 */
	private float[] emptyZBuffer;
	private BufferedImage floor;
	private Sector currentSector;
	private BufferedImage img;
	private Graphics2D g2;
	
	public Raycaster2(int _width, int _height, RaycastMap _map, Mouse _mouse, JPanel _panel, Camera _camera, boolean[] _keys) {
		WIDTH = _width;
		HEIGHT = _height;
		
		map = _map;
		mouse = _mouse;
		panel = _panel;
		camera = _camera;
		keys = _keys;
		
		emptyZBuffer = new float[WIDTH * HEIGHT];
		for (int i = 0; i < emptyZBuffer.length; i++) {
			emptyZBuffer[i] = Float.MAX_VALUE;
		}
		
		zbuf2 = new float[WIDTH * HEIGHT];
		reset2DZBuffer();

		zbuf2 = new float[WIDTH * HEIGHT];
		floor = Raycaster.makeFloor(WIDTH, HEIGHT);
		for (int i = 0; i < map.sectors.length; i++) {
			boolean inSector = RenderUtils.vectorInSector(camera.pos, map.sectors[i]);
			if (inSector) {
				currentSector = map.sectors[i];
				break;
			}
		}
		
		panel.getInputMap().put(KeyStroke.getKeyStroke("held W"), "moveForward");
		panel.getInputMap().put(KeyStroke.getKeyStroke("released W"), "stopMovingForward");
		
		panel.getInputMap().put(KeyStroke.getKeyStroke("held UP"), "moveForward");
		panel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "stopMovingForward");
	}
	
	private void reset2DZBuffer() {		
		System.arraycopy(emptyZBuffer, 0, zbuf2, 0, WIDTH * HEIGHT);
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
	    Vector2 pos = camera.pos;
	    Vector2 dir = camera.dir;
	    Vector2 plane = camera.plane;
	    
	    //The correct wall (distance not affected by fisheye), perpendicular from center of screen
    	Wall perpWall = new Wall(pos.x,pos.y,pos.x+dir.x,pos.y+dir.y);
	    
	    for (int x = 0; x < WIDTH; x++) {
	    	//Map the x value to a range of -1 to 1
	    	float norm = (2 * (x/(float)WIDTH)) - 1;
	    	
	    	//The direction of the ray
	    	Vector2 rayendp = new Vector2(pos.x+dir.x+(plane.x*norm),pos.y+dir.y+(plane.y*norm));
	    	
	    	//TODO Add sectors and use those instead of just testing all the walls.
	    	for (int i = 0; i < sector.walls.length; i++) {
	    		Wall l = sector.walls[i];
	    		
	    		Vector2 hit = RenderUtils.rayHitSegment(pos,rayendp,l);
	    		
	    		if (hit != null) {
	    			Wall ray = new Wall(pos,hit);
	    			
	    			float distance = Vector2.distance(pos, hit);
	    			
	    			//TODO Change this!!! No cosine!!
	    			distance *= Math.cos(Wall.angleBetweenLines(perpWall, ray));
	    			//if (distance < zbuf[x]) {
	    			int lineHeight = (int) (HEIGHT/distance);
	    				
	    			int trueDrawStart = (int)((HEIGHT >> 1) - (lineHeight >> 1)*sector.ceilScale);
	    			int drawStart = (int)clamp(trueDrawStart,0,HEIGHT-1);
	    				
	    			int trueDrawEnd = (int)((HEIGHT >> 1) + (lineHeight >> 1)*sector.floorScale);
	    			int drawEnd = (int)clamp(trueDrawEnd,0,HEIGHT-1);
	    				
	    			float wallNorm = l.getNorm(hit);
	    				
	    			int texX = (int) ((wallNorm * l.texture.width) * l.xTiles) % l.texture.width;
	    			//makeTransparent = (l.texture.pixels[texX]==Texture2.ALPHA);
	    				
	    			for (int y = drawStart; y < drawEnd; y++) {
	    				float normY = (y-trueDrawStart)/(float)lineHeight;
	    				int texY = (int) ((normY*(l.texture.height)) * l.yTiles) % l.texture.height;
	    					
	    				int color = l.texture.pixels[texX + (texY * l.texture.width)];
	    				
	    				if (color != Texture2.ALPHA && distance < zbuf2[x + y * WIDTH]) {
	    					img.setRGB(x, y, color);
	    						
	    					zbuf2[x + y * WIDTH] = distance;
	    				}
	    			}
	    				
	    			for (int y = 0; y < drawStart; y++) {
	    				if (distance < zbuf2[x + y * WIDTH]) {
	    					img.setRGB(x,y,0xFF0000FF);
	    						
	    					zbuf2[x + y * WIDTH] = distance;
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
		g2 = (Graphics2D) g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, panel.getWidth(), panel.getHeight());
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);	
	}
	
	public void handleMovement() {
	    int sprintfactor = (keys[KeyEvent.VK_SHIFT]) ? 2 : 1;
	    
	    if (keys['W'] || keys[KeyEvent.VK_UP]) {
	    	camera.moveForward(sprintfactor);
	    }
	    
	    if (keys['S'] || keys[KeyEvent.VK_DOWN]) {
	    	camera.moveBackward(sprintfactor);
	    }
	    
	    if (keys['A'] || keys[KeyEvent.VK_LEFT]) {
	    	camera.moveLeft(sprintfactor);
	    }
	    
	    if (keys['D'] || keys[KeyEvent.VK_RIGHT]) {
	    	camera.moveRight(sprintfactor);
	    }
	}
	
	public void handleRotation() {
		if (mouse.dx() < 0) {
	    	camera.rotateLeft(Math.abs(mouse.dx()));
	    } else if (mouse.dx() > 0) {
	    	camera.rotateRight(mouse.dx());
	    }
	}
	
	/**
	 * After all visible sectors have been rendered, update the z-buffer and obtain the final image.
	 */
	public void postRender() {
		reset2DZBuffer();
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
		return mouse.hasUpdated();
	}

	@Override
	public void updateGraphicsObject(Graphics _g) {
		g = _g;		
	}

}
