package com.dezzy.skorp3.game3D;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.dezzy.skorp3.field3D.Entity3D;

public class CollisionHandler3D {
	private static Map<String,Method> collisionMethods = new HashMap<String,Method>();
	
	public boolean hasCollided(Entity3D entity1, Entity3D entity2) {
		
		return true;
	}
}
