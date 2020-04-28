package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

public class DiagonalPlantStrategy implements PlantStrategy {

	@Override
	public boolean doPlantBomb(
			GameBoard gameBoard, BoardPoint currentPosition, BoardPoint nextPosition
	) {
		ExplosionInfo positionExplosionInfo = currentPosition.getExplosionInfo();
		if (positionExplosionInfo.getStatus() != ExplosionStatus.NEVER) {
			return false;
		}
		return true;
	}
}
