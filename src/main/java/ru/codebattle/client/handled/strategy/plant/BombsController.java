package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

import java.util.Map;

public interface BombsController {

	boolean isOurBomb(BoardPoint point);

	void plantBomb(BoardPoint point);

	void tick(GameBoard gameBoard);

	Map<BoardPoint, BombsControllerImpl.StatusOfMyBomb> getMyBombs();
}
