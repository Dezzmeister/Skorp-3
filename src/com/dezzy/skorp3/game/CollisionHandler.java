package com.dezzy.skorp3.game;

import java.lang.reflect.Method;
import java.awt.Point;

import com.dezzy.skorp3.field.Entity;
import com.dezzy.skorp3.field.Line;

/**
 * Private boolean methods here check for collisions between different shapes. If you want to add a new shape and write methods for collision,
 * add your shape to the Shape enum first. Then define collision logic in a private boolean method. Finally, see hasCollided and add your
 * implementation there.
 * 
 * Please use descriptive names so that the behavior of your method is clear.
 * 
 * @see Shape
 * @see Side
 * @see CollisionHandler#hasCollided(Entity, Entity)
 * @author Dezzmeister
 *
 */
class CollisionHandler {
	
	private boolean circleHitCircle(Entity circle1, Entity circle2) {
		int circle1Radius = circle1.width/2;
		int circle2Radius = circle2.width/2;
		int maximumDistanceBetweenCircles = circle1Radius + circle2Radius;
		double actualDistanceBetweenCircles = Point.distance(circle1.point.x, circle1.point.y, circle2.point.x, circle2.point.y);
		
		return maximumDistanceBetweenCircles > actualDistanceBetweenCircles;
	}
	
	private boolean rectangleHitRectangle(Entity rect1, Entity rect2) {
		double halfOfCombinedWidths = (rect1.width/2.0) + (rect2.width/2.0);
		double halfOfCombinedHeights = (rect1.height/2.0) + (rect2.height/2.0);
		
		double distanceOnXAxis = Math.abs(rect1.point.x-rect2.point.x);
		double distanceOnYAxis = Math.abs(rect1.point.y-rect2.point.y);
		
		return (distanceOnXAxis < halfOfCombinedWidths) ||
			   (distanceOnYAxis < halfOfCombinedHeights);
	}
	
	private boolean rectangleHitCircle(Entity circle, Entity rect) {
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
	
	//TODO write this method
	private boolean lineHitRectangle(Line line, Entity rect) {
		return true;
	}
	
	private boolean pointInRectangle(Pair pair, Entity rect) {
		int x = pair.x;
		int y = pair.y;
		
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
	 * Maybe a HashMap<String,Method> could work here instead of conditionals?
	 * 
	 * @see Entity
	 * @see Shape
	 * @param ent1 
	 * @param ent2
	 * @return boolean determining if the two Entities have collided
	 */
	public boolean hasCollided(Entity ent1, Entity ent2) {
		/*
		Shape shape1 = ent1.getShape();
		Shape shape2 = ent2.getShape();
		
		if (shape1==Shape.RECTANGLE && shape2==Shape.RECTANGLE) return rectangleHitRectangle(ent1,ent2);
		if (shape1==Shape.CIRCLE && shape2==Shape.CIRCLE) return circleHitCircle(ent1,ent2);
		if (shape1==Shape.CIRCLE && shape2==Shape.RECTANGLE) return rectangleHitCircle(ent1,ent2);
		if (shape1==Shape.RECTANGLE && shape2==Shape.CIRCLE) return rectangleHitCircle(ent2,ent1);
		
		if (shape1==Shape.LINE && shape2==Shape.RECTANGLE) return lineHitRectangle((Line) ent1,ent2);
		if (shape1==Shape.RECTANGLE && shape2==Shape.LINE) return lineHitRectangle((Line) ent2,ent1);
		
		return false;
		*/
		Method m = getClass().getDeclaredMethod(format(ent1,ent2));
		return m.invoke(ent1,ent2);
	}
	private static String format(Entity e1, Entity e2){
		Entity first = (e1.getShpae().ordinal() < e2.getShpae().ordinal())?e1:e2;
		Entity second = (e1.getShpae().ordinal() > e2.getShpae().ordinal())?e1:e2;
		return first.getShape().name().toLowerCase() + "Hit" + 
		       second.getShape().name().charAt(0) + second.getShape().name().substring(1).toLowerCase();
	}
}
