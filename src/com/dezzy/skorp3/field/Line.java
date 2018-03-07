package com.dezzy.skorp3.field;

import java.util.Objects;

import com.dezzy.skorp3.game.Pair;
import com.dezzy.skorp3.game.Shape;

/**
 * Uses Geometric's endpoint coordinate pair.
 * 
 * @author Dezzmeister
 *
 */
public class Line extends Geometric {

	public Line(double x, double y, double x2, double y2) {
		super(Shape.LINE);
		placeAt(x,y,x2,y2);
	}
	
	public Line(Pair<Double> _point, Pair<Double> _endpoint) {
		point = _point;
		endpoint = _endpoint;
	}
	
	public double getXAt(double normalizedDistance) {
		return Math.abs(point.x + (normalizedDistance*(endpoint.x-point.x)));
	}
	
	public double getYAt(double normalizedDistance) {
		
		return Math.abs(point.y + (normalizedDistance*(endpoint.y-point.y)));
	}
	
	public Pair<Double> getPairAt(double normalizedDistance) {
		double xCoord = getXAt(normalizedDistance);
		double yCoord = getYAt(normalizedDistance);
		
		return new Pair<Double>(xCoord,yCoord);
	}
	
	public double slope() {
		double xDiff = endpoint.x-point.x;
		double yDiff = endpoint.y-point.y;
		return yDiff/xDiff;
	}
	
	public double yIntercept() {
		double x = point.x;
		double y = point.y;
		
		return y - (slope()*x);
	}
	
	public Geometric getBoundingBox() {
		double centerX = getXAt(0.5);
		double centerY = getYAt(0.5);
		int xDiff = (int) Math.abs(endpoint.x - point.x);
		int yDiff = (int) Math.abs(endpoint.y - point.y);
		return new Geometric(centerX,centerY,xDiff,yDiff) {};
	}
	
	/**
	 * Returned String format: "line x:0 y:0 x:100 y:100".
	 * Note that both points will be labeled "x" and "y".
	 */
	@Override
	public String encode() {
		String shapeName = shape.toString();
		return shapeName+point.encode()+endpoint.encode();
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
	
	@Override
	public int hashCode() {
		return Objects.hash(point,endpoint);
	}
}
