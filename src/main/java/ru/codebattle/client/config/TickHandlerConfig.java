package ru.codebattle.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.codebattle.client.handled.TickHandler;
import ru.codebattle.client.handled.calculator.PathCalculator;
import ru.codebattle.client.handled.calculator.PathValueCalculator;
import ru.codebattle.client.handled.strategy.move.DestinationStrategyManager;
import ru.codebattle.client.handled.strategy.plant.BombsController;
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager;
import ru.codebattle.client.history.History;
import ru.codebattle.client.history.HistoryImpl;

@Configuration
public class TickHandlerConfig {

	@Bean
	public History history() {
		return new HistoryImpl();
	}

	@Bean
	public PathCalculator pathCalculator(PathValueCalculator pathValueCalculator) {
		return new PathCalculator(pathValueCalculator);
	}

	@Bean
	public PathValueCalculator pathValueCalculator() {
		return new PathValueCalculator();
	}

	@Bean
	public TickHandler tickHandler(
			DestinationStrategyManager destinationStrategyManager,
			PathCalculator pathCalculator,
			PlantStrategiesManager plantStrategiesManager,
			BombsController bombsController,
			History history
	) {
		return new TickHandler(destinationStrategyManager, pathCalculator, plantStrategiesManager, bombsController, history);
	}
}
