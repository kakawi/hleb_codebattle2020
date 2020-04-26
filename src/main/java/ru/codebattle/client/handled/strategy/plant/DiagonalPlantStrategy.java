package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

public class DiagonalPlantStrategy implements PlantStrategy {

	@Override
	public boolean doPlantBomb(HandledGameBoard gameBoard, TypedBoardPoint position) {
		ExplosionInfo positionExplosionInfo = position.getExplosionInfo();
		if (positionExplosionInfo.getStatus() != ExplosionStatus.NEVER) {
			return false;
		}
		return true;
	}
}
