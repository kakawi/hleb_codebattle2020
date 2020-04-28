package ru.codebattle.client.handled.strategy.move;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class DestroyerStrategy implements DestinationStrategy {

	@Override
	public Optional<BoardPoint> getDestinationBoardPoint(GameBoard gameBoard) {
		Optional<SuggestedDestinationResult> optionalBomberman = getNearestBomberman(gameBoard);
		Optional<SuggestedDestinationResult> optionalMeatChopper = getNearestMeatChopper(gameBoard);
		Optional<SuggestedDestinationResult> optionalDestroyableWall = getNearestDestroyableWall(gameBoard);

		Optional<SuggestedDestinationResult> optionalSuggestion = Stream.of(optionalBomberman, optionalMeatChopper, optionalDestroyableWall)
																		.filter(Optional::isPresent)
																		.map(Optional::get)
																		.min(Comparator.comparingDouble(SuggestedDestinationResult::getDistance));
		return optionalSuggestion.map(SuggestedDestinationResult::getPoint);
	}

	private Optional<SuggestedDestinationResult> getNearestBomberman(GameBoard gameBoard) {
		SuggestedDestinationResult result = null;
		BoardPoint bomberman = gameBoard.getBomberman();
		double minDistance = Double.MAX_VALUE;
		for (BoardPoint otherBomberman : gameBoard.getOtherBombermans()) {
			if (otherBomberman.isWillBeDestoyed()) {
				continue;
			}
			if (otherBomberman.canBeDestroyedFrom(bomberman)) {
				continue;
			}
			double currentDistance = bomberman.calculateDistance(otherBomberman);
			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				result = new SuggestedDestinationResult(otherBomberman, minDistance);
			}
		}
		return Optional.ofNullable(result);
	}

	private Optional<SuggestedDestinationResult> getNearestMeatChopper(GameBoard gameBoard) {
		SuggestedDestinationResult result = null;
		BoardPoint bomberman = gameBoard.getBomberman();
		double minDistance = Double.MAX_VALUE;
		for (BoardPoint meatChopper : gameBoard.getMeatChoppers()) {
			if (meatChopper.isWillBeDestoyed()) {
				continue;
			}
			if (meatChopper.canBeDestroyedFrom(bomberman)) {
				continue;
			}
			double currentDistance = bomberman.calculateDistance(meatChopper);
			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				result = new SuggestedDestinationResult(meatChopper, minDistance);
			}
		}
		return Optional.ofNullable(result);
	}

	private Optional<SuggestedDestinationResult> getNearestDestroyableWall(GameBoard gameBoard) {
		SuggestedDestinationResult result = null;
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
				result = new SuggestedDestinationResult(destroyableWall, minDistance);
			}
		}
		return Optional.ofNullable(result);
	}

	@AllArgsConstructor
	@Getter
	private static class SuggestedDestinationResult {
		private final BoardPoint point;
		private final double distance;
	}
}
