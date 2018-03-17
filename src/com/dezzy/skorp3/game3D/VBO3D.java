package com.dezzy.skorp3.game3D;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dezzy.skorp3.field3D.Entity3D;
import com.dezzy.skorp3.game.Physics;
import com.dezzy.skorp3.math3D.Vertex;

public class VBO3D {
	private Map<Entity3D,List<Vertex>> vertices;
	private String name;
	
	public VBO3D(String _name) {
		name = _name;
		vertices = new HashMap<Entity3D,List<Vertex>>();
	}
	
	public void add(Entity3D entity) {
		vertices.put(entity,entity.decompose());
	}
	
	public List<Entity3D> getObjects() {
		List<Entity3D> temp = new ArrayList<>();
		temp.addAll(vertices.keySet());
		return temp;
	}
	
	public List<Vertex> getVBO() {
		Collection<List<Vertex>> temp = vertices.values();
		List<Vertex> result = new ArrayList<>();
		temp.forEach((l) -> result.addAll(l));
		return result;
	}
	
	public String name() {
		return name;
	}
	
	public void render(int i, Graphics graphics) {
		Entity3D entity = objects.get(i);
		Physics.render(entity, graphics);
	}
	
	public void renderAll(Graphics graphics) {
		objects.forEach((entity) -> Physics.render(entity, graphics));
	}
}
