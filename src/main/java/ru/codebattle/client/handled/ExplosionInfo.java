package ru.codebattle.client.handled;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.codebattle.client.api.BoardPoint;

@AllArgsConstructor
@Getter
public class ExplosionInfo {

	private final BoardPoint pointWithBomb;
	private final ExplosionStatus status;
}
