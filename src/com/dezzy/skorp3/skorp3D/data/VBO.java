package com.dezzy.skorp3.skorp3D.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.dezzy.skorp3.math3D.datastructures.Collapsable;
import com.dezzy.skorp3.skorp3D.geometry.Entity;
import com.dezzy.skorp3.skorp3D.primitive.Triangle;

public class VBO implements Collapsable<Triangle[]> {
	private Map<Entity, Triangle[]> objects = new HashMap<Entity, Triangle[]>();
	/**
	 * Keep track of how many Triangles are in the HashMap. Used when
	 * collapsing VBO.
	 */
	private int triangleCount = 0;
	
	public int triangleCount() {
		return triangleCount;
	}
	
	public VBO add(Entity entity) {
		objects.put(entity, entity.getTriangles());
		triangleCount += entity.triangleCount;
		return this;
	}
	
	public Map<Entity,Triangle[]> getObjects() {
		return objects;
	}
	
	public void addAll(VBO vbo) {
		triangleCount += vbo.triangleCount();
		objects.putAll(vbo.getObjects());
	}

	@Override
	public Triangle[] collapse() {
		Triangle[] result = new Triangle[triangleCount];
		int startIndex = 0;
		for (Entry<Entity,Triangle[]> entry : objects.entrySet()) {
			if (entry.getKey().hasUpdated()) {
				triangleCount -= entry.getKey().triangleCount;
				entry.setValue(entry.getKey().getTriangles());
				triangleCount += entry.getKey().triangleCount;
			}
			System.arraycopy(entry.getValue(), 0, result, startIndex, entry.getValue().length);
			startIndex += entry.getValue().length;
		}
		return result;
	}
}
