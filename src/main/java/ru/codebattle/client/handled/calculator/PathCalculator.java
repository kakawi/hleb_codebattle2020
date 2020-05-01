package ru.codebattle.client.handled.calculator;

import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.calculator.realise.BombermanPathCalculator;
import ru.codebattle.client.handled.calculator.realise.BombermanPoint;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathCalculator {

	private final BombermanPathCalculator bombermanPathCalculator;

	public PathCalculator(BombermanPathCalculator bombermanPathCalculator) {this.bombermanPathCalculator = bombermanPathCalculator;}

	public BoardPoint getNextPoint(GameBoard gameBoard, BoardPoint destinationPoint) {
		BoardPoint initialPosition = gameBoard.getBomberman();

		Predicate<BombermanPoint> needToFinish = point -> destinationPoint.canBeDestroyedFrom(point.getBoardPoint());
		BombermanPoint currentPoint = new BombermanPoint(null, 0, initialPosition);
		Optional<BombermanPoint> optionalLastPoint = bombermanPathCalculator.getLastPoint(currentPoint, needToFinish);

		// didn't find the way - Don't stand on the same place
		if (optionalLastPoint.isEmpty()) {
			return theBestMove(initialPosition);
		}

		// already stay where we want - Don't stand on the same place
		if (optionalLastPoint.get().getBoardPoint().equals(initialPosition)) {
			return theBestMove(initialPosition);
		}

		BombermanPoint lastPathPoint = optionalLastPoint.get();
		while (lastPathPoint.getPreviousPoint().getPreviousPoint() != null) {
			lastPathPoint = lastPathPoint.getPreviousPoint();
		}

		// don't allow to make a suicide move
		ExplosionInfo nextMoveExplosionInfo = lastPathPoint.getBoardPoint().getExplosionInfo();
		if (nextMoveExplosionInfo.getStatus() == ExplosionStatus.NEXT_TICK) {
			// TODO recheck: do we need `find the best` or just stay
			return theBestMove(initialPosition);
		}

		return lastPathPoint.getBoardPoint();
	}

	private BoardPoint theBestMove(BoardPoint centerPoint) {
		List<BoardPoint> variants = Stream.of(centerPoint.shiftTop(), centerPoint.shiftRight(), centerPoint.shiftBottom(), centerPoint
				.shiftLeft(), Optional.of(centerPoint))
										  .filter(Optional::isPresent)
										  .map(Optional::get)
										  .filter(point -> point.getBoardElement()
																	.isNextTickPassable() || point.equals(centerPoint))
										  .sorted((p1, p2) -> p2.getExplosionInfo()
																	.getStatus()
																	.ordinal() - p1.getExplosionInfo()
																				   .getStatus()
																				   .ordinal())
										  .limit(2)
										  .collect(Collectors.toList());
		if (variants.isEmpty()) {
			return centerPoint;
		}
		if (variants.size() == 1) {
			return variants.get(0);
		}
		BoardPoint firstVariant = variants.get(0);
		BoardPoint secondVariant = variants.get(1);
		// don't stay on your own BOMB forever
		if (firstVariant.equals(centerPoint)
				&& firstVariant.getBoardElement() == BoardElement.BOMB_BOMBERMAN
				&& secondVariant.getExplosionInfo().getStatus() != ExplosionStatus.NEXT_TICK) {
			return secondVariant;
		}
		return variants.get(0);
	}
}
