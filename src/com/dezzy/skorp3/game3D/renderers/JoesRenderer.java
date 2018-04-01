package com.dezzy.skorp3.game3D.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import com.dezzy.skorp3.UI.MouseData;
import com.dezzy.skorp3.game3D.Data3D;
import com.dezzy.skorp3.game3D.Renderer3D;
import com.dezzy.skorp3.game3D.VBO3D;
import com.dezzy.skorp3.game3D.VBO3DList;
import com.dezzy.skorp3.geometry3D.Triangle;
import com.dezzy.skorp3.math3D.Matrix4;

public class JoesRenderer {
	private VBO3DList vboList;
	private JPanel panel;
	private MouseData mouse;
	//private Data3D data3D;
	
	public JoesRenderer(VBO3DList _vboList, JPanel _panel, MouseData _mouse, Data3D _data3D) {
		vboList = _vboList;
		panel = _panel;
		mouse = _mouse;
		//data3D = _data3D;
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,  0, panel.getWidth(), panel.getHeight());
		
		VBO3D vbo = vboList.collapse();
		
		List<Triangle> triangles = vbo.getVBO();
		
		double heading = Math.toRadians(Renderer3D.map(mouse.x(),0,panel.getWidth(),0,360)); //Y
		double pitch = Math.toRadians(Renderer3D.map(mouse.y(),0,panel.getHeight(),0,360)); //X
		double roll = Math.toRadians(0); //Z
		
		Matrix4 headingTransform;
		Matrix4 pitchTransform;
		Matrix4 rollTransform;
		Matrix4 panOutTransform;
		
		Matrix4 transform;
		
		headingTransform = new Matrix4(new double[] {
				Math.cos(heading), 0, -Math.sin(heading), 0,
				0, 1, 0, 0,
				Math.sin(heading), 0, Math.cos(heading), 0,
				0, 0, 0, 1
			});
		pitchTransform = new Matrix4(new double[] {
				1, 0, 0, 0,
				0, Math.cos(pitch), Math.sin(pitch), 0,
				0, -Math.sin(pitch), Math.cos(pitch), 0,
				0, 0, 0, 1
         	});
		rollTransform = new Matrix4(new double[] {
				Math.cos(roll), -Math.sin(roll), 0, 0,
				Math.sin(roll), Math.cos(roll), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
           	});

		panOutTransform = new Matrix4(new double[] {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, -400, 1
           	});
			
		transform = headingTransform.multiply(pitchTransform).multiply(rollTransform).multiply(panOutTransform);
		
		//BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for (Triangle t : triangles) {
			t.v1 = transform.transform(t.v1);
			t.v2 = transform.transform(t.v2);
			t.v3 = transform.transform(t.v3);
			
			
		}
	}
}
