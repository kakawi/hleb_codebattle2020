package ru.codebattle.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.codebattle.client.handled.TickHandler;
import ru.codebattle.client.handled.TickHandlerImpl;
import ru.codebattle.client.handled.TickHandlerStopWatcher;
import ru.codebattle.client.handled.calculator.PathCalculator;
import ru.codebattle.client.handled.calculator.realise.BombermanPathCalculator;
import ru.codebattle.client.handled.calculator.realise.BombermanPointCalculator;
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
	public PathCalculator pathCalculator() {
		return new PathCalculator(bombermanPathCalculator());
	}

	@Bean
	public BombermanPathCalculator bombermanPathCalculator() {
		return new BombermanPathCalculator(bombermanPointCalculator());
	}

	@Bean
	public BombermanPointCalculator bombermanPointCalculator() {
		return new BombermanPointCalculator();
	}

	@Bean
	public TickHandler tickHandler(
			DestinationStrategyManager destinationStrategyManager,
			PathCalculator pathCalculator,
			PlantStrategiesManager plantStrategiesManager,
			BombsController bombsController,
			History history
	) {
		TickHandlerImpl tickHandler = new TickHandlerImpl(destinationStrategyManager,
				pathCalculator,
				plantStrategiesManager,
				bombsController,
				history);
		return new TickHandlerStopWatcher(tickHandler);
	}
}
