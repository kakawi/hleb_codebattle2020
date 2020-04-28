package ru.codebattle.client.handled.strategy.move;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class HitmanStrategy implements DestinationStrategy {

	@Override
	public Optional<BoardPoint> getDestinationBoardPoint(GameBoard gameBoard) {
		Optional<SuggestedDestinationResult> optionalBomberman = getNearestBomberman(gameBoard);
		Optional<SuggestedDestinationResult> optionalMeatChopper = getNearestMeatChopper(gameBoard);

		Optional<SuggestedDestinationResult> optionalSuggestion = Stream.of(optionalBomberman, optionalMeatChopper)
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
				result = new SuggestedDestinationResult(otherBomberman, minDistance);
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
