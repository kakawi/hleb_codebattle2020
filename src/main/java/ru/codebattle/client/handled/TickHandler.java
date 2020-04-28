package ru.codebattle.client.handled;

import ru.codebattle.client.api.TurnAction;

public interface TickHandler {

	TurnAction handle(HandledGameBoard gameBoard);
}
