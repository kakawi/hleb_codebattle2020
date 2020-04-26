package ru.codebattle.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.codebattle.client.handled.TickHandler;
import ru.codebattle.client.handled.calculator.PathCalculator;
import ru.codebattle.client.handled.calculator.PathValueCalculator;
import ru.codebattle.client.handled.strategy.move.StrategyManager;
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@Configuration
@ComponentScan(basePackages = "ru.codebattle.client.config")
public class Main {

    private static final String SERVER_ADDRESS = "http://codebattle2020s1.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/3f1pmuv22xboeomi7ts3?code=5836526866745355424&gameName=bomberman";

    public static void main(String[] args) throws URISyntaxException, IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        PlantStrategiesManager plantStrategiesManager = context.getBean(PlantStrategiesManager.class);
        StrategyManager strategyManager = context.getBean(StrategyManager.class);

        PathValueCalculator pathValueCalculator = new PathValueCalculator();
        PathCalculator pathCalculator = new PathCalculator(pathValueCalculator);
        TickHandler tickHandler = new TickHandler(strategyManager, pathCalculator, plantStrategiesManager);

        CodeBattleClient client = new CodeBattleClient(SERVER_ADDRESS);
        client.run(tickHandler::handle);

        System.in.read();

        client.initiateExit();
    }
}
