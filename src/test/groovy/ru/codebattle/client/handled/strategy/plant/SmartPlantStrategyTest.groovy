package ru.codebattle.client.handled.strategy.plant

import ru.codebattle.client.handled.HandledGameBoard
import ru.codebattle.client.handled.TypedBoardPoint
import ru.codebattle.client.handled.Utils
import spock.lang.Specification

class SmartPlantStrategyTest extends Specification {

	private BombsController bombsController
	private SmartPlantStrategy smartPlantStrategy

	void setup() {
		bombsController = Mock()
		smartPlantStrategy = new SmartPlantStrategy(bombsController)
	}

	def "don't kill yourself"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼
				☼  1☺♥  ☼
				☼  #    ☼
				☼       ☼
				☼       ☼
				☼       ☼
				☼       ☼
				☼       ☼
				☼☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint bomberman = board.getBomberman()
			TypedBoardPoint nextPosition = bomberman.shiftBottom().get()
		expect:
			!smartPlantStrategy.doPlantBomb(board, bomberman, nextPosition)
	}
}
