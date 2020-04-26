package ru.codebattle.client.handled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TickHandler_backup {

//	private final Collection<TypedBoardPoint> myBombs = new ArrayList<>();
//
//	private StopWatch stopWatch = new StopWatch();
//	private Random random = new Random(System.currentTimeMillis());
//	private final StrategyManager strategyManager;
//	private final ValueCalculator valueCalculator;
//	private final PathCalculator pathCalculator;
//
//	public TickHandler_backup(
//			StrategyManager strategyManager, ValueCalculator valueCalculator, PathCalculator pathCalculator
//	) {
//		this.strategyManager = strategyManager;
//		this.valueCalculator = valueCalculator;
//		this.pathCalculator = pathCalculator;
//	}

//	public TurnAction handle(HandledGameBoard gameBoard) {
//		stopWatch.reset();
//		stopWatch.start();
//
//		TypedBoardPoint bombermanPoint = gameBoard.getBomberman();
//
//
//		if (gameBoard.amIDead()) {
//			return new TurnAction(false, Direction.STOP);
//		}
//
//		TypedBoardPoint destinationPoint = strategyManager.getDestinationPoint(gameBoard);
//		log.info("Destination point " + destinationPoint);
//		log.info(
//				gameBoard.toString());
//
//		pathCalculator.getNextPoint(gameBoard, destinationPoint);
//
//		TreeSet<TypedBoardPoint> passablePoints = Stream.of(
//				bombermanPoint.shiftTop(),
//				bombermanPoint.shiftRight(),
//				bombermanPoint.shiftBottom(),
//				bombermanPoint.shiftLeft()
//		)
//			  .filter(Optional::isPresent)
//			  .map(Optional::get)
//			  .filter(point -> point.getBoardElement().isPassable())
//			  .collect(() -> new TreeSet<>(
//			  		(Comparator<TypedBoardPoint>) (o1, o2) -> valueCalculator.calculateValue(o1, destinationPoint) - valueCalculator.calculateValue(o2, destinationPoint)),
//					  TreeSet::add,
//					  TreeSet::addAll
//			  );
//		passablePoints.add(bombermanPoint);
//
//		TypedBoardPoint bestNextPoint = passablePoints.last();
//		Direction direction = findBestDirection(bombermanPoint, bestNextPoint);
//
////		boolean act = random.nextInt() % 3 == 0;
////		if (act) {
////			myBombs.add(bombermanPoint);
////		}
//		boolean act = false;
//
//		stopWatch.stop();
//		log.info("Handle time (milliseconds): " + (stopWatch.getNanoTime() / 1_000_000.0));
//		return new TurnAction(act, direction);
//	}

//	private Direction findBestDirection(TypedBoardPoint bombermanPoint, TypedBoardPoint bestNextPoint) {
//		if (bombermanPoint.getY() > bestNextPoint.getY()) {
//			return Direction.UP;
//		}
//		if (bombermanPoint.getX() < bestNextPoint.getX()) {
//			return Direction.RIGHT;
//		}
//		if ((bombermanPoint.getY() < bestNextPoint.getY())) {
//			return Direction.DOWN;
//		}
//		if (bombermanPoint.getX() > bestNextPoint.getX()) {
//			return Direction.LEFT;
//		}
//		return Direction.STOP;
//	}
}
