package ru.codebattle.client.handled.strategy.plant;

import lombok.Builder;
import lombok.Getter;
import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

public class SmartPlantStrategy implements PlantStrategy {

	private static final int DESTROY_WALL_RADIUS = 3;
	private static final int OTHER_BOMBERMANS_RADIUS = 3;
	private static final int MEAT_CHOPPERS_RADIUS = 3;

	@Override
	public boolean doPlantBomb(HandledGameBoard gameBoard, TypedBoardPoint position) {
		int x = position.getX();
		int y = position.getY();
		boolean canIDestroyAWall = gameBoard.getElementsInRectangle(x, y, DESTROY_WALL_RADIUS, BoardElement.DESTROY_WALL)
											.stream()
											.filter(p -> !p.isWillBeDestoyed())
											.anyMatch(p -> p.canBeDestroyedFrom(position));
		boolean isBombermanNear = !gameBoard.getElementsInRectangle(x, y, OTHER_BOMBERMANS_RADIUS, BoardElement.OTHER_BOMBERMAN, BoardElement.OTHER_BOMB_BOMBERMAN)
											.isEmpty();
		boolean isMeatChopperNear = !gameBoard.getElementsInRectangle(x, y, MEAT_CHOPPERS_RADIUS, BoardElement.MEAT_CHOPPER)
											  .isEmpty();
		boolean isMeatChopperInFrontOfMe = !gameBoard.getElementsInRectangle(x, y, 1, BoardElement.MEAT_CHOPPER)
													 .isEmpty();

		ExplosionInfo positionExplosionInfo = position.getExplosionInfo();

		SituationAround situationAround = SituationAround.builder()
														 .canIDestroyAWall(canIDestroyAWall)
														 .explosionStatusOfPosition(positionExplosionInfo.getStatus())
														 .isBombermanNear(isBombermanNear)
														 .isMeatChopperNear(isMeatChopperNear)
														 .isMeatChopperInFrontMe(isMeatChopperInFrontOfMe)
														 .isWeStayOnTheSamePlace(doWeStayOnTheSamePlace(gameBoard, position))
														 .build();
		return makeDecision(situationAround);
	}

	private boolean makeDecision(SituationAround situationAround) {
		if (situationAround.getExplosionStatusOfPosition() == ExplosionStatus.NEXT_TICK) {
			return false; // don't give additional points to competitor
		}
		if (situationAround.getExplosionStatusOfPosition().ordinal() <= ExplosionStatus.AFTER_3_TICKS.ordinal()) {
			return false;
		}
		if (situationAround.isMeatChopperInFrontMe() && situationAround.isWeStayOnTheSamePlace()) {
			return true;
		}
		if (situationAround.isMeatChopperNear() || situationAround.isBombermanNear()) {
			return true;
		}
		return false;
	}

	private boolean doWeStayOnTheSamePlace(HandledGameBoard gameBoard, TypedBoardPoint point) {
		return gameBoard.getBomberman().equals(point);
	}

	@Builder
	@Getter
	private static class SituationAround {
		private final boolean canIDestroyAWall;
		private final ExplosionStatus explosionStatusOfPosition;
		private final boolean isBombermanNear;
		private final boolean isMeatChopperNear;
		private final boolean isMeatChopperInFrontMe;
		private final boolean isWeStayOnTheSamePlace;
	}
}
