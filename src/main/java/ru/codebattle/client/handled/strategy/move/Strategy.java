package ru.codebattle.client.handled.strategy.move;

import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Optional;

public interface Strategy {
	Optional<TypedBoardPoint> getDestinationBoardPoint(HandledGameBoard gameBoard);
}
