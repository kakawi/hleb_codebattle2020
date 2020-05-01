package ru.codebattle.client.handled


import ru.codebattle.client.api.BoardElement
import ru.codebattle.client.api.Direction
import ru.codebattle.client.api.GameBoard
import ru.codebattle.client.api.TurnAction
import ru.codebattle.client.api.BoardPoint
import ru.codebattle.client.handled.calculator.PathCalculator
import ru.codebattle.client.handled.calculator.realise.BombermanPathCalculator
import ru.codebattle.client.handled.calculator.realise.BombermanPointCalculator
import ru.codebattle.client.handled.strategy.move.NearestBombermanStrategy
import ru.codebattle.client.handled.strategy.move.NearestWallStrategy
import ru.codebattle.client.handled.strategy.move.DestinationStrategyManager
import ru.codebattle.client.handled.strategy.plant.BombsController
import ru.codebattle.client.handled.strategy.plant.DiagonalPlantStrategy
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager
import ru.codebattle.client.handled.strategy.plant.SimplePlantStrategy
import ru.codebattle.client.history.History
import spock.lang.Specification
import spock.lang.Unroll

class TickHandlerTest extends Specification {

	private DestinationStrategyManager strategyManager = new DestinationStrategyManager([new NearestWallStrategy()])
	private PathCalculator pathCalculator = new PathCalculator(new BombermanPathCalculator(new BombermanPointCalculator()))
	private PlantStrategiesManager plantStrategiesManager = new PlantStrategiesManager([new SimplePlantStrategy()])
	private BombsController bombsController = Mock()
	private History history = Mock()

	@Unroll
	def "escape explosion #bomb"() {
		String bomlSymbol = bomb.getSymbol()
		String map = """
				☼☼☼☼☼
				☼${bomlSymbol}☺ ☼
				☼   ☼
				☼  #☼
				☼☼☼☼☼
			"""
		given:
			GameBoard board = new GameBoard(Utils.clearMap(map))
			TickHandlerImpl tickHandler = new TickHandlerImpl(strategyManager, pathCalculator, plantStrategiesManager, bombsController, history)
		when:
			TurnAction action = tickHandler.handle(board)
		then:
			action.getDirection() == Direction.DOWN
		where:
			bomb | _
			BoardElement.BOMB_TIMER_4 | _
			BoardElement.BOMB_TIMER_3 | _
			BoardElement.BOMB_TIMER_2 | _
			BoardElement.BOMB_TIMER_1 | _
	}

	def "chain of explosions"() {
		String map = """
				☼☼☼☼☼☼
				☼13  ☼
				☼ 4☺ ☼
				☼    ☼
				☼    ☼
				☼☼☼☼☼☼
			"""
		given:
			GameBoard board = new GameBoard(Utils.clearMap(map))
			BoardPoint bombermanPoint = board.getBomberman()
			ExplosionInfo explosionInfo = bombermanPoint.getExplosionInfo()
			TickHandlerImpl tickHandler = new TickHandlerImpl(strategyManager, pathCalculator, plantStrategiesManager, bombsController, history)
		when:
			TurnAction action = tickHandler.handle(board)
			explosionInfo.getStatus() == ExplosionStatus.NEXT_TICK
		then:
			action.getDirection() == Direction.DOWN
	}

	def "situation #1"() {
		String map = """
☼☼☼☼☼☼☼☼☼
☼♥      ☼
☼☼ ☼☼☼3☺☼
☼☼ ♥ ♥1 ☼
☼☼    ☼ ☼
☼☼    ☼ ☼
☼☼    ☼♥☼
☼☼    ☼ ☼
☼☼☼☼☼☼☼☼☼
			"""
		given:
			GameBoard board = new GameBoard(Utils.clearMap(map))
			DestinationStrategyManager strategyManager = new DestinationStrategyManager(Arrays.asList(
					new NearestBombermanStrategy()
			));
			PlantStrategiesManager plantStrategiesManager = new PlantStrategiesManager(Arrays.asList(
					new DiagonalPlantStrategy()
			));
			TickHandlerImpl tickHandler = new TickHandlerImpl(strategyManager, pathCalculator, plantStrategiesManager, bombsController, history)
		when:
			TurnAction action = tickHandler.handle(board)
		then:
			action.getDirection() == Direction.UP
	}

}
