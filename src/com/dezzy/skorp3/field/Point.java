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
	
	{
		shape = Shape.POINT;
	}
	public Point(double sharedX, double sharedY) {
		super(sharedX,sharedY);
	}
	
	public Point(Pair<Double> pair) {
		point = pair;
	}
	
	public double x() {
		return point.x;
	}
	
	public double y() {
		return point.y;
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		return java.awt.Point.distance(x1,y1,x2,y2);
	}
	
	public double distance(Point otherPoint) {
		return distance(point.x,point.y,otherPoint.x(),otherPoint.y());
	}
	
	public static double cross(Point point1, Point point2) {
		return (point1.x() * point2.y()) - (point2.x() - point1.y());
	}

	@Override
	public String encode() {
		String shapeName = shape.toString();
		return shapeName + point.encode();
	}
}
