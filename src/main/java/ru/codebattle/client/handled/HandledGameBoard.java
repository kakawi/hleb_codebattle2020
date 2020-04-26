package ru.codebattle.client.handled;

import ru.codebattle.client.api.BoardElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class HandledGameBoard {

	private final String boardString;
	private final int size;
	private final TypedBoardPoint[][] typedBoardPoints;
	private TypedBoardPoint bomberman;
	private Collection<TypedBoardPoint> otherBombermans = new HashSet<>();
	private Collection<TypedBoardPoint> meatChoppers = new HashSet<>();
	private Collection<TypedBoardPoint> destroyableWalls = new HashSet<>();

	public HandledGameBoard(String boardString) {
		this.boardString = boardString;
		this.size = (int) Math.sqrt(boardString.length());
		typedBoardPoints = new TypedBoardPoint[size][size];
		int x = 0;
		int y = 0;
		for (char symbol : boardString.toCharArray()) {
			BoardElement boardElement = BoardElement.valueOf(symbol);
			TypedBoardPoint point = new TypedBoardPoint(x, y, boardElement, this);
			typedBoardPoints[x][y] = point;
			switch (boardElement) {
				case BOMBERMAN:
				case BOMB_BOMBERMAN:
				case DEAD_BOMBERMAN:
					bomberman = point;
					break;
				case OTHER_BOMBERMAN:
				case OTHER_BOMB_BOMBERMAN:
					otherBombermans.add(point);
					break;
				case MEAT_CHOPPER:
					meatChoppers.add(point);
					break;
				case DESTROY_WALL:
					destroyableWalls.add(point);
					break;
			}
			if (x + 1 == size) {
				y++;
				x = 0;
			} else {
				x++;
			}
		}
	}

	public int size() {
		return size;
	}

	public Optional<TypedBoardPoint> getPoint(int x, int y) {
		if (x >= size || x < 0 || y >= size || y < 0) {
			return Optional.empty();
		}
		return Optional.of(typedBoardPoints[x][y]);
	}

	public Collection<TypedBoardPoint> getWalls() {
		return null;
	}

	public TypedBoardPoint getBomberman() {
		return bomberman;
	}

	public boolean amIDead() {
		return BoardElement.DEAD_BOMBERMAN == bomberman.getBoardElement();
	}

	public Collection<TypedBoardPoint> getOtherBombermans() {
		return otherBombermans;
	}

	public Collection<TypedBoardPoint> getDestroyableWalls() {
		return destroyableWalls;
	}

	public Collection<TypedBoardPoint> getMeatChoppers() {
		return meatChoppers;
	}

	public Collection<TypedBoardPoint> getBlasts() {
		return null;
	}

	public Collection<TypedBoardPoint> getBombs() {
		return null;
	}

	public Collection<TypedBoardPoint> getBarriers() {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < size(); i++) {
			stringBuilder.append(boardString, i * size(), size() * (i + 1)).append(System.lineSeparator());
		} return stringBuilder.toString();
	}

	public Collection<TypedBoardPoint> getElementsInRectangle(
			int x1, int y1, int x2, int y2, BoardElement... elementType
	) {
		Collection<TypedBoardPoint> result = new ArrayList<>();
		Set<BoardElement> elementTypes = Set.of(elementType);
		for (int i = Math.max(0, x1); i <= Math.min(size - 1, x2); i++) {
			for (int j = Math.max(0, y1); j <= Math.min(size - 1, y2); j++) {
				if (elementTypes.contains(typedBoardPoints[i][j].getBoardElement())) {
					result.add(typedBoardPoints[i][j]);
				}
			}
		}
		return result;
	}

	public Collection<TypedBoardPoint> getElementsInRectangle(
			int x, int y, int radius, BoardElement... elementType
	) {
		Collection<TypedBoardPoint> result = new ArrayList<>();
		Set<BoardElement> elementTypes = Set.of(elementType);
		int x1 = Math.max(0, x - radius);
		int x2 = Math.min(size - 1, x + radius);
		int y1 = Math.max(0, y - radius);
		int y2 = Math.min(size - 1, y + radius);
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (elementTypes.contains(typedBoardPoints[i][j].getBoardElement())) {
					result.add(typedBoardPoints[i][j]);
				}
			}
		}
		return result;
	}
}
