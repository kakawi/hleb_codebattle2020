package ru.codebattle.client.history;

import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;
import ru.codebattle.client.handled.strategy.plant.BombsControllerImpl;

import java.util.Map;

public interface History {

	void add(
			HandledGameBoard handledGameBoard,
			TypedBoardPoint destinationPoint,
			TypedBoardPoint currentPoint,
			TypedBoardPoint nextPoint,
			Map<TypedBoardPoint, BombsControllerImpl.StatusOfMyBomb> myBombs,
			TurnAction action,
			boolean isSetBomb
	);

	void printHistory();
}
