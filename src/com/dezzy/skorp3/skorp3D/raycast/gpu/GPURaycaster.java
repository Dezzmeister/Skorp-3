package com.dezzy.skorp3.skorp3D.raycast.gpu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.KeyStroke;

import com.aparapi.Kernel;
import com.aparapi.Range;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastGraphicsContainer;
import com.dezzy.skorp3.skorp3D.raycast.render.RaycastRenderer;

public class GPURaycaster implements RaycastRenderer {
	private final int WIDTH;
	private final int HEIGHT;
	private RaycastGraphicsContainer container;
    private RaycastTask kernel;
	
	public GPURaycaster(RaycastGraphicsContainer _container, int _width, int _height) {
		container = _container;
		WIDTH = _width;
		HEIGHT = _height;
		kernel = new RaycastTask(container,WIDTH,HEIGHT);
		
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
	    
	    kernel.updateCameraVectors();
	    
	    Range range = Range.create(WIDTH);
	    kernel.execute(range);
	    kernel.dispose();
	    /*
	    final int[] ints = new int[100];
	    final int rooed = 90;
	    Kernel test = new Kernel() {
	    	
	    	@Override
	    	public void run() {
	    		double i = (double)1.09;
	    	}
	    };
	    */
	    //System.out.println(ints[0]);
	    
	    //Range r = Range.create(1);
	    //test.execute(r);
	    //test.dispose();
	    
	    BufferedImage image = kernel.getImage();
	    
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
