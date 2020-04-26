package ru.codebattle.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import ru.codebattle.client.handled.TickHandler;
import ru.codebattle.client.handled.calculator.PathCalculator;
import ru.codebattle.client.handled.calculator.PathValueCalculator;
import ru.codebattle.client.handled.strategy.move.FarthestWallStrategy;
import ru.codebattle.client.handled.strategy.move.NearestBombermanStrategy;
import ru.codebattle.client.handled.strategy.move.NearestWallStrategy;
import ru.codebattle.client.handled.strategy.move.StrategyManager;
import ru.codebattle.client.handled.strategy.plant.DiagonalPlantStrategy;
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager;
import ru.codebattle.client.handled.strategy.plant.SimplePlantStrategy;
import ru.codebattle.client.handled.strategy.plant.Tick2DelayPlantStrategy;

@Slf4j
public class Main {

    private static final String SERVER_ADDRESS = "http://codebattle2020s1.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/3f1pmuv22xboeomi7ts3?code=5836526866745355424&gameName=bomberman";

    public static void main(String[] args) throws URISyntaxException, IOException {
        StrategyManager strategyManager = new StrategyManager(Arrays.asList(
                new NearestBombermanStrategy(),
                new NearestWallStrategy(),
                new FarthestWallStrategy()
        ));
        PlantStrategiesManager plantStrategiesManager = new PlantStrategiesManager(Arrays.asList(
                new DiagonalPlantStrategy(),
                new Tick2DelayPlantStrategy(),
                new SimplePlantStrategy()
        ));
        PathValueCalculator pathValueCalculator = new PathValueCalculator();
        PathCalculator pathCalculator = new PathCalculator(pathValueCalculator);
        TickHandler tickHandler = new TickHandler(strategyManager, pathCalculator, plantStrategiesManager);

        CodeBattleClient client = new CodeBattleClient(SERVER_ADDRESS);
        client.run(tickHandler::handle);

        System.in.read();

        client.initiateExit();
    }
}
