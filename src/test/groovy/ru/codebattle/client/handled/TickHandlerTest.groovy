package ru.codebattle.client.handled


import ru.codebattle.client.api.BoardElement
import ru.codebattle.client.api.Direction
import ru.codebattle.client.api.TurnAction
import ru.codebattle.client.handled.calculator.PathCalculator
import ru.codebattle.client.handled.calculator.PathValueCalculator
import ru.codebattle.client.handled.strategy.move.NearestBombermanStrategy
import ru.codebattle.client.handled.strategy.move.NearestWallStrategy
import ru.codebattle.client.handled.strategy.move.DestinationStrategyManager
import ru.codebattle.client.handled.strategy.plant.BombsController
import ru.codebattle.client.handled.strategy.plant.DiagonalPlantStrategy
import ru.codebattle.client.handled.strategy.plant.PlantStrategiesManager
import ru.codebattle.client.handled.strategy.plant.SimplePlantStrategy
import spock.lang.Specification
import spock.lang.Unroll

class TickHandlerTest extends Specification {

	private DestinationStrategyManager strategyManager = new DestinationStrategyManager([new NearestWallStrategy()])
	private PathCalculator pathCalculator = new PathCalculator(new PathValueCalculator())
	private PlantStrategiesManager plantStrategiesManager = new PlantStrategiesManager([new SimplePlantStrategy()])
	private BombsController bombsController = Mock()

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
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TickHandler tickHandler = new TickHandler(strategyManager, pathCalculator, plantStrategiesManager, bombsController)
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
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			TypedBoardPoint bombermanPoint = board.getBomberman()
			ExplosionInfo explosionInfo = bombermanPoint.getExplosionInfo()
			TickHandler tickHandler = new TickHandler(strategyManager, pathCalculator, plantStrategiesManager, bombsController)
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
			HandledGameBoard board = new HandledGameBoard(Utils.clearMap(map))
			DestinationStrategyManager strategyManager = new DestinationStrategyManager(Arrays.asList(
					new NearestBombermanStrategy()
			));
			PlantStrategiesManager plantStrategiesManager = new PlantStrategiesManager(Arrays.asList(
					new DiagonalPlantStrategy()
			));
			TickHandler tickHandler = new TickHandler(strategyManager, pathCalculator, plantStrategiesManager, bombsController)
		when:
			TurnAction action = tickHandler.handle(board)
		then:
			action.getDirection() == Direction.UP
	}

}
