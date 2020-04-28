package ru.codebattle.client.handled;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.TurnAction;

@Slf4j
public class TickHandlerStopWatcher implements TickHandler {

	private final TickHandler tickHandler;
	private final StopWatch stopWatch = new StopWatch();

	public TickHandlerStopWatcher(TickHandler tickHandler) {this.tickHandler = tickHandler;}

	@Override
	public TurnAction handle(GameBoard gameBoard) {
		stopWatch.reset();
		stopWatch.start();

		TurnAction action = tickHandler.handle(gameBoard);

		stopWatch.stop();
		log.info("Handle time (milliseconds): {}", (stopWatch.getNanoTime() / 1_000_000.0));

		return action;
	}
}
