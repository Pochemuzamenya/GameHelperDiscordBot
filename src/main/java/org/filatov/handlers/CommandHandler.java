package org.filatov.handlers;

import discord4j.core.object.entity.Message;
import org.filatov.service.command.MessageCommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public interface CommandHandler<T> {

    Logger LOG = LoggerFactory.getLogger(CommandHandler.class);

    Mono<Void> handleCommand(T event);

    String getMyCommandName();

    @Autowired
    default void registerCommand(MessageCommandExecutor executor){
        executor.register(getMyCommandName(), this);
        LOG.info("Message-command {} registered", getMyCommandName());
    }


}
