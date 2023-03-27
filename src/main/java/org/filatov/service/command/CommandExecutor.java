package org.filatov.service.command;

import discord4j.core.object.entity.Message;
import org.filatov.handlers.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandExecutor {

    private final Logger LOG = LoggerFactory.getLogger(CommandExecutor.class);

    private Map<String, CommandHandler> commands = new HashMap<>();

    public void register(String commandName, CommandHandler handler) {
        commands.put(commandName, handler);
    }

    public Mono<String> execute(Message message) {
        CommandHandler commandHandler = commands.get(message.getContent());
        LOG.info(commandHandler.getMyCommandName(), "executed");
        return commandHandler.handleCommand(message);
    }
}
