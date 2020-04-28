package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.Optional;

public class NearestWallStrategy implements DestinationStrategy {

	@Override
	public Optional<BoardPoint> getDestinationBoardPoint(GameBoard gameBoard) {
		BoardPoint result = null;
		BoardPoint bomberman = gameBoard.getBomberman();
		double minDistance = Double.MAX_VALUE;
		for (BoardPoint destroyableWall : gameBoard.getDestroyableWalls()) {
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
