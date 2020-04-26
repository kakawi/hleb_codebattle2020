package ru.codebattle.client.handled;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExplosionInfo {

	private final TypedBoardPoint pointWithBomb;
	private final ExplosionStatus status;
}
