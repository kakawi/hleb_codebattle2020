package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.Optional;

public interface DestinationStrategy {
	Optional<BoardPoint> getDestinationBoardPoint(GameBoard gameBoard);
}
