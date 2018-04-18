package com.dezzy.skorp3.skorp3D.raycast.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.dezzy.skorp3.skorp3D.raycast.core.Element;
import com.dezzy.skorp3.skorp3D.raycast.core.WorldMap;
import com.dezzy.skorp3.skorp3D.raycast.render.Texture;

public class TopDownMap {
	public BufferedImage map;
	private int width;
	private int height;
	
	public TopDownMap(WorldMap _world) {
		updateMap(_world);
	}
	
	private void updateMap(WorldMap _world) {
		width = _world.width();
		height = _world.height();
		
		map = new BufferedImage(width*Texture.SCALESIZE,height*Texture.SCALESIZE,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = map.createGraphics();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Element element = _world.get(x, y);
				int[] smallpix = element.frontTexture().smallpixels;
				BufferedImage img = new BufferedImage(Texture.SCALESIZE,Texture.SCALESIZE,BufferedImage.TYPE_INT_RGB);
				//TODO finish this
			}
		}
	}
}
