package ru.codebattle.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.codebattle.client.handled.strategy.move.NearestBombermanStrategy;
import ru.codebattle.client.handled.strategy.move.NearestMeatChopperStrategy;
import ru.codebattle.client.handled.strategy.move.NearestWallStrategy;
import ru.codebattle.client.handled.strategy.move.DestinationStrategy;
import ru.codebattle.client.handled.strategy.move.DestinationStrategyManager;

import java.util.Collections;

@Configuration
public class DestinationStrategyConfig {

	@Bean
	public DestinationStrategyManager strategyManager(DestinationStrategy destinationStrategy) {
		return new DestinationStrategyManager(Collections.singletonList(destinationStrategy));
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
