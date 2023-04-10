package org.filatov;


import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import org.filatov.listener.EventListener;
import org.filatov.service.OpenDotaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BotConfiguration.class);


    private OpenDotaService statService;

    @Value("${token}")
    private String token;


    public Map<String, Mono<String>> commands() {
        Map<String, Mono<String>> commandsMap = new HashMap<>();
        commandsMap.put("!winlose", statService.winLose());
        commandsMap.put("!wardmap", statService.wardMap());
        commandsMap.put("!wordcloud", statService.wordCloud());
        commandsMap.put("!refresh", statService.refresh());
        return commandsMap;
    }

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {

        GatewayDiscordClient client = null;

        try {

            client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();

            assert client != null;

            client.getEventDispatcher().on(ReadyEvent.class)
                    .subscribe(event -> {
                        User self = event.getSelf();
                        log.info("Успешный логин как {}#{}", self.getUsername(), self.getDiscriminator());
                        //log.info(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                    });

            for (EventListener<T> listener : eventListeners) {
                client.on(listener.getEventType())
                        .flatMap(listener::execute)
                        .onErrorResume(listener::handleError)
                        .subscribe();
            }
            client.onDisconnect().block();
        } catch (Exception exception) {
            log.error("Be sure to use a valid bot token!", exception);
        }

        return client;
    }
}