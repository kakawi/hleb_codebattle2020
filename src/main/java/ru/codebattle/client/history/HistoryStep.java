package ru.codebattle.client.history;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.handled.strategy.plant.BombsControllerImpl;

import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
@Slf4j
public class HistoryStep {

	private final GameBoard gameBoard;
	private final BoardPoint destinationPoint;
	private final BoardPoint currentPoint;
	private final BoardPoint nextPoint;
	private final Map<BoardPoint, BombsControllerImpl.StatusOfMyBomb> myBombs;
	private final TurnAction action;
	private final boolean isSetBomb;

	public void printHistory() {
		log.info("\n" + gameBoard.toString());
		String myBombsString = myBombs.entrySet()
									  .stream()
									  .map(e -> e.getKey() + ": " + e.getValue().name())
									  .collect(Collectors.joining(", ", "[", "]"));
		log.info("My bombs: {}", myBombsString);
		log.info("Current Point: {}", currentPoint);
		log.info("Next Point: {}", nextPoint);
		log.info("Destination Point: {}", destinationPoint);
		log.info("Set bomb: {}", isSetBomb);
		log.info("Action: {}", action);
	}
}
