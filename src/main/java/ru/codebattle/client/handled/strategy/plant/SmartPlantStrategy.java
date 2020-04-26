package ru.codebattle.client.handled.strategy.plant;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.handled.ExplosionInfo;
import ru.codebattle.client.handled.ExplosionStatus;
import ru.codebattle.client.handled.HandledGameBoard;
import ru.codebattle.client.handled.TypedBoardPoint;

@Slf4j
public class SmartPlantStrategy implements PlantStrategy {

	private static final int DESTROY_WALL_RADIUS = 3;
	private static final int OTHER_BOMBERMANS_RADIUS = 3;
	private static final int MEAT_CHOPPERS_RADIUS = 2;

	private final BombsController bombsController;

	public SmartPlantStrategy(BombsController bombsController) {this.bombsController = bombsController;}

	@Override
	public boolean doPlantBomb(
			HandledGameBoard gameBoard,
			TypedBoardPoint currentPosition,
			TypedBoardPoint nextPosition
	) {
		int x = currentPosition.getX();
		int y = currentPosition.getY();
		boolean canIDestroyAWall = gameBoard.getElementsInRectangle(x, y, DESTROY_WALL_RADIUS, BoardElement.DESTROY_WALL)
											.stream()
											.filter(p -> !p.isWillBeDestoyed())
											.anyMatch(p -> p.canBeDestroyedFrom(currentPosition));
		boolean isBombermanNear = !gameBoard.getElementsInRectangle(x, y, OTHER_BOMBERMANS_RADIUS, BoardElement.OTHER_BOMBERMAN, BoardElement.OTHER_BOMB_BOMBERMAN)
											.isEmpty();
		boolean isMeatChopperNear = !gameBoard.getElementsInRectangle(x, y, MEAT_CHOPPERS_RADIUS, BoardElement.MEAT_CHOPPER)
											  .isEmpty();
		boolean isMeatChopperInFrontOfMe = !gameBoard.getElementsInRectangle(x, y, 1, BoardElement.MEAT_CHOPPER)
													 .isEmpty();

		ExplosionInfo positionExplosionInfo = currentPosition.getExplosionInfo();
		TypedBoardPoint theFirstBombToExplode = positionExplosionInfo.getPointWithBomb();

		boolean ourBomb = bombsController.isOurBomb(theFirstBombToExplode);
		SituationAround situationAround = SituationAround.builder()
														 .canIDestroyAWall(canIDestroyAWall)
														 .explosionStatusOfPosition(positionExplosionInfo.getStatus())
														 .isBombermanNear(isBombermanNear)
														 .isMeatChopperNear(isMeatChopperNear)
														 .isMeatChopperInFrontMe(isMeatChopperInFrontOfMe)
														 .isWeStayOnTheSamePlace(currentPosition.equals(nextPosition))
														 .isOurBombWillExploded(ourBomb)
														 .build();
		return makeDecision(situationAround);
	}

	private boolean makeDecision(SituationAround situationAround) {
		// dangerous place
		if (situationAround.getExplosionStatusOfPosition() != ExplosionStatus.NEVER) {
			if (isSuicide(situationAround)) {
				if (situationAround.isOurBombWillExploded()) {
					return true; // let's try to loose less points
				}
				return false; // don't give additional points to a competitor
			}
			if (situationAround.getExplosionStatusOfPosition().ordinal() > ExplosionStatus.AFTER_3_TICKS.ordinal()) {
				// put the bomb if we will earn points
				if (situationAround.isOurBombWillExploded()) {
					return true;
				}
			}
			return false; // don't give points to competitors
		}

		if (situationAround.isMeatChopperInFrontMe() && situationAround.isWeStayOnTheSamePlace()) {
			return true;
		}
		if (situationAround.isMeatChopperNear() || situationAround.isBombermanNear()) {
			return true;
		}
		return false;
	}

	private boolean isSuicide(SituationAround situationAround) {
		return situationAround.getExplosionStatusOfPosition() == ExplosionStatus.NEXT_TICK && situationAround.isWeStayOnTheSamePlace();
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
		private final boolean isOurBombWillExploded;
	}
}
