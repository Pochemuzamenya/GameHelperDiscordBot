package org.filatov.handlers;

import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.filatov.service.command.SlashCommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public interface CommandHandler {

    Logger LOG = LoggerFactory.getLogger(CommandHandler.class);

    Mono<Void> handleCommand(ChatInputInteractionEvent event);

    String getMyCommandName();

    @Autowired
    default void registerCommand(SlashCommandExecutor executor){
        executor.register(getMyCommandName(), this);
        LOG.info("Message-command {} registered", getMyCommandName());
    }


}
