package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.List;
import java.util.Optional;

public class DestinationStrategyManager {

	private final List<DestinationStrategy> strategies;

	public DestinationStrategyManager(List<DestinationStrategy> strategies) {
		this.strategies = strategies;
	}

	public BoardPoint getDestinationPoint(GameBoard gameBoard) {
		if (strategies.isEmpty()) {
			return getDefaultDestinationPoint(gameBoard);
		}
		Optional<BoardPoint> optionalPoint = chooseStrategy().getDestinationBoardPoint(gameBoard);
		return optionalPoint.orElseGet(() -> getDefaultDestinationPoint(gameBoard));
	}

	private DestinationStrategy chooseStrategy() {
		return strategies.get(0);
	}

	private BoardPoint getDefaultDestinationPoint(GameBoard gameBoard) {
		int size = gameBoard.size();
		return gameBoard.getPoint(size / 2, size / 2).get();
	}
}
