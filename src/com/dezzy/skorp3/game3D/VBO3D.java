package com.dezzy.skorp3.game3D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.field3D.Transformable3D;
import com.dezzy.skorp3.geometry3D.Triangle;

public class VBO3D implements Serializable, Transformable3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1126661781636952261L;
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
	
	public Map<Entity3D,List<Triangle>> get() {
		return triangles;
	}
	
	public List<Triangle> getVBO() {
		List<Triangle> result = new ArrayList<>();
		
		for (Entry<Entity3D,List<Triangle>> entry : triangles.entrySet()) {
			Entity3D e = entry.getKey();
			if (e.needsUpdate()) {
				entry.setValue(e.getTransformedTriangles());
			}
			result.addAll(entry.getValue());
		}
		
		return result;
	}
	
	public List<Triangle> getVBOFor(Entity3D entity) {
		return triangles.get(entity);
	}
	
	public String name() {
		return name;
	}
	
	public void addAll(VBO3D vbo) {
		triangles.putAll(vbo.get());
	}

	@Override
	public void rotateX(double deg) {
		
	}

	@Override
	public void rotateY(double deg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotateZ(double deg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void translate(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scale(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean needsUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void applyTransformations() {
		// TODO Auto-generated method stub
		
	}
}
