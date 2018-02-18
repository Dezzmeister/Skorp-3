package com.dezzy.skorp3.net;

@FunctionalInterface
public interface StringActor<T> {
	public T act(String s);
}
