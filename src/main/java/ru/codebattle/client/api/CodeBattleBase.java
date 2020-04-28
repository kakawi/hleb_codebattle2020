package ru.codebattle.client.api;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

@Slf4j
public abstract class CodeBattleBase extends WebSocketClient {
    private final String responsePrefix = "board=";

    public CodeBattleBase(String url) throws URISyntaxException {
        super(new URI(url.replace("http", "ws").replace("board/player/", "ws?user=").replace("?code=", "&code=")));
    }

    @Setter
    @Getter
    private boolean shouldExit = false;

    protected abstract String doMove(GameBoard bomberManGameBoard);

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.info("Connection established");
    }

    @Override
    public void onMessage(String message) {
        log.info("A Message is come from Server");
//        if (!shouldExit) {
            if (!message.startsWith(responsePrefix)) {
                log.error(String.format("Something strange is happening on the server... Response:%n%s", message));
                shouldExit = true;
            } else {
                String boardString = message.substring(responsePrefix.length());
                log.info("Before `doMove`");
                String action = doMove(new GameBoard(boardString));
                log.info("Action: " + action);
                send(action);
                log.info("An Action is sent");
                log.info("");
            }
//        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.warn("### disconnected ###");
    }

    @Override
    public void onError(Exception ex) {
        log.error("### error ###", ex);
    }
}
