package ru.codebattle.client.handled

import ru.codebattle.client.api.GameBoard
import ru.codebattle.client.api.BoardPoint
import spock.lang.Specification

class BoardPointTest extends Specification {
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint point = board.getDestroyableWalls().iterator().next()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint point = board.getDestroyableWalls().iterator().next()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint point = board.getDestroyableWalls().iterator().next()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint point = board.getDestroyableWalls().iterator().next()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint point = board.getDestroyableWalls().iterator().next()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint point = board.getDestroyableWalls().iterator().next()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getDestroyableWalls().iterator().next()
			BoardPoint bomberman = board.getBomberman()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getDestroyableWalls().iterator().next()
			BoardPoint bomberman = board.getBomberman()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getDestroyableWalls().iterator().next()
			BoardPoint bomberman = board.getBomberman()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getDestroyableWalls().iterator().next()
			BoardPoint bomberman = board.getBomberman()
		expect:
			wall.canBeDestroyedFrom(bomberman)
	}
}
