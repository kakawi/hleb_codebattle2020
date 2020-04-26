package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

public interface PlantStrategy {
	boolean doPlantBomb(
			HandledGameBoard gameBoard, TypedBoardPoint currentPosition, TypedBoardPoint nextPosition
	);
}
