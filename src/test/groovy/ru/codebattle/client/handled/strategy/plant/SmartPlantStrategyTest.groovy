package ru.codebattle.client.handled.strategy.plant

import ru.codebattle.client.api.GameBoard
import ru.codebattle.client.api.BoardPoint
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint bomberman = board.getBomberman()
			BoardPoint nextPosition = bomberman.shiftBottom().get()
		expect:
			!smartPlantStrategy.doPlantBomb(board, bomberman, nextPosition)
	}

	def "suicide"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼
				☼       ☼
				☼       ☼
				☼  ☼☼   ☼
				☼  ☼☺♥  ☼
				☼  14   ☼
				☼       ☼
				☼       ☼
				☼☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint bomberman = board.getBomberman()
		and:
			bombsController.isOurBomb(_) >> true
		expect:
			smartPlantStrategy.doPlantBomb(board, bomberman, bomberman)
	}

	def "suicide #2"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼
				☼       ☼
				☼       ☼
				☼   12  ☼
				☼   ☼☺♥☼☼
				☼   ☼☼☼☼☼
				☼       ☼
				☼       ☼
				☼☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint bomberman = board.getBomberman()
		and:
			bombsController.isOurBomb(_) >> true
		expect:
			smartPlantStrategy.doPlantBomb(board, bomberman, bomberman)
	}

	def "suicide #3"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼
				☼       ☼
				☼       ☼
				☼       ☼
				☼       ☼
				☼ ☼☼☼   ☼
				☼   ☺21 ☼
				☼ & 4   ☼
				☼☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint bomberman = board.getBomberman()
		and:
			bombsController.isOurBomb(_) >> true
		expect:
			smartPlantStrategy.doPlantBomb(board, bomberman, bomberman.shiftLeft().get())
	}

	def "wall near"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼
				☼       ☼
				☼       ☼
				☼       ☼
				☼       ☼
				☼ ☼☼☼   ☼
				☼   ☺#  ☼
				☼       ☼
				☼☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint bomberman = board.getBomberman()
		and:
			bombsController.isOurBomb(_) >> true
		expect:
			smartPlantStrategy.doPlantBomb(board, bomberman, bomberman.shiftLeft().get())
	}
}
