package ru.codebattle.client.handled.calculator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class PathPoint {
	private double price;
	private final TypedBoardPoint point;
	private PathPoint previousPoint;

	public void addPrice(double additional) {
		this.price += additional;
	}

	public int getTick() {
		if (isInitial()) {
			return 0;
		}
		return previousPoint.getTick() + 1;
	}

	private boolean isInitial() {
		return previousPoint == null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PathPoint pathPoint = (PathPoint) o;
		return Objects.equals(point, pathPoint.point);
	}

	@Override
	public int hashCode() {
		return Objects.hash(point);
	}
}
