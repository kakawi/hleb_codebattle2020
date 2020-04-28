package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.api.GameBoard;
import ru.codebattle.client.api.BoardPoint;

public class SimplePlantStrategy implements PlantStrategy {

	@Override
	public boolean doPlantBomb(
			GameBoard gameBoard, BoardPoint currentPosition, BoardPoint nextPosition
	) {
		ExplosionInfo positionExplosionInfo = currentPosition.getExplosionInfo();
		if (positionExplosionInfo.getStatus() == ExplosionStatus.NEXT_TICK) { // TODO check our bombs ???
			return false;
		}
		return true;
	}

}
