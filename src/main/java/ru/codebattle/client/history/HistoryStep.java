package ru.codebattle.client.history;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;
import ru.codebattle.client.handled.strategy.plant.BombsControllerImpl;

import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
@Slf4j
public class HistoryStep {

	private final HandledGameBoard handledGameBoard;
	private final TypedBoardPoint destinationPoint;
	private final TypedBoardPoint currentPoint;
	private final TypedBoardPoint nextPoint;
	private final Map<TypedBoardPoint, BombsControllerImpl.StatusOfMyBomb> myBombs;
	private final TurnAction action;
	private final boolean isSetBomb;

	public void printHistory() {
		log.info("\n" + handledGameBoard.toString());
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
