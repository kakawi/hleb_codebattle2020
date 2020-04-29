package ru.codebattle.client.handled.calculator

import ru.codebattle.client.api.GameBoard
import ru.codebattle.client.api.BoardPoint
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getPoint(2, 2).get()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getPoint(3, 2).get()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getPoint(2, 2).get()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getDestroyableWalls().iterator().next()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == board.getPoint(2, 3).get()
	}

	def "2 walls - Don't stand on the same place"() {
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getPoint(2, 3).get()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint != board.getPoint(2, 4).get()
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getPoint(2, 2).get()
			BoardPoint bomberman = board.getBomberman()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getPoint(2, 2).get()
			BoardPoint bomberman = board.getBomberman()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
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
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint wall = board.getDestroyableWalls().iterator().next()
			BoardPoint bomberman = board.getBomberman()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, wall)
		then:
			nextPoint == bomberman
	}

	def "not move #3"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼
				☼       ☼
				☼ ☼☼☼☼  ☼
				☼ ☼☺ ☼  ☼
				☼   2   ☼
				☼#☼ 1☼  ☼
				☼ ☼☼☼☼  ☼
				☼       ☼
				☼☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint destinationPoint = board.getDestroyableWalls().iterator().next()
			BoardPoint bomberman = board.getBomberman()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, destinationPoint)
		then:
			nextPoint == bomberman
	}

	def "situation #1"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼
				☼ ☼     ☼
				☼    #  ☼
				☼  ☺    ☼
				☼ ☼ ☼☼  ☼
				☼ ☼21   ☼
				☼ ☼     ☼
				☼ ☼     ☼
				☼☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint destinationPoint = board.getDestroyableWalls().iterator().next()
			BoardPoint bomberman = board.getBomberman()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, destinationPoint)
		then:
			nextPoint == bomberman.shiftRight().get() || nextPoint == bomberman.shiftLeft().get()
	}

	def "situation #2 - near MeatChopper"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼☼☼
				☼         ☼
				☼         ☼
				☼         ☼
				☼         ☼
				☼         ☼
				☼ #       ☼
				☼  ☼☼☼☼☼☼ ☼
				☼  & ☺    ☼
				☼       4 ☼
				☼☼☼☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint destinationPoint = board.getDestroyableWalls().iterator().next()
			BoardPoint bomberman = board.getBomberman()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, destinationPoint)
		then:
			nextPoint != bomberman.shiftLeft().get()
	}

	def "situation #3 - near MeatChopper, wall and bomb"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼☼☼☼☼
				☼          ☼
				☼  ☼☼☼☼☼☼☼ ☼
				☼  ☼       ☼
				☼  ☼       ☼
				☼  ☼       ☼
				☼ ☺☼       ☼
				☼# ☼       ☼
				☼& ☼       ☼
				☼ 1☼       ☼
				☼          ☼
				☼☼☼☼☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint destinationPoint = board.getMeatChoppers().iterator().next()
			BoardPoint bomberman = board.getBomberman()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, destinationPoint)
		then:
			nextPoint != bomberman.shiftBottom().get()
	}

	def "move LEFT #2"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼  ☼   ☼
				☼  ☼   ☼
				☼     ☼☼
				☼♥   ☺☼☼
				☼     ☼☼
				☼  ☼   ☼
				☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			def bomberman = board.getBomberman()
			BoardPoint competitor = board.getOtherBombermans().iterator().next()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, competitor)
		then:
			nextPoint == bomberman.shiftLeft().get()
	}

	def "trap move"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼  ☺ #♥☼
				☼  2   ☼
				☼      ☼
				☼      ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			def bomberman = board.getBomberman()
			BoardPoint competitor = board.getPoint(6, 1).get()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, competitor)
		then:
			nextPoint == bomberman.shiftLeft().get()
	}

	def "NOT step in BOOM"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼♥  ҉☺ ☼
				☼҉҉҉҉҉ ☼
				☼ ҉    ☼
				☼      ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			def bomberman = board.getBomberman()
			BoardPoint competitor = board.getOtherBombermans().iterator().next()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, competitor)
		then:
			nextPoint != bomberman.shiftLeft().get() && nextPoint != bomberman.shiftBottom().get()
	}

	def "NOT step in DEAD_OTHER_BOMBERMAN"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼♥  ♣☺ ☼
				☼ ☼☼☼♣ ☼
				☼      ☼
				☼      ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			def bomberman = board.getBomberman()
			BoardPoint competitor = board.getOtherBombermans().iterator().next()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, competitor)
		then:
			nextPoint != bomberman.shiftLeft().get() && nextPoint != bomberman.shiftBottom().get()
	}

	def "don't stay if can't reach the goal"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼  ♥   ☼
				☼☼☼☼☼☼☼☼
				☼      ☼
				☼  ☻   ☼
				☼      ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			def bomberman = board.getBomberman()
			BoardPoint competitor = board.getOtherBombermans().iterator().next()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, competitor)
		then:
			nextPoint != bomberman
	}

	def "stay on the same place"() {
		given:
			String map = '''
				☼☼☼☼☼☼☼☼
				☼  ♥   ☼
				☼      ☼
				☼   1  ☼
				☼  ☻   ☼
				☼ 1    ☼
				☼      ☼
				☼☼☼☼☼☼☼☼
			'''
			GameBoard board = new GameBoard(Utils.clearMap(map))
			def bomberman = board.getBomberman()
			BoardPoint competitor = board.getOtherBombermans().iterator().next()
		when:
			BoardPoint nextPoint = pathCalculator.getNextPoint(board, competitor)
		then:
			nextPoint == bomberman
	}

//	def "stay on the line"() {
//		given:
//			String map = '''
//				☼☼☼☼☼☼☼☼
//				☼      ☼
//				☼☼☼☼☼☼☼☼
//				☼   ☺ 3☼
//				☼ ♥   2☼
//				☼  ☼☼☼☼☼
//				☼      ☼
//				☼☼☼☼☼☼☼☼
//			'''
//			GameBoard board = new GameBoard(Utils.clearMap(map))
//			def bomberman = board.getBomberman()
//			BoardPoint competitor = board.getOtherBombermans().iterator().next()
//		when:
//			BoardPoint nextPoint = pathCalculator.getNextPoint(board, competitor)
//		then:
//			nextPoint == bomberman.shiftLeft().get()
//	}
}
