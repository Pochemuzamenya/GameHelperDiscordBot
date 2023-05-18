package org.filatov.service.command;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.handlers.CommandHandler;
import org.filatov.handlers.util.UserInputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс реализует регистрацию новых команд методом {@link MessageCommandExecutor#register(String, CommandHandler)},
 * вызывается он в {@link CommandHandler} default методом {@link CommandHandler#registerCommand(MessageCommandExecutor)}.
 * <p>
 * Получилось что то среднее между шаблоном <a href="https://java-design-patterns.com/patterns/registry/">реестр</a>
 * и <a href="https://java-design-patterns.com/patterns/strategy/">стратегия</a>.
 *
 */
@Service
@RequiredArgsConstructor
public class MessageCommandExecutor {

    private final Logger LOG = LoggerFactory.getLogger(MessageCommandExecutor.class);

    private final UserInputHandler inputHandler;

    private Map<String, CommandHandler> commands = new HashMap<>();

    public void register(String commandName, CommandHandler handler) {
        commands.put(commandName, handler);
    }

    /**
     * Так как подпишемся мы только в классе {@link org.filatov.BotConfiguration} обрабатываем null здесь
     *
     */
    public Mono<String> execute(Message message) {
        Mono<String> commandName = inputHandler.getCommandName(message.getContent());

        return commandName
                .map(name -> commands.get(name))
                .flatMap(handler -> handler.handleCommand(message))
                .onErrorResume(e -> Mono.just("Команда не найдена"));
    }
}
