package ru.codebattle.client.history;

import lombok.extern.slf4j.Slf4j;
import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.handled.strategy.plant.BombsControllerImpl;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

@Slf4j
public class HistoryImpl implements History {

	private static final int STEP_BUFFER = 5;
	private final Queue<HistoryStep> history = new ArrayDeque<>();

	@Override
	public void add(
			GameBoard gameBoard,
			BoardPoint destinationPoint,
			BoardPoint currentPoint,
			BoardPoint nextPoint,
			Map<BoardPoint, BombsControllerImpl.StatusOfMyBomb> myBombs,
			TurnAction action,
			boolean isSetBomb
	) {
		HistoryStep step = HistoryStep.builder()
									  .gameBoard(gameBoard)
									  .destinationPoint(destinationPoint)
									  .currentPoint(currentPoint)
									  .nextPoint(nextPoint)
									  .myBombs(myBombs)
									  .action(action)
									  .isSetBomb(isSetBomb)
									  .build();
		history.add(step);
		if (history.size() > STEP_BUFFER) {
			history.poll();
		}
	}

	@Override
	public void printHistory() {
		log.info("===================================================");
		history.forEach(HistoryStep::printHistory);
		history.clear();
		log.info("===================================================");
	}
}
