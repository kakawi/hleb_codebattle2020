package ru.codebattle.client.handled.calculator

import ru.codebattle.client.handled.HandledGameBoard
import ru.codebattle.client.handled.TypedBoardPoint
import ru.codebattle.client.handled.Utils
import spock.lang.Specification

class PathCalculatorTest extends Specification {

	private PathValueCalculator pathValueCalculator = new PathValueCalculator()
	private PathCalculator pathCalculator = new PathCalculator(pathValueCalculator)

	def "move LEFT"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼      ☼
				☼    4 ☼
				☼  ☺   ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getPoint(2, 2).get()
		when:
			TypedBoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == board.getPoint(2, 5).get()
	}

	def "move RIGHT"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼  #   ☼
				☼      ☼
				☼3     ☼
				☼ ☺    ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getPoint(3, 2).get()
		when:
			TypedBoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == board.getPoint(3, 5).get()
	}

	def "move UP"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼      ☼
				☼      ☼
				☼☺1    ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getPoint(2, 2).get()
		when:
			TypedBoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == board.getPoint(1, 4).get()
	}

	def "move DOWN"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼
				☼1 2    ☼
				☼ ☺     ☼
				☼       ☼
				☼       ☼
				☼       ☼
				☼ #     ☼
				☼       ☼
				☼☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getDestroyableWalls().iterator().next()
		when:
			TypedBoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == board.getPoint(2, 3).get()
	}

	def "2 walls STOP"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼ #    ☼
				☼ ☺    ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getPoint(2, 3).get()
		when:
			TypedBoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == board.getPoint(2, 4).get()
	}

	def "2 walls not stop"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼ #    ☼
				☼ ☺    ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getPoint(2, 2).get()
			TypedBoardPoint bomberman = board.getBomberman()
		when:
			TypedBoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint != bomberman
			nextPoint == board.getPoint(1, 4).get() || nextPoint == board.getPoint(3, 4).get()
	}

	def "not move"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼   1  ☼
				☼    ☺☼☼
				☼    4 ☼
				☼    # ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getPoint(2, 2).get()
			TypedBoardPoint bomberman = board.getBomberman()
		when:
			TypedBoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == bomberman
	}

	def "not move #2"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼☼☼    ☼
				☼☼☺    ☼
				☼  1   ☼
				☼      ☼
				☼    # ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getDestroyableWalls().iterator().next()
			TypedBoardPoint bomberman = board.getBomberman()
		when:
			TypedBoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == bomberman
	}
}
