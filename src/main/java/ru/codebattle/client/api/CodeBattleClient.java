package ru.codebattle.client.api;

import java.util.function.Function;

import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.api.CodeBattleBase;
import ru.codebattle.client.api.GameBoard;

import java.net.URISyntaxException;

public class CodeBattleClient extends CodeBattleBase {

    private Function<GameBoard, TurnAction> callback;

    public CodeBattleClient(String url) throws URISyntaxException {
        super(url);
    }

    public void run(Function<GameBoard, TurnAction> callback) {
        this.callback = callback;
        connect();
    }

    @Override
    protected String doMove(GameBoard gameBoard) {
        TurnAction action = callback.apply(gameBoard);
        return action.toString();
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void initiateExit()
    {
        setShouldExit(true);
    }
}
