package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Optional;

public class FarthestWallStrategy implements Strategy {

	@Override
	public Optional<TypedBoardPoint> getDestinationBoardPoint(HandledGameBoard gameBoard) {
		TypedBoardPoint result = null;
		TypedBoardPoint bomberman = gameBoard.getBomberman();
		double maxDistance = Double.MIN_VALUE;
		for (TypedBoardPoint destroyableWall : gameBoard.getDestroyableWalls()) {
			double currentDistance = bomberman.calculateDistance(destroyableWall);
			if (currentDistance > maxDistance) {
				maxDistance = currentDistance;
				result = destroyableWall;
			}
		}
		return Optional.ofNullable(result);
	}
}
