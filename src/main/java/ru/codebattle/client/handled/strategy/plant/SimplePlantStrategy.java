package ru.codebattle.client.handled.strategy.plant;

import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.TypedBoardPoint;

public class SimplePlantStrategy implements PlantStrategy {

	@Override
	public boolean doPlantBomb(TypedBoardPoint position) {
		ExplosionInfo positionExplosionInfo = position.getExplosionInfo();
		if (positionExplosionInfo.getStatus() == ExplosionStatus.NEXT_TICK) { // TODO check our bombs ???
			return false;
		}
		return true;
	}

}
