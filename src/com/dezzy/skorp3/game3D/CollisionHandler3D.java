package com.dezzy.skorp3.game3D;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.field3D.Geometric3D;

@SuppressWarnings("unused")
public class CollisionHandler3D {
	private static Map<String,Method> collisionMethods = new HashMap<String,Method>();
	
	static {
		try {
			for (Method m : CollisionHandler3D.class.getDeclaredMethods()) {
				collisionMethods.put(m.getName(), m);
			}
		} catch (Exception e) {
			System.out.println("Error occurred loading collision methods.");
		}
	}
	
	private boolean rectprismHitRectprism(Geometric3D entity1, Geometric3D entity2) {
		double halfOfCombinedWidths = (entity1.width + entity2.width) / 2;
		double halfOfCombinedHeights = (entity1.height + entity2.height) / 2;
		double halfOfCombinedLengths = (entity1.length + entity2.length) / 2;
		
		double xDistance = Math.abs(entity1.point.x - entity2.point.x);
		double yDistance = Math.abs(entity1.point.y - entity2.point.y);
		double zDistance = Math.abs(entity1.point.z - entity2.point.z);
		
		return (xDistance < halfOfCombinedWidths) ||
			   (yDistance < halfOfCombinedHeights) ||
			   (zDistance < halfOfCombinedLengths);
	}
	
	//TODO sort these out
	private boolean compositeHitComposite(Entity3D entity1, Entity3D entity2) {
		return true;
	}
	
	public boolean hasCollided(Entity3D entity1, Entity3D entity2) {
		
		return true;
	}
	
	private String format(Entity3D first, Entity3D second) {
		return first.getShape().name().toLowerCase() + "Hit" + 
			       second.getShape().name().charAt(0) + second.getShape().name().substring(1).toLowerCase();
	}
}
