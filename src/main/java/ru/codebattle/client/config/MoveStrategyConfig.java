package ru.codebattle.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.codebattle.client.handled.strategy.move.NearestBombermanStrategy;
import ru.codebattle.client.handled.strategy.move.NearestWallStrategy;
import ru.codebattle.client.handled.strategy.move.Strategy;
import ru.codebattle.client.handled.strategy.move.StrategyManager;

import java.util.Collections;

@Configuration
public class MoveStrategyConfig {

	@Bean
	public StrategyManager strategyManager(Strategy strategy) {
		return new StrategyManager(Collections.singletonList(strategy));
	}

	@Bean
	@Primary
	public Strategy nearestBombermanStrategy() {
		return new NearestBombermanStrategy();
	}

	@Bean
	public Strategy nearestWallStrategy() {
		return new NearestWallStrategy();
	}
}
