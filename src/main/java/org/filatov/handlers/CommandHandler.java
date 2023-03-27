package org.filatov.handlers;

import discord4j.core.object.entity.Message;
import org.filatov.service.command.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public interface CommandHandler {

    Logger LOG = LoggerFactory.getLogger(CommandHandler.class);

    Mono<String> handleCommand(Message message);

    String getMyCommandName();

    @Autowired
    default void registerCommand(CommandExecutor executor){
        executor.register(getMyCommandName(), this);
    }


}
