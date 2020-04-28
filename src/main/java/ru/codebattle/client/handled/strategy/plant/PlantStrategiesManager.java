package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.Collection;

public class PlantStrategiesManager implements PlantStrategy {

	private final Collection<PlantStrategy> plantStrategies;

	public PlantStrategiesManager(Collection<PlantStrategy> plantStrategies) {this.plantStrategies = plantStrategies;}

	@Override
	public boolean doPlantBomb(
			GameBoard gameBoard, BoardPoint currentPosition, BoardPoint nextPosition
	) {
		return plantStrategies.iterator().next().doPlantBomb(gameBoard, currentPosition, nextPosition);
	}
}
