package ru.codebattle.client.handled.calculator;

import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathCalculator {

	private final PathValueCalculator pathValueCalculator;

	public PathCalculator(PathValueCalculator pathValueCalculator) {this.pathValueCalculator = pathValueCalculator;}

	public TypedBoardPoint getNextPoint(HandledGameBoard gameBoard, TypedBoardPoint destinationPoint) {
		TypedBoardPoint initialPosition = gameBoard.getBomberman();

		Set<PathPoint> alreadyOpenedPoints = new HashSet<>();
		Set<PathPoint> visitedPoints = new HashSet<>();
		PathPoint currentMinPoint = new PathPoint(0, initialPosition, null);
		alreadyOpenedPoints.add(currentMinPoint);
		visitedPoints.add(currentMinPoint);

		Optional<PathPoint> optionalLastPoint = getPathToDestination(destinationPoint, currentMinPoint, alreadyOpenedPoints, visitedPoints);

		// didn't find the way - Don't stand on the same place
		if (optionalLastPoint.isEmpty()) {
			return theBestMove(initialPosition);
		}

		// already stay where we want - Don't stand on the same place
		if (optionalLastPoint.get().equals(currentMinPoint)) {
//			if (doINeedToTrySurvive(initialPosition)) {
			return theBestMove(initialPosition);
//			}
//			return initialPosition;
		}

		PathPoint lastPathPoint = optionalLastPoint.get();
		while (lastPathPoint.getPreviousPoint().getPreviousPoint() != null) {
			lastPathPoint = lastPathPoint.getPreviousPoint();
		}

		// don't allow to make a suicide move
		ExplosionInfo nextMoveExplosionInfo = lastPathPoint.getPoint().getExplosionInfo();
		if (nextMoveExplosionInfo.getStatus() == ExplosionStatus.NEXT_TICK) {
			// TODO recheck: do we need `find the best` or just stay
			return theBestMove(initialPosition);
		}

		return lastPathPoint.getPoint();
	}

	private boolean doINeedToTrySurvive(TypedBoardPoint initialPosition) {
		return initialPosition.getExplosionInfo().getStatus() == ExplosionStatus.NEXT_TICK;
	}

	private TypedBoardPoint theBestMove(TypedBoardPoint centerPoint) {
		return Stream.of(centerPoint.shiftTop(), centerPoint.shiftRight(), centerPoint.shiftBottom(), centerPoint.shiftLeft(), Optional.of(centerPoint))
					 .filter(Optional::isPresent)
					 .map(Optional::get)
					 .filter(point -> point.getBoardElement().isPassable() || point.equals(centerPoint))
					 .sorted((p1, p2) -> p2.getExplosionInfo().getStatus().ordinal() - p1.getExplosionInfo()
																						 .getStatus()
																						 .ordinal())
					 .findFirst()
					 .orElse(centerPoint);
	}

	private Optional<PathPoint> getPathToDestination(
			TypedBoardPoint destinationPoint,
			PathPoint currentMinPoint,
			Set<PathPoint> alreadyOpenedPoints,
			Set<PathPoint> visitedPoints
	) {
		while (!destinationPoint.canBeDestroyedFrom(currentMinPoint.getPoint())) {
			visitedPoints.add(currentMinPoint);
			recalculatePrice(alreadyOpenedPoints, currentMinPoint, visitedPoints);

			Set<PathPoint> newOpenedPoints = openNewPoints(currentMinPoint, alreadyOpenedPoints);
			alreadyOpenedPoints.addAll(newOpenedPoints);

			Optional<PathPoint> optionalPathPoint = getMinPoint(alreadyOpenedPoints, visitedPoints);
			if (optionalPathPoint.isEmpty()) {
				return Optional.empty();
			}
			currentMinPoint = optionalPathPoint.get();

		}
		return Optional.of(currentMinPoint);
	}

	private void recalculatePrice(
			Set<PathPoint> alreadyOpenedPoints, PathPoint currentMinPoint, Set<PathPoint> visitedPoints
	) {
		for (PathPoint openedPoint : alreadyOpenedPoints) {
			if (visitedPoints.contains(openedPoint)) {
				continue;
			}
			if (!currentMinPoint.getPoint().isNeighbour(openedPoint.getPoint())) {
				continue;
			}

			double priceForStep = pathValueCalculator.calculateValueForStep(openedPoint.getPoint(), openedPoint.getTick());
			double fullPrice = currentMinPoint.getPrice() + priceForStep;
			if (openedPoint.getPrice() > fullPrice) {
				openedPoint.setPrice(fullPrice);
				openedPoint.setPreviousPoint(currentMinPoint);
			}
		}
	}

	private Optional<PathPoint> getMinPoint(
			Set<PathPoint> openedPoints, Set<PathPoint> visitedPoints
	) {
		return openedPoints.stream()
						   .filter(point -> !visitedPoints.contains(point))
						   .min(Comparator.comparingDouble(PathPoint::getPrice));
	}

	private Set<PathPoint> openNewPoints(
			PathPoint centralPathPoint, Set<PathPoint> alreadyOpenedPoints
	) {
		TypedBoardPoint centerPoint = centralPathPoint.getPoint();
		return Stream.of(centerPoint.shiftTop(), centerPoint.shiftRight(), centerPoint.shiftBottom(), centerPoint.shiftLeft())
					 .filter(Optional::isPresent)
					 .map(Optional::get)
					 .filter(point -> point.getBoardElement().isPassable())
					 .map(point -> {
						 double price = pathValueCalculator.calculateValueForStep(point, centralPathPoint.getTick());
						 return new PathPoint(centralPathPoint.getPrice() + price, point, centralPathPoint);
					 })
					 .filter(pathPoint -> !alreadyOpenedPoints.contains(pathPoint))
					 .collect(Collectors.toSet());
	}
}
