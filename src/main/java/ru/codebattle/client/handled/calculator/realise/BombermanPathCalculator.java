package ru.codebattle.client.handled.calculator.realise;

import ru.codebattle.client.api.BoardPoint;
import com.hlebon.general.GeneralPathCalculator;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BombermanPathCalculator extends GeneralPathCalculator<BombermanPoint, BombermanPointCalculator> {
	public BombermanPathCalculator(BombermanPointCalculator calculator) {
		super(calculator);
	}

	@Override
	protected void afterRecalculation(Set<BombermanPoint> openedPoints, BombermanPoint currentPoint) {
		int tick = currentPoint.getTick();
		BoardPoint centerPoint = currentPoint.getBoardPoint();
		Set<BombermanPoint> newBombermanPoints = Stream.of(centerPoint.shiftTop(),
				centerPoint.shiftRight(),
				centerPoint.shiftBottom(),
				centerPoint.shiftLeft())
											.filter(Optional::isPresent)
											.map(Optional::get)
											.filter(isPassableFilter(tick))
											.map(p -> calculator.generateBombermanPoint(p, currentPoint))
											.filter(bombermanPoint -> !openedPoints.contains(bombermanPoint))
											.collect(Collectors.toSet());
		openedPoints.addAll(newBombermanPoints);
	}

	private Predicate<BoardPoint> isPassableFilter(int tick) {
		return point -> tick == 0 && point.getBoardElement()
										  .isNextTickPassable() || tick != 0 && point.getBoardElement().isPassable();
	}
}
