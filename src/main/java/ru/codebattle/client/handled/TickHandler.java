package ru.codebattle.client.handled;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import ru.codebattle.client.api.Direction;
import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.handled.calculator.PathCalculator;
import ru.codebattle.client.handled.strategy.move.DestinationStrategyManager;
import ru.codebattle.client.handled.strategy.plant.BombsController;
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager;
import ru.codebattle.client.history.History;

@Slf4j
public class TickHandler {

	private final StopWatch stopWatch = new StopWatch();
	private final DestinationStrategyManager destinationStrategyManager;
	private final PathCalculator pathCalculator;
	private final PlantStrategiesManager plantStrategiesManager;
	private final BombsController bombsController;
	private final History history;

	public TickHandler(
			DestinationStrategyManager destinationStrategyManager,
			PathCalculator pathCalculator,
			PlantStrategiesManager plantStrategiesManager,
			BombsController bombsController,
			History history
	) {
		this.destinationStrategyManager = destinationStrategyManager;
		this.pathCalculator = pathCalculator;
		this.plantStrategiesManager = plantStrategiesManager;
		this.bombsController = bombsController;
		this.history = history;
	}

	public TurnAction handle(HandledGameBoard gameBoard) {
		stopWatch.reset();
		stopWatch.start();
		bombsController.tick(gameBoard);

		TypedBoardPoint bombermanPoint = gameBoard.getBomberman();

		if (gameBoard.amIDead()) { // TODO
			TurnAction action = new TurnAction(false, Direction.STOP);
			history.add(gameBoard, null, bombermanPoint, null, bombsController.getMyBombs(), action, false);
			history.printHistory();
			return action;
		}

		TypedBoardPoint destinationPoint = destinationStrategyManager.getDestinationPoint(gameBoard);

		TypedBoardPoint nextPoint = pathCalculator.getNextPoint(gameBoard, destinationPoint);
		Direction direction = findBestDirection(bombermanPoint, nextPoint);

		boolean act = plantStrategiesManager.doPlantBomb(gameBoard, bombermanPoint, nextPoint);

		TurnAction action = new TurnAction(act, direction);

		history.add(gameBoard, destinationPoint, bombermanPoint, nextPoint, bombsController.getMyBombs(), action, act);

		if (act) {
			bombsController.plantBomb(bombermanPoint);
		}

		stopWatch.stop();
		log.info("Handle time (microseconds): " + (stopWatch.getNanoTime() / 1_000_000.0));

		return action;
	}

	private Direction findBestDirection(TypedBoardPoint bombermanPoint, TypedBoardPoint bestNextPoint) {
		if (bombermanPoint.getY() > bestNextPoint.getY()) {
			return Direction.UP;
		}
		if (bombermanPoint.getX() < bestNextPoint.getX()) {
			return Direction.RIGHT;
		}
		if ((bombermanPoint.getY() < bestNextPoint.getY())) {
			return Direction.DOWN;
		}
		if (bombermanPoint.getX() > bestNextPoint.getX()) {
			return Direction.LEFT;
		}
		return Direction.STOP;
	}
}
