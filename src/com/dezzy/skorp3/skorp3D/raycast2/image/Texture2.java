package com.dezzy.skorp3.skorp3D.raycast2.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.dezzy.skorp3.log.Logger;

public class Texture2 {
	/**
	 * Some weird purple color
	 * (242,35,248)
	 * <p>
	 * The alpha mask for any transparency in a texture. If a texture has a pixel of this color
	 * in the top row, then that entire column will be transparent. This will be changed in the future so that
	 * transparency can be applied without being restricted to the entire stripe.
	 */
	public static final int ALPHA = 0xFFF223F8;
	
	public int[] pixels;
	public String path;
	public final int width;
	public final int height;
	
	public Texture2(String _path, int _width, int _height) {
		path = _path;
		width = _width;
		height = _height;
		pixels = new int[width * height];
		load();
	}
	
	public Texture2(int[] _pixels, int _width, int _height) {
		pixels = _pixels;
		width = _width;
		height = _height;
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch(Exception e) {
			e.printStackTrace();
			e.printStackTrace(Logger.log);
		}
	}
}
