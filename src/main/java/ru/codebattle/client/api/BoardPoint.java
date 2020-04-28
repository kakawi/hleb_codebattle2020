package ru.codebattle.client.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.comparator.ExplosionStatusComparator;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public class BoardPoint {
	private static final int RANGE_OF_EXPLOSION = 4;
	private static final Set<BoardElement> NOT_PASSED_FOR_EXPLOSION = Set.of(BoardElement.WALL, BoardElement.DESTROY_WALL);
	private static final Set<BoardElement> BOMBS = Set.of(BoardElement.BOMB_TIMER_1, BoardElement.BOMB_TIMER_2, BoardElement.BOMB_TIMER_3, BoardElement.BOMB_TIMER_4, BoardElement.BOMB_BOMBERMAN, BoardElement.OTHER_BOMB_BOMBERMAN);

	private final int x;
	private final int y;

	private final BoardElement boardElement;

	@Getter(AccessLevel.NONE)
	private final GameBoard gameBoard;

	public Optional<BoardPoint> shiftTop() {
		return shiftTop(1);
	}

	public Optional<BoardPoint> shiftRight() {
		return shiftRight(1);
	}

	public Optional<BoardPoint> shiftBottom() {
		return shiftBottom(1);
	}

	public Optional<BoardPoint> shiftLeft() {
		return shiftLeft(1);
	}

	public Optional<BoardPoint> shiftTop(int delta) {
		return gameBoard.getPoint(x, y - delta);
	}

	public Optional<BoardPoint> shiftRight(int delta) {
		return gameBoard.getPoint(x + delta, y);
	}

	public Optional<BoardPoint> shiftBottom(int delta) {
		return gameBoard.getPoint(x, y + delta);
	}

	public Optional<BoardPoint> shiftLeft(int delta) {
		return gameBoard.getPoint(x - delta, y);
	}

	public ExplosionInfo getExplosionInfo() {
		TreeSet<BoardPoint> foundedBombs = new TreeSet<>(new ExplosionStatusComparator());
		fillConnectedBombs(foundedBombs);
		if (foundedBombs.isEmpty()) {
			return new ExplosionInfo(null, ExplosionStatus.NEVER);
		}
		BoardPoint earliestBomb = foundedBombs.first();

		return new ExplosionInfo(earliestBomb, earliestBomb.getMyExplosionStatus());
	}

	public boolean isNotPassForExplosion() {
		return NOT_PASSED_FOR_EXPLOSION.contains(boardElement);
	}

	public boolean isBomb() {
		return BOMBS.contains(boardElement);
	}

	public double calculateDistance(BoardPoint anotherPoint) {
		return Math.sqrt(Math.pow(this.getX() - anotherPoint.getX(), 2) + Math.pow(this.getY() - anotherPoint.getY(), 2));
	}

	public ExplosionStatus getMyExplosionStatus() {
		switch (boardElement) {
			// TODO add BOMB under Bomberman (take a look at others bombs)
			case BOMB_BOMBERMAN:
			case OTHER_BOMB_BOMBERMAN:
			case BOMB_TIMER_4:
				return ExplosionStatus.AFTER_4_TICKS;
			case BOMB_TIMER_3:
				return ExplosionStatus.AFTER_3_TICKS;
			case BOMB_TIMER_2:
				return ExplosionStatus.AFTER_2_TICKS;
			case BOMB_TIMER_1:
				return ExplosionStatus.NEXT_TICK;
			default:
				return ExplosionStatus.NEVER;
		}
	}

	public boolean isNeighbour(BoardPoint anotherPoint) {
		return (this.getX() - 1 == anotherPoint.getX() && this.getY() == anotherPoint.getY())
				|| (this.getX() + 1 == anotherPoint.getX() && this.getY() == anotherPoint.getY())
				|| (this.getX() == anotherPoint.getX() && this.getY() - 1 == anotherPoint.getY())
				|| (this.getX() == anotherPoint.getX() && this.getY() + 1 == anotherPoint.getY());
	}

	public boolean canBeDestroyedFrom(BoardPoint anotherPoint) {
		if (this.getX() != anotherPoint.getX() && this.getY() != anotherPoint.getY()) {
			return false;
		}
		if (this.getX() == anotherPoint.getX()) {
			if (this.getY() < anotherPoint.getY() && this.getY() + RANGE_OF_EXPLOSION > anotherPoint.getY()) {
				ResearchDirection direction = ResearchDirection.BOTTOM;
				return isFreeForExplosion(direction, anotherPoint);
			}
			if (this.getY() > anotherPoint.getY() && this.getY() - RANGE_OF_EXPLOSION < anotherPoint.getY()) {
				ResearchDirection direction = ResearchDirection.TOP;
				return isFreeForExplosion(direction, anotherPoint);
			}
		} else {
			if (this.getX() < anotherPoint.getX() && this.getX() + RANGE_OF_EXPLOSION > anotherPoint.getX()) {
				ResearchDirection direction = ResearchDirection.RIGHT;
				return isFreeForExplosion(direction, anotherPoint);
			}
			if (this.getX() > anotherPoint.getX() && this.getX() - RANGE_OF_EXPLOSION < anotherPoint.getX()) {
				ResearchDirection direction = ResearchDirection.LEFT;
				return isFreeForExplosion(direction, anotherPoint);
			}
		}
		return false;
	}

	private boolean isFreeForExplosion(ResearchDirection direction, BoardPoint lastPoint) {
		for (int offset = 1; offset < RANGE_OF_EXPLOSION; offset++) {
			Optional<BoardPoint> isPointToResearch = getNextPointToResearch(direction, offset);
			if (isPointToResearch.isEmpty()) {
				throw new IllegalArgumentException("Something goes wrong");
			}

			BoardPoint pointToResearch = isPointToResearch.get();

			if (lastPoint.equals(pointToResearch)) {
				return true;
			}
			if (pointToResearch.isNotPassForExplosion()) {
				return false;
			}
		}
		return false;
	}


	private void fillConnectedBombs(Set<BoardPoint> foundedBombs) {
		for (ResearchDirection direction : ResearchDirection.values()) {
			for (int offset = 1; offset < RANGE_OF_EXPLOSION; offset++) {
				Optional<BoardPoint> isPointToResearch = getNextPointToResearch(direction, offset);
				if (isPointToResearch.isEmpty()) {
					break;
				}
				BoardPoint pointToResearch = isPointToResearch.get();
				if (pointToResearch.isNotPassForExplosion()) {
					break;
				}
				if (pointToResearch.isBomb() && !foundedBombs.contains(pointToResearch)) {
					foundedBombs.add(pointToResearch);
					pointToResearch.fillConnectedBombs(foundedBombs);
				}
			}
		}
	}

	private Optional<BoardPoint> getNextPointToResearch(ResearchDirection direction, int offset) {
		switch (direction) {
			case TOP:
				return shiftTop(offset);
			case RIGHT:
				return shiftRight(offset);
			case BOTTOM:
				return shiftBottom(offset);
			case LEFT:
				return shiftLeft(offset);
			default:
				throw new UnsupportedOperationException("unsupported Direction " + direction);
		}
	}

	public boolean isWillBeDestoyed() {
		for (ResearchDirection direction : ResearchDirection.values()) {
			for (int offset = 1; offset < RANGE_OF_EXPLOSION; offset++) {
				Optional<BoardPoint> isPointToResearch = getNextPointToResearch(direction, offset);
				if (isPointToResearch.isEmpty()) {
					break;
				}
				BoardPoint pointToResearch = isPointToResearch.get();
				if (pointToResearch.isNotPassForExplosion()) {
					break;
				}
				if (pointToResearch.isBomb()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isNearMeatChopper() {
		return Stream.of(this.shiftTop(), this.shiftRight(), this.shiftBottom(), this.shiftLeft())
					 .filter(Optional::isPresent)
					 .map(Optional::get)
					 .map(BoardPoint::getBoardElement)
					 .anyMatch(b -> b == BoardElement.MEAT_CHOPPER);
	}

	private enum ResearchDirection {
		TOP, RIGHT, BOTTOM, LEFT
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BoardPoint that = (BoardPoint) o;
		return x == that.x && y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return String.format("[%s,%s]", x, y);
	}
}
