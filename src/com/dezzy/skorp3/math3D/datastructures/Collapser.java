package com.dezzy.skorp3.math3D.datastructures;

import java.util.List;

@FunctionalInterface
public interface Collapser<T> {
	public T collapse(List<T> list);
}
