package ru.codebattle.client.handled

import ru.codebattle.client.api.BoardElement
import spock.lang.Specification
import spock.lang.Unroll

class HandledGameBoardTest extends Specification {
	def "size"() {
		String map = '''
				☼☼☼☼
				☼  ☼
				☼  ☼
				☼☼☼☼
			'''
		given:
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
		expect:
			board.size() == 4
	}

	@Unroll
	def "bomberman as #bombermanSymbol"() {
		String symbol = bombermanSymbol.getSymbol()
		String map = """
				☼☼☼☼
				☼ ${symbol}☼
				☼  ☼
				☼☼☼☼
			"""
		given:
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
		expect:
			board.getBomberman() == new TypedBoardPoint(2, 1, bombermanSymbol, board)
		where:
			bombermanSymbol             | _
			BoardElement.BOMBERMAN      | _
			BoardElement.BOMB_BOMBERMAN | _
			BoardElement.DEAD_BOMBERMAN | _

	}
}
