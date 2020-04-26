package ru.codebattle.client.handled.strategy.plant;

import lombok.extern.slf4j.Slf4j;
import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class BombsController {

	private Map<TypedBoardPoint, StatusOfMyBomb> myBombs = new HashMap<>();

	public boolean isOurBomb(TypedBoardPoint point) {
		return myBombs.containsKey(point);
	}

	public void plantBomb(TypedBoardPoint point) {
		myBombs.put(point, StatusOfMyBomb.TICK_4);
	}

	public void tick(HandledGameBoard gameBoard) {
		Iterator<Map.Entry<TypedBoardPoint, StatusOfMyBomb>> iterator = myBombs.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<TypedBoardPoint, StatusOfMyBomb> next = iterator.next();
			switch (next.getValue()) {
				case EXPLODED:
					iterator.remove();
					break;
				case TICK_1:
				case TICK_2:
				case TICK_3:
				case TICK_4:
					if (check(next, gameBoard)) {
						decreaseStatus(next);
					} else {
						iterator.remove();
					}
			}
		}
	}

	private void decreaseStatus(Map.Entry<TypedBoardPoint, StatusOfMyBomb> next) {
		switch (next.getValue()) {
			case TICK_4:
				next.setValue(StatusOfMyBomb.TICK_3);
				break;
			case TICK_3:
				next.setValue(StatusOfMyBomb.TICK_2);
				break;
			case TICK_2:
				next.setValue(StatusOfMyBomb.TICK_1);
				break;
			case TICK_1:
				next.setValue(StatusOfMyBomb.EXPLODED);
				break;
		}
	}

	private boolean check(Map.Entry<TypedBoardPoint, StatusOfMyBomb> next, HandledGameBoard gameBoard) {
		TypedBoardPoint savedPoint = next.getKey();
		Optional<TypedBoardPoint> optionalCurrentPoint = gameBoard.getPoint(savedPoint.getX(), savedPoint.getY());
		if (optionalCurrentPoint.isEmpty()) {
			log.error("The bomb is disappeared");
			return false;
		}
		TypedBoardPoint currentPoint = optionalCurrentPoint.get();
		StatusOfMyBomb savedStatus = next.getValue();
		BoardElement currentElement = currentPoint.getBoardElement();
		if (currentElement == BoardElement.BOMB_BOMBERMAN) {
			return true; // we're sitting on the bomb (can't check the timer)
		}
		switch (savedStatus) {
			case TICK_1:
				return currentElement == BoardElement.BOMB_TIMER_1;
			case TICK_2:
				return currentElement == BoardElement.BOMB_TIMER_2;
			case TICK_3:
				return currentElement == BoardElement.BOMB_TIMER_3;
			case TICK_4:
				return currentElement == BoardElement.BOMB_TIMER_4;
			default:
				log.error("Is wanted {} - is gotten {}", savedPoint, currentElement);
				return false;
		}
	}

	public Map<TypedBoardPoint, StatusOfMyBomb> getMyBombs() {
		return new HashMap<>(myBombs);
	}

	public enum StatusOfMyBomb {
		EXPLODED, TICK_1, TICK_2, TICK_3, TICK_4, TICK_5
	}
}
