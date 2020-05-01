package com.hlebon.general;

public abstract class GeneralPointCalculator<T extends GeneralPoint<T>> {

	public abstract T generateMinPoint(T nextPoint, T currentPoint);

	public boolean isNeedToRecalculate(T nextPoint, T currentPoint) {
		return true;
	}
}
