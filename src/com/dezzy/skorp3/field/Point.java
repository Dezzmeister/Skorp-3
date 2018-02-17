package com.dezzy.skorp3.field;

import com.dezzy.skorp3.game.Pair;
import com.dezzy.skorp3.game.Shape;

/**
 * Wrapper for java.awt.Point that extends Entity. Pair would be used, but Pair cannot extend Entity
 * or a StackOverflowError will be thrown. Add functionality to this class when needed.
 * 
 * @author Dezzmeister
 *
 */
public class Point extends Entity {

	public Point(int x, int y) {
		super(x,y);
		shape = Shape.POINT;
	}
	
	public Point(Pair<Integer> pair) {
		point = pair;
	}
	
	public int x() {
		return point.x;
	}
	
	public int y() {
		return point.y;
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		return java.awt.Point.distance(x1,y1,x2,y2);
	}
}
