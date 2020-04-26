package ru.codebattle.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.codebattle.client.handled.strategy.plant.DiagonalPlantStrategy;
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager;
import ru.codebattle.client.handled.strategy.plant.PlantStrategy;
import ru.codebattle.client.handled.strategy.plant.SimplePlantStrategy;
import ru.codebattle.client.handled.strategy.plant.SmartPlantStrategy;
import ru.codebattle.client.handled.strategy.plant.Tick2DelayPlantStrategy;

import java.util.Collections;

@Configuration
public class PlantStrategyConfig {

	@Bean
	public PlantStrategiesManager plantStrategiesManager(PlantStrategy plantStrategy) {
		return new PlantStrategiesManager(Collections.singleton(plantStrategy));
	}

	@Bean
	@Primary
	public PlantStrategy smartPlantStrategy() {
		return new SmartPlantStrategy();
	}

	@Bean
//	@Primary
	public PlantStrategy diagonalPlantStrategy() {
		return new DiagonalPlantStrategy();
	}

	@Bean
	public PlantStrategy simplePlantStrategy() {
		return new SimplePlantStrategy();
	}

	@Bean
	public PlantStrategy tick2DelayPlantStrategy() {
		return new Tick2DelayPlantStrategy();
	}
}
