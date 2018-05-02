package com.dezzy.skorp3.skorp3D.raycast2.gpu;

import com.dezzy.skorp3.skorp3D.raycast.core.Vector2;
import com.dezzy.skorp3.skorp3D.raycast2.core.Linetype;

public class GPURenderUtils {
	
	/*
	public static int rayHitSegment(float rayStartX, float rayStartY, float rayDirectionX, float rayDirectionY, float segx1, float segy1, float segx2, float segy2) {
		float[] r0 = rayStart;
		float[] r1 = rayDirection;
		float[] a = segment.v0();
		float[] b = segment.v1();
		
		float s1x = rayDirectionX-rayStartX;
		float s1y = rayDirectionY-rayStartY;
		
		float s2x = segx2-segx1;
		float s2y = segy2-segy1;
		  
		float s,t;
		s = (-s1y * (rayStartX - segx1) + s1x * (rayStartY - segy1)) / (-s2x * s1y + s1x * s2y);
		t = (s2x * (rayStartY - segy1) - s2y * (rayStartX - segx1)) / (-s2x * s1y + s1x * s2y);
		  
		if (s >= 0 && s <= 1 && t >= 0) {
		  return new Vector2(rayStartX + (t * s1x), rayStartY + (t * s1y));
		}
		return null;
	}
	*/
}
