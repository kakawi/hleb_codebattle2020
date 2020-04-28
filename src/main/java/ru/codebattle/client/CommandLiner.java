package ru.codebattle.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.codebattle.client.api.CodeBattleClient;
import ru.codebattle.client.handled.TickHandler;

@Component
public class CommandLiner implements CommandLineRunner {

	private final CodeBattleClient codeBattleClient;
	private final TickHandler tickHandler;

	public CommandLiner(CodeBattleClient codeBattleClient, TickHandler tickHandler) {
		this.codeBattleClient = codeBattleClient;
		this.tickHandler = tickHandler;
	}

	@Override
	public void run(String... args) throws Exception {
		codeBattleClient.run(tickHandler::handle);
	}
}
