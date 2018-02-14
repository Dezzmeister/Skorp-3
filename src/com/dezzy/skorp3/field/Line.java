package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Pair;
import com.dezzy.skorp3.game.Shape;

/**
 * Uses Entity's endpoint coordinate pair.
 * 
 * @author Dezzmeister
 *
 */
public class Line extends Entity {

	public Line(int x, int y, int x2, int y2) {
		super();
		shape = Shape.LINE;
		
		//Ensure that (x,y) is left of (x2,y2)
		if (x2 < x) {
			int temp = x;
			x = x2;
			x2 = temp;
			
			temp = y;
			y = y2;
			y2 = y;
		}
		placeAt(x,y,x2,y2);
	}
	
	public int getXAt(double normalizedDistance) {
		return point.x + (int)(normalizedDistance*(endpoint.x-point.x));
	}
	
	public int getYAt(double normalizedDistance) {
		int lowerY = (point.y < endpoint.y) ? point.y : endpoint.y;
		int upperY = (lowerY==point.y) ? endpoint.y : point.y;
		
		return lowerY + (int)(normalizedDistance*(upperY-lowerY));
	}
	
	public Pair getPairAt(double normalizedDistance) {
		int xCoord = getXAt(normalizedDistance);
		int yCoord = getYAt(normalizedDistance);
		
		return new Pair(xCoord,yCoord);
	}
}
