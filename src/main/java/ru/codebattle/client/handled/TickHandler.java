package ru.codebattle.client.handled;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.TurnAction;

public interface TickHandler {

	TurnAction handle(GameBoard gameBoard);
}
