package ru.codebattle.client.handled.comparator;

import ru.codebattle.client.handled.TypedBoardPoint;

import java.util.Comparator;

public class ExplosionStatusComparator implements Comparator<TypedBoardPoint> {

	@Override
	public int compare(TypedBoardPoint o1, TypedBoardPoint o2) {
		return o1.getMyExplosionStatus().ordinal() - o2.getMyExplosionStatus().ordinal();
	}
}
