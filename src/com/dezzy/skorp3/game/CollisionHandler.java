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
@SuppressWarnings("unused")
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
	
	private boolean rectangleHitCircle(Entity rect, Entity circle) {
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
	
	
	private boolean lineHitRectangle(Line line, Entity rect) {
		return false; //Everyone knows that it is physically impossible for a line to touch a rectangle.
			      //Woah, did you just say that all lines touch rectangles?
					//yes
	}
	
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
	
	private boolean lineHitLine(Line line1, Line line2){
		double slope1 = (line1.getYAt(0)-line1.getYAt(1))/(line1.getXAt(0)-line1.getXAt(1));
		double slope2 = (line2.getYAt(0)-line2.getYAt(1))/(line2.getXAt(0)-line2.getXAt(1));
		int intercept1 = (int) (line1.getYAt(0)-slope1*line1.getXAt(0));
		int intercept2 = (int) (line2.getYAt(0)-slope2*line2.getXAt(0));
		if(slope1==slope2)
			return false;
		int intersectionX = (intercept2-intercept1)/((int)(slope1-slope2));
		return pointInLine(new Pair(intersectionX,(int)(slope1*intersectionX)+intercept1),line1) &&
		       pointInLine(new Pair(intersectionX,(int)(slope1*intersectionX)+intercept1),line2);
		
	}
	
	private boolean pointInLine(Pair pair, Line line) {
		double slope = (line.getYAt(0)-line.getYAt(1))/(line.getXAt(0)-line.getXAt(1));
		int intercept = (int) (line.getYAt(0)-slope*line.getXAt(0));
		return pair.y == (int)(slope*pair.x)+intercept && line.getXAt(0)<pair.x && line.getXAt(1)>pair.x
							       && line.getYAt(0)<pair.y && line.getYAt(1)>pair.y;
	}
	
	private boolean pointInCircle(Pair pair, Entity circle) {
			  //sqrt sqrt,
			  //hit the dirt
		return Math.sqrt(Math.pow(circle.point.x-pair.x,2)+Math.pow(circle.point.y-pair.y,2))<=circle.width/2;
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
		Entity[] ordered = orderByShape(ent1,ent2);
		try {			
			Method m = getClass().getDeclaredMethod(format(ordered[0],ordered[1]),Entity.class, Entity.class);

			return (boolean) m.invoke(ordered[0],ordered[1]);
		} catch (Exception e) {
			return false;
		}
	}
	
	private String format(Entity first, Entity second){		
		return first.getShape().name().toLowerCase() + "Hit" + 
		       second.getShape().name().charAt(0) + second.getShape().name().substring(1).toLowerCase();
	}
	
	/**
	 * Orders a pair of entities by their shape values and returns an array of Entities like 
	 * [lowest,highest].
	 * 
	 * @param e1
	 * @param e2
	 * @return
	 */
	private Entity[] orderByShape(Entity e1, Entity e2) {
		Entity first = (e1.getShape().ordinal() < e2.getShape().ordinal()) ? e1 : e2;
		Entity second = (first==e2) ? e1 : e2;
		
		return new Entity[]{first,second};
	}
}
