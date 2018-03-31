package com.dezzy.skorp3.game;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.dezzy.skorp3.annotations.untested.Untested;
import com.dezzy.skorp3.field.Entity;
import com.dezzy.skorp3.field.Geometric;
import com.dezzy.skorp3.field.Line;
import com.dezzy.skorp3.field.Point;

/**
 * Private boolean methods here check for collisions between different shapes. If you want to add a new shape and write methods for collision,
 * add your shape to the Shape enum first. Then define collision logic in a private boolean method. FOLLOW THE CONVENTION:
 * 
 * -Your private boolean must be declared like such: "shape1HitShape2" example: "rectangleHitCircle", and
 * shape1 MUST come before Shape2 in the Shape enum. Also, the two parameters should be ordered just like the name.
 * "shape1HitShape2" should not have parameters in the order, "(Shape2 shape2, Shape1 shape1)"
 * 
 * -Method names MUST be spelled correctly and every word must be either "Hit" or a Shape enum value (camel-case).
 * 
 * 
 * The convention exists so that reflection can be used to call appropriate methods given two Entities. Instead of having to write dozens of if-statements
 * or requiring the programmer to call "lineHitRectangle" or "pointHitLine" themselves, reflection is used to accept two Entities, order them by Shape, and format the names of their
 * Shapes to produce a method name following the convention. Then, the appropriate method is called.
 * 
 * Also, please use descriptive variable names in your method so that the behavior of your method is clear.
 * 
 * @author Dezzmeister
 * @see Shape
 * @see Side
 * @see CollisionHandler#hasCollided(Entity, Entity)
 */
@SuppressWarnings("unused")
@Untested
class CollisionHandler {
	private static Map<String, Method> methods = new HashMap<String, Method>();
	
	static {
		for (Method m : CollisionHandler.class.getDeclaredMethods()) {
			methods.put(m.getName(), m);
		}
	}
	
	private boolean circleHitCircle(Geometric circle1, Geometric circle2) {
		int circle1Radius = circle1.width/2;
		int circle2Radius = circle2.width/2;
		int maximumDistanceBetweenCircles = circle1Radius + circle2Radius;
		double actualDistanceBetweenCircles = Point.distance(circle1.point.x, circle1.point.y, circle2.point.x, circle2.point.y);
		
		return maximumDistanceBetweenCircles > actualDistanceBetweenCircles;
	}
	
	private boolean rectangleHitRectangle(Geometric rect1, Geometric rect2) {
		double halfOfCombinedWidths = (rect1.width/2.0) + (rect2.width/2.0);
		double halfOfCombinedHeights = (rect1.height/2.0) + (rect2.height/2.0);
		
		double distanceOnXAxis = Math.abs(rect1.point.x-rect2.point.x);
		double distanceOnYAxis = Math.abs(rect1.point.y-rect2.point.y);
		
		return (distanceOnXAxis < halfOfCombinedWidths) ||
			   (distanceOnYAxis < halfOfCombinedHeights);
	}
	
	private boolean rectangleHitCircle(Geometric rect, Geometric circle) {
		double halfOfRectDiagonal = Math.sqrt((rect.width*rect.width)+(rect.height*rect.height))/2.0;
		double circleRadius = circle.width/2.0;
		
		double maximumDistance = halfOfRectDiagonal + circleRadius;
		
		double maximumXDifference = circleRadius + (rect.width/2.0);
		double maximumYDifference = circleRadius + (rect.height/2.0);
		
		double actualDistance = Point.distance(circle.point.x, circle.point.y, rect.point.x, rect.point.y);
		double actualXDifference = Math.abs(circle.point.x-rect.point.x);
		double actualYDifference = Math.abs(circle.point.y-rect.point.y);
		
		return (actualDistance < maximumDistance) &&
			   (actualXDifference < maximumXDifference) &&
			   (actualYDifference < maximumYDifference);
	}
	
	//TODO check logic here
	/*
	private boolean lineHitCircle(Line line, Entity circle) {
		if(pointInCircle(line.getPairAt(0),circle)||pointInCircle(line.getPairAt(1),circle))
			return true;
		double pSlope = (line.getXAt(1)-line.getXAt(0))/(line.getYAt(0)-line.getYAt(1));
		int x1 = (int) (circle.point.x-circle.width/2*Math.cos(Math.atan(pSlope)));
		int x2 = (int) (circle.point.x+circle.width/2*Math.cos(Math.atan(pSlope)));
		int y1 = (int) (circle.point.y-circle.width/2*Math.sin(Math.atan(pSlope)));
		int y2 = (int) (circle.point.y+circle.width/2*Math.sin(Math.atan(pSlope)));
		return lineHitLine(line, new Line(x1,x2,y1,y2));
	}
	*/
	/**
	 * Finds both the equations of the lines and finds the point of intersection. Then tests if the point of intersection lies on both lines.
	 * This can probably be changed to use fewer booleans.
	 * 
	 * @param line1 first Line
	 * @param line2 second Line
	 * @return
	 */
	//TODO make it more efficient
	private boolean lineHitLine(Line line1, Line line2) {
		//y = mx+b
		double m1 = line1.slope();
		double b1 = line1.yIntercept();
		
		double m2 = line2.slope();
		double b2 = line2.yIntercept();
		
		//If the two lines are parallel or coincident, check if line2 contains one of line1's endpoints.
		if (m1==m2) {
			if (pointHitLine(new Point(line1.point),line2) ||
				pointHitLine(new Point(line1.endpoint),line2)) {
				return true;
			}
			return false;
		}
		
		double bDiff = b1-b2;
		double mDiff = m2-m1;

		double sharedX = (bDiff/mDiff);

		double sharedY = (m1*sharedX)+b1;

		Point intersectionPoint = new Point((int)sharedX,(int)sharedY);
		
		return pointHitLine(intersectionPoint,line1) &&
			   pointHitLine(intersectionPoint,line2);
	}

	private boolean pointHitLine(Point point, Line line) {
		double slope = line.slope();
		int b = (int) line.yIntercept();
		
		double mx = (slope*point.x());
		int y = (int) (mx+b);
	
		return y==(int)point.y() && pointHitRectangle(point, line.getBoundingBox());
	}
	
	private boolean lineHitRectangle(Line line, Geometric rect) {
		return false;
	}
	
	private boolean pointHitCircle(Point point, Geometric circle) {
		int radius = circle.width;
		double distanceFromCircle = point.distance(new Point(circle.point));
		
		return radius >= distanceFromCircle;
	}
	
	private boolean pointHitRectangle(Point point, Geometric rect) {
		double x = point.x();
		double y = point.y();
		
		int upperXBound = (int) (rect.point.x + (rect.width/2.0));
		int lowerXBound = (int) (rect.point.x - (rect.width/2.0));
		int upperYBound = (int) (rect.point.y + (rect.height/2.0));
		int lowerYBound = (int) (rect.point.y - (rect.height/2.0));
		
		return (x < upperXBound) &&
			   (x > lowerXBound) &&
			   (y < upperYBound) &&
			   (y > lowerYBound);
	}
	
	/**
	 * Collision logic is NOT defined here. This evaluates the Shapes associated with the Entity parameters
	 * and calls the appropriate private collision method based on their values.
	 * 
	 * Maybe a HashMap(String,Method) could work here instead of conditionals?
	 * 
	 * @param ent1 first Entity
	 * @param ent2 second Entity
	 * @return boolean determining if the two Entities have collided
	 * @see com.dezzy.skorp3.field.Entity
	 * @see Shape
	 */
	public boolean hasCollided(Entity ent1, Entity ent2) {
		Entity[] ordered = Shape.orderByShape(ent1,ent2);
		System.out.println("Formatted Name: "+format(ordered[0],ordered[1]));
		
		try {			
			Method collisionMethod = methods.get(format(ordered[0],ordered[1]));

			return (boolean) collisionMethod.invoke(this, ordered[0],ordered[1]);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Takes two entities and returns a formatted String. Example:
	 * "rectangleHitCircle" if the first Entity has a RECTANGLE and the second Entity
	 * has a CIRCLE.
	 * 
	 * @param first first Entity
	 * @param second second Entity
	 * @return String formatted like "firstshapeHitSecondshape"
	 */
	private String format(Entity first, Entity second){		
		return first.getShape().name().toLowerCase() + "Hit" + 
		       second.getShape().name().charAt(0) + second.getShape().name().substring(1).toLowerCase();
	}
}
