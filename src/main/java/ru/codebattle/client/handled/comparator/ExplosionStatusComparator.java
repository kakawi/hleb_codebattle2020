package ru.codebattle.client.handled.comparator;

import ru.codebattle.client.api.BoardPoint;

import java.util.Comparator;

public class ExplosionStatusComparator implements Comparator<BoardPoint> {

	@Override
	public int compare(BoardPoint o1, BoardPoint o2) {
		return o1.getMyExplosionStatus().ordinal() - o2.getMyExplosionStatus().ordinal();
	}
}
