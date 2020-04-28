package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.Optional;

public class NearestMeatChopperStrategy implements DestinationStrategy {

	@Override
	public Optional<BoardPoint> getDestinationBoardPoint(GameBoard gameBoard) {
		BoardPoint result = null;
		BoardPoint bomberman = gameBoard.getBomberman();
		double minDistance = Double.MAX_VALUE;
		for (BoardPoint otherBomberman : gameBoard.getMeatChoppers()) {
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
