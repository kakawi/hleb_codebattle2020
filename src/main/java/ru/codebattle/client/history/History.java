package ru.codebattle.client.history;

import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.handled.strategy.plant.BombsControllerImpl;

import java.util.Map;

public interface History {

	void add(
			GameBoard gameBoard,
			BoardPoint destinationPoint,
			BoardPoint currentPoint,
			BoardPoint nextPoint,
			Map<BoardPoint, BombsControllerImpl.StatusOfMyBomb> myBombs,
			TurnAction action,
			boolean isSetBomb
	);

	void printHistory();
}
