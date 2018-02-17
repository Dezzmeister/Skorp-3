package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Pair;
import com.dezzy.skorp3.game.Shape;

/**
 * Uses Entity's endpoint coordinate pair.
 * 
 * @author Dezzmeister
 *
 */
public class Line extends Geometric {

	public Line(int x, int y, int x2, int y2) {
		super(Shape.LINE);
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
	
	public Pair<Integer> getPairAt(double normalizedDistance) {
		int xCoord = getXAt(normalizedDistance);
		int yCoord = getYAt(normalizedDistance);
		
		return new Pair<Integer>(xCoord,yCoord);
	}
	
	public double slope() {
		double xDiff = endpoint.x-point.x;
		double yDiff = endpoint.y-point.y;
		return yDiff/xDiff;
	}
	
	public double yIntercept() {
		int x = point.x;
		int y = point.y;
		
		return y - (slope()*x);
	}
	
	@Override
	public boolean equals(Object object) {
		boolean isALine = object instanceof Line;
		return isALine && 
			   ((Line)object).point.x == this.point.x &&
			   ((Line)object).point.y == this.point.y &&
			   ((Line)object).endpoint.x == this.endpoint.x &&
			   ((Line)object).endpoint.y == this.endpoint.y;
	}
}
