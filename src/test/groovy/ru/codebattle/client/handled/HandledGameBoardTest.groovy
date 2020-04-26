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

	@Unroll
	def "find #elementType in radius #radius"() {
		String map = """
				☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼
				☼                     ☼
				☼                     ☼
				☼                     ☼
				☼                     ☼
				☼                     ☼
				☼                     ☼
				☼          &          ☼
				☼                     ☼
				☼        ☼ ♥   &      ☼
				☼     &   ☼  #        ☼
				☼       ♥ ♥☺♠ ♥       ☼
				☼     ♥  #  ☼♠        ☼
				☼       ♥  #☼    &    ☼
				☼                     ☼
				☼         #           ☼
				☼   &                 ☼
				☼                     ☼
				☼                     ☼
				☼                     ☼
				☼                     ☼
				☼                     ☼
				☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼
			"""
		given:
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint bomberman = board.getBomberman()
			int x1 = bomberman.getX() - radius
			int x2 = bomberman.getX() + radius
			int y1 = bomberman.getY() - radius
			int y2 = bomberman.getY() + radius
		when:
			Collection<TypedBoardPoint> elements = board.getElementsInRectangle(x1, y1, x2, y2, elementType as BoardElement[])
			Collection<TypedBoardPoint> elementsFromCenter = board.getElementsInRectangle(bomberman.getX(), bomberman.getY(), radius, elementType as BoardElement[])
		then:
			elements.size() == countOfElements
			elements == elementsFromCenter
		where:
			elementType                                                       | radius | countOfElements
			BoardElement.DESTROY_WALL                                         | 1      | 0
			BoardElement.DESTROY_WALL                                         | 2      | 3
			[BoardElement.OTHER_BOMB_BOMBERMAN, BoardElement.OTHER_BOMBERMAN] | 1      | 2
			BoardElement.BOMBERMAN                                            | 100    | 1
	}
}
