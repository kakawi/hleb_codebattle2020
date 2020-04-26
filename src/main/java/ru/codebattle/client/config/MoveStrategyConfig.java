package ru.codebattle.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.codebattle.client.handled.strategy.move.NearestBombermanStrategy;
import ru.codebattle.client.handled.strategy.move.NearestMeatChopperStrategy;
import ru.codebattle.client.handled.strategy.move.NearestWallStrategy;
import ru.codebattle.client.handled.strategy.move.DestinationStrategy;
import ru.codebattle.client.handled.strategy.move.StrategyManager;

import java.util.Collections;

@Configuration
public class MoveStrategyConfig {

	@Bean
	public StrategyManager strategyManager(DestinationStrategy destinationStrategy) {
		return new StrategyManager(Collections.singletonList(destinationStrategy));
	}

	@Bean
	@Primary
	public DestinationStrategy nearestMeatChopperStrategy() {
		return new NearestMeatChopperStrategy();
	}

	@Bean
	public DestinationStrategy nearestBombermanStrategy() {
		return new NearestBombermanStrategy();
	}

	@Bean
	public DestinationStrategy nearestWallStrategy() {
		return new NearestWallStrategy();
	}
}
