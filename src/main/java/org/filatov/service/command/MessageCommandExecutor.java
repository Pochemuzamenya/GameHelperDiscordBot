package org.filatov.service.command;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.handlers.CommandHandler;
import org.filatov.handlers.UserInputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageCommandExecutor {

    private final Logger LOG = LoggerFactory.getLogger(MessageCommandExecutor.class);

    private final UserInputHandler inputHandler;

    private Map<String, CommandHandler> commands = new HashMap<>();

    public void register(String commandName, CommandHandler handler) {
        commands.put(commandName, handler);
    }

    public Mono<String> execute(Message message) {
        Mono<String> commandName = inputHandler.getCommandName(message.getContent());

        //TODO зарефакторить блокировку
        CommandHandler commandHandler = commands.get(commandName.block());

        if (commandHandler != null) {
            return commandHandler.handleCommand(message);
        }

        return Mono.just("Команда не найдена");
    }
}
