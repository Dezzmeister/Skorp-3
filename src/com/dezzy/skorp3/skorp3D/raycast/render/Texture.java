package com.dezzy.skorp3.skorp3D.raycast.render;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.dezzy.skorp3.log.Logger;

public class Texture {
	public int[] pixels;
	private String path;
	public final int SIZE;
	
	public Texture(String _path, int _size) {
		path = _path;
		SIZE = _size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	public Texture(int[] _pixels, int _size) {
		pixels = _pixels;
		SIZE = _size;
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			int width = image.getWidth();
			int height = image.getHeight();
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch(Exception e) {
			e.printStackTrace();
			e.printStackTrace(Logger.log);
		}
	}
	
	public Texture darken() {
		int[] pix = new int[SIZE * SIZE];
		
		for (int i = 0; i < pixels.length; i++) {
			Color c = new Color(pixels[i]);
			pix[i] = c.darker().getRGB();
		}
		
		return new Texture(pix,SIZE);
	}
}
