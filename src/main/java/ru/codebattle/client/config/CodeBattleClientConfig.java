package ru.codebattle.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.codebattle.client.CodeBattleClient;

import java.net.URISyntaxException;

@Configuration
public class CodeBattleClientConfig {

	@Value("${server.address:#{null}}")
	private String serverAddress;

	@Bean
	public CodeBattleClient codeBattleClient() throws URISyntaxException {
		if (serverAddress == null) {
			throw new IllegalStateException("Please set `SERVER_ADDRESS`");
		}
		return new CodeBattleClient(serverAddress);
	}
}
