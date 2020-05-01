package ru.codebattle.client.handled.calculator.realise;

import lombok.Getter;
import lombok.Setter;
import ru.codebattle.client.api.BoardPoint;
import com.hlebon.general.GeneralPoint;

import java.util.Objects;

@Getter
@Setter
public class BombermanPoint extends GeneralPoint<BombermanPoint> {

	private final BoardPoint boardPoint;

	public BombermanPoint(BombermanPoint previousPoint, double price, BoardPoint boardPoint) {
		super(previousPoint, price);
		this.boardPoint = boardPoint;
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
		BombermanPoint that = (BombermanPoint) o;
		return boardPoint.equals(that.boardPoint);
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardPoint);
	}
}
