package com.dezzy.skorp3.math3D.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collapsable Stack containing objects of type T.
 * 
 * @author Dezzmeister
 *
 * @param <T> Stack's type
 */
public class Stack<T> {
	private List<T> stack = new ArrayList<T>();
	private int maxSize = 20;
	
	private Collapser<T> collapser;
	
	public Stack(Collapser<T> _collapser) {
		collapser = _collapser;
	}
	
	public Stack<T> setMaxSize(int size) {
		maxSize = size;
		return this;
	}
	
	public void defineCollapseLogic(Collapser<T> _collapser) {
		collapser = _collapser;
	}
	
	public void push(T item) {
		stack.add(item);
	}
	
	public T pop() {
		return stack.remove(stack.size()-1);
	}
	
	public int maxSize() {
		return maxSize;
	}
	
	public T collapse() {
		return collapser.collapse(stack);
	}
	
	public int size() {
		return stack.size();
	}
	
	public void clear() {
		stack = new ArrayList<T>();
	}
	
	/**
	 * Returns a stack with same type, collapse logic, and max size as this one.
	 * Does not copy stack contents.
	 * 
	 * @return a new Stack
	 */
	public Stack<T> copy() {
		return new Stack<T>(collapser).setMaxSize(maxSize);
	}
}
