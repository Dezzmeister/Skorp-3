package com.dezzy.skorp3.game;

import java.awt.Graphics;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.dezzy.skorp3.field.Entity;
import com.dezzy.skorp3.field.Geometric;
import com.dezzy.skorp3.field.Line;
import com.dezzy.skorp3.field.Point;

/**
 * Provides methods to render shapes to graphics objects. Uses reflection to call the appropriate private method.
 * When adding methods, follow the convention:
 * -Name them renderShape with Shape being the type of shape to be rendered
 * -Accept two parameters, an Entity (or subclass) first then a Graphics object
 * 
 * @author Dezzmeister
 *
 */
@SuppressWarnings("unused")
class Renderer {
	private static Map<String,Method> methods = new HashMap<String,Method>();
	
	static {
		for (Method m : Renderer.class.getDeclaredMethods()) {
			methods.put(m.getName(), m);
		}
	}
	
	private void renderCircle(Geometric circle, Graphics g) {
		g.setColor(circle.color);
		
		int x = circle.point.x.intValue();
		int y = circle.point.y.intValue();
		int width = circle.width;
		int height = circle.height;
		g.fillOval(x, y, width, height);
	}
	
	private void renderLine(Line line, Graphics g) {
		g.setColor(line.color);
		
		int x = line.point.x.intValue();
		int y = line.point.y.intValue();
		int x2 = line.endpoint.x.intValue();
		int y2 = line.endpoint.y.intValue();
		g.drawLine(x, y, x2, y2);
	}
	
	private void renderRectangle(Geometric rect, Graphics g) {
		g.setColor(rect.color);
		
		int x = (int)rect.getCornerX();
		int y = (int)rect.getCornerY();
		int width = rect.width;
		int height = rect.height;
		g.fillRect(x, y, width, height);
	}
	
	private void renderPoint(Point point, Graphics g) {
		g.setColor(point.color);
		
		int x = (int)point.x();
		int y = (int)point.y();
		g.drawLine(x,y,x,y);
	}
	
	public void render(Entity entity, Graphics graphics) {
		try {
			Method renderMethod = methods.get(format(entity));
			renderMethod.invoke(this, entity,graphics);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String format(Entity entity) {
		return "render" + entity.getShape().name().charAt(0) + entity.getShape().name().substring(1).toLowerCase();
	}
}
