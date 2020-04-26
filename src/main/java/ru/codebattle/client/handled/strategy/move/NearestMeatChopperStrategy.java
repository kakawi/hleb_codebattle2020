package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Optional;

public class NearestMeatChopperStrategy implements DestinationStrategy {

	@Override
	public Optional<TypedBoardPoint> getDestinationBoardPoint(HandledGameBoard gameBoard) {
		TypedBoardPoint result = null;
		TypedBoardPoint bomberman = gameBoard.getBomberman();
		double minDistance = Double.MAX_VALUE;
		for (TypedBoardPoint otherBomberman : gameBoard.getMeatChoppers()) {
			if (otherBomberman.isWillBeDestoyed()) {
				continue;
			}
			if (otherBomberman.canBeDestroyedFrom(bomberman)) {
				continue;
			}
			double currentDistance = bomberman.calculateDistance(otherBomberman);
			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				result = otherBomberman;
			}
		}
		return Optional.ofNullable(result);
	}
}
