package ru.codebattle.client;

import ru.codebattle.client.handled.TickHandler;
import ru.codebattle.client.handled.calculator.PathCalculator;
import ru.codebattle.client.handled.calculator.PathValueCalculator;
import ru.codebattle.client.handled.strategy.move.DestinationStrategyManager;
import ru.codebattle.client.handled.strategy.move.NearestBombermanStrategy;
import ru.codebattle.client.handled.strategy.plant.BombsController;
import ru.codebattle.client.handled.strategy.plant.BombsControllerImpl;
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager;
import ru.codebattle.client.handled.strategy.plant.SmartPlantStrategy;
import ru.codebattle.client.history.History;
import ru.codebattle.client.history.HistoryImpl;

import java.net.URISyntaxException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        DestinationStrategyManager destinationStrategyManager = new DestinationStrategyManager(Arrays.asList(new NearestBombermanStrategy()));
        PathCalculator pathCalculator = new PathCalculator(new PathValueCalculator());
        BombsController bombsController = new BombsControllerImpl();
        PlantStrategiesManager plantStrategiesManager = new PlantStrategiesManager(Arrays.asList(new SmartPlantStrategy(bombsController)));
        History history = new HistoryImpl();
        TickHandler tickHandler = new TickHandler(destinationStrategyManager, pathCalculator, plantStrategiesManager, bombsController, history);

        CodeBattleClient codeBattleClient = new CodeBattleClient("http://codebattle2020s1.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/3f1pmuv22xboeomi7ts3?code=5836526866745355424");
        codeBattleClient.run(tickHandler::handle);
    }
}
