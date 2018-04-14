package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	public boolean[] keys = new boolean[256];
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() < 256) {
			keys[arg0.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() < 256) {
			keys[arg0.getKeyCode()] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
