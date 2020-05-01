package ru.codebattle.client.handled.calculator.realise;

import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import com.hlebon.general.GeneralPointCalculator;

public class BombermanPointCalculator extends GeneralPointCalculator<BombermanPoint> {

	public BombermanPoint generateBombermanPoint(BoardPoint boardPoint, BombermanPoint currentPoint) {
		double price = calculate(boardPoint, currentPoint.getTick());
		return new BombermanPoint(currentPoint, currentPoint.getPrice() + price, boardPoint);
	}

	@Override
	public boolean isNeedToRecalculate(BombermanPoint nextPoint, BombermanPoint currentPoint) {
		return currentPoint.getBoardPoint().isNeighbour(nextPoint.getBoardPoint());
	}

	@Override
	public BombermanPoint generateMinPoint(BombermanPoint nextPoint, BombermanPoint currentPoint) {
		int tick = currentPoint.getTick();
		BoardPoint boardPoint = nextPoint.getBoardPoint();
		double price = calculate(boardPoint, tick);
		if (nextPoint.getPrice() > currentPoint.getPrice() + price) {
			return new BombermanPoint(currentPoint, currentPoint.getPrice() + price, nextPoint.getBoardPoint());
		}
		return nextPoint;
	}

	private double calculate(BoardPoint boardPoint, int tick) {
		switch (tick) {
			case 0:
				return firstTick(boardPoint);
			case 1:
				return secondTick(boardPoint);
			case 2:
				return thirdTick(boardPoint);
			case 3:
				return forthTick(boardPoint);
			default:
				return othersTick(boardPoint);
		}
	}

	private double firstTick(
			BoardPoint nextPoint
	) {
		ExplosionInfo explosionInfo = nextPoint.getExplosionInfo();
		ExplosionStatus explosionStatus = explosionInfo.getStatus();
		int price;
		switch (explosionStatus) {
			case AFTER_4_TICKS:
				price = 100;
				break;
			case AFTER_3_TICKS:
				price = 200;
				break;
			case AFTER_2_TICKS:
				price = 300;
				break;
			case NEXT_TICK:
				return Double.MAX_VALUE;
			default:
				price = 30;
		}

		if (nextPoint.isNearMeatChopper()) {
			price += 1000;
		}

		return price;
	}

	private double secondTick(BoardPoint nextPoint) {
		ExplosionInfo explosionInfo = nextPoint.getExplosionInfo();
		ExplosionStatus explosionStatus = explosionInfo.getStatus();
		int price;
		switch (explosionStatus) {
			case AFTER_4_TICKS:
				price = 80;
				break;
			case AFTER_3_TICKS:
				price = 160;
				break;
			case AFTER_2_TICKS:
				return Double.MAX_VALUE;
			case NEXT_TICK:
			default:
				price = 30;
		}

		return price;
	}

	private double thirdTick(BoardPoint nextPoint) {
		ExplosionInfo explosionInfo = nextPoint.getExplosionInfo();
		ExplosionStatus explosionStatus = explosionInfo.getStatus();
		int price;
		switch (explosionStatus) {
			case AFTER_4_TICKS:
				price = 70;
				break;
			case AFTER_3_TICKS:
				return Double.MAX_VALUE;
			case AFTER_2_TICKS:
			case NEXT_TICK:
			default:
				price = 30;
		}

		return price;
	}

	private double forthTick(BoardPoint nextPoint) {
		ExplosionInfo explosionInfo = nextPoint.getExplosionInfo();
		ExplosionStatus explosionStatus = explosionInfo.getStatus();
		int price;
		switch (explosionStatus) {
			case AFTER_4_TICKS:
				return Double.MAX_VALUE;
			case AFTER_3_TICKS:
			case AFTER_2_TICKS:
			case NEXT_TICK:
			default:
				price = 30;
		}

		return price;
	}

	private double othersTick(BoardPoint nextPoint) {
		return 30;
	}
}
