package org.filatov;


import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import discord4j.rest.RestClient;
import org.filatov.appcommand.AppCommand;
import org.filatov.listener.EventListener;
import org.filatov.service.command.appcommand.AppCommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BotConfiguration.class);

    /**
     * Получаем токен на портале разработчика discord
     */
    @Value("${token}")
    private String token;

    @Autowired
    private AppCommandDispatcher dispatcher;

    /**
     * Discord отправляет информацию в режиме реального времени подключенным клиентам через события (Event)
     * <p>
     * Слушать события приложение может с помощью метода {@link discord4j.core.GatewayDiscordClient#on(java.lang.Class)}
     * <p>
     * Spring инжектит нам лист @param eventListeners
     * @return {@link GatewayDiscordClient}
     * @param <T> для собственных типов событий
     */
    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners, List<AppCommand> commands) {

        GatewayDiscordClient client = null;

        try {
            client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();

            assert client != null;

            for (AppCommand appCommand : commands) {
                dispatcher.register(appCommand.command(), discordRestClient(client));
                log.info("команда: {} зарегестрирована", appCommand.command().name());
            }

            client.getEventDispatcher().on(ReadyEvent.class)
                    .subscribe(event -> {
                        User self = event.getSelf();
                        log.info("Logged in as {}#{}", self.getUsername(), self.getDiscriminator());
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

    @Bean
    public RestClient discordRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }
}