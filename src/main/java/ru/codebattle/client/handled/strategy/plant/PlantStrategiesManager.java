package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Collection;

public class PlantStrategiesManager {

	private final Collection<PlantStrategy> plantStrategies;

	public PlantStrategiesManager(Collection<PlantStrategy> plantStrategies) {this.plantStrategies = plantStrategies;}

	public boolean isPlantBomb(TypedBoardPoint position) {
		return plantStrategies.iterator().next().doPlantBomb(position);
	}
}
