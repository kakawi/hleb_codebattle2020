package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Map;

public interface BombsController {

	boolean isOurBomb(TypedBoardPoint point);

	void plantBomb(TypedBoardPoint point);

	void tick(HandledGameBoard gameBoard);

	Map<TypedBoardPoint, BombsControllerImpl.StatusOfMyBomb> getMyBombs();
}
