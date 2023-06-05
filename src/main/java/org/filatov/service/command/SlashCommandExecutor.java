package org.filatov.service.command;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.filatov.handlers.CommandHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class SlashCommandExecutor{

    private Map<String, CommandHandler> commands = new HashMap<>();

    public void register(String commandName, CommandHandler handler) {
        commands.put(commandName, handler);
    }

    public Mono<Void> execute(ChatInputInteractionEvent event) {
        CommandHandler commandHandler = commands.get(event.getCommandName());

        return Mono.from(commandHandler.handleCommand(event));
    }
}
