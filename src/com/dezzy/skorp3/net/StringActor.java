package com.dezzy.skorp3.net;

/**
 * Acts on a String and returns an object of type T. Used in TCPManager.
 * 
 * @author Dezzmeister
 *
 * @param <T> return type
 * 
 * @see TCPManager
 */
@FunctionalInterface
public interface StringActor<T> {
	public T act(String s);
}
