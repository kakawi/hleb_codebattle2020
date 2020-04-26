package ru.codebattle.client.handled;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import ru.codebattle.client.api.Direction;
import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.handled.calculator.PathCalculator;
import ru.codebattle.client.handled.strategy.move.StrategyManager;
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@Slf4j
public class TickHandler {

	private final Collection<TypedBoardPoint> myBombs = new ArrayList<>(); // TODO

	private StopWatch stopWatch = new StopWatch();
	private Random random = new Random(System.currentTimeMillis());
	private final StrategyManager strategyManager;
	private final PathCalculator pathCalculator;
	private final PlantStrategiesManager plantStrategiesManager;

	public TickHandler(
			StrategyManager strategyManager,
			PathCalculator pathCalculator,
			PlantStrategiesManager plantStrategiesManager
	) {
		this.strategyManager = strategyManager;
		this.pathCalculator = pathCalculator;
		this.plantStrategiesManager = plantStrategiesManager;
	}

	public TurnAction handle(HandledGameBoard gameBoard) {
		stopWatch.reset();
		stopWatch.start();

		TypedBoardPoint bombermanPoint = gameBoard.getBomberman();

		if (gameBoard.amIDead()) { // TODO
			return new TurnAction(false, Direction.STOP);
		}

		TypedBoardPoint destinationPoint = strategyManager.getDestinationPoint(gameBoard);
//		log.info("Destination point " + destinationPoint);
//		log.info(
//				gameBoard.toString());

		TypedBoardPoint nextPoint = pathCalculator.getNextPoint(gameBoard, destinationPoint);
		Direction direction = findBestDirection(bombermanPoint, nextPoint);

//		boolean act = false;
		boolean act = plantStrategiesManager.isPlantBomb(bombermanPoint);
//		if (act) {
//			myBombs.add(bombermanPoint);
//		}

		stopWatch.stop();
		log.info("Handle time (microseconds): " + (stopWatch.getNanoTime() / 1_000_000.0));
		return new TurnAction(act, direction);
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
