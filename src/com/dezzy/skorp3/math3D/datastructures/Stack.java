package com.dezzy.skorp3.math3D.datastructures;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collapsable Stack containing objects of type T.
 * 
 * @author Dezzmeister
 *
 * @param <T> Stack's type
 */
public class Stack<T> implements Serializable, Collapsable<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5353549904744124977L;
	private List<T> stack;
	private int maxSize = 20;
	
	private StackCollapser<T> collapser;
	
	public Stack(StackCollapser<T> _collapser, int maxSize) {
		stack = new LinkedList<T>();
		collapser = _collapser;
	}
	
	public void defineCollapseLogic(StackCollapser<T> _collapser) {
		collapser = _collapser;
	}
	
	public void push(T item) {
		stack.add(item);
	}
	
	public T pop() {
		return stack.remove(stack.size()-1);
	}
	
	public StackCollapser<T> collapser() {
		return collapser;
	}
	
	public void clear() {
		stack.clear();
	}
	
	public T collapse() {
		T t = collapser.collapse(this);
		clear();
		return t;
	}
	
	public int size() {
		return stack.size();
	}
	
	/**
	 * Returns a stack with same type, collapse logic, and max size as this one.
	 * Does not copy stack contents.
	 * 
	 * @return a new Stack
	 */
	public Stack<T> copy() {
		return new Stack<T>(collapser,maxSize);
	}
}
