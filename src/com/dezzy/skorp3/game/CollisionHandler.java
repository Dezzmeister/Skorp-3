package com.dezzy.skorp3.game;

import java.awt.Point;

import com.dezzy.skorp3.field.Entity;

public class CollisionHandler {
	//Rectangles checked here are assumed to be axis aligned
	
	private boolean circleHitCircle(Entity circle1, Entity circle2) {
		int circle1Radius = circle1.width/2;
		int circle2Radius = circle2.width/2;
		int maximumDistanceBetweenCircles = circle1Radius + circle2Radius;
		double actualDistanceBetweenCircles = Point.distance(circle1.x, circle1.y, circle2.x, circle2.y);
		
		return maximumDistanceBetweenCircles > actualDistanceBetweenCircles;
	}
	
	private boolean rectangleHitRectangle(Entity rect1, Entity rect2) {
		double halfOfCombinedWidths = (rect1.width/2.0) + (rect2.width/2.0);
		double halfOfCombinedHeights = (rect1.height/2.0) + (rect2.height/2.0);
		
		double distanceOnXAxis = Math.abs(rect1.x-rect2.x);
		double distanceOnYAxis = Math.abs(rect1.y-rect2.y);
		
		return (distanceOnXAxis < halfOfCombinedWidths) ||
			   (distanceOnYAxis < halfOfCombinedHeights);
	}
	
	private boolean rectangleHitCircle(Entity circle, Entity rect) {
		double halfOfRectDiagonal = Math.sqrt((rect.width*rect.width)+(rect.height*rect.height))/2.0;
		double circleRadius = circle.width/2.0;
		
		double maximumDistance = halfOfRectDiagonal + circleRadius;
		
		double maximumXDifference = circleRadius + (rect.width/2.0);
		double maximumYDifference = circleRadius + (rect.height/2.0);
		
		double actualDistance = Point.distance(circle.x, circle.y, rect.x, rect.y);
		double actualXDifference = Math.abs(circle.x-rect.x);
		double actualYDifference = Math.abs(circle.y-rect.y);
		
		return (actualDistance < maximumDistance) &&
			   (actualXDifference < maximumXDifference) &&
			   (actualYDifference < maximumYDifference);
	}
	
	public boolean hasCollided(Entity ent1, Entity ent2) {
		Shape shape1 = ent1.getShape();
		Shape shape2 = ent2.getShape();
		
		if (shape1==Shape.RECTANGLE && shape2==Shape.RECTANGLE) return rectangleHitRectangle(ent1,ent2);
		if (shape1==Shape.CIRCLE && shape2==Shape.CIRCLE) return circleHitCircle(ent1,ent2);
		if (shape1==Shape.CIRCLE && shape2==Shape.RECTANGLE) return rectangleHitCircle(ent1,ent2);
		if (shape1==Shape.RECTANGLE && shape2==Shape.CIRCLE) return rectangleHitCircle(ent2,ent1);
		
		return false;
	}
}
