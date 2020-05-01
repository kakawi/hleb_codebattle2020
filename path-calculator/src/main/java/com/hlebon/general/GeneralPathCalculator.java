package com.hlebon.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public abstract class GeneralPathCalculator<T extends GeneralPoint<T>, E extends GeneralPointCalculator<T>> {

	protected final E calculator;

	protected GeneralPathCalculator(E calculator) {this.calculator = calculator;}

	public Optional<T> getLastPoint(T currentPoint, Predicate<T> needToFinish) {
		Set<T> visitedPoints = new HashSet<>();
		Set<T> allPoints = new HashSet<>();
		allPoints.add(currentPoint);

		while (!needToFinish.test(currentPoint)) {
			visitedPoints.add(currentPoint);
			recalculatePrice(allPoints, currentPoint, visitedPoints);

			afterRecalculation(allPoints, currentPoint);

			Optional<T> optionalNextPoint = getNextPoint(allPoints, visitedPoints);
			if (optionalNextPoint.isEmpty()) {
				return Optional.empty();
			}
			currentPoint = optionalNextPoint.get();

		}
		return Optional.of(currentPoint);
	}

	protected void afterRecalculation(Set<T> openedPoints, T currentPoint) {
		// extend point
	}

	private void recalculatePrice(Set<T> openedPoints, T currentPoint, Set<T> visitedPoints) {
		Collection<T> betterPoints = new ArrayList<>();
		Iterator<T> iterator = openedPoints.iterator();
		while (iterator.hasNext()) {
			T openedPoint = iterator.next();
			if (visitedPoints.contains(openedPoint)) {
				continue;
			}
			if (!calculator.isNeedToRecalculate(openedPoint, currentPoint)) {
				continue;
			}

			T betterPoint = calculator.generateMinPoint(openedPoint, currentPoint);
			if (betterPoint.equals(openedPoint)) {
				continue;
			}
			iterator.remove();
			betterPoints.add(betterPoint);
		}
		openedPoints.addAll(betterPoints);
	}

	private Optional<T> getNextPoint(Set<T> openedPoints, Set<T> visitedPoints) {
		return openedPoints.stream()
						   .filter(point -> !visitedPoints.contains(point))
						   .min(Comparator.comparingDouble(GeneralPoint::getPrice));
	}
}
