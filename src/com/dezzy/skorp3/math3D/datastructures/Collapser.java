package com.dezzy.skorp3.math3D.datastructures;

import java.io.Serializable;
import java.util.List;

@FunctionalInterface
public interface Collapser<T> extends Serializable {
	public T collapse(List<T> list);
}
