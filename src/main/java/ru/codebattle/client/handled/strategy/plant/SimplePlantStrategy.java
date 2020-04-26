package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

public class SimplePlantStrategy implements PlantStrategy {

	@Override
	public boolean doPlantBomb(
			HandledGameBoard gameBoard, TypedBoardPoint currentPosition, TypedBoardPoint nextPosition
	) {
		ExplosionInfo positionExplosionInfo = currentPosition.getExplosionInfo();
		if (positionExplosionInfo.getStatus() == ExplosionStatus.NEXT_TICK) { // TODO check our bombs ???
			return false;
		}
		return true;
	}

}
