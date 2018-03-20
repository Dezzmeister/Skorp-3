package com.dezzy.skorp3.game3D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.geometry3D.Triangle;

public class VBO3D {
	private Map<Entity3D,List<Triangle>> triangles;
	private String name;
	
	public VBO3D(String _name) {
		name = _name;
		triangles = new HashMap<Entity3D,List<Triangle>>();
	}
	
	public void add(Entity3D entity) {
		triangles.put(entity,entity.decompose());
	}
	
	public void remove(Entity3D entity) {
		triangles.remove(entity);
	}
	
	public List<Entity3D> getObjects() {
		List<Entity3D> temp = new ArrayList<>();
		temp.addAll(triangles.keySet());
		return temp;
	}
	
	public List<Triangle> getVBO() {
		Collection<List<Triangle>> temp = triangles.values();
		List<Triangle> result = new ArrayList<>();
		temp.forEach((l) -> result.addAll(l));
		return result;
	}
	
	public List<Triangle> getVBOFor(Entity3D entity) {
		return triangles.get(entity);
	}
	
	public String name() {
		return name;
	}
}
