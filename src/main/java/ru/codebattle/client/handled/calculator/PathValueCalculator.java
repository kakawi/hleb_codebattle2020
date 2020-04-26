package ru.codebattle.client.handled.calculator;

import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class PathValueCalculator {

	public double calculateValueForStep(
			TypedBoardPoint nextPoint, int tick, TypedBoardPoint fromPoint, HandledGameBoard gameBoard
	) {
		switch (tick) {
			case 0:
				return firstTick(nextPoint, fromPoint, gameBoard);
			case 1:
				return secondTick(nextPoint);
			case 2:
				return thirdTick(nextPoint);
			case 3:
				return forthTick(nextPoint);
			default:
				return othersTick(nextPoint);
		}
	}

	private double firstTick(
			TypedBoardPoint nextPoint, TypedBoardPoint fromPoint, HandledGameBoard gameBoard
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

	private double secondTick(TypedBoardPoint nextPoint) {
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

	private double thirdTick(TypedBoardPoint nextPoint) {
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

	private double forthTick(TypedBoardPoint nextPoint) {
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

	private double othersTick(TypedBoardPoint nextPoint) {
		return 30;
	}
}
