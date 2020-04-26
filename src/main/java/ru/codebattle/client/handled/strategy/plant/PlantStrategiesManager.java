package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Collection;

public class PlantStrategiesManager implements PlantStrategy {

	private final Collection<PlantStrategy> plantStrategies;

	public PlantStrategiesManager(Collection<PlantStrategy> plantStrategies) {this.plantStrategies = plantStrategies;}

	@Override
	public boolean doPlantBomb(
			HandledGameBoard gameBoard, TypedBoardPoint currentPosition, TypedBoardPoint nextPosition
	) {
		return plantStrategies.iterator().next().doPlantBomb(gameBoard, currentPosition, nextPosition);
	}
}
