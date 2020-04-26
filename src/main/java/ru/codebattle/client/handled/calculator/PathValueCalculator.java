package ru.codebattle.client.handled.calculator;

import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.TypedBoardPoint;

public class PathValueCalculator {

	public double calculateValueForStep(TypedBoardPoint nextPoint, int tick) {
		ExplosionInfo explosionInfo = nextPoint.getExplosionInfo();
		ExplosionStatus explosionStatus = explosionInfo.getStatus();
		int points;
		switch (explosionStatus) {
			case AFTER_4_TICKS:
				points = 100;
				break;
			case AFTER_3_TICKS:
				points = 200;
				break;
			case AFTER_2_TICKS:
				points = 300;
				break;
			case NEXT_TICK:
				return Double.MAX_VALUE;
			default:
				points = 30;
		}

		return points;
	}
}
