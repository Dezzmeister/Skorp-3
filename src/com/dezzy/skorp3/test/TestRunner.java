package com.dezzy.skorp3.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.dezzy.skorp3.game.test.CollisionTest;

public class TestRunner {
	
	public static void testCollision() {
		Result result = JUnitCore.runClasses(CollisionTest.class);
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure);
		}
		
		System.out.println(result.wasSuccessful());
	}
}
