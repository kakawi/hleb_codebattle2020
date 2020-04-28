package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.Optional;

public class FarthestWallStrategy implements DestinationStrategy {

	@Override
	public Optional<BoardPoint> getDestinationBoardPoint(GameBoard gameBoard) {
		BoardPoint result = null;
		BoardPoint bomberman = gameBoard.getBomberman();
		double maxDistance = Double.MIN_VALUE;
		for (BoardPoint destroyableWall : gameBoard.getDestroyableWalls()) {
			double currentDistance = bomberman.calculateDistance(destroyableWall);
			if (currentDistance > maxDistance) {
				maxDistance = currentDistance;
				result = destroyableWall;
			}
		}
		return Optional.ofNullable(result);
	}
}
