package com.dezzy.skorp3.game3D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.field3D.Transformer;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Matrix4;
import com.dezzy.skorp3.math3D.datastructures.Stack;

public class VBO3D {
	private Map<Entity3D,List<Triangle>> triangles;
	private String name;
	private Transformer transformer = new Transformer();
	private Stack<Matrix4> stack = new Stack<Matrix4>(Matrix4::collapse);
	
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
	
	public VBO3D rotateX(double deg) {
		transformer.rotateX(stack,deg);
		return this;
	}
	
	public VBO3D rotateY(double deg) {
		transformer.rotateY(stack, deg);
		return this;
	}
	
	public VBO3D rotateZ(double deg) {
		transformer.rotateZ(stack, deg);
		return this;
	}
	
	public VBO3D translate(double x, double y, double z) {
		transformer.translate(stack, x, y, z);
		return this;
	}
	
	public VBO3D scale(double x, double y, double z) {
		transformer.scale(stack, x, y, z);
		return this;
	}
	
	public List<Entity3D> getObjects() {
		List<Entity3D> temp = new ArrayList<>();
		temp.addAll(triangles.keySet());
		return temp;
	}
	
	private void transformAll() {
		Matrix4 transform = stack.collapse();
		
	}
	
	@Deprecated
	public List<Triangle> getVBO_OLD() {
		Collection<List<Triangle>> temp = triangles.values();
		List<Triangle> result = new ArrayList<>();
		temp.forEach((l) -> result.addAll(l));
		return result;
	}
	
	public List<Triangle> getVBO() {
		List<Triangle> finalVBO = new ArrayList<Triangle>();
		for (Entry<Entity3D,List<Triangle>> entry : triangles.entrySet()) {
			finalVBO.addAll(entry.getKey().getTriangles());
		}
		
		return finalVBO;
	}
	
	public String name() {
		return name;
	}
}
