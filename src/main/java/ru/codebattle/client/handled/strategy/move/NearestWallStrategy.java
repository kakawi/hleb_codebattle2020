package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Optional;

public class NearestWallStrategy implements DestinationStrategy {

	@Override
	public Optional<TypedBoardPoint> getDestinationBoardPoint(HandledGameBoard gameBoard) {
		TypedBoardPoint result = null;
		TypedBoardPoint bomberman = gameBoard.getBomberman();
		double minDistance = Double.MAX_VALUE;
		for (TypedBoardPoint destroyableWall : gameBoard.getDestroyableWalls()) {
			if (destroyableWall.isWillBeDestoyed()) {
				continue;
			}
			// we will place bomb if we can
			if (destroyableWall.canBeDestroyedFrom(bomberman)) {
				continue;
			}
			double currentDistance = bomberman.calculateDistance(destroyableWall);
			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				result = destroyableWall;
			}
		}
		return Optional.ofNullable(result);
	}
}
