package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

public interface PlantStrategy {
	boolean doPlantBomb(
			GameBoard gameBoard, BoardPoint currentPosition, BoardPoint nextPosition
	);
}
