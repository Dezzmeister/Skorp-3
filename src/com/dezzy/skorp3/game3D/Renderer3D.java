package com.dezzy.skorp3.game3D;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.Mouse;
import com.dezzy.skorp3.game3D.renderers.BarycentricVBORenderer;

public class Renderer3D {	
	private BarycentricVBORenderer bRenderer;
	
	public static double map(double value, double min1, double max1, double min2, double max2) {
		double range1 = max1-min1;
		double norm = value/range1;
		double range2 = max2-min2;
		double mapped = range2*norm;
		return (mapped + min2);
	}
	
	public void initializeBarycentricRenderer(VBO3DList vboList, JPanel panel, Mouse mousedata, Data3D data3D) {
		bRenderer = new BarycentricVBORenderer(vboList,panel,mousedata,data3D);
	}
	
	public void barycentricRaster(Graphics g) {
		bRenderer.render(g);
	}
	
	public static Color getShade(Color color, double shade) {
		double redLinear = Math.pow(color.getRed(), 2.4) * shade;
		double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
		double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;
		
		int red = (int) Math.pow(redLinear,  1/2.4);
		int green = (int) Math.pow(greenLinear, 1/2.4);
		int blue = (int) Math.pow(blueLinear, 1/2.4);
		
		return new Color(red, green, blue);
	}
}
