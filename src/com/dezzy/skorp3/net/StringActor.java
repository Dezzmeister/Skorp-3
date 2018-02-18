package com.dezzy.skorp3.net;

/**
 * Acts on a String and returns an object of type T. Used in TCPManager.
 * 
 * @see TCPManager
 * @author Dezzmeister
 *
 * @param <T> return type
 */
@FunctionalInterface
public interface StringActor<T> {
	public T act(String s);
}
