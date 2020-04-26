package ru.codebattle.client.handled.strategy.plant;

import lombok.Builder;
import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Collection;

public class SmartPlantStrategy implements PlantStrategy {

	@Override
	public boolean doPlantBomb(HandledGameBoard gameBoard, TypedBoardPoint position) {
		int wallRadius = 3;
		Collection<TypedBoardPoint> destroyableWallsAround = gameBoard.getElementsInRectangle(position.getX() - wallRadius, position.getY() - wallRadius, position.getX() + wallRadius, position.getY() + wallRadius, BoardElement.DESTROY_WALL);
		ExplosionInfo positionExplosionInfo = position.getExplosionInfo();
		if (positionExplosionInfo.getStatus() != ExplosionStatus.NEVER) {
			return false;
		}
		return true;
	}

	@Builder
	private static class SituationAround {
		private boolean canIDestroyAWall;
		private ExplosionStatus explosionStatusOfCurrentPosition;
		private boolean isBombermanNear;
		private boolean isMeatChopperNear;
		private boolean isMeatChopperInFrontMe;
	}
}
