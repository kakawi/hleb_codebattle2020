package ru.codebattle.client.handled

import spock.lang.Specification

class TypedBoardPointTest extends Specification {
	def "will be destroyed #1"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼ 4    ☼
				☼      ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint point = board.getDestroyableWalls().iterator().next()
		expect:
			point.isWillBeDestoyed()
	}

	def "will be destroyed #2"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼      ☼
				☼ 3    ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint point = board.getDestroyableWalls().iterator().next()
		expect:
			point.isWillBeDestoyed()
	}

	def "will be destroyed #3"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼      ☼
				☼      ☼
				☼ 2    ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint point = board.getDestroyableWalls().iterator().next()
		expect:
			point.isWillBeDestoyed()
	}

	def "will be destroyed #4"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ #    ☼
				☼      ☼
				☼      ☼
				☼ ☻    ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint point = board.getDestroyableWalls().iterator().next()
		expect:
			point.isWillBeDestoyed()
	}

	def "will be destroyed #5"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼ # ♠  ☼
				☼      ☼
				☼      ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint point = board.getDestroyableWalls().iterator().next()
		expect:
			point.isWillBeDestoyed()
	}

	def "will NOT be destroyed #1"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼☼☼
				☼    1    ☼
				☼         ☼
				☼         ☼
				☼         ☼
				☼1   #   1☼
				☼         ☼
				☼         ☼
				☼         ☼
				☼    1    ☼
				☼☼☼☼☼☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint point = board.getDestroyableWalls().iterator().next()
		expect:
			!point.isWillBeDestoyed()
	}

	def "can be destroyed from Left side #1"() {
		given:
			String map = '''
				☼☼☼☼☼☼
				☼☺  #☼
				☼    ☼
				☼    ☼
				☼    ☼
				☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getDestroyableWalls().iterator().next()
			TypedBoardPoint bomberman = board.getBomberman()
		expect:
			wall.canBeDestroyedFrom(bomberman)
	}

	def "can be destroyed from Right side #1"() {
		given:
			String map = '''
				☼☼☼☼☼☼
				☼#  ☺☼
				☼    ☼
				☼    ☼
				☼    ☼
				☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getDestroyableWalls().iterator().next()
			TypedBoardPoint bomberman = board.getBomberman()
		expect:
			wall.canBeDestroyedFrom(bomberman)
	}

	def "can be destroyed from Top side #1"() {
		given:
			String map = '''
				☼☼☼☼☼☼
				☼☺   ☼
				☼    ☼
				☼    ☼
				☼#   ☼
				☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getDestroyableWalls().iterator().next()
			TypedBoardPoint bomberman = board.getBomberman()
		expect:
			wall.canBeDestroyedFrom(bomberman)
	}

	def "can be destroyed from Bottom side #1"() {
		given:
			String map = '''
				☼☼☼☼☼☼
				☼#   ☼
				☼    ☼
				☼    ☼
				☼☺   ☼
				☼☼☼☼☼☼
			'''
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint wall = board.getDestroyableWalls().iterator().next()
			TypedBoardPoint bomberman = board.getBomberman()
		expect:
			wall.canBeDestroyedFrom(bomberman)
	}
}
